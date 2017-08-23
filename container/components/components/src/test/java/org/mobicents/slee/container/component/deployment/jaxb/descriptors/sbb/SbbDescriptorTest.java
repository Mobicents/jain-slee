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
 * Start time:14:23:34 2009-01-23<br>
 * Project: restcomm-jainslee-server-core<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com">baranowb - Bartosz Baranowski
 *         </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
package org.mobicents.slee.container.component.deployment.jaxb.descriptors.sbb;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.slee.EventTypeID;
import javax.slee.management.DeploymentException;
import javax.slee.management.LibraryID;

import org.mobicents.slee.container.component.common.EnvEntryDescriptor;
import org.mobicents.slee.container.component.common.ProfileSpecRefDescriptor;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.SbbDescriptorFactoryImpl;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.SbbDescriptorImpl;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.TCUtilityClass;
import org.mobicents.slee.container.component.sbb.CMPFieldDescriptor;
import org.mobicents.slee.container.component.sbb.EjbRefDescriptor;
import org.mobicents.slee.container.component.sbb.EventDirection;
import org.mobicents.slee.container.component.sbb.EventEntryDescriptor;
import org.mobicents.slee.container.component.sbb.GetChildRelationMethodDescriptor;
import org.mobicents.slee.container.component.sbb.GetProfileCMPMethodDescriptor;
import org.mobicents.slee.container.component.sbb.ResourceAdaptorEntityBindingDescriptor;
import org.mobicents.slee.container.component.sbb.ResourceAdaptorTypeBindingDescriptor;
import org.mobicents.slee.container.component.sbb.SbbAbstractClassDescriptor;
import org.xml.sax.SAXException;

