/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionTasks.Gui;

import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Gui.AddProject;
import com.blackout.scrumify.GestionProjets.Gui.Dashboard;
import com.blackout.scrumify.GestionProjets.Gui.ProjectDetailsForm;
import com.blackout.scrumify.GestionProjets.Gui.ProjectsForm;
import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
import com.blackout.scrumify.GestionTasks.Entities.Tasks;
import com.blackout.scrumify.GestionTasks.Services.TasksService;
import com.blackout.scrumify.GestionTeams.Entities.Team;
import com.blackout.scrumify.GestionTeams.Gui.TeamForm;
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
import javafx.scene.layout.Priority;

/**
 *
 * @author Hidaya
 */
public class EditTask extends SideMenuBaseForm {

    SideMenuBaseForm current;

    public EditTask(Resources res, Form previous, Tasks p) {
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

        add(new Label("Edit Task", "TodayTitle"));
        TextField title = new TextField(p.getTitle(), "Title");
        TextField Description = new TextField(p.getDescription(), "Description");
        TextField priority = new TextField(p.getDescription(), "Description"); 

     

        Button btnValider = new Button("Submit");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((title.getText().length() == 0) || (Description.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {

                      
                        p.setTitle(title.getText());
                        p.setDescription(Description.getText());
                        p.setPriority(Integer.parseInt(priority.getText()));
                        if(TasksService.getInstance().editTask(p)){
                        new TasksDetailsForm(res, current, p).show();}

                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "type", new Command("OK"));
                    }

                }

            }
        });

        addAll(title, Description, priority, btnValider);

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

  @Override
    protected void showTasks(Resources res) {
         new TasksForm(res).show();

    }
}
