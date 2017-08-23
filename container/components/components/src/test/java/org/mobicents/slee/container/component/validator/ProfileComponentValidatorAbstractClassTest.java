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

import org.mobicents.slee.container.component.ProfileSpecificationComponentImpl;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.ProfileSpecificationDescriptorFactoryImpl;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.ProfileSpecificationDescriptorImpl;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.TCUtilityClass;
import org.mobicents.slee.container.component.validator.profile.abstrakt.ProfileAbstractClass10LackLifeCycle;
import org.mobicents.slee.container.component.validator.profile.abstrakt.ProfileAbstractClassConcreteUsageParametersAccess;
import org.mobicents.slee.container.component.validator.profile.abstrakt.ProfileAbstractClassDeclareConcreteCMP;
import org.mobicents.slee.container.component.validator.profile.abstrakt.ProfileAbstractClassDefaultConstructorThrows;
import org.mobicents.slee.container.component.validator.profile.abstrakt.ProfileAbstractClassImplementingProfileLocalObject;
import org.mobicents.slee.container.component.validator.profile.abstrakt.ProfileAbstractClassLackLifeCycle;
import org.mobicents.slee.container.component.validator.profile.abstrakt.ProfileAbstractClassNoDefaultConstructor;
import org.mobicents.slee.container.component.validator.profile.abstrakt.ProfileAbstractClassNoUsageParametersAccess;
import org.mobicents.slee.container.component.validator.profile.abstrakt.ProfileAbstractClassNotImplementingCMP;
import org.mobicents.slee.container.component.validator.profile.abstrakt.ProfileAbstractClassNotImplementingManagementInterface;
import org.mobicents.slee.container.component.validator.profile.abstrakt.ProfileAbstractClassNotImplementingProfileLocalMethod;

