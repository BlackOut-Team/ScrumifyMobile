/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionUsers.gui;

import com.blackout.scrumify.GestionUsers.entities.User;
import com.blackout.scrumify.GestionUsers.services.userService;
import static com.blackout.scrumify.GestionUsers.services.userService.getResponse;
import com.blackout.scrumify.Utils.Session;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.capture.Capture;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.components.MultiButton;
import com.codename1.io.MultipartRequest;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.Resources;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author AmiraDoghri
 */
public class resetPassword extends SideMenuBaseForm {

    static Map g;
    Form current;
    userService u = new userService();

    public resetPassword(Resources res) {
        // super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        super(BoxLayout.y());
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        String username = Session.u.getUsername();
        String name = Session.u.getName();
        String lastname = Session.u.getLastname();

        String image = Session.u.getAvatar();
        System.out.println(image);
        EncodedImage placeholder = EncodedImage.createFromImage(res.getImage("Image6.png"), false);
        Image profilePic = URLImage.createToStorage(placeholder, "http://localhost/scrumify/web/uploads/images/" + image,
                "http://localhost/scrumify/web/uploads/images/" + image);

        ImageViewer img = new ImageViewer(profilePic);
        Image mask = res.getImage("round-mask.png");
        //profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Container remainingTasks = BoxLayout.encloseY(
                new Label("12", "CenterTitle"),
                new Label("Projects", "CenterSubTitle")
        );
        remainingTasks.setUIID("RemainingTasks");
        Container completedTasks = BoxLayout.encloseY(
                new Label("32", "CenterTitle"),
                new Label("Teams", "CenterSubTitle")
        );
        completedTasks.setUIID("CompletedTasks");

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(
                                new Label(name + " " + lastname, "Title"),
                                new Label(username, "SubTitle")
                        )
                ).add(BorderLayout.WEST, profilePicLabel),
                GridLayout.encloseIn(2, remainingTasks, completedTasks)
        );

        add(new Label("Edit Profile", "TodayTitle"));

        TextField Name = new TextField(Session.u.getName(), "Name");
        TextField LastName = new TextField(Session.u.getLastname(), "Lastname");
        TextField Username = new TextField(Session.u.getUsername(), "Username");
        TextField Email = new TextField(Session.u.getEmail(), "Email");
        Button btnValider = new Button("Save ");
        Button editPass = new Button("Reset Password");
        btnValider.setUIID("LoginButton");
        editPass.setUIID("ActionButton");

        btnValider.addActionListener((e)-> {
            User us = new User(Session.u.getId(), Name.getText(),LastName.getText(),Username.getText(),Email.getText());
            if(u.update(us)){
                new settingsForm(res).show();
            }
            else {
                System.out.println("error update");
            }
        });
        
     
        add(FlowLayout.encloseCenterMiddle(BoxLayout.encloseY(Name, LastName, Username, Email, btnValider,editPass)));
        getToolbar().setTitleComponent(titleCmp);

        setupSideMenu(res);
    }

}
