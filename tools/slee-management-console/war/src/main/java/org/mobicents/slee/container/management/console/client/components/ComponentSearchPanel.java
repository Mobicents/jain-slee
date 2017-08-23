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

package org.mobicents.slee.container.management.console.client.components;

import org.mobicents.slee.container.management.console.client.Logger;
import org.mobicents.slee.container.management.console.client.ManagementConsoleException;
import org.mobicents.slee.container.management.console.client.ServerCallback;
import org.mobicents.slee.container.management.console.client.ServerConnection;
import org.mobicents.slee.container.management.console.client.common.BrowseContainer;
import org.mobicents.slee.container.management.console.client.common.ControlContainer;
import org.mobicents.slee.container.management.console.client.components.info.ComponentInfo;
import org.mobicents.slee.container.management.console.client.components.info.ComponentSearchParams;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * @author Stefano Zappaterra
 * 
 */
public class ComponentSearchPanel extends Composite {

  private ComponentsServiceAsync service = ServerConnection.componentsService;

  private BrowseContainer browseContainer;

  private ControlContainer rootPanel = new ControlContainer();

  private TextBox nameBox = new TextBox();

  private TextBox idBox = new TextBox();

  private TextBox vendorBox = new TextBox();

  private TextBox versionBox = new TextBox();

  private Button searchButton = new Button("Search");

  public ComponentSearchPanel(BrowseContainer browseContainer) {
    super();

    this.browseContainer = browseContainer;

    initWidget(rootPanel);

    setData();
  }

  private void setData() {

    searchButton.addClickListener(new ClickListener() {
      public void onClick(Widget sender) {
        onSearch();
      }
    });

    rootPanel.setWidth("");

    rootPanel.setWidget(0, 0, new Label("Name"));
    rootPanel.setWidget(1, 0, new Label("ID"));
    rootPanel.setWidget(2, 0, new Label("Vendor"));
    rootPanel.setWidget(3, 0, new Label("Version"));

    rootPanel.setWidget(0, 1, nameBox);
    rootPanel.setWidget(1, 1, idBox);
    rootPanel.setWidget(2, 1, vendorBox);
    rootPanel.setWidget(3, 1, versionBox);

    rootPanel.setWidget(4, 0, searchButton);
  }

  public void onSearch() {
    ServerCallback callback = new ServerCallback(this) {

      public void onSuccess(Object result) {
        ComponentInfo[] componentInfos = (ComponentInfo[]) result;

        if (componentInfos == null || componentInfos.length == 0) {
          Logger.warning("No component found");
        }
        else {
          ComponentListPanel componentListPanel = new ComponentListPanel(browseContainer, componentInfos);
          browseContainer.add("Search results (" + componentInfos.length + ")", componentListPanel);
        }
      }
    };

    try {
      ComponentSearchParams params = new ComponentSearchParams(nameBox.getText(), idBox.getText(), vendorBox.getText(), versionBox.getText());
      service.searchComponents(params, callback);
    }
    catch (ManagementConsoleException e) {
      Logger.error(e.getMessage());
    }
  }
}
