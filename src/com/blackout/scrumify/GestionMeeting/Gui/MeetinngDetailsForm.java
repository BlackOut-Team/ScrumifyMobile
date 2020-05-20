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
import com.blackout.scrumify.GestionProjets.Gui.ProjectDetailsForm;
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
        
             Button returnButton = new Button("");
        returnButton.setUIID("Title");
        FontImage.setMaterialIcon(returnButton, FontImage.MATERIAL_ARROW_BACK);
        returnButton.addActionListener(e -> new MeetingsForm(res, current,p).showBack());
        
        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(returnButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                  new Label(p.getName(), "Title"),
                                  new Label("meetingDate : " + m.getMeetingDate()+"", "SubTitle")
                        )
                ).add(BorderLayout.WEST, profilePicLabel)
                );
               getToolbar().setTitleComponent(titleCmp);
        
        add(new Label(p.getName(), "TodayTitle"));
            Container rightContainer = new Container(BoxLayout.y());
            rightContainer.add(new Label( "Name : " +m.getName()));
            rightContainer.add(new Label( "Type : " +m.getType()));
            rightContainer.add(new Label( "place : " +m.getPlace()));
            add(rightContainer);
        setupSideMenu(res);
        
        Button edit = new Button("EDIT Meeting");
        Button delete = new Button("Delete");

        edit.setUIID("LoginButton");
        delete.setUIID("LoginButton");

        add(BoxLayout.encloseYBottom(edit,delete));
        
        edit.addActionListener((evt) -> {
                        System.out.println(p.getId());

         new EditMeeting(res,previous,m, p).show();
             
        });
        
        delete.addActionListener((evt) -> {
                        MeetingService.getInstance().deleteMeeting(m);
                        new MeetingsForm(res, current,p).show();
        });
                
    }

  

  
    
}
