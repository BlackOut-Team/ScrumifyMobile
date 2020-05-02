/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionProjets.Gui;

import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
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
public class ProjectDetailsForm extends SideMenuBaseForm {
    SideMenuBaseForm current ;
    public ProjectDetailsForm(Resources res, Form previous,Project p) {
        super(BoxLayout.y());
        current=this;

        getToolbar().setTitleCentered(false);
        ActionListener ev = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                 new  ProjectsForm(res,current).showBack()    ;        
        };
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
                                  new Label("Created : " + p.getCreated()+"", "SubTitle"),
                                  new Label("Dudedate: " + p.getDuedate()+"", "SubTitle")
                        )
                ).add(BorderLayout.WEST, profilePicLabel)
                );
               getToolbar().setTitleComponent(titleCmp);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        
        add(new Label(p.getName(), "TodayTitle"));
            Container rightContainer = new Container(BoxLayout.y());
            rightContainer.add(new Label(p.getDescription()));
            rightContainer.add(new Label(p.getTeam_id() + ""));
            add(rightContainer);
        setupSideMenu(res);
        
        Button edit = new Button("EDIT PROJECT");
        Button archive = new Button("ARCHIVE");

        edit.setUIID("LoginButton");
        archive.setUIID("LoginButton");

        add(BoxLayout.encloseYBottom(edit,archive));
        
        edit.addActionListener((evt) -> {
                        System.out.println(p.getId());

         new EditProject(res,previous, p).show();
             
        });
        
        archive.addActionListener((evt) -> {
                        ServiceProjet.getInstance().archiveProject(p);
                        new ProjectsForm(res, current).show();
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
  
}
