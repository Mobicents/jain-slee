/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc. and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.mobicents.slee.container;

import org.apache.log4j.Logger;
import org.jboss.msc.service.ServiceController;
import org.restcomm.cluster.MobicentsCluster;
import org.restcomm.cluster.MobicentsClusterFactory;
import org.mobicents.slee.connector.local.SleeConnectionService;
import org.mobicents.slee.container.activity.ActivityContextFactory;
import org.mobicents.slee.container.component.ComponentRepository;
import org.mobicents.slee.container.component.classloading.ReplicationClassLoader;
import org.mobicents.slee.container.component.du.DeployableUnitManagement;
import org.mobicents.slee.container.congestion.CongestionControl;
import org.mobicents.slee.container.deployment.SleeContainerDeployer;
import org.mobicents.slee.container.event.EventContextFactory;
import org.mobicents.slee.container.eventrouter.EventRouter;
import org.mobicents.slee.container.facilities.ActivityContextNamingFacility;
import org.mobicents.slee.container.facilities.TimerFacility;
import org.mobicents.slee.container.facilities.nullactivity.NullActivityContextInterfaceFactory;
import org.mobicents.slee.container.facilities.nullactivity.NullActivityFactory;
import org.mobicents.slee.container.jndi.JndiManagement;
import org.mobicents.slee.container.management.*;
import org.mobicents.slee.container.management.jmx.editors.SleePropertyEditorRegistrator;
import org.mobicents.slee.container.rmi.RmiServerInterface;
import org.mobicents.slee.container.sbbentity.SbbEntityFactory;
import org.mobicents.slee.container.transaction.SleeTransactionManager;

import javax.management.MBeanServer;
import javax.slee.InvalidStateException;
import javax.slee.management.SleeState;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;

/**
 * Implements the SleeContainer. The SleeContainer is the anchor for the SLEE.
 * It is the central location from where all container modules are accessible.
 * 
 * @author F.Moggia
 * @author M. Ranganathan
 * @author Ivelin Ivanov
 * @author Emil Ivov
 * @author Tim Fox
 * @author eduardomartins
 */
public class SleeContainer {

	private final static Logger logger = Logger.getLogger(SleeContainer.class);

	static {
		// Force this property to allow invocation of getters.
		// http://code.google.com/p/restcomm/issues/detail?id=63
		System.setProperty("jmx.invoke.getters", "true");
		// establish the location of restcomm in JBoss AS deploy folder
		/*
        try {
			java.net.URL url = VFSUtils.getCompatibleURL(VFS
					.getRoot(SleeContainer.class.getClassLoader().getResource(
							"..")));
			java.net.URI uri = new java.net.URI(url.toExternalForm()
					.replaceAll(" ", "%20"));
			deployPath = new File(new File(uri).getParent()).getAbsolutePath();
		} catch (Exception e) {
			logger
					.error(
							"Failed to establish path to Restcomm root deployment directory",
							e);
			deployPath = null;
		}
		*/
		// Config JUL logger to use Log4J filter
		Handler[] handlers = java.util.logging.Logger.getLogger("")
				.getHandlers();
		for (Handler handler : handlers)
			if (handler instanceof ConsoleHandler)
				handler.setFilter(new MobicentsLogFilter());
	}

	private final String deployPath;
	
	private static SleeContainer sleeContainer;

	/**
	 * 
	 * @return the full file system path where restcomm.sar is located
	 */
	public String getDeployPath() {
		return deployPath;
	}

	
	private LinkedList<SleeContainerModule> modules = new LinkedList<SleeContainerModule>();
	
	private final SleeTransactionManager sleeTransactionManager;
	private final MobicentsClusterFactory clusterFactory;
	
	private ConcurrentHashMap<CacheType,MobicentsCluster> clustersMap=new ConcurrentHashMap<CacheType, MobicentsCluster>();
	
	// mbean server where the container's mbeans are registred
	private final MBeanServer mbeanServer;
	/** The lifecycle state of the SLEE */
	private SleeState sleeState;

