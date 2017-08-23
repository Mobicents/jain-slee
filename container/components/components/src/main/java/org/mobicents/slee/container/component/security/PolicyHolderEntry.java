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

package org.mobicents.slee.container.component.security;

import java.security.CodeSource;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;

import sun.security.provider.PolicyParser;
import sun.security.provider.PolicyParser.PrincipalEntry;

class PolicyHolderEntry {
	private final CodeSource codesource;
	private final List<Permission> permissions;
	private final List<PolicyParser.PrincipalEntry> principals;

	public PolicyHolderEntry(CodeSource codesource, List<PrincipalEntry> principals) {
		super();
		this.codesource = codesource;
		this.principals = principals;
		permissions = new ArrayList<Permission>();
	}

	public PolicyHolderEntry(CodeSource codesource) {
		this(codesource, null);

	}

	public CodeSource getCodeSource() {
		return codesource;
	}

	public List<PolicyParser.PrincipalEntry> getPrincipals() {
		return principals;
	}

	public void add(Permission p) {
		permissions.add(p);
	}

	public List<Permission> getPermissions() {
		// FIXME: add new list?
		return this.permissions;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName()+" cs: "+codesource+" -- "+permissions.size();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codesource == null) ? 0 : codesource.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PolicyHolderEntry other = (PolicyHolderEntry) obj;
		if (codesource == null) {
			if (other.codesource != null)
				return false;
		} else if (!codesource.equals(other.codesource))
			return false;
		return true;
	}
	
}