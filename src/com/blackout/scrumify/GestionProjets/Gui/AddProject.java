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
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 *
 * @author AmiraDoghri
 */
public class AddProject extends Form {

    public AddProject(Form previous) {
        setTitle("Add a new project");
        setLayout(BoxLayout.y());

        TextField tfName = new TextField("", "Project Name");
        TextField tfDescription = new TextField("", "Description");
        Picker tfDuedate = new Picker();
        tfDuedate.setFormatter(new SimpleDateFormat("yyyy-mm-dd"));
        ComboBox<String> team = new ComboBox<String>();
        ServiceTeam ser = new ServiceTeam();

        Map x = ser.getResponse("teams");
        ArrayList<Team> listT = ser.getAllTeams(x);
        for (Team ev : listT) {
            team.addItem(ev.getName());
        }

        Button btnValider = new Button("Add task");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0) || (tfDescription.getText().length() == 0) || (tfDuedate.getValue() == null)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {

                        Date c = tfDuedate.getDate();
                        Date d = tfDuedate.getDate();
                        int ind = team.getSelectedIndex();
                        // Team t = listT.get(ind);
                        System.out.println(c);
                        Project t = new Project(tfName.getText(), tfDescription.getText(), c, d, 1);
                        System.out.println(t);
                        if (ServiceProjet.getInstance().addProject(t)) {
                            Dialog.show("Success", "Connection accepted", new Command("OK"));
                        } else {
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }

                }

            }
        });

        addAll(tfName, tfDescription, tfDuedate, team, btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e -> previous.showBack());

    }

}