	// the class that actually posts events to the SBBs.
	// This should be made into a facility and registered with jmx and jndi
	// so it can be independently controlled.
	private final EventRouter router;
	// monitor object for sync on management operations
	private final Object managementMonitor = new Object();
	// for external access to slee
	@SuppressWarnings("unused")
	private RmiServerInterface rmiServerInterfaceMBean;
	// component managers
	private final ComponentManagement componentManagement;
	private final ServiceManagement serviceManagement;

	private final ProfileManagement sleeProfileTableManager;
	private final ResourceManagement resourceManagement;
	private final SbbManagement sbbManagement;
	// object pool management
	// non clustered scheduler
	private final ScheduledExecutorService nonClusteredScheduler;
	private static final int NON_CLUSTERED_SCHEDULER_THREADS = 4;
	// slee factories
	private final ActivityContextFactory activityContextFactory;
	private final NullActivityContextInterfaceFactory nullActivityContextInterfaceFactory;

	private final NullActivityFactory nullActivityFactory;

	// slee facilities
	private final ActivityContextNamingFacility activityContextNamingFacility;

	private final AlarmManagement alarmMBeanImpl;

	private final TraceManagement traceMBeanImpl;

	private final TimerFacility timerFacility;

	private final MobicentsUUIDGenerator uuidGenerator;

	private final UsageParametersManagement usageParametersManagement;

	private ReplicationClassLoader replicationClassLoader;

	private final SbbEntityFactory sbbEntityFactory;

	private final EventContextFactory eventContextFactory;

	private final CongestionControl congestionControl;
	
	private final SleeConnectionService sleeConnectionService;
 	
	private final SleeContainerDeployer deployer;
	
	private JndiManagement jndiManagement;

	private ServiceController serviceController;

	/**
	 * Creates a new instance of SleeContainer -- This is called from the
	 * SleeManagementMBean to get the whole thing running.
	 * 
	 */
	public SleeContainer(
			String deployPath, ServiceController serviceController, MBeanServer mBeanServer,
			ComponentManagement componentManagement,
			SbbManagement sbbManagement,
			ServiceManagement serviceManagement,
			ResourceManagement resourceManagement,
			ProfileManagement profileManagement,
			EventContextFactory eventContextFactory,
			EventRouter eventRouter,
			TimerFacility timerFacility,
			ActivityContextFactory activityContextFactory,
			ActivityContextNamingFacility activityContextNamingFacility,
			NullActivityContextInterfaceFactory nullActivityContextInterfaceFactory,
			NullActivityFactory nullActivityFactory,
			RmiServerInterface rmiServerInterface,
			SleeTransactionManager sleeTransactionManager,
			MobicentsClusterFactory clusterFactory,
			ReplicationClassLoader replicationClassLoader,
			AlarmManagement alarmMBeanImpl,
			TraceManagement traceMBeanImpl,
			UsageParametersManagement usageParametersManagement,
			SbbEntityFactory sbbEntityFactory, CongestionControl congestionControl,
			SleeConnectionService sleeConnectionService, SleeContainerDeployer sleeContainerDeployer) throws Exception {
        this.deployPath = deployPath;

		this.serviceController = serviceController;

        this.mbeanServer = mBeanServer;

		this.sleeTransactionManager = sleeTransactionManager;
		addModule(sleeTransactionManager);

		this.componentManagement = componentManagement;
		addModule(componentManagement);

		this.replicationClassLoader = replicationClassLoader;

		this.clusterFactory = clusterFactory;
		for(CacheType currType:CacheType.values())
			this.clustersMap.put(currType, this.clusterFactory.getCluster(currType.getValue()));
		this.uuidGenerator = new MobicentsUUIDGenerator(this.clustersMap.get(CacheType.ACTIVITIES)
				.getMobicentsCache().isLocalMode());

		this.alarmMBeanImpl = alarmMBeanImpl;
		addModule(alarmMBeanImpl);

		this.traceMBeanImpl = traceMBeanImpl;
		addModule(traceMBeanImpl);

		this.usageParametersManagement = usageParametersManagement;
		addModule(usageParametersManagement);
				
		this.activityContextFactory = activityContextFactory;
		addModule(activityContextFactory);

		this.activityContextNamingFacility = activityContextNamingFacility;
		addModule(activityContextNamingFacility);

		this.nullActivityFactory = nullActivityFactory;
		addModule(nullActivityFactory);

		this.nullActivityContextInterfaceFactory = nullActivityContextInterfaceFactory;
		addModule(nullActivityContextInterfaceFactory);

		this.timerFacility = timerFacility;
		addModule(timerFacility);

		this.eventContextFactory = eventContextFactory;
		addModule(eventContextFactory);

		this.router = eventRouter;
		addModule(router);

		this.rmiServerInterfaceMBean = rmiServerInterface;
		addModule(rmiServerInterface);

		this.nonClusteredScheduler = new ScheduledThreadPoolExecutor(
				NON_CLUSTERED_SCHEDULER_THREADS);

		this.sbbEntityFactory = sbbEntityFactory;
		addModule(sbbEntityFactory);

		this.congestionControl = congestionControl;
		addModule(congestionControl);
		
		this.sleeConnectionService = sleeConnectionService;
		addModule(sleeConnectionService);

		// these must be the last ones to be notified of startup, and in this
		// order
		this.resourceManagement = resourceManagement;
		addModule(resourceManagement);

		this.sbbManagement = sbbManagement;
		addModule(sbbManagement);

		this.sleeProfileTableManager = profileManagement;
		addModule(sleeProfileTableManager);

		this.serviceManagement = serviceManagement;
		addModule(serviceManagement);	
		
		this.deployer = sleeContainerDeployer;
		addModule(sleeContainerDeployer);	

	}

