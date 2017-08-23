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

package org.mobicents.slee.container.component.deployment.jaxb.descriptors.profile;

import java.util.ArrayList;
import java.util.List;

import javax.slee.management.LibraryID;

import org.mobicents.slee.container.component.deployment.jaxb.descriptors.common.MEnvEntry;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.common.references.MProfileSpecRef;
import org.mobicents.slee.container.component.deployment.jaxb.descriptors.profile.query.MQuery;

/**
 * 
 * MProfileSpec.java
 *
 * <br>Project:  mobicents
 * <br>1:52:01 AM Feb 14, 2009 
 * <br>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a> 
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 */
public class MProfileSpec {

  private String description;

  private String profileSpecName;
  private String profileSpecVendor;
  private String profileSpecVersion;

  private MProfileClasses profileClasses;

  // JAIN SLEE 1.0 Only
  private List<MProfileIndex> profileIndex = new ArrayList<MProfileIndex>();

  // JAIN SLEE 1.1 Only, defaults for 1.0
  private Boolean profileReadOnly = new Boolean(true);
  private Boolean profileEventsEnabled = new Boolean(true);
  
  private List<LibraryID> libraryRefs = new ArrayList<LibraryID>();
  private List<MProfileSpecRef> profileSpecRef = new ArrayList<MProfileSpecRef>();
  private List<MCollator> collator = new ArrayList<MCollator>();

  private List<MEnvEntry> envEntry = new ArrayList<MEnvEntry>();
  private List<MQuery> query = new ArrayList<MQuery>();

  private boolean isSingleProfile = false;
  
  public MProfileSpec(org.mobicents.slee.container.component.deployment.jaxb.slee.profile.ProfileSpec profileSpec10)
  {
    this.description = profileSpec10.getDescription() == null ? null : profileSpec10.getDescription().getvalue();

    this.profileSpecName = profileSpec10.getProfileSpecName().getvalue();
    this.profileSpecVendor = profileSpec10.getProfileSpecVendor().getvalue();
    this.profileSpecVersion = profileSpec10.getProfileSpecVersion().getvalue();

    this.profileClasses = new MProfileClasses(profileSpec10.getProfileClasses());

    for(org.mobicents.slee.container.component.deployment.jaxb.slee.profile.ProfileIndex profileIndex10 : profileSpec10.getProfileIndex())
    {
      this.profileIndex.add( new MProfileIndex(profileIndex10) );
    }

    this.isSingleProfile  = profileSpec10.getProfileHints() != null && profileSpec10.getProfileHints().getSingleProfile() != null ? 
        Boolean.parseBoolean(profileSpec10.getProfileHints().getSingleProfile()) : false;
  }

  public MProfileSpec(org.mobicents.slee.container.component.deployment.jaxb.slee11.profile.ProfileSpec profileSpec11)
  {
    this.description = profileSpec11.getDescription() == null ? null : profileSpec11.getDescription().getvalue();

    this.profileSpecName = profileSpec11.getProfileSpecName().getvalue();
    this.profileSpecVendor = profileSpec11.getProfileSpecVendor().getvalue();
    this.profileSpecVersion = profileSpec11.getProfileSpecVersion().getvalue();

    this.profileClasses = new MProfileClasses(profileSpec11.getProfileClasses());

    this.profileReadOnly = Boolean.parseBoolean(profileSpec11.getProfileReadOnly());
    this.profileEventsEnabled = Boolean.parseBoolean(profileSpec11.getProfileEventsEnabled());
    
    for(org.mobicents.slee.container.component.deployment.jaxb.slee11.profile.LibraryRef libraryRef11 : profileSpec11.getLibraryRef()) {
    	this.libraryRefs.add(new LibraryID(libraryRef11.getLibraryName()
    			.getvalue(), libraryRef11.getLibraryVendor().getvalue(),
    			libraryRef11.getLibraryVersion().getvalue()));
    }

    for(org.mobicents.slee.container.component.deployment.jaxb.slee11.profile.ProfileSpecRef profileSpecRef11 : profileSpec11.getProfileSpecRef())
    {
      this.profileSpecRef.add( new MProfileSpecRef(profileSpecRef11) );
    }

    for(org.mobicents.slee.container.component.deployment.jaxb.slee11.profile.Collator collator11 : profileSpec11.getCollator())
    {
      this.collator.add( new MCollator(collator11) );
    }

    for(org.mobicents.slee.container.component.deployment.jaxb.slee11.profile.EnvEntry envEntry11 : profileSpec11.getEnvEntry())
    {
      this.envEntry.add( new MEnvEntry(envEntry11) );
    }

    for(org.mobicents.slee.container.component.deployment.jaxb.slee11.profile.Query query11 : profileSpec11.getQuery())
    {
      this.query.add( new MQuery(query11) );
    }

  }

  public String getDescription()
  {
    return description;
  }

  public String getProfileSpecName()
  {
    return profileSpecName;
  }

  public String getProfileSpecVendor()
  {
    return profileSpecVendor;
  }

  public String getProfileSpecVersion()
  {
    return profileSpecVersion;
  }

  public MProfileClasses getProfileClasses()
  {
    return profileClasses;
  }

  public List<MProfileIndex> getProfileIndex()
  {
    return profileIndex;
  }

  public Boolean getProfileReadOnly()
  {
    return profileReadOnly;
  }
  
  public Boolean getProfileEventsEnabled()
  {
    return profileEventsEnabled;
  }
  
  public List<LibraryID> getLibraryRefs()
  {
    return libraryRefs;
  }

  public List<MProfileSpecRef> getProfileSpecRef()
  {
    return profileSpecRef;
  }

  public List<MCollator> getCollator()
  {
    return collator;
  }

  public List<MEnvEntry> getEnvEntry()
  {
    return envEntry;
  }

  public List<MQuery> getQuery()
  {
    return query;
  }
  
  public boolean isSingleProfile()
  {
    return isSingleProfile;
  }

}
