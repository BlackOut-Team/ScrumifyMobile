/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionProjets.Gui;

import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.blackout.scrumify.GestionTeams.Entities.Team;
import com.blackout.scrumify.GestionTeams.Gui.TeamDetailsForm;
import com.blackout.scrumify.GestionTeams.services.ServiceTeam;
import com.blackout.scrumify.Utils.Session;
import com.codename1.ui.Button;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import java.util.ArrayList;
import java.util.Map;
import com.codename1.ui.util.Resources;

/**
 *
 * @author AmiraDoghri
 */
public class ProjectsForm extends SideMenuBaseForm {

    public static Resources res;
    ServiceProjet pr = new ServiceProjet();
    Map m;
    ArrayList<Project> listT;
    SideMenuBaseForm current;

    public ProjectsForm(Resources res, Form previous) {
        super(BoxLayout.y());
        this.res = res;
        current = this;
        getToolbar().setTitleCentered(false);

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);

        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Container Allprojects = BoxLayout.encloseY(
                new Label("All ", "CenterTitle")
        );
        Allprojects.setUIID("RemainingTasks");
        Container currentProjects = BoxLayout.encloseY(
                new Label("Current", "CenterTitle")
        );
        currentProjects.setUIID("Current");
        Container completedProjects = BoxLayout.encloseY(
                new Label("Completed", "CenterTitle")
        );
        completedProjects.setUIID("CompletedTasks");

        Button all = new Button();
        Button comp = new Button();
        Button curr = new Button();
        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY()
                ),
                GridLayout.encloseIn(3, Allprojects, currentProjects, completedProjects)
        );
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Byte.MAX_VALUE);
        fab.getAllStyles().setMargin(BOTTOM, completedProjects.getPreferredH() - fab.getPreferredH() / 2);
        getToolbar().setTitleComponent(fab.bindFabToContainer(titleCmp, RIGHT, TOP));
        add(new Label("Projects", "TodayTitle"));

        fab.addActionListener((evvt) -> {
            new AddProject(res).show();
        });
        setupSideMenu(res);

        curr.addActionListener((evt) -> {

            new CurrProjectsForm(res, current).show();;

        });
        comp.addActionListener((evt) -> {
            new CompProjectsForm(res, current).show();;

        });

        currentProjects.setLeadComponent(curr);
        completedProjects.setLeadComponent(comp);
        Allprojects.setLeadComponent(all);
        m = ServiceProjet.getResponse("Project/showP/" + Session.u.getId());

       if(m != null ){

        listT = pr.getAllProjects(m);
        for (int i = 0; i < listT.size(); i++) {

            Project p = listT.get(i);

            addProjectBox(p);

        }
        }
        else
        {
                    Image empty = res.getImage("landing_1.png");
                 
                    Container ct = new Container(BoxLayout.yCenter());
                    ct.add(empty );
                    ct.add(new Label("No active project !","TodayTitle"));
                    Button add = new Button("Get started");
                    add.setUIID("LoginButton");
                    add.addActionListener((evt) -> {
                        new AddProject(res).show();
                    });
                    ct.add(add);
                    add(FlowLayout.encloseCenterMiddle(ct));

        }

    }

    private void addProjectBox(Project p) {
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_MORE_HORIZ, "Label", 6);
        MultiButton projectBox = new MultiButton(p.getName());

        projectBox.setEmblem(arrowDown);
        projectBox.setUIID("ProjectItem");
        projectBox.setUIIDLine1("TodayEntry");
        projectBox.setUIIDLine2("TodayEntry");

        projectBox.setTextLine2(p.getDescription());
        projectBox.setTextLine4("From : " + p.getCreated() + "to : " + p.getDuedate());
        ServiceTeam s = new ServiceTeam();
        Map m = s.getResponse("gett/" + p.getTeam_id());
        Team tt = s.getTeam(m);
        projectBox.setTextLine3(tt.getName());
        Button gt = new Button();
        gt.addActionListener((evt) -> {
            new ProjectDetailsForm(res, current, p).show();
        });
        Button showt = new Button();
        showt.addActionListener((evt) -> {
            new TeamDetailsForm(res, current, tt).show();
        });
       // projectBox.getTextLine3().setLeadComponent(showt);
        
        
        projectBox.setLeadComponent(gt);
        add(BoxLayout.encloseY(projectBox));

    }

    private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if (first) {
            y = height / 6 + 1;
        }
        g.drawLine(height / 2, y, height / 2, height);
        g.drawLine(height / 2 - 1, y, height / 2 - 1, height);
        g.setColor(color);
        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
        return img;
    }

}
