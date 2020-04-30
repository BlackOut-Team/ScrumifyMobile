/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionProjets.Services;

import com.blackout.scrumify.GestionProjets.Entities.Project;
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
        static Map h;
    public static Double Rate ;
    
    public static ServiceProjet instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceProjet() {
         req = new ConnectionRequest();
    }

    public static ServiceProjet getInstance() {
        if (instance == null) {
            instance = new ServiceProjet();
        }
        return instance;
    }

    public boolean addProject(Project t) {
      String url =  "http://localhost/scrumifyApi/web/app_dev.php/Project/Add?name="+t.getName()+"&description="+t.getDescription()+"&duedate="+t.getDuedate()+"&etat=1";
      // String url;
        //    url = "http://localhost/scrumifyApi/web/app_dev.php/Project/Add?name=gg&description=ddd&etat=1";
       req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }

    public ArrayList<Project> parseProjects(String jsonText){
        try {
            projects=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Project t = new Project();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setEtat(((int)Float.parseFloat(obj.get("etat").toString())));
                t.setName(obj.get("name").toString());
                t.setDescription(obj.get("description").toString());
                float datedebut = Float.parseFloat(obj.get("created").toString());
                String created =new SimpleDateFormat("dd/MM/yyyy").format(new Date((long) (datedebut * 1000L)));
               float datefin = Float.parseFloat(obj.get("duedate").toString());
                String duedate =new SimpleDateFormat("dd/MM/yyyy").format(new Date((long) (datefin * 1000L)));
                t.setCreated((Date) obj.get("created"));
                t.setDuedate((Date) obj.get("created"));
                t.setTeam_id(Integer.parseInt(obj.get("team_id").toString()));
                
                projects.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return projects;
    }
    
    public ArrayList<Project> getAllProjects(){
        String url = Statics.BASE_URL+"/Project/showP";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                projects = parseProjects(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return projects;
    }
 
    public static Map<String, Object> getResponse(String url){
        url = "http://127.0.0.1:8000/bonplan/"+url;
        System.out.println(url);
        ConnectionRequest r = new ConnectionRequest();
        r.setUrl(url);
        r.setPost(false);
        System.out.println("url   :   "+r);
        InfiniteProgress prog = new InfiniteProgress();
        Dialog dlg = prog.showInifiniteBlocking();
        r.setDisposeOnCompletion(dlg);
        r.addResponseListener((evt) -> {
            try {
                JSONParser p = new JSONParser();
                Reader targetReader = new InputStreamReader(new ByteArrayInputStream(r.getResponseData()));
                System.out.println(targetReader);
                h= p.parseJSON(targetReader);
                System.out.println("h            ::::----------"+h);
                
            } catch (IOException ex) {
                //Logger.getLogger(MyApplication.class.getName()).log(Level.SEVERE, null, ex);
            }
 
        });
        NetworkManager.getInstance().addToQueueAndWait(r);
        return h; 
    }
}
