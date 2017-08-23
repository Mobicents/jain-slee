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

/**
 * 
 */
package org.mobicents.slee.runtime.usage;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import javax.slee.management.NotificationSource;
import javax.slee.usage.SampleStatistics;

import org.mobicents.slee.container.SleeContainer;
import org.mobicents.slee.container.management.jmx.UsageMBeanImpl;
import org.mobicents.slee.runtime.usage.local.UsageMBeanLocalData;

/**
 * Base class for impl of usage parameters, it offers basic ops for params.
 * 
 * @author baranowb
 * 
 */
public abstract class AbstractUsageParameterSet {

	private static final String _DEFAULT_SET_NAME_ = "";
	
	private final UsageMBeanData usageMbeanData;
	
	private NotificationSource notificationSource;
	private String parameterSetName;
	private UsageMBeanImpl usageMBean;
	
	public AbstractUsageParameterSet(NotificationSource notificationSource, String parameterSetName, SleeContainer sleeContainer) {

		// checks
		if (notificationSource == null) {
			throw new NullPointerException("Notification Source must not be null.");
		}
		if (sleeContainer == null) {
			throw new NullPointerException("Slee Container must not be null.");
		}

		// set
		this.notificationSource = notificationSource;
		if (parameterSetName == null) {
			this.parameterSetName = _DEFAULT_SET_NAME_;
		} else {
			this.parameterSetName = parameterSetName;
		}
		
		// now init.
		this.usageMbeanData = new UsageMBeanLocalData(notificationSource,this.parameterSetName);
		this.usageMbeanData.create();
		
		Collection<String> paramNames = this.getParameterNames();
		for (String parameterName : paramNames) {
			if (this.usageMbeanData.getParameter(parameterName) == null) {
				this.usageMbeanData.setParameter(parameterName, new UsageParameter(this.notificationSource,parameterName));
			}
		}

	}

	protected abstract Collection<String> getParameterNames();

	public UsageMBeanImpl getUsageMBean() {
		return usageMBean;
	}

	public void setUsageMBean(UsageMBeanImpl usageMBean) {
		this.usageMBean = usageMBean;
	}

	public NotificationSource getNotificationSource() {
		return notificationSource;
	}

	public String getParameterSetName() {
		if (this.parameterSetName.equals(_DEFAULT_SET_NAME_))
			return null;
		return parameterSetName;
	}

	public void remove() {
		this.usageMbeanData.remove();
	}

	public void reset() {
		// XXX: we use strings as names(wow what a discovery), so underlying
		// impl must return String collection.
		Collection<String> parameterNames = this.usageMbeanData.getParameterNames();
		for (String parameterName : parameterNames) {
			resetParameter(parameterName);
		}
	}

	private void resetParameter(String parameterName) {
		UsageParameter parameter = this.usageMbeanData.getParameter(parameterName);
		if (parameter != null) {
			parameter.reset();
			this.usageMbeanData.setParameter(parameterName, parameter);

		}

	}

	public void incrementParameter(String parameterName, long incValue) {
		UsageParameter usageParameter = this.usageMbeanData.getParameter(parameterName);
		usageParameter.increment(incValue);
		this.usageMbeanData.setParameter(parameterName, usageParameter);
		this.usageMBean.sendUsageNotification(usageParameter.getValue(), usageParameter.getCount(), getParameterSetName(), parameterName, true);
	}

	public long getParameter(String parameterName, boolean reset) {
		UsageParameter usageParameter = this.usageMbeanData.getParameter(parameterName);

		long tmpValue = usageParameter.getValue();
		if (reset) {
			usageParameter.reset();
			this.usageMbeanData.setParameter(parameterName, usageParameter);
		}
		return tmpValue;
	}

	public void sampleParameter(String parameterName, long sample) {
		UsageParameter usageParameter = this.usageMbeanData.getParameter(parameterName);
		usageParameter.sample(sample);
		this.usageMbeanData.setParameter(parameterName, usageParameter);
		this.usageMBean.sendUsageNotification(sample, usageParameter.getCount(), getParameterSetName(), parameterName, false);
	}

	public SampleStatistics getParameterSampleStatistics(String parameterName, boolean reset) {

		UsageParameter usageParameter = this.usageMbeanData.getParameter(parameterName);

		SampleStatistics ss = new SampleStatistics(usageParameter.getCount(), usageParameter.getMin(), usageParameter.getMax(), usageParameter.getMean());
		if (reset) {
			usageParameter.reset();
			this.usageMbeanData.setParameter(parameterName, usageParameter);
		}
		return ss;

	}

	/**
	 * Convenience method to create new instance of concrete impl class of
	 * {@link AbstractUsageParameterSet}.
	 * 
	 * @param concreteClass
	 * @param notificationSource
	 * @param parameterSetName
	 * @param mcCache
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static final AbstractUsageParameterSet newInstance(Class<?> concreteClass, NotificationSource notificationSource, String parameterSetName, SleeContainer sleeContainer)
			throws SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException, IllegalAccessException, InvocationTargetException {
		Constructor<?> constructor = concreteClass.getConstructor(NotificationSource.class, String.class, SleeContainer.class);
		AbstractUsageParameterSet instance = (AbstractUsageParameterSet) constructor.newInstance(notificationSource, parameterSetName, sleeContainer);
		return instance;
	}

}
