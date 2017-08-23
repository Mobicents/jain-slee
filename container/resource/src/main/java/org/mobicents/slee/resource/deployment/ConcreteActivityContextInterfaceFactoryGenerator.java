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

/*
 * Created on Mar 14, 2005
 * 
 * The Open SLEE Project
 * 
 * A SLEE for the People
 * 
 * The source code contained in this file is in in the public domain.          
 * It can be used in any project or product without prior permission, 	      
 * license or royalty payments. There is no claim of correctness and
 * NO WARRANTY OF ANY KIND provided with this code.
 */
package org.mobicents.slee.resource.deployment;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtMethod;
import javassist.CtNewMethod;

import javax.slee.resource.ResourceAdaptorTypeID;

import org.apache.log4j.Logger;
import org.mobicents.slee.container.SleeContainer;
import org.mobicents.slee.container.component.ClassPool;
import org.mobicents.slee.container.component.ratype.ResourceAdaptorTypeComponent;
import org.mobicents.slee.container.deployment.ConcreteClassGeneratorUtils;
import org.mobicents.slee.resource.AbstractActivityContextInterfaceFactory;

/**
 * Generator for the concrete aci factory for an ra type
 * @author martins
 */
public class ConcreteActivityContextInterfaceFactoryGenerator {

    private static Logger logger= Logger.getLogger(ConcreteActivityContextInterfaceFactoryGenerator.class);
    
    private final ResourceAdaptorTypeComponent component;

    public ConcreteActivityContextInterfaceFactoryGenerator(ResourceAdaptorTypeComponent component) {
    	this.component = component;        
    }
    
    /**
	 * Create a constructor. This method simply records the input parameters in
	 * appropriately named fields.
	 * 
	 * @param
	 * @param classes
	 */
    private void createConstructor(CtClass concreteClass, CtClass sleeContainerClass, CtClass resourceAdaptorTypeIDClass)
            throws Exception {

        CtConstructor ctCons = new CtConstructor(new CtClass[]{sleeContainerClass,resourceAdaptorTypeIDClass}, concreteClass);
        ctCons.setBody("{ super($1,$2); }");
        concreteClass.addConstructor(ctCons);

    }
    
    public void generateClass() throws Exception {
    	
    	if(component.getActivityContextInterfaceFactoryInterface() == null) {
    		return;
    	}
    	
    	ClassPool classPool = component.getClassPool();
       
        String interfaceName = component.getActivityContextInterfaceFactoryInterface().getName();
        CtClass interfaceCtClass = classPool.get(interfaceName);
                        	
        // make the class
		String concreteClassName = interfaceName + "Impl";
		if (logger.isDebugEnabled()) {
        	logger.debug("generating "+concreteClassName);
        }
		CtClass concreteCtClass = classPool.makeClass(concreteClassName);
        // set interface
        ConcreteClassGeneratorUtils.createInterfaceLinks(concreteCtClass,new CtClass[]{interfaceCtClass});
        // set super class
        CtClass superCtClass = classPool.get(AbstractActivityContextInterfaceFactory.class.getName());
        concreteCtClass.setSuperclass(superCtClass);
        // create constructor
        this.createConstructor(concreteCtClass, classPool.get(SleeContainer.class
                .getName()),classPool.get(ResourceAdaptorTypeID.class
                        .getName()));
        // generate methods        
        for (CtMethod method : interfaceCtClass.getMethods()) {
        	if (method.getName().equals("getActivityContextInterface")) {
        		CtMethod concreteMethod = CtNewMethod.copy(method,concreteCtClass, null);
            	concreteMethod.setBody("{ return super.getACI($1); }");
            	concreteCtClass.addMethod(concreteMethod);
        	}
        }
        // write file
        String deploymentPathStr = component.getDeploymentDir().getAbsolutePath();
        concreteCtClass.writeFile(deploymentPathStr);
        logger.debug("Writing file " + concreteClassName);
        // load class into component        
        component.setActivityContextInterfaceFactoryConcreteClass(Thread.currentThread().getContextClassLoader().loadClass(concreteCtClass.getName()));
        // and defrost in javassist pool
        concreteCtClass.defrost();
    }
    
}

