/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionTeams.Gui;

import com.blackout.scrumify.GestionProjets.Gui.ProjectsForm;
import com.blackout.scrumify.GestionTeams.Entities.Team;
import com.blackout.scrumify.GestionTeams.services.OpinionDAO;
import com.blackout.scrumify.GestionTeams.services.ServiceTeam;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import static com.codename1.ui.Component.RIGHT;
import static com.codename1.ui.Component.TOP;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.Resources;

/**
 *
 * @author Iheb
 */
public class Email extends SideMenuBaseForm {
   SideMenuBaseForm current;
 public static int id;
    public Email(Resources res) {
        current = this ;
     
        
        setTitle("Report Problem");
          getToolbar().setTitleCentered(false);

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);

        menuButton.addActionListener(e -> getToolbar().openSideMenu());


 
       

        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        Container C1 = new Container(BoxLayout.yCenter());
        Container C2 = new Container(BoxLayout.yCenter());
        Container C3 = new Container(BoxLayout.yCenter());
        Container C4 = new Container(BoxLayout.yCenter());
        Container C5 = new Container(BoxLayout.yCenter());
        Container C6 = new Container(BoxLayout.yCenter());
        Container C7 = new Container(BoxLayout.yCenter());
        Container C8 = new Container(BoxLayout.yCenter());
        
        
        SpanLabel lign = new SpanLabel("  " );
        SpanLabel ligne = new SpanLabel("  " );
        SpanLabel lignx = new SpanLabel("  " );
        SpanLabel lig = new SpanLabel("  " );
        SpanLabel li = new SpanLabel("  " );
        SpanLabel l = new SpanLabel("  " );
        SpanLabel lix = new SpanLabel("  " );
        SpanLabel lx = new SpanLabel("  " );
        
                C1.add(lign);
                C2.add(ligne);
                C3.add(lignx);
                C4.add(lig);
                C5.add(li);
                C6.add(l);
                C7.add(lix);
                C8.add(lx);
                
                add(C1);
                add(C2);
                add(C3);
                add(C4);
                add(C5);
                add(C6);
                add(C7);
                add(C8);

     
        Button btnValider = new Button("Report");
        
        btnValider.setUIID("LoginButton");
        btnValider.addActionListener(new ActionListener() {
           @Override
        public void actionPerformed(ActionEvent evt) {
            new OpinionDAO();
                }
            });

        addAll( btnValider);
setupSideMenu(res);
    }
    
    
}
