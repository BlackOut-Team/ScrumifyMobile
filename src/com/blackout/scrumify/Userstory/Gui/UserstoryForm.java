/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.Userstory.Gui;

import com.blackout.scrumify.Feature.Gui.*;
import com.blackout.scrumify.Feature.Entities.Features;
import com.blackout.scrumify.Feature.services.ServiceFeature;
import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Gui.AddProject;
import com.blackout.scrumify.GestionTeams.Gui.AddTeam;
import com.blackout.scrumify.Userstory.Entities.UserStory;
import com.blackout.scrumify.Userstory.Services.ServiceUserstory;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.components.FloatingActionButton;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.RIGHT;
import static com.codename1.ui.Component.TOP;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Amine
 */
public class UserstoryForm extends SideMenuBaseForm {

    public static Resources res;

    public UserstoryForm(Resources res, Form previous) {
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

        add(new Label("Userstories", "TodayTitle"));
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);

        ServiceUserstory pr = new ServiceUserstory();
        Map m = pr.getResponse("allUserstory");
        if (m != null) {
            ArrayList<UserStory> listT = pr.getAllUserstory(m);
            System.out.println(listT);
            if(!listT.isEmpty()){
            for (int i = 0; i < listT.size(); i++) {

                UserStory p = listT.get(i);

                Container c = new Container(BoxLayout.x());

                addButtonBottom(p);

            }
            }else{
                 Image empty = res.getImage("landing_1.png").scaledSmallerRatio(Display.getInstance().getDisplayWidth() / 3, Display.getInstance().getDisplayHeight() / 4);

                Container ct = new Container(BoxLayout.yCenter());
                ct.add(empty);
                ct.add(new Label("No userstories yet !", "TodayTitle"));
                Button add = new Button("Get started");
                add.setUIID("LoginButton");
                add.addActionListener((evt) -> {
                    new AddUserstory(res).show();
                });
                ct.add(add);
                add(FlowLayout.encloseCenterMiddle(ct));
            }

        } else {
            System.out.println("null");
        }
        setupSideMenu(res);
        fab.addActionListener((evt) -> {
            new AddUserstory(res).show();
        });
    }

    UserstoryForm(Resources res, Form previous, Features p) {
    }

    UserstoryForm(Resources res, AddFeature aThis, Project projet) {
    }

    private void addButtonBottom(UserStory p) {
        FontImage icon = FontImage.createMaterial(FontImage.MATERIAL_LIST, "icon", 3);
        Container featureBox = new Container(BorderLayout.absolute(), "ProjectItem");

        Container title = new Container(BoxLayout.x());
        title.add(icon);
        title.add(new Label(p.getDescription(), "TodayProject"));

        featureBox.add(BorderLayout.NORTH, BoxLayout.encloseY(title));

        Button edit = new Button(FontImage.MATERIAL_EDIT, "icon");
        Button archive = new Button(FontImage.MATERIAL_ARCHIVE, "icon");
        Button view = new Button(FontImage.MATERIAL_ZOOM_IN, "icon");
        edit.addActionListener((evt) -> {
            System.out.println(p.getId());

        });

        archive.addActionListener((evt) -> {
            ServiceUserstory.getInstance().archiveUserstory(p);
            new UserstoryForm(res, this).show();
        });
     
        Container con = new Container(BoxLayout.xRight());

        con.addAll(view, edit, archive);
        featureBox.add(BorderLayout.SOUTH, con);
        add(BoxLayout.encloseY(featureBox));
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
