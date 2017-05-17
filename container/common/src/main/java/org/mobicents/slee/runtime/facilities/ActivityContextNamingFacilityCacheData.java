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

package org.mobicents.slee.runtime.facilities;

import org.infinispan.tree.Fqn;
import org.infinispan.tree.Node;
import org.restcomm.cache.CacheData;
import org.restcomm.cache.FqnWrapper;
import org.restcomm.cluster.MobicentsCluster;

import javax.slee.facilities.NameAlreadyBoundException;
import javax.slee.facilities.NameNotBoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * Proxy object for ac naming facility data management through JBoss Cache
 * 
 * @author martins
 * 
 */

public class ActivityContextNamingFacilityCacheData extends CacheData {

	/**
	 * the name of the cache node that holds all data
	 */
	public static final String CACHE_NODE_NAME = "aci-names";

	private static final Boolean CACHE_NODE_MAP_KEY = Boolean.TRUE;

	/**
	 * 
	 * @param cluster
	 */
	public ActivityContextNamingFacilityCacheData(MobicentsCluster cluster) {
		super(FqnWrapper.fromElementsWrapper(CACHE_NODE_NAME),
				cluster.getMobicentsCache());
	}

	/**
	 * Binds the specified aci name with the specified activity context handle
	 * @param ach
	 * @param name
	 * @throws NameAlreadyBoundException
	 */
	public void bindName(Object ach, String name)
			throws NameAlreadyBoundException {
		if (this.hasChildNode(name)) {
			throw new NameAlreadyBoundException("name already bound");
		} else {
			this.putChildNodeValue(FqnWrapper.fromElementsWrapper(name), CACHE_NODE_MAP_KEY, ach);
		}
	}

	/**
	 * Unbinds the specified aci name with the specified activity context id
	 * @param name
	 * @return
	 * @throws NameNotBoundException
	 */
	public Object unbindName(String name) throws NameNotBoundException {
		final Node childNode = (Node) this.getChildNode(name);
		if (childNode == null) {
			throw new NameNotBoundException("name not bound");
		} else {
			final Object ach = this.getChildNodeValue(name, CACHE_NODE_MAP_KEY);
			this.removeChildNode(name);
			return ach;
		}
	}

	/**
	 * Lookup of the activity context id bound to the specified aci name
	 * @param name
	 * @return
	 */
	public Object lookupName(String name) {
		final Node childNode = (Node) this.getChildNode(name);
		if (childNode == null) {
			return null;
		} else {
			return this.getChildNodeValue(name, CACHE_NODE_MAP_KEY);
		}
	}

	/**
	 * Retrieves a map of the bindings. Key is the aci name and Value is the activity context handle
	 * @return
	 */
	public Map getNameBindings() {

		Map result = new HashMap();
		Node childNode = null;
		Object name = null;
		for (Object obj : getNode().getChildren()) {
			childNode = (Node) obj;
			name = childNode.getFqn().getLastElement();
			result.put(name, childNode.get(CACHE_NODE_MAP_KEY));
		}
		return result;
	}
}
