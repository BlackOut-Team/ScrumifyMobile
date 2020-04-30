/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionUsers.gui;

import com.blackout.scrumify.GestionProjets.Gui.ProjectsForm;
import com.blackout.scrumify.Utils.Statics;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkEvent;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.BOTTOM;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;

/**
 *
 * @author AmiraDoghri
 */
public class LoginForm extends Form {

    Form current;

    public LoginForm() {
        current = this;

        setTitle("Login");
        setLayout(new FlowLayout(CENTER, CENTER));
        TextField Login = new TextField("", "Login");
        TextField pwd = new TextField("", "Password");
        pwd.setConstraint(TextField.PASSWORD);
        Button submit = new Button("Valider");
        Container c3 = new Container(new BoxLayout(BOTTOM));
        c3.add(Login);
        c3.add(pwd);
        c3.add(submit);
        Button register = new Button("Register");
        c3.add(new Label("Not a member yet ?"));
        c3.add(register);
        register.addActionListener((evt) -> new RegisterForm().show());
        submit.addActionListener((ActionListener) (ActionEvent evt) -> {
            ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
            String Url = "http://localhost/scrumifyApi/web/app_dev.php/login?username=" + Login.getText() + "&password=" + pwd.getText();

            con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

            con.addResponseListener((ev) -> {
          
           
                    if (con.getResponseCode() == 200) { //Code HTTP 200 OK

                        Dialog.show("Success", "Logged in  successfully", new Command("OK"));
                        con.removeResponseListener((ActionListener<NetworkEvent>) this);
                        new ProjectsForm(current).show();

                    } else {
                        Dialog.show("Erroor", "Wrong credentials", new Command("OK"));

                    }

                });
            });
     
        add(c3);

    }
}
