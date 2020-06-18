/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionTasks.Gui;

import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Gui.AddProject;
import com.blackout.scrumify.GestionProjets.Gui.CompProjectsForm;
import com.blackout.scrumify.GestionProjets.Gui.CurrProjectsForm;
import com.blackout.scrumify.GestionProjets.Gui.Dashboard;
import com.blackout.scrumify.GestionProjets.Gui.ProjectsForm;
import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
import com.blackout.scrumify.GestionTasks.Entities.Tasks;
import com.blackout.scrumify.GestionTasks.Services.TasksService;
import com.blackout.scrumify.GestionTeams.Gui.TeamForm;
import com.blackout.scrumify.Utils.Session;
import com.blackout.scrumify.Utils.SideMenuBaseForm;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.MultiButton;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import static com.codename1.ui.Component.RIGHT;
import static com.codename1.ui.Component.TOP;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.FontImage;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.Tabs;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Hidaya
 */
public class DoingForm extends SideMenuBaseForm {

    public static Resources res;

    public DoingForm(Resources res) {
        super(BoxLayout.y());

        Container content = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        content.setScrollableY(true);
        this.res = res;
        getToolbar().setTitleCentered(false);

        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        Image profilePic = res.getImage("Image3.png");
        Image mask = res.getImage("round-mask.png");
        profilePic = profilePic.fill(mask.getWidth(), mask.getHeight());
        Label profilePicLabel = new Label(profilePic, "ProfilePicTitle");
        profilePicLabel.setMask(mask.createMask());
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

//        
//        ButtonGroup barGroup = new ButtonGroup();
//        RadioButton all = RadioButton.createToggle("To Do ", barGroup);
//        all.setUIID("SelectBar");
//        RadioButton Services = RadioButton.createToggle("Done", barGroup);
//        Services.setUIID("SelectBar");
//        RadioButton Events = RadioButton.createToggle("Doing", barGroup);
//        Events.setUIID("SelectBar");
//        Label arrow = new Label(res.getImage("news-tab-down-arrow.png"), "Container");
//        add(LayeredLayout.encloseIn(
//                GridLayout.encloseIn(4, all, Services, Events),
//                FlowLayout.encloseBottom(arrow)
//        ));
//        //kn clika ala to do 
//        Services.addActionListener(l->{
//            if(Services.isSelected())
//            {
//            System.out.println("Taxi");
//            }
//        });
//        //kn clika ala doing
//        Events.addActionListener(l->{
//            if(Events.isSelected())
//            {
//                System.out.println("tt");
//            }
//        });
//        //kn clika ala done
//        all.addActionListener(l->{
//            if(all.isSelected())
//            {
//            System.out.println("all");
//            }
//        });
        Container ToDo = BoxLayout.encloseY(
                new Label("To do ", "CenterTitle")
        );
        ToDo.setUIID("RemainingTasks");
        Container Doing = BoxLayout.encloseY(
                new Label("Doing", "CenterTitle")
        );
        Doing.setUIID("Current");
        Container Done = BoxLayout.encloseY(
                new Label("Done", "CenterTitle")
        );
        Done.setUIID("CompletedTasks");

        Container titleCmp = BoxLayout.encloseY(
                FlowLayout.encloseIn(menuButton),
                BorderLayout.centerAbsolute(
                        BoxLayout.encloseY(new Label("Tasks", "CenterTitle"))
                ),
                GridLayout.encloseIn(3, ToDo, Doing, Done)
        );
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD);
        fab.getAllStyles().setMarginUnit(Byte.MAX_VALUE);
        fab.getAllStyles().setMargin(BOTTOM, Done.getPreferredH() - fab.getPreferredH() / 2);
        getToolbar().setTitleComponent(fab.bindFabToContainer(titleCmp, RIGHT, TOP));
        add(new Label("Doing", "TodayTitle"));

        FontImage arrowDown = FontImage.createMaterial(FontImage.MATERIAL_KEYBOARD_ARROW_DOWN, "Label", 3);

        TasksService pr = new TasksService();
        ArrayList<Tasks> listT = null;

        listT = pr.getDoing();
        if (!listT.isEmpty()) {
            for (int i = 0; i < listT.size(); i++) {
                Tasks p = listT.get(i);

                Container c = new Container(BoxLayout.x());
                c.setName(p.getTitle());
                addButtonBottom(arrowDown, c, p);

            }
        } else {

            Image empty = res.getImage("landing_1.png").scaledSmallerRatio(Display.getInstance().getDisplayWidth() / 3, Display.getInstance().getDisplayHeight() / 4);

            Container ct = new Container(BoxLayout.yCenter());
            ct.add(empty);
            ct.add(new Label("No  tasks here yet !", "TodayTitle"));
            Button add = new Button("Add new task");
            add.setUIID("LoginButton");
            add.addActionListener((evt) -> {
                new AddTasks(res).show();
            });
            ct.add(add);
            add(FlowLayout.encloseCenterMiddle(ct));

            ToastBar.getInstance().setPosition(TOP);
            ToastBar.Status status = ToastBar.getInstance().createStatus();
            status.setIcon(res.getImage("scrumify.png").scaledSmallerRatio(Display.getInstance().getDisplayWidth() / 10, Display.getInstance().getDisplayWidth() / 15));
            status.setMessage("No tasks yet");
            status.setExpires(3000);  // only show the status for 3 seconds, then have it automatically clear
            status.show();
        }

        fab.addActionListener((evt) -> {
            new AddTasks(res).show();
        });
        setupSideMenu(res);
        Button todo = new Button();
        Button doing = new Button();
        Button done = new Button();
        todo.addActionListener((evt) -> {

            new TasksForm(res).show();;

        });
        done.addActionListener((evt) -> {
            new DoneForm(res).show();;

        });

        ToDo.setLeadComponent(todo);
        Doing.setLeadComponent(doing);
        Done.setLeadComponent(done);

    }

    private void addButtonBottom(Image arrowDown, Container c, Tasks p) {
        MultiButton finishLandingPage = new MultiButton(c.getName());
        finishLandingPage.setEmblem(arrowDown);
        finishLandingPage.setUIID("ProjectItem");
        finishLandingPage.setUIIDLine1("TodayEntry");

        add(BoxLayout.encloseY(finishLandingPage));
        Button gt = new Button();
        gt.addActionListener((ActionEvent evt) -> {
            new TasksDetailsForm(res, this, p).show();

        });
        finishLandingPage.setLeadComponent(gt);
    }

    private Image createCircleLine(int color, int height, boolean first) {
        Image img = Image.createImage(height, height, 0);
        Graphics g = img.getGraphics();
        g.setAntiAliased(true);
        g.setColor(0xcccccc);
        int y = 0;
        if (first) {
            y = height / 6 + 1;
        }
        g.drawLine(height / 2, y, height / 2, height);
        g.drawLine(height / 2 - 1, y, height / 2 - 1, height);
        g.setColor(color);
        g.fillArc(height / 2 - height / 4, height / 6, height / 2, height / 2, 0, 360);
        return img;
    }

}
