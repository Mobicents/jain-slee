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
package org.mobicents.tools.twiddle.jslee;

import java.io.PrintWriter;

import javax.management.ObjectName;

/**
 * @author baranowb
 * 
 */
public class ServiceUsageCommand extends AbstractUsageCommand {

	/**
	 * 
	 */
	public ServiceUsageCommand() {
		super(
				"usage.service",
				"This command performs operations on JSLEE MBeans which are associated with usage parameter sets - be it specific MBean for parameter set, notifiaction management MBean...");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.mobicents.tools.twiddle.jslee.AbstractUsageCommand#
	 * getProvisioningMBeanName()
	 */
	@Override
	public ObjectName getProvisioningMBeanName() {
		return super.SERVICE_MANAGEMENT_MBEAN;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seeorg.mobicents.tools.twiddle.jslee.AbstractUsageCommand#
	 * getUsageMGMTMBeanOperation()
	 */
	@Override
	public String getUsageMGMTMBeanOperation() {

		return SERVICE_GET_METHOD;
	}
	/* (non-Javadoc)
	 * @see org.mobicents.tools.twiddle.jslee.AbstractUsageCommand#addExamples()
	 */
	@Override
	protected void addExamples(PrintWriter out) {
		out.println("");
		out.println("     1. List usage parameters type for Service and Sbb:");
		out.println("" + name + " ServiceID[name=XXX,vendor=restcomm,version=1.0] SbbID[name=example,vendor=restcomm,version=0.1] -l --parameters");
		out.println("");
		out.println("     2. List existing usage parameters sets:");
		out.println("" + name + " ServiceID[name=XXX,vendor=restcomm,version=1.0] SbbID[name=example,vendor=restcomm,version=0.1] -l --sets");
		out.println("");
		out.println("     3. Get value of parameter in certain set:");
		out.println("" + name + " ServiceID[name=XXX,vendor=restcomm,version=1.0] SbbID[name=example,vendor=restcomm,version=0.1] CertainSetWithValue -g --name=CookiesCount");
		out.println("");
		out.println("     4. Get value of parameter in certain set and reset value:");
		out.println("" + name + " ServiceID[name=XXX,vendor=restcomm,version=1.0] SbbID[name=example,vendor=restcomm,version=0.1] CertainSetWithValue -g --name=CookiesCount --rst");
		out.println("");
		out.println("     5. Reset all parameters in parameter sets of service:");
		out.println("" + name + " ServiceID[name=XXX,vendor=restcomm,version=1.0] --reset");
		out.println("");
		out.println("     6. Reset parameters in defaulet parameter set of sbb in service:");
		out.println("" + name + " ServiceID[name=XXX,vendor=restcomm,version=1.0] SbbID[name=example,vendor=restcomm,version=0.1] --reset");
		out.println("");
		out.println("     7. Create parameter set:");
		out.println("" + name + " ServiceID[name=XXX,vendor=restcomm,version=1.0] SbbID[name=example,vendor=restcomm,version=0.1] NewSet --create");
		out.println("");
		out.println("     8. Enable notification generation for parameter:");
		out.println("" + name + " ServiceID[name=XXX,vendor=restcomm,version=1.0] SbbID[name=example,vendor=restcomm,version=0.1] -n --name=CookiesCount --value=true");
		
	}

	/* (non-Javadoc)
	 * @see org.mobicents.tools.twiddle.jslee.AbstractUsageCommand#addHeaderDescription()
	 */
	@Override
	protected void addHeaderDescription(PrintWriter out) {
		out.println("usage: " + name + " <ServiceID> [SbbID] [SetName] <-operation[[arg] | [--option[=arg]]*]>");
	}
}
