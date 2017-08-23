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

package org.mobicents.slee.container.profile;

import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * Management interface to configure the profile object pools.
 * @author martins
 *
 */
public interface ProfileObjectPoolManagementMBean {

	/**
	 * Object name where this MBean is accessible 
	 */
	public static final String MBEAN_NAME = "org.mobicents.slee:service=ProfileObjectPoolManagement";
	
	/**
	 * Retrieves current config parameter.
	 * @see GenericObjectPool.Config#maxActive
	 * @param maxActive
	 */
	public int getMaxActive();
	
	/**
	 * Changes current config parameter.
	 * @see GenericObjectPool.Config#maxActive
	 * @param maxActive
	 */
	public void setMaxActive(int maxActive);
	
	/**
	 * Retrieves current config parameter.
	 * @see GenericObjectPool.Config#maxIdle
	 */
	public int getMaxIdle();
	
	/**
	 * Changes current config parameter.
	 * @see GenericObjectPool.Config#maxIdle
	 * @param maxIdle
	 */
	public void setMaxIdle(int maxIdle);
	
	/**
	 * Retrieves current config parameter.
	 * @see GenericObjectPool.Config#minIdle
	 */
	public int getMinIdle();
	
	/**
	 * Changes current config parameter.
	 * @see GenericObjectPool.Config#minIdle
	 * @param minIdle
	 */
	public void setMinIdle(int minIdle);
	
	/**
	 * Retrieves current config parameter.
	 * @see GenericObjectPool.Config#maxWait
	 */
	public long getMaxWait();
	
	/**
	 * Changes current config parameter.
	 * @see GenericObjectPool.Config#maxWait
	 * @param maxWait
	 */
	public void setMaxWait(long maxWait);
	
	/**
	 * Retrieves current config parameter.
	 * @see GenericObjectPool.Config#minEvictableIdleTimeMillis
	 */
	public long getMinEvictableIdleTimeMillis();
	
	/**
	 * Changes current config parameter.
	 * @see GenericObjectPool.Config#minEvictableIdleTimeMillis
	 * @param minEvictableIdleTimeMillis
	 */
	public void setMinEvictableIdleTimeMillis(long minEvictableIdleTimeMillis);
	
	/**
	 * Retrieves current config parameter.
	 * @see GenericObjectPool.Config#numTestsPerEvictionRun
	 */
	public int getNumTestsPerEvictionRun();
	
	/**
	 * Changes current config parameter.
	 * @see GenericObjectPool.Config#numTestsPerEvictionRun
	 * @param numTestsPerEvictionRun
	 */
	public void setNumTestsPerEvictionRun(int numTestsPerEvictionRun);
	
	/**
	 * Retrieves current config parameter.
	 * @see GenericObjectPool.Config#testOnBorrow
	 */
	public boolean getTestOnBorrow();
	
	/**
	 * Changes current config parameter.
	 * @see GenericObjectPool.Config#testOnBorrow
	 * @param testOnBorrow
	 */
	public void setTestOnBorrow(boolean testOnBorrow);
	
	/**
	 * Retrieves current config parameter.
	 * @see GenericObjectPool.Config#testOnReturn
	 */
	public boolean getTestOnReturn();
	
	/**
	 * Changes current config parameter.
	 * @see GenericObjectPool.Config#testOnReturn
	 * @param testOnReturn
	 */
	public void setTestOnReturn(boolean testOnReturn);
	
	/**
	 * Retrieves current config parameter.
	 * @see GenericObjectPool.Config#testWhileIdle
	 */
	public boolean getTestWhileIdle();
	
	/**
	 * Changes current config parameter.
	 * @see GenericObjectPool.Config#testWhileIdle
	 * @param testWhileIdle
	 */
	public void setTestWhileIdle(boolean testWhileIdle);
	
	/**
	 * Retrieves current config parameter.
	 * @see GenericObjectPool.Config#timeBetweenEvictionRunsMillis
	 */
	public long getTimeBetweenEvictionRunsMillis();
	
	/**
	 * Changes current config parameter.
	 * @see GenericObjectPool.Config#timeBetweenEvictionRunsMillis
	 * @param timeBetweenEvictionRunsMillis
	 */
	public void setTimeBetweenEvictionRunsMillis(long timeBetweenEvictionRunsMillis);
	
	/**
	 * Retrieves current config parameter.
	 * @see GenericObjectPool.Config#whenExhaustedAction
	 */
	public byte getWhenExhaustedAction();
	
	/**
	 * Changes current config parameter.
	 * @see GenericObjectPool.Config#whenExhaustedAction
	 * @param whenExhaustedAction
	 */
	public void setWhenExhaustedAction(byte whenExhaustedAction);
	
	/**
	 * Triggers process to reconfig all current pools
	 */
	public void reconfig();
}
