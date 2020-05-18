/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */
package com.blackout.scrumify.Utils;

import com.blackout.scrumify.GestionProjets.Gui.AddProject;
import com.blackout.scrumify.GestionProjets.Gui.CalendarForm;
import com.blackout.scrumify.GestionProjets.Gui.Dashboard;
import com.blackout.scrumify.GestionProjets.Gui.ProjectsForm;
import com.blackout.scrumify.GestionTasks.Gui.TasksForm;
import com.blackout.scrumify.GestionTeams.Gui.TeamForm;
import com.blackout.scrumify.GestionUsers.gui.LoginForm;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.Layout;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Common code that can setup the side menu
 *
 * @author Shai Almog
 */
public abstract class SideMenuBaseForm extends Form {
    public SideMenuBaseForm(String title, Layout contentPaneLayout) {
        super(title, contentPaneLayout);
    }

    public SideMenuBaseForm(String title) {
        super(title);
    }

    public SideMenuBaseForm() {
    }

    public SideMenuBaseForm(Layout contentPaneLayout) {
        super(contentPaneLayout);
    }

    public void setupSideMenu(Resources res) {

        Image profilePic = res.getImage("Image3.png");
        Image mask = res.getImage("round-mask.png");
        mask = mask.scaledHeight(mask.getHeight() / 4 * 3);
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(" Hello , "+Session.u.getName(), profilePic, "SideMenuTitle");
        Button profile = new Button();
        profilePicLabel.setMask(mask.createMask());
        Container sidemenuTop = BorderLayout.center(profilePicLabel);
        sidemenuTop.setUIID("SidemenuTop");
        sidemenuTop.setLeadComponent(profile);
        getToolbar().addComponentToSideMenu(sidemenuTop);
       // getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_BACK, e -> goBack(res, new Form()));
        getToolbar().addMaterialCommandToSideMenu("  Dashboard", FontImage.MATERIAL_DASHBOARD, e -> showDashboard(res));
        getToolbar().addMaterialCommandToSideMenu("  Projects", FontImage.MATERIAL_VIEW_LIST, e -> showProjects(res));
        getToolbar().addMaterialCommandToSideMenu("  Calendar", FontImage.MATERIAL_CALENDAR_TODAY, e -> showCalendar(res));
        getToolbar().addMaterialCommandToSideMenu("  Tasks", FontImage.MATERIAL_ACCESS_TIME, e -> showTasks(res));
        getToolbar().addMaterialCommandToSideMenu("  Activity", FontImage.MATERIAL_TRENDING_UP, e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Teams", FontImage.MATERIAL_GROUP, e -> showTeamForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Account Settings", FontImage.MATERIAL_SETTINGS, e -> showOtherForm(res));
        getToolbar().addMaterialCommandToSideMenu("  Logout", FontImage.MATERIAL_EXIT_TO_APP, e -> new LoginForm(res).show());

        profile.addActionListener((evt) -> {

            //returns session 
            //Object result =Storage.getInstance().readObject("session");
        });
    }

    protected void showOtherForm(Resources res) {
        new AddProject(res).show();
    }

    protected void showDashboard(Resources res) {
        new Dashboard(res).show();
    }

    protected void showProjects(Resources res) {
        new ProjectsForm(res, this).show();

        /* DropboxAccess.setConsumerKey("4wwgb8kt70pr31r");
        DropboxAccess.setConsumerSecret("rhm6b48dzsl166g");
        DropboxAccess.getInstance().showAuthentication(new ActionListener() {
            @Override

            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource() != null) {
                    Dropbox drop = new Dropbox();
                    drop.showDropboxFilePicker(true);
                }
            }

        });*/
    }
    protected void showCalendar(Resources res) {
             new CalendarForm(res).show();

    }
    protected void showTeamForm(Resources res) {
        new TeamForm(res, this).show();
    }

    protected void showTasks(Resources res) {
        new TasksForm(res).show();
    }


}