	private void addModule(SleeContainerModule module) {
		if(module != null) {
			modules.add(module);
			module.setSleeContainer(this);
		}
	}

	// GETTERS -- managers

	public JndiManagement getJndiManagement() {
		return this.jndiManagement;
	}
	
	public void setJndiManagement(JndiManagement jndiManagement) {
		this.jndiManagement = jndiManagement;
	}

	public ServiceController getServiceController() {
		return this.serviceController;
	}
	
	/**
	 * dumps the container state as a string, useful for debug/profiling
	 * 
	 * @return
	 */
	public String dumpState() {
		return componentManagement + "\n" + resourceManagement + "\n"
				+ timerFacility + "\n" + traceMBeanImpl + "\n"
				+ sleeProfileTableManager + "\n" + activityContextFactory
				+ "\n" + activityContextNamingFacility + "\n"
				+ nullActivityFactory + "\n" 
				+ getEventRouter() + "\n"
				+ getEventContextFactory() + "\n"
				+ getTransactionManager() + "\n";
				//+ cluster.getMobicentsCache().getCacheContent();
	}

	public ActivityContextFactory getActivityContextFactory() {
		return this.activityContextFactory;
	}

	public ActivityContextNamingFacility getActivityContextNamingFacility() {
		return activityContextNamingFacility;
	}

	/**
	 * 
	 * @return
	 */
	public AlarmManagement getAlarmManagement() {
		return alarmMBeanImpl;
	}

	/**
	 * The cache which manages the container's HA and FT data
	 * 
	 * @return
	 */
	public MobicentsClusterFactory getClusterFactory() {
		return clusterFactory;
	}

	public MobicentsCluster getCluster(CacheType type) {
		return clustersMap.get(type);
	}
	
	/**
	 * 
	 * @return
	 */
	public SleeContainerDeployer getDeployer() {
		return deployer;
	}
	
	/**
	 * @return the componentManagement
	 */
	public ComponentManagement getComponentManagement() {
		return componentManagement;
	}

	/**
	 * retrieves the container's component repository implementation
	 * 
	 * @return
	 */
	public ComponentRepository getComponentRepository() {
		return componentManagement.getComponentRepository();
	}

	/**
	 * 
	 * @return
	 */
	public CongestionControl getCongestionControl() {
		return congestionControl;
	}
	
	/**
	 * Retrieves the deployable unit manager
	 * 
	 * @return
	 */
	public DeployableUnitManagement getDeployableUnitManagement() {
		return componentManagement.getDeployableUnitManagement();
	}

