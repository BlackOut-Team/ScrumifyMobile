/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionProjets.Services;

import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionTeams.Entities.Team;
import com.blackout.scrumify.Utils.Statics;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author AmiraDoghri
 */
public class ServiceProjet {

    public ArrayList<Project> projects;
    static Map g;
    public static Double Rate;

    public static ServiceProjet instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
                    boolean t;

    public ServiceProjet() {
        req = new ConnectionRequest();
    }

    public static ServiceProjet getInstance() {
        if (instance == null) {
            instance = new ServiceProjet();
        }
        return instance;
    }

    public boolean addProject(Project p) {
        String url = "http://localhost/scrumifyApi/web/app_dev.php/Project/Add?name=" + p.getName() + "&description=" + p.getDescription() + "&duedate=" + p.getDuedate() + "&team_id=" + p.getTeam_id() + "&etat=1";
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.setPost(true);
        con.addResponseListener((NetworkEvent evt) -> {

            byte[] data = (byte[]) evt.getMetaData();
            String s = new String(data);
            System.out.println(s);
            if (!s.contains("erreur")) {
                Dialog.show("Confirmation", "success", "Ok", null);
                ArrayList<Project> pr = parseProjects(s);
                p.setId(pr.get(0).getId());
                System.out.println(p.getId());
                 t= true;
            } else {
                Dialog.show("Erreur", "date", "Ok", null);
                t=false;
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
       return t;

    }

    public boolean editProject(Project p) {
        String url = "http://localhost/scrumifyApi/web/app_dev.php/Project/EditP/" + p.getId() + "?name=" + p.getName() + "&description=" + p.getDescription() + "&duedate=" + p.getDuedate() + "&team_id=" + p.getTeam_id() ;
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.setPost(true);
        con.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) evt.getMetaData();
            String s = new String(data);
            System.out.println(s);
            if (!s.contains("erreur")) {
                Dialog.show("Confirmation", "success", "Ok", null);
                t=true;

            } else {
                Dialog.show("Erreur", "date", "Ok", null);
                t=false;

            }
        });

        NetworkManager.getInstance().addToQueueAndWait(con);
return t ;
    }

    public void archiveProject(Project p) {
        String url = "http://localhost/scrumifyApi/web/app_dev.php/Project/archiver/" + p.getId();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.setPost(true);
        con.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) evt.getMetaData();
            String s = new String(data);
            if (!s.equals("")) {
                Dialog.show("Confirmation", "success", "Ok", null);

            } else {
                Dialog.show("Erreur", "erreur", "Ok", null);

            }
        });

        NetworkManager.getInstance().addToQueueAndWait(con);

    }
//      String url =  "http://localhost/scrumifyApi/web/app_dev.php/Project/Add?name="+t.getName()+"&description="+t.getDescription()+"&duedate="+t.getDuedate()+"&etat=1";
//       String url;
//            url = "http://localhost/scrumifyApi/web/app_dev.php/Project/Add?name=gg&description=ddd&etat=1";
//       req.setUrl(url);
//        req.addResponseListener(new ActionListener<NetworkEvent>() {
//            @Override
//            public void actionPerformed(NetworkEvent evt) {
//                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
//                req.removeResponseListener(this);
//            }
//        });
//        NetworkManager.getInstance().addToQueueAndWait(req);
//        return resultOK;

    public ArrayList<Project> parseProjects(String jsonText) {
        try {

            projects = new ArrayList<>();
            JSONParser j = new JSONParser();
            g = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            for (int i = 0; i < g.size(); i++) {

                Project t = new Project();
                float id = Float.parseFloat(g.get("id").toString());
                t.setId((int) id);
                t.setEtat(((int) Float.parseFloat(g.get("etat").toString())));
                t.setName(g.get("name").toString());
                t.setDescription(g.get("description").toString());
                Map<String, Object> mapDateDebut = (Map<String, Object>) g.get("created");

                float datedebut = Float.parseFloat(mapDateDebut.get("timestamp").toString());
                String created = new SimpleDateFormat("dd/MM/yyyy").format(new Date((long) (datedebut * 1000L)));
                float datefin = Float.parseFloat(mapDateDebut.get("timestamp").toString());
                String duedate = new SimpleDateFormat("dd/MM/yyyy").format(new Date((long) (datefin * 1000L)));
                t.setCreated(created);
                t.setDuedate(duedate);
                Map<String, Object> team = (Map<String, Object>) g.get("team");

                t.setTeam_id((int) Float.parseFloat(team.get("id").toString()));

                projects.add(t);
            }

        } catch (IOException ex) {

        }
        return projects;
    }

    public ArrayList<Project> getAllProjects(Map m) {
        ArrayList<Project> listP = new ArrayList<>();
        ArrayList d = (ArrayList) m.get("root");

        ArrayList n = (ArrayList) d.get(0);
        System.out.println(n.size());
        for (int i = 0; i < n.size(); i++) {
            Map f = (Map) n.get(i);

            Project t = new Project();
            float id = Float.parseFloat(f.get("id").toString());
            t.setId((int) id);
            t.setEtat(((int) Float.parseFloat(f.get("etat").toString())));
            t.setName(f.get("name").toString());
            t.setDescription(f.get("description").toString());
            Map<String, Object> mapDateDebut = (Map<String, Object>) f.get("created");
            Map<String, Object> mapDateFin = (Map<String, Object>) f.get("duedate");

            float datedebut = Float.parseFloat(mapDateDebut.get("timestamp").toString());
            String created = new SimpleDateFormat("MM/dd/yyyy").format(new Date((long) (datedebut * 1000L)));
            float datefin = Float.parseFloat(mapDateFin.get("timestamp").toString());
            String duedate = new SimpleDateFormat("MM/dd/yyyy").format(new Date((long) (datefin * 1000L)));
            t.setCreated(created);
            t.setDuedate(duedate);

            listP.add(t);
        }
        return listP;

    }

    public static Map<String, Object> getResponse(String url) {
        url = "http://localhost/scrumifyApi/web/app_dev.php/" + url;
        System.out.println(url);
        ConnectionRequest r = new ConnectionRequest();
        r.setUrl(url);
        r.setPost(false);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInfiniteBlocking();
        r.setDisposeOnCompletion(dlg);
        r.addResponseListener((evt) -> {
            try {
                JSONParser p = new JSONParser();
                Reader targetReader = new InputStreamReader(new ByteArrayInputStream(r.getResponseData()));
                g = p.parseJSON(targetReader);

            } catch (IOException ex) {
                //Logger.getLogger(MyApplication.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(r);
        return g;
    }
}
