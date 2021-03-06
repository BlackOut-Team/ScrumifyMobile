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
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Hidaya
 */
public class TasksDetailsForm extends SideMenuBaseForm {
    SideMenuBaseForm current ;
    public TasksDetailsForm(Resources res, Form previous,Tasks p) {
        super(BoxLayout.y());
        current=this;

        getToolbar().setTitleCentered(false);
        ActionListener ev = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                 new  TasksForm(res).showBack()    ;        
        };
        };
        Image profilePic = res.getImage("task.jpg");
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
                                  new Label("Task details", "Title")
                               
                                
                        )
                ).add(BorderLayout.WEST, profilePicLabel)
                );
               getToolbar().setTitleComponent(titleCmp);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        
        add(new Label(p.getTitle(), "TodayTitle"));
            Container rightContainer = new Container(BoxLayout.y());
            rightContainer.add(new Label(p.getDescription()));
            add(rightContainer);
        setupSideMenu(res);
        
        Button edit = new Button("EDIT");
        Button archive = new Button("ARCHIVE");

        edit.setUIID("SkipButton");
        archive.setUIID("SkipButton");

        add(BoxLayout.encloseYBottom(edit,archive));
        
        edit.addActionListener((evt) -> {
                        System.out.println(p.getId());

         new EditTask(res,previous, p).show();
             
        });
        
        archive.addActionListener((evt) -> {
                        TasksService.getInstance().archive(p);
                        new TasksForm(res).show();
        });
                
    }

  
    
}
