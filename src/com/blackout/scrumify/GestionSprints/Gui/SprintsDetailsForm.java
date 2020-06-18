/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionSprints.Gui;

import com.blackout.scrumify.Feature.Gui.AfficherFeature;
import com.blackout.scrumify.GestionProjets.Gui.*;
import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
import com.blackout.scrumify.GestionSprints.Entities.Sprint;
import com.blackout.scrumify.GestionSprints.Services.ServiceSprint;
import com.blackout.scrumify.GestionTasks.Gui.TasksForm;
import com.blackout.scrumify.GestionTeams.Gui.TeamForm;
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

/**
 *
 * @author AmiraDoghri
 */
public class SprintsDetailsForm extends SideMenuBaseForm {

    SideMenuBaseForm current;
    public Project projet;

    public SprintsDetailsForm(Resources res, Form previous, Project pr, Sprint p) {

        super(BoxLayout.y());
        current = this;
        projet = pr;
        getToolbar().setTitleCentered(false);

        Button returnButton = new Button("");
        returnButton.setUIID("Title");
        FontImage.setMaterialIcon(returnButton, FontImage.MATERIAL_ARROW_BACK);
        returnButton.addActionListener(e -> new SprintsForm(res, current, pr).showBack());

        Container titleCmp = BoxLayout.encloseY(
                BorderLayout.west(returnButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label(p.getName(), "Title"),
                                new Label("Created : " + p.getCreated() + "", "SubTitle"),
                                new Label("Dudedate: " + p.getDuedate() + "", "SubTitle")
                        )
                )
        );
        getToolbar().setTitleComponent(titleCmp);
        add(new Label(p.getName(), "TodayTitle"));
        Container rightContainer = new Container(BoxLayout.y());
        rightContainer.add(new Label(p.getDescription()));
        add(rightContainer);
        setupSideMenu(res);
        Button button = new Button("Features");

        button.setUIID("ActionButton");

        add(BoxLayout.encloseYBottom(button));

        button.addActionListener((evt) -> {
            System.out.println(p.getId());

            new AfficherFeature(res,this).show();

        });

    }

}
