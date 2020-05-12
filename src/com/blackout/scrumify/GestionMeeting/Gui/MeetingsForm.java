/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionMeeting.Gui;

import com.blackout.scrumify.GestionMeeting.Entities.Meeting;
import com.blackout.scrumify.GestionMeeting.Services.MeetingService;
import com.blackout.scrumify.GestionProjets.Gui.*;
import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
import com.blackout.scrumify.GestionTeams.Gui.TeamForm;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
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
 * @author AmiraDoghri
 */
public class MeetingsForm extends SideMenuBaseForm {

    public static Resources res;
    Project pp;
    public MeetingsForm(Resources res, Form previous, Project p) {
        
        super(BoxLayout.y());
        this.res = res;
        this.pp = p;
        getToolbar().setTitleCentered(true);

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);

        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        add(new Label("Meetings", "TodayTitle"));
Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY()
                )
        );


           FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Byte.MAX_VALUE);
        getToolbar().setTitleComponent(fab.bindFabToContainer(titleCmp, RIGHT, TOP));

        MeetingService mr = new MeetingService();
        Map m = mr.getResponse("afficher_meetings/"+p.getId());
        ArrayList<Meeting> listT = mr.getAllMeetings(m);
        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);

        for (int i = 0; i < listT.size(); i++) {

            Meeting mm = listT.get(i);

            Container c = new Container(BoxLayout.y());
            Label sprint = new Label();
            Label type = new Label();
            Label place = new Label();
            place.setText("place :" + mm.getPlace());
            type.setText("type :"+mm.getType());
            sprint.setText("sprint :"+mm.getSprint());
            c.setName(mm.getName());    
            addButtonBottom(arrowDown, c, mm,p);

        }
        fab.addActionListener((evt) -> {
            new AddMeeting(res,pp).show();
        });
        setupSideMenu(res);
    }

    private void addButtonBottom(Image arrowDown, Container c, Meeting m,Project p) {
        MultiButton finishLandingPage = new MultiButton(c.getName());
        finishLandingPage.setEmblem(arrowDown);
        finishLandingPage.setUIID("MeetingItem");
        finishLandingPage.setUIIDLine1("TodayEntry");
        add(BoxLayout.encloseY(finishLandingPage));
        Button gt = new Button();
        gt.addActionListener((evt) -> {
            new MeetinngDetailsForm(res, this, m,p).show();
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
        new MeetingsForm(res, this, pp).show();
    }
    
    @Override
    protected void showTeamForm(Resources res) {
        new TeamForm(res, this).show();
    }

    @Override
    protected void showTasks(Resources res) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
