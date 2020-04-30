/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionProjets.Gui;

import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
import com.codename1.components.SpanLabel;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;

/**
 *
 * @author AmiraDoghri
 */
public class ProjectsForm extends Form{
    
     public  ProjectsForm(Form previous) {
        setTitle("List projects");
        
        SpanLabel sp = new SpanLabel();
        sp.setText(ServiceProjet.getInstance().getAllProjects().toString());
        add(sp);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
    }
    
}
