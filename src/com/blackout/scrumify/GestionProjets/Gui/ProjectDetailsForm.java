/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionProjets.Gui;

import com.blackout.scrumify.GestionMeeting.Gui.MeetingsForm;
import com.blackout.scrumify.GestionProjets.Entities.Project;
import static com.blackout.scrumify.GestionProjets.Gui.ProjectsForm.res;
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
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.TOP;
import com.codename1.ui.Display;
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

        ServiceTeam s = new ServiceTeam();
        Map m = s.getResponse("gett/" + p.getTeam_id());
        Team tt = s.getTeam(m);

        Container te = FlowLayout.encloseCenter(new Label("Team : " + tt.getName(), "SideMenuTitle"));

        Button returnButton = new Button("");
        returnButton.setUIID("Title");
        FontImage.setMaterialIcon(returnButton, FontImage.MATERIAL_ARROW_BACK);
        returnButton.addActionListener(e -> new ProjectsForm(res, current).showBack());

        Container titleCmp = BoxLayout.encloseY(
                BorderLayout.west(returnButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label(p.getName(), "Title"),
                                te
                        )
                )
        );
        Button showt = new Button();
        showt.addActionListener((evt) -> {
            new TeamDetailsForm(res, current, tt).show();
        });
        te.setLeadComponent(showt);

        getToolbar().setTitleComponent(titleCmp);

        add(new Label("Project Details ", "TodayTitle"));

        add(new Label("Created : " + p.getCreated(), "Label"));
        add(new Label("Duedate : " + p.getDuedate(), "Label"));
        add(new Label("Description : " + p.getDescription(), "Label"));

        add(new Label("More ", "TodayTitle"));
        Button meetings = new Button("Meetings");

        meetings.setUIID("ActionButton");

        Button sprints = new Button("Sprints");

        sprints.setUIID("ActionButton");

        add(BoxLayout.encloseYBottom(meetings, sprints));

        meetings.addActionListener((evt) -> {
            System.out.println(p.getId());

            new MeetingsForm(res, this, p).show();

        });

        sprints.addActionListener((evt) -> {
            new SprintsForm(res, current, p).show();
        });


        setupSideMenu(res);

    }

}
