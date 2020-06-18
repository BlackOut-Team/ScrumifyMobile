/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionMeeting.Gui;

import com.blackout.scrumify.GestionMeeting.Entities.Meeting;
import com.blackout.scrumify.GestionMeeting.Services.MeetingService;
import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Gui.ProjectDetailsForm;
import com.blackout.scrumify.GestionSprints.Entities.Sprint;
import com.blackout.scrumify.GestionSprints.Services.ServiceSprint;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.processing.Result;
import com.codename1.ui.AutoCompleteTextField;
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
import com.codename1.ui.list.DefaultListModel;
import com.codename1.ui.spinner.Picker;
import java.util.ArrayList;
import java.util.Map;
import com.codename1.ui.util.Resources;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

/**
 *
 * @author AmiraDoghri
 */
public class AddMeeting extends SideMenuBaseForm {

    SideMenuBaseForm current;
    public static int id;
    Project pp;

    public AddMeeting(Resources res, Project p) {
        this.pp = p;
        current = this;
        setTitle("Scrumify");
        setLayout(BoxLayout.y());
        getToolbar().setTitleCentered(false);
        Button returnButton = new Button("");
        returnButton.setUIID("Title");
        FontImage.setMaterialIcon(returnButton, FontImage.MATERIAL_ARROW_BACK);
        returnButton.addActionListener(e -> new ProjectDetailsForm(res, current, p).showBack());

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(returnButton)
        );
        getToolbar().setTitleComponent(titleCmp);

        setupSideMenu(res);

        add(new Label("Add Meeting", "TodayTitle"));
        TextField tfName = new TextField("", "Name");
        ComboBox<String> tftype = new ComboBox<String>();
        //TextField tftype = new TextField("", "type");
        //TextField tfplace = new TextField("", "place");
        final DefaultListModel<String> options = new DefaultListModel<>();
        AutoCompleteTextField tfplace = new AutoCompleteTextField(options) {
            @Override
            protected boolean filter(String text) {
                if (text.length() == 0) {
                    return false;
                }
                String[] l = searchLocations(text);
                System.out.println(l);
                if (l == null || l.length == 0) {
                    return false;
                }

                options.removeAll();
                for (String s : l) {
                    options.addItem(s);
                }
                return true;
            }

        };
        Picker meetingDate = new Picker();

        meetingDate.setFormatter(new SimpleDateFormat("MM-dd-yyyy"));
        ComboBox<String> sprint = new ComboBox<String>();
        ServiceSprint ser = new ServiceSprint();
        tftype.addItem("Daily");
        tftype.addItem("Sprint Retro ");
        tftype.addItem("Sprint Review");
        Map x = ser.getResponse("Sprint/sprint/" + p.getId());
        System.out.println("Sprint/sprint/" + p.getId());
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

                    int ind = sprint.getSelectedIndex();
                    Sprint te = listT.get(ind);
                    int sprintId = te.getId();
                    System.out.println(tftype.getSelectedItem());
                    Meeting m = new Meeting(tfName.getText(), tfplace.getText(), tftype.getSelectedItem(), sprintId, meetingDate.getText());
                    if (MeetingService.getInstance().addMeeting(m)) {
                        new MeetingsForm(res, current, pp).show();
                    }

                }

            }
        });

        addAll(tfName, tftype, tfplace, meetingDate, sprint, btnValider);

    }
    //TextField apiKey = new TextField();
    //private static final String apiKey = "AIzaSyB22oQWKugTFxxqbcSkMc4MqALFi094auU";
 String apiKey;
    String[] searchLocations(String text) {
        try {
            if (text.length() > 0) {
                ConnectionRequest r = new ConnectionRequest();
                r.setPost(false);
                r.setUrl("https://maps.googleapis.com/maps/api/place/autocomplete/json");
                r.addArgument("key", apiKey);
                r.addArgument("input", text);
                NetworkManager.getInstance().addToQueueAndWait(r);
                Map<String, Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(r.getResponseData()), "UTF-8"));
                String[] res = Result.fromContent(result).getAsStringArray("//description");
                return res;
            }
        } catch (Exception err) {
            Log.e(err);
        }
        return null;
    }
}
