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

package org.mobicents.slee.container.component.deployment.jaxb.descriptors.profile.query;

import java.util.ArrayList;
import java.util.List;

import org.mobicents.slee.container.component.profile.query.QueryDescriptor;

/**
 * Start time:17:26:08 2009-01-18<br>
 * Project: restcomm-jainslee-server-core<br>
 * 
 * @author <a href="mailto:baranowb@gmail.com"> Bartosz Baranowski </a>
 * @author <a href="mailto:brainslog@gmail.com"> Alexandre Mendonca </a>
 */
public class MQuery implements QueryDescriptor {

  private String name;

  private List<MQueryParameter> queryParameters;
  private MQueryOptions queryOptions;
  private MQueryExpression queryExpression;

  public MQuery(org.mobicents.slee.container.component.deployment.jaxb.slee11.profile.Query query11)
  {
    this.name = query11.getName();

    this.queryParameters = new ArrayList<MQueryParameter>();

    if (query11.getQueryParameter() != null && query11.getQueryParameter().size() > 0)
    {
      for (org.mobicents.slee.container.component.deployment.jaxb.slee11.profile.QueryParameter queryParameter11 : query11.getQueryParameter())
      {
        this.queryParameters.add(new MQueryParameter(queryParameter11));
      }
    }

    if (query11.getQueryOptions() != null)
    {
      this.queryOptions = new MQueryOptions(query11.getQueryOptions());
    }

    //get(0) - xml validation takes care of it, we always have exactly one at this stage
    this.queryExpression = new MQueryExpression(query11.getCompareOrRangeMatchOrLongestPrefixMatchOrHasPrefixOrAndOrOrOrNot().get(0));
  }

  public List<MQueryParameter> getQueryParameters() {
    return queryParameters;
  }

  public MQueryOptions getQueryOptions() {
    return queryOptions;
  }

  public MQueryExpression getQueryExpression() {
    return queryExpression;
  }

  public String getName() {
    return name;
  }

}
