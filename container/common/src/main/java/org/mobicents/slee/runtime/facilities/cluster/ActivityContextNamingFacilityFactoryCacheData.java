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

package org.mobicents.slee.runtime.facilities.cluster;

import java.util.Map;

import org.restcomm.cluster.MobicentsCluster;
import org.mobicents.slee.container.activity.ActivityContextHandle;

/**
 * 
 * Proxy object for activity context factory data management through JBoss Cache
 * 
 * @author martins
 * 
 */

public class ActivityContextNamingFacilityFactoryCacheData {

	private MobicentsCluster cluster;
	
	/**
	 * 
	 * @param cluster
	 */
	public ActivityContextNamingFacilityFactoryCacheData(MobicentsCluster cluster) {
		this.cluster=cluster;
	}

	/**
	 * Retrieves a set containing all activity context naming in the factory's
	 * cache data
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,ActivityContextHandle> getNameBindings() {
		return (Map<String,ActivityContextHandle>)this.cluster.getMobicentsCache().getAllElements();		
	}	
}