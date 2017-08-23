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

package org.mobicents.slee.container.component.deployment.jaxb.descriptors;

import java.io.InputStream;

import javax.slee.management.DeploymentException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.mobicents.slee.container.component.deployment.xml.DefaultSleeEntityResolver;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public abstract class AbstractDescriptorFactory {

	private static final Logger logger = Logger.getLogger(AbstractDescriptorFactory.class);
	
	private static final DocumentBuilder documentBuilder = initDocumentBuilder();
    
	private static final JAXBContext jaxbContext10 = initJAXBContext10();
	
	private static final JAXBContext jaxbContext11 = initJAXBContext11();
	
	private static JAXBContext initJAXBContext10() {
        try {
        		return JAXBContext.newInstance("org.mobicents.slee.container.component.deployment.jaxb.slee.du"
                        + ":org.mobicents.slee.container.component.deployment.jaxb.slee.event"
                        + ":org.mobicents.slee.container.component.deployment.jaxb.slee.ratype"
                        + ":org.mobicents.slee.container.component.deployment.jaxb.slee.ra"
                        + ":org.mobicents.slee.container.component.deployment.jaxb.slee.sbb"
                        + ":org.mobicents.slee.container.component.deployment.jaxb.slee.service"
                        + ":org.mobicents.slee.container.component.deployment.jaxb.slee.profile");
        	
        } catch (Exception e) {
        	logger.error("Failed to create jaxb context", e);
        	//we cant throw exception
            return null;
        }
    } 
    
	private static DocumentBuilder initDocumentBuilder() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			factory.setValidating(true);
			DocumentBuilder builder = factory.newDocumentBuilder();
			builder.setEntityResolver(DefaultSleeEntityResolver.getInstance());
			builder.setErrorHandler(new PrivateErrorHandler());
			return builder;
		} catch (Exception e) {
			logger.error("Failed to create DOM builder", e);
			return null;
		}		
	}

	private static JAXBContext initJAXBContext11() {
        try {
        	return JAXBContext
            .newInstance(":org.mobicents.slee.container.component.deployment.jaxb.slee11.du"
                    + ":org.mobicents.slee.container.component.deployment.jaxb.slee11.event"
                    + ":org.mobicents.slee.container.component.deployment.jaxb.slee11.ratype"
                    + ":org.mobicents.slee.container.component.deployment.jaxb.slee11.ra"
                    + ":org.mobicents.slee.container.component.deployment.jaxb.slee11.sbb"
                    + ":org.mobicents.slee.container.component.deployment.jaxb.slee11.service"
                    + ":org.mobicents.slee.container.component.deployment.jaxb.slee11.profile"
                    + ":org.mobicents.slee.container.component.deployment.jaxb.slee11.library");
        	
        } catch (Exception e) {
        	logger.error("failed to create jaxb context"+e);
        	//we cant throw exception
            return null;
        }
    } 
	
	protected Object buildJAXBPojo(InputStream inputStream) throws DeploymentException {
		
		if (inputStream == null) {
			throw new NullPointerException("null input stream");
		}
		
		// first we parse into dom, which validates dtd
		Document document = null;		
		try {			
			document = documentBuilder.parse(inputStream);
			// trim text nodes in parsed document
			trimTextChildNodes(document.getDocumentElement());
		} catch (SAXException e) {
			throw new DeploymentException("Failed to parse descriptor into dom document cause: \""+e.getMessage()+"\"");
		}catch (Exception e) {
			throw new DeploymentException("Failed to parse descriptor into dom document",e);
		}
		finally {
			try {
				inputStream.close();
			} catch (Exception e) {
				logger.error("failed to close inputstream ",e);
			}			
		}
		
		//By default this is: http://java.sun.com/dtd/slee-deployable-unit_1_1.dtd
		DocumentType documentType = document.getDoctype();
		if(documentType==null || documentType.getSystemId()==null)
		{
			throw new DeploymentException("Doctype not present in xml file or invalid. Document can not be recognized as valid descriptor nor parsed");
		}
		boolean isSlee11 = documentType.getSystemId().endsWith("_1_1.dtd") || documentType.getSystemId().endsWith("_1_1-ext.dtd");
		
		// now we build the descriptor pojo with jaxb
		Unmarshaller unmarshaller = getUnmarshaller(!isSlee11); 		
		try {
			return unmarshaller.unmarshal(document);
		} catch (JAXBException e) {
			throw new DeploymentException("failed to unmarshall descriptor with jaxb",e);
		}
		
	}

	private void trimTextChildNodes(Node node) {
		if (node.getNodeType() == Node.TEXT_NODE) {
			node.setNodeValue(node.getNodeValue().trim());
		}
		else {
			NodeList childNodesList = node.getChildNodes();
			for (int i=0;i< childNodesList.getLength();i++) {
				trimTextChildNodes(childNodesList.item(i));
			}
		}
	}
	
    private Unmarshaller getUnmarshaller(boolean isV10) throws DeploymentException {
        try {
        	if(isV10)
        	{
        		return jaxbContext10.createUnmarshaller();
        	}
        	else
        	{
        		return jaxbContext11.createUnmarshaller();
        	}
        }catch(NullPointerException npe) {        	
        	throw new DeploymentException("Failed to create unmarshaler, probably context has not been create, see console for error.", npe);
        } catch (Exception e) {
        	throw new DeploymentException("Failed to create unmarshaler!",e);
        }
    } 
    
   static class PrivateErrorHandler implements ErrorHandler
    {

		public void error(SAXParseException e) throws SAXException {
			throw new SAXException("Error. Failed to parse due to: "+e.getMessage());
			
		}

		public void fatalError(SAXParseException e) throws SAXException {
			throw new SAXException("Fatal error. Failed to parse due to: "+e.getMessage());
			
		}

		public void warning(SAXParseException e) throws SAXException {
			logger.error(e.getMessage());
			
		}
    	
    }
	
}

