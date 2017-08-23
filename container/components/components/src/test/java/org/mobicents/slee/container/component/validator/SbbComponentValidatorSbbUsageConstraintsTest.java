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

/**
 * Start time:17:07:31 2009-01-31<br>
 * Project: restcomm-jainslee-server-core<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com">baranowb - Bartosz Baranowski
 *         </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
package org.mobicents.slee.container.component.validator;

import java.util.List;

import javax.slee.Sbb;

import org.mobicents.slee.container.component.SbbComponentImpl;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.SbbDescriptorFactoryImpl;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.SbbDescriptorImpl;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.TCUtilityClass;
import org.mobicents.slee.container.component.validator.sbb.abstracts.usage.UsageInterfaceToFewMethods;
import org.mobicents.slee.container.component.validator.sbb.abstracts.usage.UsageInterfaceToManyMethods;
import org.mobicents.slee.container.component.validator.sbb.abstracts.usage.UsageThrowsOnGetterInterface;
import org.mobicents.slee.container.component.validator.sbb.abstracts.usage.UsageThrowsOnSetterInterface;
import org.mobicents.slee.container.component.validator.sbb.abstracts.usage.UsageWrongAccesorLevelInterface;
import org.mobicents.slee.container.component.validator.sbb.abstracts.usage.UsageWrongSampleReturnTypeInterface;
import org.mobicents.slee.container.component.validator.sbb.abstracts.usage.UsageWrongSetterPrameterInterface;

/**
 * Start time:17:07:31 2009-01-31<br>
 * Project: restcomm-jainslee-server-core<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com">baranowb - Bartosz Baranowski
 *         </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class SbbComponentValidatorSbbUsageConstraintsTest extends TCUtilityClass {

	public static final String _SBB_JAR_ONE_11_USAGE_CONSTRAINTS_OK = "xml/validator/sbb/usage/sbb-jar-one-SbbConstraintsUsageOk_1_1.xml";

	public void testSbbOne11UsageConstraintsOk() throws Exception {

		List<SbbDescriptorImpl> specs = new SbbDescriptorFactoryImpl().parse(super.getFileStream(_SBB_JAR_ONE_11_USAGE_CONSTRAINTS_OK));
		final SbbDescriptorImpl descriptor = specs.get(0);
		SbbComponentImpl component = new SbbComponentImpl(descriptor);
		component.setAbstractSbbClass(Thread.currentThread().getContextClassLoader().loadClass(
				descriptor.getSbbAbstractClass().getSbbAbstractClassName()));
		component.setUsageParametersInterface(Thread.currentThread().getContextClassLoader().loadClass(
				descriptor.getSbbUsageParametersInterface().getUsageParametersInterfaceName()));
		SbbComponentValidator validator = new SbbComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateSbbUsageParameterInterface(ClassUtils.getAbstractMethodsFromClass(component.getAbstractSbbClass()), ClassUtils
				.getAbstractMethodsFromSuperClasses(component.getAbstractSbbClass()));

		assertTrue("Sbb class has not been validated", b);

	}

	public void testSbbOne11ToManyUsageMethodDefined() throws Exception {
		
		List<SbbDescriptorImpl> specs = new SbbDescriptorFactoryImpl().parse(super.getFileStream(_SBB_JAR_ONE_11_USAGE_CONSTRAINTS_OK));
		final SbbDescriptorImpl descriptor = specs.get(0);
		SbbComponentImpl component = new SbbComponentImpl(descriptor);
		component.setAbstractSbbClass(SbbUsageInterfaceToManyMethods.class);
		component.setUsageParametersInterface(UsageInterfaceToManyMethods.class);
		SbbComponentValidator validator = new SbbComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateSbbUsageParameterInterface(ClassUtils.getAbstractMethodsFromClass(component.getAbstractSbbClass()), ClassUtils
				.getAbstractMethodsFromSuperClasses(component.getAbstractSbbClass()));

		assertFalse("Sbb class has been validated", b);

	}

	public void testSbbOne11WrongSetterPrameter() throws Exception {
	
		List<SbbDescriptorImpl> specs = new SbbDescriptorFactoryImpl().parse(super.getFileStream(_SBB_JAR_ONE_11_USAGE_CONSTRAINTS_OK));
		final SbbDescriptorImpl descriptor = specs.get(0);
		SbbComponentImpl component = new SbbComponentImpl(descriptor);
		component.setAbstractSbbClass(SbbUsageWrongSetterPrameterInterface.class);
		component.setUsageParametersInterface(UsageWrongSetterPrameterInterface.class);
		SbbComponentValidator validator = new SbbComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateSbbUsageParameterInterface(ClassUtils.getAbstractMethodsFromClass(component.getAbstractSbbClass()), ClassUtils
				.getAbstractMethodsFromSuperClasses(component.getAbstractSbbClass()));

		assertFalse("Sbb class has been validated", b);

	}

	public void testSbbOne11ToFewUsageMethodDefined() throws Exception {

		List<SbbDescriptorImpl> specs = new SbbDescriptorFactoryImpl().parse(super.getFileStream(_SBB_JAR_ONE_11_USAGE_CONSTRAINTS_OK));
		final SbbDescriptorImpl descriptor = specs.get(0);
		SbbComponentImpl component = new SbbComponentImpl(descriptor);
		component.setAbstractSbbClass(SbbUsageInterfaceToFewMethods.class);
		component.setUsageParametersInterface(UsageInterfaceToFewMethods.class);
		SbbComponentValidator validator = new SbbComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateSbbUsageParameterInterface(ClassUtils.getAbstractMethodsFromClass(component.getAbstractSbbClass()), ClassUtils
				.getAbstractMethodsFromSuperClasses(component.getAbstractSbbClass()));

		assertFalse("Sbb class has been validated", b);

	}

	public void testSbbOne11ThrowsOnGetter() throws Exception {
		// setter is increment or sample method
	
		List<SbbDescriptorImpl> specs = new SbbDescriptorFactoryImpl().parse(super.getFileStream(_SBB_JAR_ONE_11_USAGE_CONSTRAINTS_OK));
		final SbbDescriptorImpl descriptor = specs.get(0);
		SbbComponentImpl component = new SbbComponentImpl(descriptor);
		component.setAbstractSbbClass(SbbUsageThrowsOnGetterInterface.class);
		component.setUsageParametersInterface(UsageThrowsOnGetterInterface.class);
		SbbComponentValidator validator = new SbbComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateSbbUsageParameterInterface(ClassUtils.getAbstractMethodsFromClass(component.getAbstractSbbClass()), ClassUtils
				.getAbstractMethodsFromSuperClasses(component.getAbstractSbbClass()));

		assertFalse("Sbb class has been validated", b);

	}

	public void testSbbOne11ThrowsOnSetter() throws Exception {
		// setter is increment or sample method
	
		List<SbbDescriptorImpl> specs = new SbbDescriptorFactoryImpl().parse(super.getFileStream(_SBB_JAR_ONE_11_USAGE_CONSTRAINTS_OK));
		final SbbDescriptorImpl descriptor = specs.get(0);
		SbbComponentImpl component = new SbbComponentImpl(descriptor);
		component.setAbstractSbbClass(SbbUsageThrowsOnSetterInterface.class);
		component.setUsageParametersInterface(UsageThrowsOnSetterInterface.class);
		SbbComponentValidator validator = new SbbComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateSbbUsageParameterInterface(ClassUtils.getAbstractMethodsFromClass(component.getAbstractSbbClass()), ClassUtils
				.getAbstractMethodsFromSuperClasses(component.getAbstractSbbClass()));

		assertFalse("Sbb class has been validated", b);

	}

	public void testSbbOne11WrongSampleReturnType() throws Exception {
		// setter is increment or sample method
	
		List<SbbDescriptorImpl> specs = new SbbDescriptorFactoryImpl().parse(super.getFileStream(_SBB_JAR_ONE_11_USAGE_CONSTRAINTS_OK));
		final SbbDescriptorImpl descriptor = specs.get(0);
		SbbComponentImpl component = new SbbComponentImpl(descriptor);
		component.setAbstractSbbClass(SbbUsageWrongSampleReturnTypeInterface.class);
		component.setUsageParametersInterface(UsageWrongSampleReturnTypeInterface.class);
		SbbComponentValidator validator = new SbbComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateSbbUsageParameterInterface(ClassUtils.getAbstractMethodsFromClass(component.getAbstractSbbClass()), ClassUtils
				.getAbstractMethodsFromSuperClasses(component.getAbstractSbbClass()));

		assertFalse("Sbb class has been validated", b);

	}

	public void testSbbOne11UsageSetterWrongLevelInterface() throws Exception {
		// setter is increment or sample method

		List<SbbDescriptorImpl> specs = new SbbDescriptorFactoryImpl().parse(super.getFileStream(_SBB_JAR_ONE_11_USAGE_CONSTRAINTS_OK));
		final SbbDescriptorImpl descriptor = specs.get(0);
		SbbComponentImpl component = new SbbComponentImpl(descriptor);
		component.setAbstractSbbClass(SbbUsageSetterWrongLevelInterface.class);
		component.setUsageParametersInterface(UsageSetterWrongLevelInterface.class);
		SbbComponentValidator validator = new SbbComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateSbbUsageParameterInterface(ClassUtils.getAbstractMethodsFromClass(component.getAbstractSbbClass()), ClassUtils
				.getAbstractMethodsFromSuperClasses(component.getAbstractSbbClass()));

		assertFalse("Sbb class has been validated", b);

	}

	// methods from interfaces are always public?
	public void _testSbbOne11UsageWrongAccesorLevelInterface() throws Exception {
		// setter is increment or sample method

		List<SbbDescriptorImpl> specs = new SbbDescriptorFactoryImpl().parse(super.getFileStream(_SBB_JAR_ONE_11_USAGE_CONSTRAINTS_OK));
		final SbbDescriptorImpl descriptor = specs.get(0);
		SbbComponentImpl component = new SbbComponentImpl(descriptor);
		component.setAbstractSbbClass(SbbUsageWrongAccesorLevelInterface.class);
		component.setUsageParametersInterface(UsageWrongAccesorLevelInterface.class);
		SbbComponentValidator validator = new SbbComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateSbbUsageParameterInterface(ClassUtils.getAbstractMethodsFromClass(component.getAbstractSbbClass()), ClassUtils
				.getAbstractMethodsFromSuperClasses(component.getAbstractSbbClass()));

		assertFalse("Sbb class has been validated", b);

	}

}

abstract class SbbUsageInterfaceToManyMethods implements Sbb {
	public abstract UsageInterfaceToManyMethods getDefaultSbbUsageParameterSet();
}

abstract class SbbUsageWrongSetterPrameterInterface implements Sbb {
	public abstract UsageWrongSetterPrameterInterface getDefaultSbbUsageParameterSet();
}

abstract class SbbUsageThrowsOnGetterInterface implements Sbb {
	public abstract UsageThrowsOnGetterInterface getDefaultSbbUsageParameterSet();
}

abstract class SbbUsageThrowsOnSetterInterface implements Sbb {
	public abstract UsageThrowsOnSetterInterface getDefaultSbbUsageParameterSet();
}

abstract class SbbUsageInterfaceToFewMethods implements Sbb {
	public abstract UsageInterfaceToFewMethods getDefaultSbbUsageParameterSet();
}

abstract class SbbUsageWrongSampleReturnTypeInterface implements Sbb {
	public abstract UsageWrongSampleReturnTypeInterface getDefaultSbbUsageParameterSet();
}

abstract class SbbUsageSetterWrongLevelInterface implements Sbb {
	public abstract UsageSetterWrongLevelInterface getDefaultSbbUsageParameterSet();
}

abstract class SbbUsageWrongAccesorLevelInterface implements Sbb {
	public abstract UsageWrongAccesorLevelInterface getDefaultSbbUsageParameterSet();
}
