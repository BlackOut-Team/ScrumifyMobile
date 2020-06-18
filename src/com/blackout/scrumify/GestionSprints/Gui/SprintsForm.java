/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionSprints.Gui;

import com.blackout.scrumify.GestionProjets.Gui.*;
import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
import com.blackout.scrumify.GestionSprints.Entities.Sprint;
import com.blackout.scrumify.GestionSprints.Services.ServiceSprint;
import com.blackout.scrumify.GestionTasks.Gui.TasksForm;
import com.blackout.scrumify.GestionTeams.Entities.Team;
import com.blackout.scrumify.GestionTeams.Gui.TeamForm;
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
import com.codename1.io.Preferences;
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
public class SprintsForm extends SideMenuBaseForm {

    public static Resources res;
    public Project projett;

    public SprintsForm(Resources res, Form previous, Project projet) {
        super(BoxLayout.y());
        this.res = res;

        getToolbar().setTitleCentered(false);
        projett = projet;

        Button returnButton = new Button("");
        returnButton.setUIID("Title");
        FontImage.setMaterialIcon(returnButton, FontImage.MATERIAL_ARROW_BACK);
        returnButton.addActionListener(e -> new ProjectDetailsForm(res, this, projet).showBack());

        Container Allsprints = BoxLayout.encloseY(
                new Label("All ", "CenterTitle")
        );
        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(returnButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY()
                ),
                GridLayout.encloseIn(1, Allsprints)
        );

        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Byte.MAX_VALUE);
        fab.getAllStyles().setMargin(BOTTOM, Allsprints.getPreferredH() - fab.getPreferredH() / 2);
        getToolbar().setTitleComponent(fab.bindFabToContainer(titleCmp, RIGHT, TOP));
        add(new Label("Sprints", "TodayTitle"));

        ServiceSprint pr = new ServiceSprint();
        Map m = ServiceSprint.getResponse("Sprint/sprint/" + projet.getId());
        ArrayList<Sprint> listT = pr.getAllSprints(m);
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);
        if (m != null) {
            for (int i = 0; i < listT.size(); i++) {

                Sprint p = listT.get(i);

                Container c = new Container(BoxLayout.x());

                c.setName(p.getName());
                addButtonBottom(arrowDown, c, projet, p);

            }
        } else {
            Image empty = res.getImage("landing_1.png");
            Container ct = new Container(BoxLayout.yCenter());
            ct.add(empty);
            ct.add(new Label("No  active sprints in this project !", "TodayTitle"));
            Button add = new Button("Get started");
            add.setUIID("LoginButton");
            add.addActionListener((evt) -> {
                new AddSprint(res, projet).show();
            });
            ct.add(add);
            add(FlowLayout.encloseCenterMiddle(ct));
        }
        fab.addActionListener((evt) -> {
            new AddSprint(res, projet).show();
        });

        setupSideMenu(res);
    }

    private void addButtonBottom(Image arrowDown, Container c, Project pr, Sprint p) {


        Container sprintBox = new Container(BorderLayout.absolute(), "ProjectItem");
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_LIST, "icon", 3);

       Container title = new Container(BoxLayout.x());
        title.add(icon);
        title.add(new Label(p.getName(),"TodayProject"));
       
        sprintBox.add(BorderLayout.NORTH, BoxLayout.encloseY(title, new Label(p.getDescription(), "TodayEntry"), new Label("From : " + p.getCreated() + " to : " + p.getDuedate(), "TodayEntry")));

        Button edit = new Button(FontImage.MATERIAL_EDIT, "icon");
        Button archive = new Button(FontImage.MATERIAL_ARCHIVE, "icon");
        Button view = new Button(FontImage.MATERIAL_ZOOM_IN, "icon");

        edit.addActionListener((evt) -> {
            System.out.println(p.getId());

            new EditSprint(res, this, pr, p).show();

        });
        archive.addActionListener((evt) -> {
            ServiceSprint.getInstance().archiveSprint(p);
            new SprintsForm(res, this, pr).show();
        });
        view.addActionListener((evt) -> {
            new SprintsDetailsForm(res, this, pr, p).show();
        });
         Container con = new Container(BoxLayout.xRight());

        con.addAll(view, edit, archive );
        sprintBox.add(BorderLayout.SOUTH, con);
        add(BoxLayout.encloseY(sprintBox));
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
