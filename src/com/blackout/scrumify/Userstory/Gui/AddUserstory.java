/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.Userstory.Gui;

import com.blackout.scrumify.Feature.Entities.Features;
import com.blackout.scrumify.Feature.Gui.AfficherFeature;
import com.blackout.scrumify.Feature.services.ServiceFeature;
import com.blackout.scrumify.Userstory.Entities.UserStory;
import com.blackout.scrumify.Userstory.Services.ServiceUserstory;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Amine
 */
public class AddUserstory extends SideMenuBaseForm {

    Form f;
    Container c2;
    TextArea description;
    TextArea etat;
    TextArea story_point;
    TextArea priority;
    Button create;

    public AddUserstory(Resources theme) {
        super(new BorderLayout(BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE));
        f = this;
        setTitle("Scrumify");
        setLayout(BoxLayout.y());
        getToolbar().setTitleCentered(false);
        Button returnButton = new Button("");
        returnButton.setUIID("Title");
        FontImage.setMaterialIcon(returnButton, FontImage.MATERIAL_ARROW_BACK);
        returnButton.addActionListener(e -> new AfficherFeature(theme, this).showBack());

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(returnButton)
        );
        getToolbar().setTitleComponent(titleCmp);

        setupSideMenu(theme);

        create = new Button("creer");
        description = new TextField();
        etat = new TextField();
        priority = new TextField();
        story_point = new TextField();
        c2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        c2.add(description);
        c2.add(etat);
        c2.add(priority);
        c2.add(story_point);
        c2.add(create);
        f.add(c2);
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ServiceUserstory SF = new ServiceUserstory();
                UserStory F = new UserStory();
                F.setEtat(Integer.parseInt(etat.getText()));
                F.setIsDeleted(0);
                F.setDescription(description.getText());
                F.setStory_point(Integer.parseInt(story_point.getText()));
                F.setPriority(Integer.parseInt(priority.getText()));
                SF.ajoutUserstory(F);
                new UserstoryForm(theme, f).show();

            }
        });
    }

    public Form getF() {
        return f;
    }

}