/**
 * Start time:14:23:34 2009-01-23<br>
 * Project: restcomm-jainslee-server-core<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com">baranowb - Bartosz Baranowski
 *         </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class SbbDescriptorTest extends TCUtilityClass {

	
	
	
	
	private static final String _ONE_DESCRIPTOR_FILE="xml/sbb-jar-one_1_1.xml";
	private static final String _TWO_DESCRIPTOR_FILE="xml/sbb-jar-two_1_1.xml";
	
	private static final String _ONE_DESCRIPTOR_FILE10="xml/sbb-jar-one_1_0.xml";
	private static final String _TWO_DESCRIPTOR_FILE10="xml/sbb-jar-two_1_0.xml";
	
	private static final String _SBB_NAME="sbb-name";
	private static final String _SBB_VENDOR="sbb-vendor";
	private static final String _SBB_VERSION="sbb-version";
	private static final String _SBB_ALIAS="sbb-alias";
	
	private static final String _LIBRARY_REF_NAME="library-name";
	private static final String _LIBRARY_REF_VENDOR="library-vendor";
	private static final String _LIBRARY_REF_VERSION="library-version";
	
	
	private static final String _PROFILE_SPEC_NAME="profile-spec-name";
	private static final String _PROFILE_SPEC_VENDOR="profile-spec-vendor";
	private static final String _PROFILE_SPEC_VERSION="profile-spec-version";
	private static final String _PROFILE_SPEC_ALIAS="profile-spec-alias";
	
	private static final String _EVENT_NAME="event-name";
	private static final String _INITIAL_EVENT_SELECTOR_METHOD_NAME="initial-event-selector-method-name";
	
	private static final String _ATTRIBUTE_ALIAS_NAME="attribute-alias-name";
	private static final String _SBB_ACI_ATTRIBUTE_NAME="sbb-activity-context-attribute-name";
	
	private static final String _ENV_ENTRY_NAME="env-entry-name";
	private static final String _ENV_ENTRY_TYPE="env-entry-type";
	private static final String _ENV_ENTRY_VALUE="env-entry-value";
	
	private static final String _RATYPE_BINDING_NAME="resource-adaptor-type-name";
	private static final String _RATYPE_BINDING_VENDOR="resource-adaptor-type-vendor";
	private static final String _RATYPE_BINDING_VERSION="resource-adaptor-type-version";
	
	private static final String _RATYPE_ENTITY_BINDING_ON="resource-adaptor-object-name";
	private static final String  _RATYPE_ENTITY_BINDING_LINK="resource-adaptor-entity-link";
	
	private static final String  _EJB_REF_NAME="ejb-ref-name";
	private static final String  _EJB_REF_TYPE="ejb-ref-type";
	private static final String  _EJB_REF_HOME="home";
	private static final String  _EJB_REF_REMOTE="remote";
	private static final String  _EJB_REF_LINK="ejb-link";
	
	private static final String _SBB_ABSTRACT_CLASS_NAME="sbb-abstract-class-name";
	
	private static final String _SBB_ALIAS_REF="sbb-alias-ref";
	private static final String _GET_CHILD_RELATION_METHOD_NAME="get-child-relation-method-name";
	private static final String _CMP_FIELD_NAME="cmp-field-name";
	
	private static final String _PROFILE_SPEC_ALIAST_REF="profile-spec-alias-ref";
	private static final String _GET_PROFILE_CMP_METHOD_NAME="get-profile-cmp-method-name";
	
	public void testParseOne10() throws DeploymentException, SAXException, IOException, URISyntaxException
	{
		

		List<SbbDescriptorImpl> specs = new SbbDescriptorFactoryImpl().parse(super.getFileStream(_ONE_DESCRIPTOR_FILE10));
		
		
		assertNotNull("Sbb return value is null", specs);
		assertTrue("Sbb  size is wrong!!!", specs.size()==1);
		assertNotNull("Sbb return value cell is null", specs.get(0));
		assertFalse("Sbb should indicate v1.0 not v1.1",specs.get(0).isSlee11());
		//Test values
		doTestOnValues(specs.get(0));
		
	}

	
	public void testParseTwo10() throws DeploymentException, SAXException, IOException, URISyntaxException
	{
		
		List<SbbDescriptorImpl> specs = new SbbDescriptorFactoryImpl().parse(super.getFileStream(_TWO_DESCRIPTOR_FILE10));
		

		assertNotNull("Sbb return value is null", specs);
		assertTrue("Sbb  size is wrong!!!", specs.size()==2);
		assertNotNull("Sbb return value cell is null", specs.get(0));
		assertFalse("Sbb should indicate v1.0 not v1.1",specs.get(0).isSlee11());
		//Test values
		doTestOnValues(specs.get(0));
		
		
		assertNotNull("Sbb return value cell is null", specs.get(1));
		assertFalse("Sbb should indicate v1.0 not v1.1",specs.get(1).isSlee11());
		//Test values
		doTestOnValues(specs.get(1));
	}

	
	
	public void testParseOne() throws DeploymentException, SAXException, IOException, URISyntaxException
	{

		List<SbbDescriptorImpl> specs = new SbbDescriptorFactoryImpl().parse(super.getFileStream(_ONE_DESCRIPTOR_FILE));
		assertNotNull("Sbb return value is null", specs);
		assertTrue("Sbb  size is wrong!!!", specs.size()==1);
		assertNotNull("Sbb return value cell is null", specs.get(0));
		assertTrue("Sbb should indicate v1.1 not v1.0",specs.get(0).isSlee11());
		//Test values
		doTestOnValues(specs.get(0));
		
	}

	
	public void testParseTwo() throws DeploymentException, SAXException, IOException, URISyntaxException
	{
		

		List<SbbDescriptorImpl> specs = new SbbDescriptorFactoryImpl().parse(super.getFileStream(_TWO_DESCRIPTOR_FILE));
		assertNotNull("Sbb return value is null", specs);
		assertTrue("Sbb  size is wrong!!!", specs.size()==2);
		assertNotNull("Sbb return value cell is null", specs.get(0));
		assertTrue("Sbb should indicate v1.1 not v1.0",specs.get(0).isSlee11());
		//Test values
		doTestOnValues(specs.get(0));
		
		
		assertNotNull("Sbb return value cell is null", specs.get(1));
		assertTrue("Sbb should indicate v1.1 not v1.0",specs.get(1).isSlee11());
		//Test values
		doTestOnValues(specs.get(1));
	}
	
	
	protected void doTestOnValues(SbbDescriptorImpl sbb) {
		
		
		
		
		validateKey(sbb.getSbbID(),"Sbb component key",new String[]{_SBB_NAME,_SBB_VENDOR,_SBB_VERSION});
		assertNotNull("Sbb component key cant be null",sbb.getSbbID());
	
		
		//Alias is Optional, but its filled
		assertNotNull("Sbb component key sbb-alias cant be null",sbb.getSbbAlias());
		assertTrue("Sbb component key sbb-alias is not equal to "+_SBB_ALIAS,sbb.getSbbAlias().compareTo(_SBB_ALIAS)==0);
		
		List<ProfileSpecRefDescriptor> profilesSpecs=sbb.getProfileSpecRefs();
		assertNotNull("Profile specs references list is null",profilesSpecs);
		assertTrue("Profile specs references list size is not 1",profilesSpecs.size()==1);
		ProfileSpecRefDescriptor ref=profilesSpecs.get(0);
		
		assertNotNull("Profile specs reference is null",ref);
		
		
		validateKey(ref.getComponentID(),"Profile specs reference",new String[]{_PROFILE_SPEC_NAME,_PROFILE_SPEC_VENDOR,_PROFILE_SPEC_VERSION});
		if(!sbb.isSlee11())
		  assertNotNull("Profile specs reference alias is null ",ref.getProfileSpecAlias());
		assertTrue("Profile specs reference alias is not equal to "+_PROFILE_SPEC_ALIAS,ref.getProfileSpecAlias().compareTo(_PROFILE_SPEC_ALIAS)==0);
		
		
		Map<EventTypeID,EventEntryDescriptor> events= sbb.getEventEntries();
		
		assertNotNull("Events list is null",events);
		
		assertTrue("Events list size is not equal 1",events.size()==1);
		
		EventEntryDescriptor eventEntry=events.values().iterator().next();
		
		assertNotNull("Event entry is null",eventEntry);
		
		//validateKey(eventEntry.getEventReference().getReference(), " Event entry reference ", new String[]{_EVENT_TYPE_NAME,_EVENT_TYPE_VENDOR,_EVENT_TYPE_VERSION});
		
		assertNotNull("Event entry event-name is null",eventEntry.getEventName());
		assertTrue("Event entry event-name is not equal "+_EVENT_NAME,eventEntry.getEventName().compareTo(_EVENT_NAME)==0);
		
		assertNotNull("Event entry direction is null",eventEntry.getEventDirection());
		assertTrue("Event entry direction is not equal Receive",eventEntry.getEventDirection()==EventDirection.Receive);
		
		assertNotNull("Event entry initial event selector method is null",eventEntry.getInitialEventSelectorMethod());
		assertTrue("Event entry initial event selector method is not equal "+_INITIAL_EVENT_SELECTOR_METHOD_NAME,eventEntry.getInitialEventSelectorMethod().compareTo(_INITIAL_EVENT_SELECTOR_METHOD_NAME)==0);		
						
		assertTrue("Event initial select variable Address is selected",eventEntry.getInitialEventSelectVariables().isAddressSelected());
		assertTrue("Event initial select variable ActivityContext is not selected",!eventEntry.getInitialEventSelectVariables().isActivityContextSelected());
		assertTrue("Event initial select variable AddressProfile is not selected",!eventEntry.getInitialEventSelectVariables().isAddressProfileSelected());
		assertTrue("Event initial select variable Event is not selected",!eventEntry.getInitialEventSelectVariables().isEventSelected());
		assertTrue("Event initial select variable EventType is not selected",!eventEntry.getInitialEventSelectVariables().isEventTypeSelected());
	
		Set<Entry<String, String>> aciAliasses=sbb.getActivityContextAttributeAliases().entrySet();
		
		assertNotNull("Activity context inteface attribute aliasses list is null",aciAliasses);
		assertTrue("Activity context inteface attribute aliasses list size is not 1",aciAliasses.size()==1);
		
		Entry<String, String> aciAlias=aciAliasses.iterator().next();
		assertNotNull("Activity context inteface attribute aliass is null",aciAlias);
		assertNotNull("Activity context inteface attribute aliass name is null",aciAlias.getValue());

		assertTrue("Activity context inteface attribute aliass name is nto equal to "+_ATTRIBUTE_ALIAS_NAME,aciAlias.getValue().compareTo(_ATTRIBUTE_ALIAS_NAME)==0);
		
		assertNotNull("Activity context inteface attribute aliass aci attribute names set is null",aciAlias.getKey());
		assertTrue("Activity context inteface attribute aliass aci attribute name is not equal to "+_SBB_ACI_ATTRIBUTE_NAME,aciAlias.getKey().compareTo(_SBB_ACI_ATTRIBUTE_NAME)==0);
		
		List<EnvEntryDescriptor> envEntries=sbb.getEnvEntries();
		

		assertNotNull("Sbb env entries are null",envEntries);
		assertTrue("Sbb env entries size is not equal to 1",envEntries.size()==1);
		assertNotNull("Sbb env entry is null",envEntries.get(0));
		EnvEntryDescriptor entry=envEntries.get(0);
		assertNotNull("Sbb env entry is null",entry.getEnvEntryName());
		assertNotNull("Sbb env entry name is null ", entry.getEnvEntryName());
		assertNotNull("Sbb env entry type is null ", entry.getEnvEntryType());
		assertNotNull("Sbb env entry value is null ", entry.getEnvEntryValue());
		assertTrue("Sbb env entry name not equal: "+_ENV_ENTRY_NAME, entry.getEnvEntryName().compareTo(_ENV_ENTRY_NAME)==0);
		assertTrue("Sbb env entry type not equal: "+_ENV_ENTRY_TYPE, entry.getEnvEntryType().compareTo(_ENV_ENTRY_TYPE)==0);
		assertTrue("Sbb env entry value not equal: "+_ENV_ENTRY_VALUE, entry.getEnvEntryValue().compareTo(_ENV_ENTRY_VALUE)==0);
		
		
		List<ResourceAdaptorTypeBindingDescriptor> raTypeBindings=sbb.getResourceAdaptorTypeBindings();
		
		
		assertNotNull("Sbb ra typee bindings list is null",raTypeBindings);
		assertTrue("Sbb ra typee bindings list size is not equal to 1",raTypeBindings.size()==1);
		assertNotNull("Sbb ra typee binding is null",raTypeBindings.get(0));
		
		ResourceAdaptorTypeBindingDescriptor raTypeBinding=raTypeBindings.get(0);
		
		validateKey(raTypeBinding.getResourceAdaptorTypeRef(), "Resource Adaptor Type Binding reference key ", new String[]{_RATYPE_BINDING_NAME,_RATYPE_BINDING_VENDOR,_RATYPE_BINDING_VERSION});
		
		List<ResourceAdaptorEntityBindingDescriptor> endityBindings=raTypeBinding.getResourceAdaptorEntityBinding();
		
		
		assertNotNull("Sbb ra type entity bindings list is null",endityBindings);
		assertTrue("Sbb ra type entity bindings list size is not equal to 1",endityBindings.size()==1);
		assertNotNull("Sbb ra type entity binding is null",endityBindings.get(0));
		
		
		ResourceAdaptorEntityBindingDescriptor entityBinding=endityBindings.get(0);
		assertNotNull("Sbb ra type entity binding link is null",entityBinding.getResourceAdaptorEntityLink());
		assertTrue("Sbb ra type entity bindings link is not equal to ",entityBinding.getResourceAdaptorEntityLink().compareTo(_RATYPE_ENTITY_BINDING_LINK)==0);
		assertNotNull("Sbb ra type entity binding object name is null",entityBinding.getResourceAdaptorObjectName());
		assertTrue("Sbb ra type entity bindings object name is not equal to ",entityBinding.getResourceAdaptorObjectName().compareTo(_RATYPE_ENTITY_BINDING_ON)==0);

		
		List<EjbRefDescriptor> ejbRefs=sbb.getEjbRefs();
		assertNotNull("Sbb ejb refs list is null",ejbRefs);
		assertTrue("Sbb ejb refs list size is not equal to 1",ejbRefs.size()==1);
		assertNotNull("Sbb ejb ref is null",ejbRefs.get(0));
		
		EjbRefDescriptor ejbRef=ejbRefs.get(0);
		
		validateValue(ejbRef.getEjbRefName()," Ejb ref name ",_EJB_REF_NAME);
		validateValue(ejbRef.getEjbRefType()," Ejb ref type ",_EJB_REF_TYPE);
		validateValue(ejbRef.getHome()," Ejb ref home ",_EJB_REF_HOME);
		validateValue(ejbRef.getRemote()," Ejb ref remote ",_EJB_REF_REMOTE);
		if(!sbb.isSlee11())
			validateValue(ejbRef.getEjbLink()," Ejb ref link ",_EJB_REF_LINK);
		
		SbbAbstractClassDescriptor mSbbAbstractClass=sbb.getSbbAbstractClass();
		
		assertNotNull("Sbb abstract class is null",mSbbAbstractClass);
		
		validateValue(mSbbAbstractClass.getSbbAbstractClassName(),"Sbb abstract class name",_SBB_ABSTRACT_CLASS_NAME);
		
		
		Map<String,GetChildRelationMethodDescriptor> getChildRelationMethods=mSbbAbstractClass.getChildRelationMethods();
		
		
		
		assertNotNull("Sbb get child relation list is null",getChildRelationMethods);
		assertTrue("Sbb get child relation list size is not equal to 1",getChildRelationMethods.size()==1);
		assertNotNull("Sbb get child relation is null",getChildRelationMethods.values().iterator().next());
		
		MGetChildRelationMethod getChildRelationMethod = (MGetChildRelationMethod) getChildRelationMethods.values().iterator().next();
		
		
		validateValue(getChildRelationMethod.getChildRelationMethodName(), "Get child relation method name ", _GET_CHILD_RELATION_METHOD_NAME);
		validateValue(getChildRelationMethod.getSbbAliasRef(), "Get child relation method name - sbb alias ref", _SBB_ALIAS_REF);
		assertTrue("Get child relation method default priority is not equal to 123",getChildRelationMethod.getDefaultPriority()==123);
		
		
//		Map<String,MSbbCMPField> cmpFields=mSbbAbstractClass.getCmpFields();
//		
//		assertNotNull("Sbb cmp fields list is null",cmpFields);
//		assertTrue("Sbb cmp fields list size is not equal to 1",cmpFields.size()==1);
//		assertNotNull("Sbb cmp field is null",cmpFields.get(cmpFields.keySet().iterator().next()));
//		
//		MSbbCMPField cmpField=cmpFields.get(cmpFields.keySet().iterator().next());
//		assertTrue("CMP Field name does not match cmp field key",cmpFields.keySet().iterator().next().compareTo(cmpField.getCmpFieldName())==0);
//		validateValue(cmpField.getCmpFieldName(), "CMP Field name ", _CMP_FIELD_NAME);
//		validateValue(cmpField.getSbbAliasRef(), "CMP Field sbba alias ref ", _SBB_ALIAS_REF);
//		
		
		Collection<CMPFieldDescriptor> cmpFields=mSbbAbstractClass.getCmpFields();
		
		assertNotNull("Sbb cmp fields list is null",cmpFields);
		assertTrue("Sbb cmp fields list size is not equal to 1",cmpFields.size()==1);
		assertNotNull("Sbb cmp field is null",cmpFields.iterator().next());
		
		MSbbCMPField cmpField=(MSbbCMPField) cmpFields.iterator().next();
	
		validateValue(cmpField.getCmpFieldName(), "CMP Field name ", _CMP_FIELD_NAME);
		validateValue(cmpField.getSbbAliasRef(), "CMP Field sbba alias ref ", _SBB_ALIAS_REF);
		
		
		Map<String,GetProfileCMPMethodDescriptor> profileCMPMethods=mSbbAbstractClass.getProfileCMPMethods();
		
		
		
		assertNotNull("Sbb profile cmp methods list is null",profileCMPMethods);
		assertTrue("Sbb profile cmp methods size is not equal to 1",profileCMPMethods.size()==1);
		assertNotNull("Sbb profile cmp method is null",profileCMPMethods.values().iterator().next());
		
		MGetProfileCMPMethod profileCMPMethod = (MGetProfileCMPMethod) profileCMPMethods.values().iterator().next();
		
		validateValue(profileCMPMethod.getProfileSpecAliasRef(), "profile cmp method profile specs alias ref ", _PROFILE_SPEC_ALIAST_REF);
		validateValue(profileCMPMethod.getProfileCmpMethodName(), "profile cmp method name ", _GET_PROFILE_CMP_METHOD_NAME);
		
		
		//if slee 1.1
		if(sbb.isSlee11())
		{
			
			Set<LibraryID> libraryRefs=sbb.getLibraryRefs();
			
			
			assertNotNull("Sbb library refs list is null",libraryRefs);
			assertTrue("Sbb library refs size is not equal to 1",libraryRefs.size()==1);
			validateKey(libraryRefs.iterator().next(), "Sbb library ref key", new String[]{_LIBRARY_REF_NAME,_LIBRARY_REF_VENDOR,_LIBRARY_REF_VERSION});
			
			
			
		}
		
	}
	
	
}
