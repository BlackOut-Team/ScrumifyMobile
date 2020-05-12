/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionMeeting.Gui;

import com.blackout.scrumify.GestionMeeting.Entities.Meeting;
import com.blackout.scrumify.GestionMeeting.Services.MeetingService;
import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Gui.AddProject;
import com.blackout.scrumify.GestionProjets.Gui.Dashboard;
import com.blackout.scrumify.GestionProjets.Gui.EditProject;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.io.ConnectionRequest;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
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
 * @author RAMI
 */
public class MeetinngDetailsForm  extends SideMenuBaseForm{
SideMenuBaseForm current ;
     public MeetinngDetailsForm(Resources res, Form previous,Meeting m,Project p) {

        super(BoxLayout.y());
        current=this;

        getToolbar().setTitleCentered(false);
        ActionListener ev = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                 new  MeetingsForm(res,current,p).showBack()    ;        
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
                                  new Label("meetingDate : " + m.getMeetingDate()+"", "SubTitle")
                        )
                ).add(BorderLayout.WEST, profilePicLabel)
                );
               getToolbar().setTitleComponent(titleCmp);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        
        add(new Label(p.getName(), "TodayTitle"));
            Container rightContainer = new Container(BoxLayout.y());
            rightContainer.add(new Label(m.getType()));
            rightContainer.add(new Label(m.getPlace() + ""));
            add(rightContainer);
        setupSideMenu(res);
        
        Button edit = new Button("EDIT Meeting");
        Button delete = new Button("Delete");

        edit.setUIID("LoginButton");
        delete.setUIID("LoginButton");

        add(BoxLayout.encloseYBottom(edit,delete));
        
        edit.addActionListener((evt) -> {
                        System.out.println(p.getId());

         new EditProject(res,previous, p).show();
             
        });
        
        delete.addActionListener((evt) -> {
                        MeetingService.getInstance().deleteMeeting(m);
                        new MeetingsForm(res, current,p).show();
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

    protected void showProjects(Resources res, Project p) {
        new MeetingsForm(res, this ,p).show();
    }

    @Override
    protected void showProjects(Resources res) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void showTeamForm(Resources res) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void showTasks(Resources res) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
  
    
}
