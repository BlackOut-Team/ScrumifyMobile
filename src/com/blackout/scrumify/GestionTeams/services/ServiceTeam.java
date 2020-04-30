/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionTeams.services;

import blackout.scrumify.Scrumify;
import com.blackout.scrumify.GestionTeams.Entities.Team;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AmiraDoghri
 */
public class ServiceTeam {
         public ArrayList<Team> teams;
        static Map h;

    public static ServiceTeam instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceTeam() {
         req = new ConnectionRequest();
    }

    public static ServiceTeam getInstance() {
        if (instance == null) {
            instance = new ServiceTeam();
        }
        return instance;
    }

     public ArrayList<Team> parseTeams(String jsonText){
        try {
            teams=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> teamsListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)teamsListJson.get("root");
            for(Map<String,Object> obj : list){
                Team t = new Team();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setEtat(((int)Float.parseFloat(obj.get("etat").toString())));
                t.setName(obj.get("name").toString());
                
                
                teams.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return teams;
    }
    
    public ArrayList<Team> getAllTeams(Map m){
        ArrayList<Team> listT = new ArrayList<>();
        ArrayList d = (ArrayList)m.get("root");
        System.out.println("list teams "+d);
        System.out.println("Size"+d.size());

        for(int i = 0; i<d.size();i++){
            Map f =  (Map) d.get(i);
            Team t = new Team();
            Double ll = (Double) f.get("id");
            t.setId(ll.intValue());
            
            
            t.setName((String)f.get("name"));
 //           t.setCreated((Date)f.get("created"));
           
                //      t.setUpdated((Date)f.get("updated"));

            
           

            listT.add(t);  
        }        
        return listT;

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
                System.out.println(targetReader);
                h= p.parseJSON(targetReader);
                System.out.println("h / "+h);
                
            } catch (IOException ex) {
                Logger.getLogger(Scrumify.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        });
        NetworkManager.getInstance().addToQueueAndWait(r);
        return h; 
    }
}
