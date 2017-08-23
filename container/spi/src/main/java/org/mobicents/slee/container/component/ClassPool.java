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

package org.mobicents.slee.container.component;

import java.io.IOException;
import java.io.InputStream;

import javassist.ClassPath;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * Wrapper for javassist class pool. Tracks class made and allows the pool
 * cleanup.
 * 
 * @author martins
 * 
 */
public interface ClassPool {

	/**
	 * @see javassist.ClassPool#appendClassPath(ClassPath)
	 */
	public ClassPath appendClassPath(ClassPath cp);

	/**
	 * cleans up the class pool cache
	 */
	public void clean();

	/**
	 * @see javassist.ClassPool#get(String)
	 */
	public CtClass get(String classname) throws NotFoundException;

	/**
	 * Retrieves the wrapped javassist class pool.
	 * 
	 * @return
	 */
	public javassist.ClassPool getClassPool();

	/**
	 * @see javassist.ClassPool#makeClass(InputStream)
	 * @param classfile
	 * @return
	 * @throws IOException
	 * @throws RuntimeException
	 */
	public CtClass makeClass(InputStream inputStream) throws IOException,
			RuntimeException;

	/**
	 * @see javassist.ClassPool#makeClass(String)
	 */
	public CtClass makeClass(String classname) throws RuntimeException;

	/**
	 * @see javassist.ClassPool#makeInterface(String)
	 * @param interfacename
	 * @return
	 * @throws RuntimeException
	 */
	public CtClass makeInterface(String interfacename) throws RuntimeException;

}
