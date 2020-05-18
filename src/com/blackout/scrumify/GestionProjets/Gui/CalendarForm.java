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
package com.blackout.scrumify.GestionProjets.Gui;

import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
import com.blackout.scrumify.GestionSprints.Entities.Sprint;
import com.blackout.scrumify.GestionSprints.Services.ServiceSprint;
import com.blackout.scrumify.GestionTeams.Entities.Team;
import com.blackout.scrumify.GestionTeams.services.ServiceTeam;
import com.blackout.scrumify.Utils.Session;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.l10n.DateFormat;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Calendar;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.spinner.Picker;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

/**
 * GUI builder created Form
 *
 * @author shai
 */
public class CalendarForm extends SideMenuBaseForm {

    SimpleDateFormat p1 = new SimpleDateFormat("yyyy-MM-dd");

    public CalendarForm() {
    }

    public CalendarForm(Resources resourceObjectInstance) {
        initGuiBuilderComponents(resourceObjectInstance);
        setLayout(BoxLayout.y());
        setScrollableY(true);
        getContentPane().setScrollVisible(false);
        getToolbar().setUIID("Container");
        Button b = new Button(" ");
        b.setUIID("Container");
        getToolbar().setTitleComponent(b);
        getTitleArea().setUIID("Container");
        setupSideMenu(resourceObjectInstance);
        gui_Calendar_1.setTwoDigitMode(true);
        getToolbar().setTitleCentered(false);

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);

