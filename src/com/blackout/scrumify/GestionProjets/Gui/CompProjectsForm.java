/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionProjets.Gui;

import com.blackout.scrumify.Dropbox.Dropbox;
import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
import com.blackout.scrumify.GestionTasks.Gui.TasksForm;
import com.blackout.scrumify.GestionTeams.Gui.TeamForm;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.blackout.scrumify.Dropbox.DropboxAccess;
import com.blackout.scrumify.GestionUsers.entities.User;
import com.blackout.scrumify.Utils.Session;
import com.blackout.scrumify.Utils.Statics;
import com.codename1.io.Preferences;
import com.codename1.io.Storage;
import com.codename1.ui.Button;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import java.util.ArrayList;
import java.util.Map;
import com.codename1.ui.util.Resources;
import java.util.Collection;

/**
 *
 * @author AmiraDoghri
 */
public class CompProjectsForm extends SideMenuBaseForm {

    public static Resources res;
    ServiceProjet pr = new ServiceProjet();
    Map m;
    ArrayList<Project> listT;
    SideMenuBaseForm current ;
    SideMenuBaseForm previous ;

    public CompProjectsForm(Resources res, Form previous) {
        super(BoxLayout.y());
        this.res = res;
        current=this;
        this.previous=(SideMenuBaseForm) previous;
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
        all.addActionListener((evt) -> {

        new ProjectsForm(res, current).show();
        });
        curr.addActionListener((evt) -> {
            

           new CurrProjectsForm(res, current).show();

        });
       
        currentProjects.setLeadComponent(curr);
        completedProjects.setLeadComponent(comp);
        Allprojects.setLeadComponent(all);
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
        m = ServiceProjet.getResponse("Project/showCo/" + Preferences.get("user", 0));

        listT = pr.getAllProjects(m);

        for (int i = 0; i < listT.size(); i++) {

            Project p = listT.get(i);

            Container c = new Container(BoxLayout.x());

            c.setName(p.getName());
            addButtonBottom(arrowDown, c, p);

        }

        completedProjects.setUIID("CompletedTasks");

    }

    private void addButtonBottom(Image arrowDown, Container c, Project p) {
        MultiButton finishLandingPage = new MultiButton(c.getName());
        finishLandingPage.setEmblem(arrowDown);
        finishLandingPage.setUIID("ProjectItem");
        finishLandingPage.setUIIDLine1("TodayEntry");
        add(BoxLayout.encloseY(finishLandingPage));
        Button gt = new Button();
        gt.addActionListener((evt) -> {
            new ProjectDetailsForm(res, this, p).show();
        });
        finishLandingPage.setLeadComponent(gt);
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

//    @Override
//    protected void goBack(Resources res, Form previous) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

   

 
}