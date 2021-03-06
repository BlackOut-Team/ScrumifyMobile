/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionProjets.Gui;

import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
import com.blackout.scrumify.GestionTasks.Gui.TasksForm;
import com.blackout.scrumify.GestionTeams.Entities.Team;
import com.blackout.scrumify.GestionTeams.Gui.TeamForm;
import com.blackout.scrumify.GestionTeams.services.ServiceTeam;
import com.blackout.scrumify.Utils.Session;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ToastBar;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.RIGHT;
import static com.codename1.ui.Component.TOP;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.spinner.Picker;
import java.util.ArrayList;
import java.util.Map;
import com.codename1.ui.util.Resources;

/**
 *
 * @author AmiraDoghri
 */
public class AddProject extends SideMenuBaseForm {

    SideMenuBaseForm current;
    public static int id;

    public AddProject(Resources res) {
        current = this;
        setTitle("Scrumify");
        setLayout(BoxLayout.y());
        getToolbar().setTitleCentered(false);

        Button returnButton = new Button("");
        returnButton.setUIID("Title");
        FontImage.setMaterialIcon(returnButton, FontImage.MATERIAL_ARROW_BACK);
        returnButton.addActionListener(e -> new ProjectsForm(res, current).showBack());

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(returnButton)
        );
        getToolbar().setTitleComponent(titleCmp);

        setupSideMenu(res);

        add(new Label("Add a project", "TodayTitle"));
        TextField tfName = new TextField("Project Name", "Project Name");
        TextField tfDescription = new TextField("Description", "Description");
        Picker tfDuedate = new Picker();

        tfDuedate.setFormatter(new SimpleDateFormat("MM-dd-yyyy"));
        ComboBox<String> team = new ComboBox<String>();
        team.setActAsSpinnerDialog(true);

        ServiceTeam ser = new ServiceTeam();

        Map x = ser.getResponse("affteam/" + Session.u.getId());
        ArrayList<Team> listT = ser.getAllTeams(x);
        for (Team ev : listT) {
            team.addItem(ev.getName());
        }

        Button btnValider = new Button("Add Project");
        btnValider.setUIID("LoginButton");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0) || (tfDescription.getText().length() == 0) || (tfDuedate.getValue() == null)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {

                        int ind = team.getSelectedIndex();
                        Team te = listT.get(ind);
                        Project t = new Project(tfName.getText(), tfDescription.getText(), tfDuedate.getText(), tfDuedate.getText(), 1, te.getId(), 1, 1);
                        if (ServiceProjet.getInstance().addProject(t)) {
                            ToastBar.getInstance().setPosition(TOP);

                            ToastBar.Status status = ToastBar.getInstance().createStatus();
                            status.setIcon(res.getImage("scrumify.png").scaledSmallerRatio(Display.getInstance().getDisplayWidth() / 10, Display.getInstance().getDisplayWidth() / 15));

                            status.setMessage("Project added successfully");
                            status.setExpires(3000);  // only show the status for 3 seconds, then have it automatically clear
                            status.show();
                            new ProjectsForm(res, current).show();
                        }

                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "type", new Command("OK"));
                    }

                }

            }
        });
        add(FlowLayout.encloseCenterMiddle(BoxLayout.encloseY(tfName, tfDescription, tfDuedate, team, btnValider)));

    }

//    @Override
//    protected void goBack(Resources res, Form previous) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
