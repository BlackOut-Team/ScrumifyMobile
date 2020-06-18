/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.Feature.Gui;

import com.blackout.scrumify.Feature.Entities.Features;
import com.blackout.scrumify.Userstory.Gui.UserstoryForm;
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
 * @author Amine
 */
public class FeaturesDetailsForm extends SideMenuBaseForm {

    SideMenuBaseForm current;

    public FeaturesDetailsForm(Resources res, Form previous, Features p) {
        super(BoxLayout.y());
        current = this;
        getToolbar().setTitleCentered(false);
        ActionListener ev = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                new AfficherFeature(res,current).showBack();
            }
        ;
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
                                new Label("Created : " + p.getName() + "", "SubTitle"),
                                new Label("Etat: " + p.getEtat() + "", "SubTitle")
                        )
                ).add(BorderLayout.WEST, profilePicLabel)
        );
        getToolbar().setTitleComponent(titleCmp);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        add(new Label(p.getName(), "TodayTitle"));
        Container rightContainer = new Container(BoxLayout.y());
        rightContainer.add(new Label(p.getName()));

        add(rightContainer);
        setupSideMenu(res);
        Button userstories = new Button(FontImage.MATERIAL_VIEW_MODULE);

        userstories.setUIID("ActionButton");

        add(BoxLayout.encloseYBottom(userstories));

        userstories.addActionListener((evt) -> {
            System.out.println(p.getId());

            new UserstoryForm(res, this).show();

        });

    }

}
