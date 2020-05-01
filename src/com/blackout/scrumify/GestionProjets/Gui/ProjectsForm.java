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
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author AmiraDoghri
 */
public class ProjectsForm extends Form{
    
     public  ProjectsForm(Form previous) {
        setTitle("List projects");
         ServiceProjet pr = new ServiceProjet();
         Map m = pr.getResponse("Project/showP");
        ArrayList<Project> listT = pr.getAllProjects(m);
        for(int i=0; i<listT.size();i++){
            
            Project p = listT.get(i);

            Container c = new Container(BoxLayout.x());
            Container rightContainer = new Container(BoxLayout.y());

            rightContainer.add(new Label(p.getName()));
            rightContainer.add(new Label(p.getDescription()));
            rightContainer.add(new Label(p.getDuedate()));
            
            rightContainer.add(new Label(p.getCreated()));
            rightContainer.add(new Label(p.getTeam_id()+""));

//            Button btn = new Button();
//            btn.addActionListener((evt) -> {
//                new profileForm(p,current).show();
//            });
//            
//            c.setLeadComponent(btn);
            
            c.add(rightContainer);
            
            add(c);
         }
        //SpanLabel sp = new SpanLabel();
        //sp.setText(ServiceProjet.getInstance().getAllProjects().toString());
        //add(sp);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
    
}
