/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionMeeting.Gui;

import com.blackout.scrumify.GestionMeeting.Entities.Meeting;
import com.blackout.scrumify.GestionMeeting.Services.MeetingService;
import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Gui.Dashboard;
import com.blackout.scrumify.GestionSprints.Entities.Sprint;
import com.blackout.scrumify.GestionSprints.Services.ServiceSprint;
import com.blackout.scrumify.GestionTeams.Gui.TeamForm;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.spinner.Picker;
import java.util.ArrayList;
import java.util.Map;
import com.codename1.ui.util.Resources;

/**
 *
 * @author AmiraDoghri
 */
public class AddMeeting extends SideMenuBaseForm {
    SideMenuBaseForm current;
 public static int id;
    Project pp;
    public AddMeeting(Resources res ,Project p ) {
        this.pp = p;
        current = this ;
        setTitle("Scrumify");
        setLayout(BoxLayout.y());
        getToolbar().setTitleCentered(false);
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);

        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton)
                  
        );
        getToolbar().setTitleComponent(titleCmp);

        setupSideMenu(res);

        add(new Label("Add Meeting", "TodayTitle"));
        TextField tfName = new TextField("", "Name");
        TextField tftype = new TextField("", "type");
        TextField tfplace = new TextField("", "place");
        Picker meetingDate = new Picker();

        meetingDate.setFormatter(new SimpleDateFormat("MM/dd/yyyy"));
        ComboBox<String> sprint = new ComboBox<String>();
        ServiceSprint ser = new ServiceSprint();

        Map x = ser.getResponse("Sprint/sprint/"+ p.getId());
        System.out.println("Sprint/sprint/"+ p.getId());
        ArrayList<Sprint> listT = ser.getAllSprints(x);
        for (Sprint ev : listT) {
            sprint.addItem(ev.getName());
        }

        Button btnValider = new Button("Add Meeting");

        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfName.getText().length() == 0) || (meetingDate.getText().length() == 0) || (meetingDate.getValue() == null)) {
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                } else {
                    try {

                  

                        int ind = sprint.getSelectedIndex();
                        Sprint te = listT.get(ind);
                        int sprintId = te.getId();
                        Meeting m = new Meeting(tfName.getText(), tfplace.getText(), tftype.getText(),Integer.toString(sprintId), meetingDate.getText());
                        if(MeetingService.getInstance().addMeeting(m)){
                        new MeetingsForm(res, current ,pp).show();
                        }
                       
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "type", new Command("OK"));
                    }

                }

            }
        });

        addAll(tfName, tftype, tfplace, meetingDate, sprint, btnValider);

    }

    @Override
    protected void showOtherForm(Resources res) {
        new AddMeeting(res, pp).show();
    }

    @Override
    protected void showDashboard(Resources res) {
        new Dashboard(res).show();
    }

    @Override
    protected void showProjects(Resources res) {
        new MeetingsForm(res, this, pp).show();
    }
    
    @Override
    protected void showTeamForm(Resources res) {
        new TeamForm(res, this).show();
    }

    @Override
    protected void showTasks(Resources res) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
