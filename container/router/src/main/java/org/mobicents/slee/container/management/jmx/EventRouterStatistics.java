/*
 * TeleStax, Open Source Cloud Communications
 * Copyright 2011-2017, Telestax Inc and individual contributors
 * by the @authors tag.
 *
 * This program is free software: you can redistribute it and/or modify
 * under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package org.mobicents.slee.container.management.jmx;

import javax.slee.EventTypeID;
import javax.slee.management.ManagementException;

import org.mobicents.slee.container.eventrouter.EventRouter;

/**
 * 
 * @author martins
 * 
 */
public class EventRouterStatistics implements
		EventRouterStatisticsMBean {

	private final EventRouter eventRouter;

	public EventRouterStatistics(EventRouter eventRouter) {
		this.eventRouter = eventRouter;
	}

	private org.mobicents.slee.container.eventrouter.stats.EventRouterStatistics getEventRouterStatistics()
			throws ManagementException {
		if (eventRouter == null) {
			throw new ManagementException("router not set");
		}
		if (eventRouter.getEventRouterStatistics() == null) {
			throw new ManagementException("router stats not available");
		}
		return eventRouter.getEventRouterStatistics();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean
	 * #getActivitiesMapped()
	 */
	public int getActivitiesMapped() throws ManagementException {
		return getEventRouterStatistics().getActivitiesMapped();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean
	 * #getActivitiesMapped(int)
	 */
	public int getActivitiesMapped(int executor) throws ManagementException {
		return getEventRouterStatistics().getActivitiesMapped(executor);
	}

	/*
	 * (non-Javadoc)
	 * @see org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean#getAverageEventRoutingTime()
	 */
	public long getAverageEventRoutingTime() throws ManagementException {
		return getEventRouterStatistics().getAverageEventRoutingTime();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean#getAverageEventRoutingTime(javax.slee.EventTypeID)
	 */
	public long getAverageEventRoutingTime(EventTypeID eventTypeID)
			throws ManagementException {
		return getEventRouterStatistics().getAverageEventRoutingTime(eventTypeID);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean#getAverageEventRoutingTime(int)
	 */
	public long getAverageEventRoutingTime(int executor)
			throws ManagementException {
		return getEventRouterStatistics().getAverageEventRoutingTime(executor);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean#getAverageEventRoutingTime(int, javax.slee.EventTypeID)
	 */
	public long getAverageEventRoutingTime(int executor, EventTypeID eventTypeID)
			throws ManagementException {
		return getEventRouterStatistics().getAverageEventRoutingTime(executor,eventTypeID);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean
	 * #getEventsRouted(javax.slee.EventTypeID)
	 */
	public long getEventsRouted(EventTypeID eventTypeID)
			throws ManagementException {
		return getEventRouterStatistics().getEventsRouted(eventTypeID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean
	 * #getEventsRouted(int, javax.slee.EventTypeID)
	 */
	public long getEventsRouted(int executor, EventTypeID eventTypeID)
			throws ManagementException {
		return getEventRouterStatistics()
				.getEventsRouted(executor, eventTypeID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean
	 * #getExecutedTasks()
	 */
	public long getExecutedTasks() throws ManagementException {
		return getEventRouterStatistics().getExecutedTasks();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean
	 * #getExecutedTasks(int)
	 */
	public long getExecutedTasks(int executor) throws ManagementException {
		return getEventRouterStatistics().getExecutedTasks(executor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean
	 * #getExecutingTime(int)
	 */
	public long getExecutingTime(int executor) throws ManagementException {
		return getEventRouterStatistics().getExecutedTasks(executor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean
	 * #getIdleTime(int)
	 */
	public long getIdleTime(int executor) throws ManagementException {
		return getEventRouterStatistics().getIdleTime(executor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean
	 * #getMiscTasksExecuted()
	 */
	public long getMiscTasksExecuted() throws ManagementException {
		return getEventRouterStatistics().getMiscTasksExecuted();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean
	 * #getMiscTasksExecuted(int)
	 */
	public long getMiscTasksExecuted(int executor) throws ManagementException {
		return getEventRouterStatistics().getMiscTasksExecuted(executor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean
	 * #getMiscTasksExecutingTime(int)
	 */
	public long getMiscTasksExecutingTime(int executor)
			throws ManagementException {
		return getEventRouterStatistics().getMiscTasksExecutingTime(executor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean
	 * #getRoutingTime(int, javax.slee.EventTypeID)
	 */
	public long getRoutingTime(int executor, EventTypeID eventTypeID)
			throws ManagementException {
		return getEventRouterStatistics().getRoutingTime(executor, eventTypeID);
	}

	/*
	 * (non-Javadoc)
	 * @see org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean#getWorkingQueueSize()
	 */
	@Override
	public int getWorkingQueueSize() throws ManagementException {
		return getEventRouterStatistics().getWorkingQueueSize();
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean#getWorkingQueueSize(int)
	 */
	@Override
	public int getWorkingQueueSize(int executor) throws ManagementException {
		return getEventRouterStatistics().getWorkingQueueSize(executor);
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.mobicents.slee.container.management.jmx.EventRouterStatisticsMBean#printAllStats()
	 */
	public String printAllStats() throws ManagementException {
		return getEventRouterStatistics().toString();
	}
}
