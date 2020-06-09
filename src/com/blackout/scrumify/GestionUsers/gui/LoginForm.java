/*
 * Copyright (c) 2016, Codename One
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated 
 * documentation files (the "Software"), to deal in the Software without restriction, including without limitation 
 * the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, 
 * and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions 
 * of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A 
 * PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT 
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF 
 * CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE 
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE. 
 */

package com.blackout.scrumify.GestionUsers.gui;

import com.blackout.scrumify.GestionMeeting.Entities.Meeting;
import com.blackout.scrumify.GestionProjets.Gui.ProjectsForm;
import com.blackout.scrumify.GestionProjets.Gui.WalkthruForm;
import com.blackout.scrumify.GestionUsers.services.userService;
import com.blackout.scrumify.Utils.Session;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;
import com.codename1.io.Storage;


/**
 * The Login form
 *
 * @author AmiraDoghri
 */
public class LoginForm extends Form {
    static Map g;
    Form current;
    userService u = new userService();
    public LoginForm(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        setUIID("LoginForm");
                Container c = FlowLayout.encloseCenter(new ImageViewer(theme.getImage("scrumify.png").scaledSmallerRatio(Display.getInstance().getDisplayWidth() / 4, Display.getInstance().getDisplayHeight() / 10)));

        Container welcome = BoxLayout.encloseYCenter(
                c,
                new Label("Welcome Scrumifyer,", "WelcomeWhite")
        );
         current=this;

        getTitleArea().setUIID("Container");
        
  
        
        TextField login = new TextField("", "Login", 20, TextField.EMAILADDR) ;
        TextField password = new TextField("", "Password", 20, TextField.PASSWORD) ;
        
        login.getAllStyles().setMargin(LEFT, 0);
        password.getAllStyles().setMargin(LEFT, 0);
        Label loginIcon = new Label("", "TextField");
        Label passwordIcon = new Label("", "TextField");
        Label error = new Label();
        loginIcon.getAllStyles().setMargin(RIGHT, 0);
        passwordIcon.getAllStyles().setMargin(RIGHT, 0);
        FontImage.setMaterialIcon(loginIcon, FontImage.MATERIAL_PERSON_OUTLINE, 3);
        FontImage.setMaterialIcon(passwordIcon, FontImage.MATERIAL_LOCK_OUTLINE, 3);
        
        Button loginButton = new Button("LOGIN");
        loginButton.setUIID("LoginButton");
        loginButton.addActionListener(e -> {
            
            if(u.connecting(login.getText(),password.getText())){
                Toolbar.setGlobalToolbar(false);
                new WalkthruForm(theme).show();
                Toolbar.setGlobalToolbar(true);
            }else{
                error.setText("verify password");
            }
            
        });
        
        Button createNewAccount = new Button("CREATE NEW ACCOUNT");
        createNewAccount.setUIID("CreateNewAccountButton");
        Button resetP = new Button("Forgot password ?");
        resetP.setUIID("CreateNewAccountButton");
        // We remove the extra space for low resolution devices so things fit better
        Label spaceLabel;
        if(!Display.getInstance().isTablet() && Display.getInstance().getDeviceDensity() < Display.DENSITY_VERY_HIGH) {
            spaceLabel = new Label();
        } else {
            spaceLabel = new Label(" ");
        }
        
        
        Container by = BoxLayout.encloseY(
                welcome,
               // profilePicLabel,
                spaceLabel,
                BorderLayout.center(login).
                        add(BorderLayout.WEST, loginIcon),
                BorderLayout.center(password).
                        add(BorderLayout.WEST, passwordIcon),
                error,
                resetP,
                loginButton,
                createNewAccount
        );
        add(BorderLayout.CENTER, by);
        // for low res and landscape devices
        by.setScrollableY(true);
        by.setScrollVisible(false);
        
        createNewAccount.addActionListener((evt) -> {
            new RegisterForm(theme).show();
        });
        
        resetP.addActionListener((evt) -> {
            new resetPassword(theme).show();
        });
    }
    
    
   
  
}