        menuButton.addActionListener(e -> getToolbar().openSideMenu());
        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY()
                ),
                GridLayout.encloseIn()
        );
        getToolbar().setTitleComponent(titleCmp);
       Container re = new Container(BoxLayout.y());
       add(re);
        Picker p = new Picker();
        b.addActionListener(e -> {
            p.pressed();
            p.released();
        });
        p.addActionListener(e -> {
            re.removeAll();
            Date d = gui_Calendar_1.getDate();
            String df = p1.format(d);
            Map m = ServiceProjet.getResponse("Project/showDeadlines/" + Session.u.getId() + "?date=" + df);
            Map ms = ServiceProjet.getResponse("Project/showDeadliness/" + Session.u.getId() + "?date=" + df);

            p.setFormatter(new SimpleDateFormat("MMMM"));
            ServiceProjet pr = new ServiceProjet();
            ServiceSprint sr = new ServiceSprint();

            ArrayList<Project> listT = pr.getAllProjects(m);
            ArrayList<Sprint> listS = sr.getAllSprints(ms);

            for (int i = 0; i < listT.size(); i++) {
                Project prr = listT.get(i);

                ServiceTeam s = new ServiceTeam();
                Map m1 = s.getResponse("gett/" + prr.getTeam_id());
                Team tt = s.getTeam(m1);

                re.add(createEntry(resourceObjectInstance, false, "", "", tt.getId() + "", prr.getName(), prr.getDescription(), "contact-a.png", "contact-b.png", "contact-c.png"));
            }
            for (int i = 0; i < listS.size(); i++) {
                Sprint prr = listS.get(i);
                re.add(createEntry(resourceObjectInstance, true, "", "",  "", prr.getName(), prr.getDescription(), "contact-a.png", "contact-b.png", "contact-c.png"));

            }
        });
        gui_Calendar_1.addActionListener((evt) -> {
            re.removeAll();
            Date d = gui_Calendar_1.getDate();
            String df = p1.format(d);
            Map m = ServiceProjet.getResponse("Project/showDeadlines/" + Session.u.getId() + "?date=" + df);
            Map ms = ServiceProjet.getResponse("Project/showDeadliness/" + Session.u.getId() + "?date=" + df);

            p.setFormatter(new SimpleDateFormat("MMMM"));
            ServiceProjet pr = new ServiceProjet();
            ServiceSprint sr = new ServiceSprint();
if(!(m==null && ms==null)){

            ArrayList<Project> listT = pr.getAllProjects(m);
            ArrayList<Sprint> listS = sr.getAllSprints(ms);
            for (int i = 0; i < listT.size(); i++) {
                Project prr = listT.get(i);

                ServiceTeam s = new ServiceTeam();
                Map m1 = s.getResponse("gett/" + prr.getTeam_id());
                Team tt = s.getTeam(m1);

                re.add(createEntry(resourceObjectInstance, false, "", "", tt.getId() + "", prr.getName(), prr.getDescription(), "contact-a.png", "contact-b.png", "contact-c.png"));
            }
            for (int i = 0; i < listS.size(); i++) {
                Sprint prr = listS.get(i);
                re.add(createEntry(resourceObjectInstance, false, "", "",  "", prr.getName(), prr.getDescription(), "contact-a.png", "contact-b.png", "contact-c.png"));

            }
            
}else
        {
                    Image empty = resourceObjectInstance.getImage("landing_1.png");
                    Container ct = new Container(BoxLayout.yCenter());
                    ct.add(empty );
                    ct.add(new Label("No deadlines this day !","TodayTitle"));
                    
                  
                                       re.add(FlowLayout.encloseCenterMiddle(ct));

        }
        });
        
        p.setDate(new Date());
        p.setUIID("CalendarDateTitle");

        Container cnt = BoxLayout.encloseY(
                new Label(" "),
                p
        //new Label(resourceObjectInstance.getImage("calendar-separator.png"), "CenterLabel")
        );

        BorderLayout bl = (BorderLayout) gui_Calendar_1.getLayout();
        Component combos = bl.getNorth();
        gui_Calendar_1.replace(combos, cnt, null);

        // add(createEntry(resourceObjectInstance, true, "12:20", "13:20", "Taco Bell", "Lunch", "Detra Mcmunn, Ami Koehler", "contact-b.png", "contact-c.png"));
        // add(createEntry(resourceObjectInstance, false, "16:15", "17:10", "3B, 2nd Floor", "Design Meeting", "Bryant Ford, Ami Koehler, Detra Mcmunn", "contact-a.png"));
    }

    private Container createEntry(Resources res, boolean selected, String startTime, String endTime, String location, String title, String attendance, String... images) {
        Component time = new Label(startTime, "CalendarHourUnselected");
        if (selected) {
            time.setUIID("CalendarHourSelected");
        }

        Container circleBox = BoxLayout.encloseY(new Label(res.getImage("label_round-selected.png")),
                new Label("-", "OrangeLine"),
                new Label("-", "OrangeLine")
        );

        Container cnt = new Container(BoxLayout.x());
        for (String att : images) {
            cnt.add(res.getImage(att));
        }
        Container mainContent = BoxLayout.encloseY(
                BoxLayout.encloseX(
                        new Label(title, "SmallLabel"),
                        new Label("-", "SmallThinLabel"),
                        new Label(startTime, "SmallThinLabel"),
                        new Label("-", "SmallThinLabel"),
                        new Label(endTime, "SmallThinLabel")),
                new Label(attendance, "TinyThinLabel"),
                cnt
        );

        Label redLabel = new Label("", "RedLabelRight");
        FontImage.setMaterialIcon(redLabel, FontImage.MATERIAL_GROUP);
        Container loc = BoxLayout.encloseY(
                redLabel,
                new Label("Team:", "TinyThinLabelRight"),
                new Label(location, "TinyBoldLabel")
        );

        mainContent = BorderLayout.center(mainContent).
                add(BorderLayout.WEST, circleBox);

        return BorderLayout.center(mainContent).
                add(BorderLayout.WEST, FlowLayout.encloseCenter(time)).
                add(BorderLayout.EAST, loc);
    }

//-- DON'T EDIT BELOW THIS LINE!!!
    private Calendar gui_Calendar_1 = new Calendar();

// <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initGuiBuilderComponents(com.codename1.ui.util.Resources resourceObjectInstance) {
        setLayout(new com.codename1.ui.layouts.GridLayout(2, 1));
        setTitle("");
        setName("CalendarForm");
        addComponent(gui_Calendar_1);
        gui_Calendar_1.setName("Calendar_1");
        gui_Calendar_1.addActionListener((e) -> {
            System.out.println(gui_Calendar_1.getDate());
        });
    }// </editor-fold>

//-- DON'T EDIT ABOVE THIS LINE!!!
//    @Override
//    protected boolean isCurrentCalendar() {
//        return true;
//    }
    @Override
    protected void initGlobalToolbar() {
        setToolbar(new Toolbar(true));
    }

}