/**
 * Start time:17:07:31 2009-01-31<br>
 * Project: restcomm-jainslee-server-core<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com">baranowb - Bartosz Baranowski
 *         </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class ProfileComponentValidatorAbstractClassTest extends TCUtilityClass {

	public static final String _PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS = "xml/validator/profile/abstrakt/profile-spec-jar-one.xml";

	public static final String _PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS_10 = "xml/validator/profile/abstrakt/profile-spec-jar-one10.xml";
	
	public void testAbstractClassConstraintsOk() throws Exception {
		
		List<ProfileSpecificationDescriptorImpl> specs = new ProfileSpecificationDescriptorFactoryImpl()
		.parse(super.getFileStream(_PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS));

		ProfileSpecificationDescriptorImpl descriptor = specs.get(0);
		
		ProfileSpecificationComponentImpl component = new ProfileSpecificationComponentImpl(
				descriptor);
		component.setProfileCmpInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileCMPInterface()
								.getProfileCmpInterfaceName()));

		component.setProfileAbstractClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileAbstractClass()
								.getProfileAbstractClassName()));

		component.setProfileManagementInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileManagementInterface()));

		component.setProfileLocalInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileLocalInterface()
								.getProfileLocalInterfaceName()));
		component.setUsageParametersInterface(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileUsageParameterInterface()
								.getUsageParametersInterfaceName()));

		ProfileSpecificationComponentValidator validator = new ProfileSpecificationComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateAbstractClass();

		assertTrue("Abstract class class has not been validated", b);

	}
	
	
	
	public void testAbstractClassConstraintsDeclareConcreteCMPMethod() throws Exception {
	
		
		List<ProfileSpecificationDescriptorImpl> specs = new ProfileSpecificationDescriptorFactoryImpl()
		.parse(super.getFileStream(_PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS));

		ProfileSpecificationDescriptorImpl descriptor = specs.get(0);
		
		
		ProfileSpecificationComponentImpl component = new ProfileSpecificationComponentImpl(
				descriptor);
		component.setProfileCmpInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileCMPInterface()
								.getProfileCmpInterfaceName()));

		component.setProfileAbstractClass(ProfileAbstractClassDeclareConcreteCMP.class);

		component.setProfileManagementInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileManagementInterface()));

		component.setProfileLocalInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileLocalInterface()
								.getProfileLocalInterfaceName()));
		component.setUsageParametersInterface(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileUsageParameterInterface()
								.getUsageParametersInterfaceName()));

		ProfileSpecificationComponentValidator validator = new ProfileSpecificationComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateAbstractClass();

		assertFalse("Abstract class class has been validated, it should not - since it declared concrete CMP method", b);

	}
	
	public void testAbstractClassConstraintsNotImplementingCMP() throws Exception {

		
		List<ProfileSpecificationDescriptorImpl> specs = new ProfileSpecificationDescriptorFactoryImpl()
		.parse(super.getFileStream(_PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS));

		ProfileSpecificationDescriptorImpl descriptor = specs.get(0);
		ProfileSpecificationComponentImpl component = new ProfileSpecificationComponentImpl(
				descriptor);
		component.setProfileCmpInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileCMPInterface()
								.getProfileCmpInterfaceName()));

		component.setProfileAbstractClass(ProfileAbstractClassNotImplementingCMP.class);

		component.setProfileManagementInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileManagementInterface()));

		component.setProfileLocalInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileLocalInterface()
								.getProfileLocalInterfaceName()));
		component.setUsageParametersInterface(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileUsageParameterInterface()
								.getUsageParametersInterfaceName()));

		ProfileSpecificationComponentValidator validator = new ProfileSpecificationComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateAbstractClass();

		assertFalse("Abstract class class has been validated, it should not - since it  does not implement CMP interface", b);

	}

	public void testAbstractClassConstraintsNotImplementingManagementInterface() throws Exception {

		
		List<ProfileSpecificationDescriptorImpl> specs = new ProfileSpecificationDescriptorFactoryImpl()
		.parse(super.getFileStream(_PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS));

		ProfileSpecificationDescriptorImpl descriptor = specs.get(0);
		ProfileSpecificationComponentImpl component = new ProfileSpecificationComponentImpl(
				descriptor);
		component.setProfileCmpInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileCMPInterface()
								.getProfileCmpInterfaceName()));

		component.setProfileAbstractClass(ProfileAbstractClassNotImplementingManagementInterface.class);

		component.setProfileManagementInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileManagementInterface()));

		component.setProfileLocalInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileLocalInterface()
								.getProfileLocalInterfaceName()));
		component.setUsageParametersInterface(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileUsageParameterInterface()
								.getUsageParametersInterfaceName()));

		ProfileSpecificationComponentValidator validator = new ProfileSpecificationComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateAbstractClass();

		assertFalse("Abstract class class has been validated, it should not - since it does nto implement management interface", b);

	}
	

	public void testAbstractClassConstraintsImplementingProfileLocalObject() throws Exception {

		List<ProfileSpecificationDescriptorImpl> specs = new ProfileSpecificationDescriptorFactoryImpl()
		.parse(super.getFileStream(_PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS));

		ProfileSpecificationDescriptorImpl descriptor = specs.get(0);
		ProfileSpecificationComponentImpl component = new ProfileSpecificationComponentImpl(
				descriptor);
		component.setProfileCmpInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileCMPInterface()
								.getProfileCmpInterfaceName()));

		component.setProfileAbstractClass(ProfileAbstractClassImplementingProfileLocalObject.class);

		component.setProfileManagementInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileManagementInterface()));

		component.setProfileLocalInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileLocalInterface()
								.getProfileLocalInterfaceName()));
		component.setUsageParametersInterface(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileUsageParameterInterface()
								.getUsageParametersInterfaceName()));

		ProfileSpecificationComponentValidator validator = new ProfileSpecificationComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateAbstractClass();

		assertFalse("Abstract class class has been validated, it should not - since it implements profile local object", b);

	}
	
	public void testAbstractClassConstraintsNotImplementingProfileLocalObjectMethod() throws Exception {

		
		List<ProfileSpecificationDescriptorImpl> specs = new ProfileSpecificationDescriptorFactoryImpl()
		.parse(super.getFileStream(_PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS));

		ProfileSpecificationDescriptorImpl descriptor = specs.get(0);
		ProfileSpecificationComponentImpl component = new ProfileSpecificationComponentImpl(
				descriptor);
		component.setProfileCmpInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileCMPInterface()
								.getProfileCmpInterfaceName()));

		component.setProfileAbstractClass(ProfileAbstractClassNotImplementingProfileLocalMethod.class);

		component.setProfileManagementInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileManagementInterface()));

		component.setProfileLocalInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileLocalInterface()
								.getProfileLocalInterfaceName()));
		component.setUsageParametersInterface(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileUsageParameterInterface()
								.getUsageParametersInterfaceName()));

		ProfileSpecificationComponentValidator validator = new ProfileSpecificationComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateAbstractClass();

		assertFalse("Abstract class class has been validated, it should not - since it does not implement profile local object method", b);

	}
	
	public void testAbstractClassConstraintsNoDefaultConstructor() throws Exception {

		List<ProfileSpecificationDescriptorImpl> specs = new ProfileSpecificationDescriptorFactoryImpl()
		.parse(super.getFileStream(_PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS));

		ProfileSpecificationDescriptorImpl descriptor = specs.get(0);
		ProfileSpecificationComponentImpl component = new ProfileSpecificationComponentImpl(
				descriptor);
		component.setProfileCmpInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileCMPInterface()
								.getProfileCmpInterfaceName()));

		component.setProfileAbstractClass(ProfileAbstractClassNoDefaultConstructor.class);

		component.setProfileManagementInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileManagementInterface()));

		component.setProfileLocalInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileLocalInterface()
								.getProfileLocalInterfaceName()));
		component.setUsageParametersInterface(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileUsageParameterInterface()
								.getUsageParametersInterfaceName()));

		ProfileSpecificationComponentValidator validator = new ProfileSpecificationComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateAbstractClass();

		assertFalse("Abstract class class has been validated, it should not - does not have default no arg consutrctor", b);

	}
	
	public void testAbstractClassConstraintsDefaultConstructorThrows() throws Exception {

		
		List<ProfileSpecificationDescriptorImpl> specs = new ProfileSpecificationDescriptorFactoryImpl()
		.parse(super.getFileStream(_PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS));

		ProfileSpecificationDescriptorImpl descriptor = specs.get(0);
		ProfileSpecificationComponentImpl component = new ProfileSpecificationComponentImpl(
				descriptor);
		component.setProfileCmpInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileCMPInterface()
								.getProfileCmpInterfaceName()));

		component.setProfileAbstractClass(ProfileAbstractClassDefaultConstructorThrows.class);

		component.setProfileManagementInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileManagementInterface()));

		component.setProfileLocalInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileLocalInterface()
								.getProfileLocalInterfaceName()));
		component.setUsageParametersInterface(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileUsageParameterInterface()
								.getUsageParametersInterfaceName()));

		ProfileSpecificationComponentValidator validator = new ProfileSpecificationComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateAbstractClass();

		assertFalse("Abstract class class has been validated, it should not -  default constructor decalre throws clause.", b);

	}
	
	public void testAbstractClassConstraintsNoUsageParameterInterfaceAccessMethod() throws Exception {

		
		List<ProfileSpecificationDescriptorImpl> specs = new ProfileSpecificationDescriptorFactoryImpl()
		.parse(super.getFileStream(_PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS));

		ProfileSpecificationDescriptorImpl descriptor = specs.get(0);
		ProfileSpecificationComponentImpl component = new ProfileSpecificationComponentImpl(
				descriptor);
		component.setProfileCmpInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileCMPInterface()
								.getProfileCmpInterfaceName()));

		component.setProfileAbstractClass(ProfileAbstractClassNoUsageParametersAccess.class);

		component.setProfileManagementInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileManagementInterface()));

		component.setProfileLocalInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileLocalInterface()
								.getProfileLocalInterfaceName()));
		component.setUsageParametersInterface(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileUsageParameterInterface()
								.getUsageParametersInterfaceName()));

		ProfileSpecificationComponentValidator validator = new ProfileSpecificationComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateAbstractClass();

		assertFalse("Abstract class class has been validated, it should not - does not declare methods to access usage.", b);

	}
	
	public void testAbstractClassConstraintsConcreteUsageParameterInterfaceAccessMethod() throws Exception {

		
		List<ProfileSpecificationDescriptorImpl> specs = new ProfileSpecificationDescriptorFactoryImpl()
		.parse(super.getFileStream(_PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS));

		ProfileSpecificationDescriptorImpl descriptor = specs.get(0);
		ProfileSpecificationComponentImpl component = new ProfileSpecificationComponentImpl(
				descriptor);
		component.setProfileCmpInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileCMPInterface()
								.getProfileCmpInterfaceName()));

		component.setProfileAbstractClass(ProfileAbstractClassConcreteUsageParametersAccess.class);

		component.setProfileManagementInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileManagementInterface()));

		component.setProfileLocalInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileLocalInterface()
								.getProfileLocalInterfaceName()));
		component.setUsageParametersInterface(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileUsageParameterInterface()
								.getUsageParametersInterfaceName()));

		ProfileSpecificationComponentValidator validator = new ProfileSpecificationComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateAbstractClass();

		assertFalse("Abstract class class has been validated, it should not - it does declare methods to access usage.", b);

	}
	
	
	public void testAbstractClassConstraintsLackLifeCycle() throws Exception {

		
		List<ProfileSpecificationDescriptorImpl> specs = new ProfileSpecificationDescriptorFactoryImpl()
		.parse(super.getFileStream(_PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS));

		ProfileSpecificationDescriptorImpl descriptor = specs.get(0);
		ProfileSpecificationComponentImpl component = new ProfileSpecificationComponentImpl(
				descriptor);
		component.setProfileCmpInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileCMPInterface()
								.getProfileCmpInterfaceName()));

		component.setProfileAbstractClass(ProfileAbstractClassLackLifeCycle.class);

		component.setProfileManagementInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileManagementInterface()));

		component.setProfileLocalInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileLocalInterface()
								.getProfileLocalInterfaceName()));
		component.setUsageParametersInterface(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileUsageParameterInterface()
								.getUsageParametersInterfaceName()));

		ProfileSpecificationComponentValidator validator = new ProfileSpecificationComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateAbstractClass();

		assertFalse("Abstract class class has been validated, it should not - it does not implement all life cycle methods.", b);

	}
	
	
	public void testAbstractClassConstraintsOk10() throws Exception {

		
		List<ProfileSpecificationDescriptorImpl> specs = new ProfileSpecificationDescriptorFactoryImpl()
		.parse(super.getFileStream(_PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS_10));

		ProfileSpecificationDescriptorImpl descriptor = specs.get(0);
		ProfileSpecificationComponentImpl component = new ProfileSpecificationComponentImpl(
				descriptor);
		component.setProfileCmpInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileCMPInterface()
								.getProfileCmpInterfaceName()));

		component.setProfileAbstractClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileAbstractClass()
								.getProfileAbstractClassName()));

		component.setProfileManagementInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileManagementInterface()));

		
		ProfileSpecificationComponentValidator validator = new ProfileSpecificationComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateAbstractClass();

		assertTrue("Abstract class class has not been validated", b);

	}
	public void testAbstractClassConstraintsLackLifeCycle10() throws Exception {

		
		List<ProfileSpecificationDescriptorImpl> specs = new ProfileSpecificationDescriptorFactoryImpl()
		.parse(super.getFileStream(_PROFILE_SPEC_JAR_ONE_ABSTRACTCLASS_OK_CONSTRAINTS_10));

		ProfileSpecificationDescriptorImpl descriptor = specs.get(0);
		ProfileSpecificationComponentImpl component = new ProfileSpecificationComponentImpl(
				descriptor);
		component.setProfileCmpInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileCMPInterface()
								.getProfileCmpInterfaceName()));

		component.setProfileAbstractClass(ProfileAbstractClass10LackLifeCycle.class);

		component.setProfileManagementInterfaceClass(Thread.currentThread()
				.getContextClassLoader().loadClass(
						descriptor.getProfileManagementInterface()));

		
		
		ProfileSpecificationComponentValidator validator = new ProfileSpecificationComponentValidator();
		validator.setComponent(component);

		boolean b = validator.validateAbstractClass();

		assertFalse("Abstract class class has been validated, it should not - it does not implement all life cycle methods.", b);

	}
	


	//FIXME: all other tests are the same? for 1.0 and 1.1 
	
}