	/**
	 * @return the eventContextFactory
	 */
	public EventContextFactory getEventContextFactory() {
		return eventContextFactory;
	}

	/**
	 * the container's event router
	 */
	public EventRouter getEventRouter() {
		return this.router;
	}

	/**
	 * object for synchronization on management operations that (un)install
	 * components
	 */
	public Object getManagementMonitor() {
		return managementMonitor;
	}

	// GETTERS -- slee factories

	/**
	 * Return the MBeanServer that the SLEEE is registers with in the current
	 * JVM
	 */
	public MBeanServer getMBeanServer() {
		return mbeanServer;
	}

	/**
	 * Retrieves the container's non clustered scheduler.
	 * 
	 * @return the nonClusteredScheduler
	 */
	public ScheduledExecutorService getNonClusteredScheduler() {
		return nonClusteredScheduler;
	}

	public NullActivityContextInterfaceFactory getNullActivityContextInterfaceFactory() {
		return this.nullActivityContextInterfaceFactory;
	}

	/**
	 * 
	 * @return
	 */
	public SleeConnectionService getSleeConnectionService() {
		return this.sleeConnectionService;
	}
	
	// GETTERS -- slee facilities

	public NullActivityFactory getNullActivityFactory() {
		return this.nullActivityFactory;
	}

	/**
	 * Retrieves the class loader used in data replication.
	 * 
	 * @return
	 */
	public ReplicationClassLoader getReplicationClassLoader() {
		return replicationClassLoader;
	}

	/**
	 * manages (un)install of resource adaptors
	 * 
	 * @return
	 */
	public ResourceManagement getResourceManagement() {
		return resourceManagement;
	}

	/**
	 * @return the sbbEntityFactory
	 */
	public SbbEntityFactory getSbbEntityFactory() {
		return sbbEntityFactory;
	}

	// GETTERS -- slee runtime

	/**
	 * manages (un)install of sbbs
	 * 
	 * @return
	 */
	public SbbManagement getSbbManagement() {
		return sbbManagement;
	}

	/**
	 * manages (un)install of services
	 * 
	 * @return
	 */
	public ServiceManagement getServiceManagement() {
		return this.serviceManagement;
	}

	public ProfileManagement getSleeProfileTableManager() {
		return this.sleeProfileTableManager;
	}

	/**
	 * Get the current state of the Slee Container
	 * 
	 * @return SleeState
	 */
	public SleeState getSleeState() {
		return this.sleeState;
	}

	/**
	 * 
	 * @return
	 */
	public TimerFacility getTimerFacility() {
		return timerFacility;
	}

	/**
	 * 
	 * @return
	 */
	public TraceManagement getTraceManagement() {
		return traceMBeanImpl;
	}

	/**
	 * Get the transaction manager
	 * 
	 * @throws
	 */
	public SleeTransactionManager getTransactionManager() {
		return sleeTransactionManager;
	}

	// JNDI RELATED

	/**
	 * 
	 * @return
	 */
	public UsageParametersManagement getUsageParametersManagement() {
		return usageParametersManagement;
	}

	/**
	 * a UUID generator for the container
	 */
	public MobicentsUUIDGenerator getUuidGenerator() {
		return uuidGenerator;
	}

	/**
	 * Initiates the SLEE container
	 */
	public void initSlee() throws InvalidStateException {
		if (sleeState != null) {
			throw new InvalidStateException("slee in "+sleeState+" state");
		}
		// slee init
		beforeModulesInitialization();
		for (Iterator<SleeContainerModule> i = modules.iterator(); i
			.hasNext();) {
			i.next().sleeInitialization();
		}
		afterModulesInitialization();
		sleeState = SleeState.STOPPED;		
	}
	
	/**
	 * Shutdown of the SLEE container
	 * @throws InvalidStateException
	 */
	public void shutdownSlee() throws InvalidStateException {
		if (sleeState != SleeState.STOPPED) {
			throw new InvalidStateException("slee in "+sleeState+" state");
		}
		// slee shutdown
		beforeModulesShutdown();
		for (Iterator<SleeContainerModule> i = modules
				.descendingIterator(); i.hasNext();) {
			i.next().sleeShutdown();
		}
		afterModulesShutdown();
		sleeState = null;
	}

