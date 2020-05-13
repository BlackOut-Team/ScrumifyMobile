/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionTeams.services;

import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionTeams.Entities.Team;
import com.blackout.scrumify.GestionUsers.entities.User;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.io.Preferences;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
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
public class ServiceTeam {
         public ArrayList<Team> teams;
        static Map k;

    public static ServiceTeam instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    boolean t;

    public ServiceTeam() {
         req = new ConnectionRequest();
    }

    public static ServiceTeam getInstance() {
        if (instance == null) {
            instance = new ServiceTeam();
        }
        return instance;
    }

   
    
        public ArrayList<Team> parseTeams(String jsonText) {
        try {

            teams = new ArrayList<>();
            JSONParser j = new JSONParser();
            k = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            for (int i = 0; i < k.size(); i++) {

                Team t = new Team();
                float id = Float.parseFloat(k.get("id").toString());
                t.setId((int) id);
                t.setEtat(((int) Float.parseFloat(k.get("etat").toString())));
                t.setName(k.get("name").toString());
               
                Map<String, Object> mapDateDebut = (Map<String, Object>) k.get("created");

                float datedebut = Float.parseFloat(mapDateDebut.get("timestamp").toString());
                String created = new SimpleDateFormat("dd/MM/yyyy").format(new Date((long) (datedebut * 1000L)));
                float datefin = Float.parseFloat(mapDateDebut.get("timestamp").toString());
                String duedate = new SimpleDateFormat("dd/MM/yyyy").format(new Date((long) (datefin * 1000L)));
                t.setCreated(created);
                t.setUpdated(duedate);
                Map<String, Object> team = (Map<String, Object>) k.get("team");

               

                teams.add(t);
            }

        } catch (IOException ex) {

        }
        return teams;
    }
        
        
    public ArrayList<Team> getAllTeams(Map m){
        ArrayList<Team> listT = new ArrayList<>();
        ArrayList d = (ArrayList)m.get("root");

        for(int i = 0; i<d.size();i++){
            Map f =  (Map) d.get(i);
            Team t = new Team();
            Double ll = (Double) f.get("id");
            t.setId(ll.intValue());
            
            
            t.setName((String)f.get("name"));
            Map<String, Object> mapDateDebut = (Map<String, Object>) f.get("created");
            Map<String, Object> mapDateFin = (Map<String, Object>) f.get("updated");

            float datedebut = Float.parseFloat(mapDateDebut.get("timestamp").toString());
            String created = new SimpleDateFormat("MM/dd/yyyy").format(new Date((long) (datedebut * 1000L)));
            float datefin = Float.parseFloat(mapDateFin.get("timestamp").toString());
            String duedate = new SimpleDateFormat("MM/dd/yyyy").format(new Date((long) (datefin * 1000L)));
            t.setCreated(created);
            t.setUpdated(duedate);
           
                 
            
           

            listT.add(t);  
        }        
        return listT;

    }
     public ArrayList<User> getTeamM(Map m){
        ArrayList<User> listM = new ArrayList<>();
        ArrayList d = (ArrayList)m.get("root");
        if(d != null) {
        for(int i = 0; i<d.size();i++){
            Map f =  (Map) d.get(i);
            User t = new User();
            Double ll = (Double) f.get("id");
            t.setId(ll.intValue());
            
          
            t.setName((String)f.get("username"));
      
           
                 
            
           

            listM.add(t);  
        }     
        }
        
        return listM;

    }
      public ArrayList<User> getAllusers(Map m){
        ArrayList<User> listM = new ArrayList<>();
        ArrayList d = (ArrayList)m.get("root");
        if(d != null) {
        for(int i = 0; i<d.size();i++){
            Map f =  (Map) d.get(i);
            User t = new User();
            Double ll = (Double) f.get("id");
            t.setId(ll.intValue());
            
          
            t.setName((String)f.get("username"));
      
           
                 
            
           

            listM.add(t);  
        }     
        }
        
        return listM;

    }
     public static Map<String, Object> getResponse(String url){
        url = "http://localhost/scrumifyApi/web/app_dev.php/"+url;
        ConnectionRequest r = new ConnectionRequest();
        r.setUrl(url);
        r.setPost(false);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        r.setDisposeOnCompletion(dlg);
        r.addResponseListener((evt) -> {
            try {
                JSONParser p = new JSONParser();
                Reader targetReader = new InputStreamReader(new ByteArrayInputStream(r.getResponseData()));

                k= p.parseJSON(targetReader);

                
            } catch (IOException ex) {
                //Logger.getLogger(Scrumify.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        });
        NetworkManager.getInstance().addToQueueAndWait(r);
        return k; 
    }
     
     
     public boolean addTeam(Team tm) {

        String url = "http://localhost/scrumifyApi/web/app_dev.php/ajm?user="+Preferences.get("user", 0)+"&name=" + tm.getName();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.setPost(true);
        con.addResponseListener((NetworkEvent evt) -> {

            byte[] data = (byte[]) evt.getMetaData();
            String s = new String(data);
            System.out.println(s);
            if (!s.contains("erreur")) {
                Dialog.show("Confirmation", "success", "Ok", null);
                ArrayList<Team> pr = parseTeams(s);
                tm.setId(pr.get(0).getId());
                System.out.println(tm.getId());
                 t= true;
            } else {
                Dialog.show("Erreur", "date", "Ok", null);
                t=false;
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
       return t;

    }
       public boolean affecter(Team tm,User u,int Role) {
        Role = Role+1;
           System.out.println(Role);
        String url = "http://localhost/scrumifyApi/web/app_dev.php/affecterT?user_id="+u.getId()+"&team_id=" + tm.getId()+"&role=" + Role;
           System.out.println(url);
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.setPost(true);
        con.addResponseListener((NetworkEvent evt) -> {

            byte[] data = (byte[]) evt.getMetaData();
            String s = new String(data);
            System.out.println(s);
            if (!s.contains("erreur")) {
                Dialog.show("Confirmation", "success", "Ok", null);
                 t= true;
            } else {
                Dialog.show("Erreur", "Utilisateur existe déja dans cette équipe", "Ok", null);
                t=false;
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
       return t;

    }
     
     public boolean editTeam(Team p) {
        String url = "http://localhost/scrumifyApi/web/app_dev.php/etm/"+ p.getId()+"?name="+p.getName() ;
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

    public void archiveTeam(Team p) {
        String url = "http://localhost/scrumifyApi/web/app_dev.php/atm/" + p.getId();
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
}
