/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionTeams.Gui;
import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Gui.AddProject;
import com.blackout.scrumify.GestionProjets.Gui.Dashboard;
import com.blackout.scrumify.GestionProjets.Gui.ProjectDetailsForm;
import com.blackout.scrumify.GestionProjets.Gui.ProjectsForm;
import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
import com.blackout.scrumify.GestionTeams.Entities.Team;
import com.blackout.scrumify.GestionTeams.services.ServiceTeam;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
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
 * @author Iheb
 */
public class TeamForm  extends SideMenuBaseForm {
    

    public static Resources res;

    public TeamForm(Resources res, Form previous) {
        super(BoxLayout.y());
        this.res = res;
      getToolbar().setTitleCentered(false);

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);

        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        
  
        
       
        
        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY()
                ),
                GridLayout.encloseIn()
        );

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Byte.MAX_VALUE);
    
        getToolbar().setTitleComponent(fab.bindFabToContainer(titleCmp, RIGHT, TOP));
       
        
      
       
  
        add(new Label("Teams", "TodayTitle"));

        ServiceTeam pr = new ServiceTeam();
        Map m = pr.getResponse("teams");
        ArrayList<Team> listT = pr.getAllTeams(m);
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);

        for (int i = 0; i < listT.size(); i++) {

            Team p = listT.get(i);

            Container c = new Container(BoxLayout.x());

            c.setName(p.getName());
            addButtonBottom(arrowDown, c, p);

        }
        fab.addActionListener((evt) -> {
            new AddTeam(res).show();
        });
        setupSideMenu(res);
    }

    private void addButtonBottom(Image arrowDown, Container c, Team p) {
        MultiButton finishLandingPage = new MultiButton(c.getName());
        finishLandingPage.setEmblem(arrowDown);
        finishLandingPage.setUIID("ProjectItem");
        finishLandingPage.setUIIDLine1("TodayEntry");
        add(BoxLayout.encloseY(finishLandingPage));
        Button gt = new Button();
         gt.addActionListener((evt) -> {
            new TeamDetailsForm(res, this, p).show();
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

    @Override
    protected void showOtherForm(Resources res) {
        new AddProject(res).show();
    }

    @Override
    protected void showDashboard(Resources res) {
        new Dashboard(res).show();
    }

    @Override
    protected void showProjects(Resources res) {
        new ProjectsForm(res, this).show();
    }
    
    @Override
    protected void showTeamForm(Resources res) {
        new TeamForm(res, this).show();
    }

}