    /**
     *
     * @param request
     * @throws InvalidStateException
     */
	public void setSleeState(final SleeStateChangeRequest request) throws InvalidStateException {

		final SleeState newState = request.getNewState();
		
		if (logger.isDebugEnabled()) {
			logger.debug("Changing state: " + sleeState + " -> " + newState);
		}

		validateStateTransition(sleeState, newState);

		// change state
		SleeState oldState = this.sleeState;
		this.sleeState = newState;
		request.stateChanged(oldState);
		
		// notify modules and complete request
		final Runnable task = new Runnable() {			
			public void run() {
				try {
					if (newState == SleeState.STARTING) {
						for (Iterator<SleeContainerModule> i = modules.iterator(); i.hasNext();) {
							i.next().sleeStarting();
						}						
					}
					else if (newState == SleeState.RUNNING) {
						for (Iterator<SleeContainerModule> i = modules.iterator(); i.hasNext();) {
							i.next().sleeRunning();
						}
					}
					else if (newState == SleeState.STOPPING) {
						for (Iterator<SleeContainerModule> i = modules.descendingIterator(); i.hasNext();) {
							i.next().sleeStopping();
						}
					}
					else if (newState == SleeState.STOPPED) {
						if(logger.isInfoEnabled()) {
							logger.info(dumpState());
						}
						for (Iterator<SleeContainerModule> i = modules.descendingIterator(); i.hasNext();) {
							i.next().sleeStopped();
						}			
					}	
				}
				catch (Throwable e) {
					logger.error(e.getMessage(),e);
				}
				request.requestCompleted();
			}							
		};
		if (request.isBlockingRequest()) {
			task.run();
		}
		else {
			final ExecutorService executorService = Executors.newSingleThreadExecutor();
			try {
				executorService.submit(task);				
			}
			catch (Throwable e) {
				logger.error(e.getMessage(),e);
			}
			executorService.shutdown();
		}		
	}
	
	/**
	 * Ensures the standard SLEE lifecycle.
	 * 
	 * @param oldState
	 * @param newState
	 * @throws InvalidStateException
	 */
	private void validateStateTransition(SleeState oldState, SleeState newState)
			throws InvalidStateException {
		if (oldState == SleeState.STOPPED) {
			if (newState == SleeState.STARTING) {
				return;
			}		
		} else if (oldState == SleeState.STARTING) {
			if (newState == SleeState.RUNNING || newState == SleeState.STOPPING) {
				return;
			}
		} else if (oldState == SleeState.RUNNING) {
			if (newState == SleeState.STOPPING) {
				return;
			}
		} else if (oldState == SleeState.STOPPING) {
			if (newState == SleeState.STOPPED) {
				return;
			}
		}
		throw new InvalidStateException("illegal slee state transition: " + oldState + " -> "+ newState);
	}
	
	public void beforeModulesInitialization() {
		sleeContainer = this;
		// Register property editors for the composite SLEE types so
		// jmx console can pass it as an argument.
		// TODO: ensure this is of any use for all containers, i.e. standard jdk jmx console or our web console takes advantage from it, if this was just a jboss as5 feature then let's move it to "build" modules 
		new SleePropertyEditorRegistrator().register();
	}
	
	public void afterModulesInitialization() {
		// start cluster
		Iterator<MobicentsCluster> clustersIterator=clustersMap.values().iterator();
		while(clustersIterator.hasNext())
			clustersIterator.next().startCluster();
	}
	
	public void beforeModulesShutdown() {
		// stop the cluster
		clusterFactory.stop();
	}
	
	public void afterModulesShutdown() {		
		/*
		try {
			unregisterWithJndi();
			Context ctx = new InitialContext();
			Util.unbind(ctx, JVM_ENV + CTX_SLEE);
		}
		catch (Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}*/
	}

	/**
	 * Return the SleeContainer instance registered in the JVM scope of JNDI
	 */
	public static SleeContainer lookupFromJndi() {
		return sleeContainer;		
	}

}