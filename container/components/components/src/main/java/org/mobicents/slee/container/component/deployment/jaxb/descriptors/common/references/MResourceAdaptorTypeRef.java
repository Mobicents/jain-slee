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

package org.mobicents.slee.container.component.deployment.jaxb.descriptors.common.references;

import javax.slee.resource.ResourceAdaptorTypeID;

/**
 * 
 * MResourceAdaptorTypeRef.java
 *
 * <br>Project:  mobicents
 * <br>6:19:43 PM Jan 22, 2009 
 * <br>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a> 
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class MResourceAdaptorTypeRef {

  protected String description;
  protected String resourceAdaptorTypeName;
  protected String resourceAdaptorTypeVendor;
  protected String resourceAdaptorTypeVersion;
  
  private ResourceAdaptorTypeID resourceAdaptorTypeID;
  
  public MResourceAdaptorTypeRef(org.mobicents.slee.container.component.deployment.jaxb.slee.ra.ResourceAdaptorTypeRef resourceAdaptorTypeRef10)
  {
    this.description = resourceAdaptorTypeRef10.getDescription() == null ? null : resourceAdaptorTypeRef10.getDescription().getvalue();
    
    this.resourceAdaptorTypeName = resourceAdaptorTypeRef10.getResourceAdaptorTypeName().getvalue();
    this.resourceAdaptorTypeVendor = resourceAdaptorTypeRef10.getResourceAdaptorTypeVendor().getvalue();
    this.resourceAdaptorTypeVersion = resourceAdaptorTypeRef10.getResourceAdaptorTypeVersion().getvalue();
    
    this.resourceAdaptorTypeID = new ResourceAdaptorTypeID(this.resourceAdaptorTypeName, this.resourceAdaptorTypeVendor, this.resourceAdaptorTypeVersion);
  }
  
  public MResourceAdaptorTypeRef(org.mobicents.slee.container.component.deployment.jaxb.slee11.ra.ResourceAdaptorTypeRef resourceAdaptorTypeRef11)
  {
    this.description = resourceAdaptorTypeRef11.getDescription() == null ? null : resourceAdaptorTypeRef11.getDescription().getvalue();
    
    this.resourceAdaptorTypeName = resourceAdaptorTypeRef11.getResourceAdaptorTypeName().getvalue();
    this.resourceAdaptorTypeVendor = resourceAdaptorTypeRef11.getResourceAdaptorTypeVendor().getvalue();
    this.resourceAdaptorTypeVersion = resourceAdaptorTypeRef11.getResourceAdaptorTypeVersion().getvalue();

    this.resourceAdaptorTypeID = new ResourceAdaptorTypeID(this.resourceAdaptorTypeName, this.resourceAdaptorTypeVendor, this.resourceAdaptorTypeVersion);
  }
  
  public String getDescription()
  {
    return description;
  }
  
  public String getResourceAdaptorTypeName()
  {
    return resourceAdaptorTypeName;
  }
  
  public String getResourceAdaptorTypeVendor()
  {
    return resourceAdaptorTypeVendor;
  }
  
  public String getResourceAdaptorTypeVersion()
  {
    return resourceAdaptorTypeVersion;
  }

  public ResourceAdaptorTypeID getComponentID()
  {
    return this.resourceAdaptorTypeID;
  }
}
