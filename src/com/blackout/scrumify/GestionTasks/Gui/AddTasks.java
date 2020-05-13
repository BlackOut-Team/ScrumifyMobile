/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionTasks.Gui;


import com.blackout.scrumify.GestionProjets.Gui.AddProject;
import com.blackout.scrumify.GestionProjets.Gui.Dashboard;
import com.blackout.scrumify.GestionProjets.Gui.ProjectsForm;
import com.blackout.scrumify.GestionTasks.Entities.Tasks;
import com.blackout.scrumify.GestionTasks.Services.TasksService;
import com.blackout.scrumify.GestionTeams.Gui.TeamForm;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import com.codename1.ui.validation.LengthConstraint;
import com.codename1.ui.validation.NumericConstraint;
import com.codename1.ui.validation.RegexConstraint;
import com.codename1.util.Base64;
//import javax.xml.validation.Validator;

/**
 *
 * @author Hidaya
 */
public class AddTasks extends SideMenuBaseForm {
 SideMenuBaseForm current;
 public static int id;
    public AddTasks(Resources res) {
        current = this ;
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

        add(new Label("Add a Task", "TodayTitle"));
        TextField title = new TextField("", "Title");
        TextField description = new TextField("", "Title");
        TextField priority = new TextField("", "priority");
        
        Button btnValider = new Button("Add ");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((title.getText().length() == 0)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {

                  

                       
                       Tasks t = new Tasks(title.getText(),description.getText(),Integer.parseInt(priority.getText()));
                        String accountSID ="AC281a4050a2fa765a6416e63c72e4a429";
                String authToken="96a86e815be268272e998a84f59b7669";
Response<java.util.Map> result = Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + "/Messages.json").
                queryParam("To", "+21623668213").
     queryParam("From","+12058468149").
     queryParam("Body", "Felicitation  Votre Tache  a éte ajouter \n Rendez Vous Sur Notre Site WWW.scrumify.com  \n Bonne Journée.").
        header("Authorization", "Basic " + Base64.encodeNoNewline((accountSID + ":" + authToken).getBytes())).
        getAsJsonMap();
                        if(TasksService.getInstance().addTask(t)){
                               
                        new TasksForm(res).show();
                        }
                       
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "type", new Command("OK"));
                    }

                }

            }
        });

        addAll(title,description,priority, btnValider);

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
