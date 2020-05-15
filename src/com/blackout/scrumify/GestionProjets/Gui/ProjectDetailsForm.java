/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionProjets.Gui;

import com.blackout.scrumify.GestionMeeting.Gui.MeetingsForm;
import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
import com.blackout.scrumify.GestionSprints.Gui.SprintsForm;
import com.blackout.scrumify.GestionTasks.Gui.TasksForm;
import com.blackout.scrumify.GestionTeams.Entities.Team;
import com.blackout.scrumify.GestionTeams.Gui.TeamDetailsForm;
import com.blackout.scrumify.GestionTeams.Gui.TeamForm;
import com.blackout.scrumify.GestionTeams.services.ServiceTeam;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.ui.Button;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import java.util.Map;

/**
 *
 * @author AmiraDoghri
 */
public class ProjectDetailsForm extends SideMenuBaseForm {

    SideMenuBaseForm current;

    public ProjectDetailsForm(Resources res, Form previous, Project p) {
        super(BoxLayout.y());
        current = this;
        getToolbar().setTitleCentered(false);
        ActionListener ev = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new ProjectsForm(res, current).showBack();
            }
        ;
        };
        Image profilePic = res.getImage("scrumify.png");
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth() / 2, mask.getHeight() / 2);
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label(p.getName(), "Title"),
                                new Label("Created : " + p.getCreated() + "", "SubTitle"),
                                new Label("Dudedate: " + p.getDuedate() + "", "SubTitle")
                        )
                ).add(BorderLayout.WEST, profilePicLabel)
        );
        getToolbar().setTitleComponent(titleCmp);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        add(new Label(p.getName(), "TodayTitle"));
        Container rightContainer = new Container(BoxLayout.y());
        rightContainer.add(new Label("Description : " + p.getDescription()));
        ServiceTeam s = new ServiceTeam();
        System.out.println(p.getTeam_id());
        Map m=s.getResponse("gett/"+p.getTeam_id());
        Team tt =s.getTeam(m);
        Label team = new Label("Team : " + tt.getName());
        team.setUIID("WalkthruTab1");
        Container t = new Container();
        t.add( team);
         Button showt = new Button();
        showt.addActionListener((evt) -> {
            new TeamDetailsForm(res, current, tt);
        });
        t.setLeadComponent(showt);
        
        rightContainer.add(t);
        add(rightContainer);
        setupSideMenu(res);

        Button edit = new Button(FontImage.MATERIAL_EDIT);
        Button archive = new Button(FontImage.MATERIAL_ARCHIVE);
        Button sprints = new Button(FontImage.MATERIAL_PAGES);
        Button meeting = new Button(FontImage.MATERIAL_MEETING_ROOM);

        edit.setUIID("ActionButton");
        archive.setUIID("ActionButton");
        sprints.setUIID("LoginButton");
        meeting.setUIID("LoginButton");

       
        add(BoxLayout.encloseXRight(edit, archive, sprints, meeting));

        edit.addActionListener((evt) -> {
            System.out.println(p.getId());

            new EditProject(res, previous, p).show();

        });

        archive.addActionListener((evt) -> {
            ServiceProjet.getInstance().archiveProject(p);
            new ProjectsForm(res, current).show();
        });
        meeting.addActionListener((evt) -> {
            System.out.println(p.getId());

            new MeetingsForm(res, previous, p).show();

        });

        sprints.addActionListener((evt) -> {
            new SprintsForm(res, current, p).show();
        });
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

    @Override
    protected void showTasks(Resources res) {
        new TasksForm(res).show();
    }

}
