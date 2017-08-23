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
 * Start time:14:23:34 2009-01-23<br>
 * Project: restcomm-jainslee-server-core<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com">baranowb - Bartosz Baranowski
 *         </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
package org.mobicents.slee.container.component.deployment.jaxb.descriptors.du;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.slee.management.DeploymentException;

import org.mobicents.slee.container.component.deployment.jaxb.descriptors.DeployableUnitDescriptorFactoryImpl;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.DeployableUnitDescriptorImpl;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.TCUtilityClass;
import org.xml.sax.SAXException;

/**
 * Start time:14:23:34 2009-01-23<br>
 * Project: restcomm-jainslee-server-core<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com">baranowb - Bartosz Baranowski
 *         </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class DUDescriptorTest extends TCUtilityClass {

	
	
	
	
	private static final String _ONE_DESCRIPTOR_FILE="xml/du_1_1.xml";
	private static final String _ONE_DESCRIPTOR_FILE10="xml/du_1_0.xml";

	
	
	public void testParseOne10() throws DeploymentException, SAXException, IOException, URISyntaxException
	{
		
		DeployableUnitDescriptorImpl du=new DeployableUnitDescriptorFactoryImpl().parse(super.getFileStream(_ONE_DESCRIPTOR_FILE10));
		assertNotNull("DU return value is null", du);

		assertFalse("DU should indicate v1.0 not v1.1",du.isSlee11());
		//Test values
		doTestOnValues(du);
		
	}

	


	
	
	public void testParseOne() throws DeploymentException, SAXException, IOException, URISyntaxException
	{
		
		DeployableUnitDescriptorImpl du=new DeployableUnitDescriptorFactoryImpl().parse(super.getFileStream(_ONE_DESCRIPTOR_FILE));
		assertNotNull("DU return value is null", du);

		assertTrue("DU should indicate v1.1 not v1.0",du.isSlee11());
		//Test values
		doTestOnValues(du);
		
	}

	protected void doTestOnValues(DeployableUnitDescriptorImpl du) {

		
		assertNotNull("Jar entries list is null", du.getJarEntries());
		assertTrue("Jar entries list size is not 3",du.getJarEntries().size()==3);
		for(int i=0;i<du.getJarEntries().size();i++)
			validateValue(du.getJarEntries().get(i), "Jar entry", "jar"+(i+1));
		
		
		
		
		assertNotNull("Services entries list is null", du.getServiceEntries());
		assertTrue("Services entries list size is not 2",du.getServiceEntries().size()==2);
		for(int i=0;i<du.getServiceEntries().size();i++)
			validateValue(du.getServiceEntries().get(i), "service entry", "service-xml"+(i+1));
		
		
	}
	
}
