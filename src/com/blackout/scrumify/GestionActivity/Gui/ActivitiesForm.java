/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionActivity.Gui;

import com.blackout.scrumify.GestionActivity.Entities.Activity;
import com.blackout.scrumify.GestionActivity.Services.ActivityService;
import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionTeams.Gui.AddTeam;
import com.blackout.scrumify.Utils.Session;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.components.MultiButton;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
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
public class ActivitiesForm extends SideMenuBaseForm {

    public static Resources res;
    Project pp;
    ActivityService pr = new ActivityService();

    public ActivitiesForm(Resources res) {
        this.res = res;
        getToolbar().setTitleCentered(false);

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);

        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(new Label("Activities"))
                ),
                GridLayout.encloseIn()
        );

        getToolbar().setTitleComponent(titleCmp);

        // add(new Label("Activities", "TodayTitle"));
        Map m = ActivityService.getResponse("afficher_activite?id=" + Session.u.getId());
        if (m != null) {
            ArrayList<Activity> listT = pr.getAllActivities(m);
            FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_NOTIFICATIONS, "Label", 3);

            for (int i = 0; i < listT.size(); i++) {

                Activity p = listT.get(i);

                Container c = new Container(BoxLayout.x());
                Map m1 = ActivityService.getResponse("connect/account/u?id=" + p.getUser_id());
                c.setName(pr.geAcOw(m1).getUsername() + " " + p.getAction());
                addButtonBottom(arrowDown, c, p);

            }
        } else {
            Image empty = res.getImage("landing_1.png");

            Container ct = new Container(BoxLayout.yCenter());
            ct.add(empty);
            ct.add(new Label("No activity from your teams yet !", "TodayTitle"));

            add(FlowLayout.encloseCenterMiddle(ct));

        }

        setupSideMenu(res);

    }

    private void addButtonBottom(Image arrowDown, Container c, Activity p) {
        FontImage arrowDown1 = FontImage.createMaterial(FontImage.MATERIAL_NOTIFICATIONS, "icon", 3);
        MultiButton finishLandingPage = new MultiButton(c.getName());
        finishLandingPage.setEmblemPosition("West");
        finishLandingPage.setUIID("MeetingItem");
        finishLandingPage.setUIIDLine1("TodayEntry");
        Button del = new Button(FontImage.MATERIAL_DELETE);
        del.setUIID("icon");
        del.addActionListener((evt) -> {
            Dialog conf = new Dialog("Delete activity?");
            conf.add("Confirm please");
            Button ok = new Button(new Command("OK"));
            Button cancel = new Button(new Command("Cancel"));
            ok.addActionListener((evt1) -> {
                pr.deleteActivity(p);
                new ActivitiesForm(res).showActivities(res);
                repaint();
                refreshTheme();
            });

            cancel.addActionListener((evt2) -> {
                conf.dispose();
            });
            conf.add(ok);
            conf.add(cancel);

            conf.showDialog();
        });

        Button view = new Button(FontImage.MATERIAL_VISIBILITY);
        view.setUIID("icon");
        view.addActionListener((evt) -> {
          pr.viewActivity(p);
          view.setVisible(false);
          finishLandingPage.setEmblem(arrowDown);
        });
        Container con = new Container(BoxLayout.x()) ;
        if(p.getViewed()==0){
        con.add(finishLandingPage);
        con.add(view,del);
        finishLandingPage.setEmblem(arrowDown1);
        }
        else {
        con.add(finishLandingPage);
        con.add(del);
        finishLandingPage.setEmblem(arrowDown);
        }
        Container cont =  BoxLayout.encloseY(con);
        add(cont);

    }

}
