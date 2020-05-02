/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionProjets.Gui;

import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
import com.blackout.scrumify.GestionTeams.Entities.Team;
import com.blackout.scrumify.GestionTeams.services.ServiceTeam;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author AmiraDoghri
 */
public class EditProject extends SideMenuBaseForm {

    SideMenuBaseForm current;

    public EditProject(Resources res, Form previous, Project p) {
        current = this;
        setTitle("Scrumify");
        setLayout(BoxLayout.y());
        getToolbar().setTitleCentered(false);
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);

        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton)
        );
        getToolbar().setTitleComponent(titleCmp);

        setupSideMenu(res);

        add(new Label("Edit project", "TodayTitle"));
        TextField tfName = new TextField(p.getName(), "Project Name");
        TextField tfDescription = new TextField(p.getDescription(), "Description");
        Picker tfDuedate = new Picker();
        tfDuedate.setFormatter(new SimpleDateFormat("dd/MM/yyyy"));
        tfDuedate.setText(p.getDuedate());
        ComboBox<String> team = new ComboBox<String>();
        ServiceTeam ser = new ServiceTeam();

        Map x = ser.getResponse("teams");
        ArrayList<Team> listT = ser.getAllTeams(x);
        for (Team ev : listT) {
            team.addItem(ev.getName());
        }

        Button btnValider = new Button("Submit");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0) || (tfDescription.getText().length() == 0) || (tfDuedate.getValue() == null)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {

                        int ind = team.getSelectedIndex();
                        Team te = listT.get(ind);
                        p.setName(tfName.getText());
                        p.setDescription(tfDescription.getText());
                        p.setDuedate(tfDuedate.getText());
                        p.setTeam_id(te.getId());
                        ServiceProjet.getInstance().editProject(p);
                        new ProjectDetailsForm(res, current, p).show();

                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "type", new Command("OK"));
                    }

                }

            }
        });

        addAll(tfName, tfDescription, tfDuedate, team, btnValider);

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

}
