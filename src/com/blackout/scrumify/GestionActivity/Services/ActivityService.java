/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionActivity.Services;

import com.blackout.scrumify.GestionActivity.Entities.Activity;
import com.blackout.scrumify.GestionMeeting.Services.*;
import com.blackout.scrumify.GestionMeeting.Entities.Meeting;
import com.blackout.scrumify.GestionUsers.entities.User;
import com.blackout.scrumify.Utils.Session;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Dialog;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author hp
 */
public class ActivityService {
    
    public ArrayList<Activity> activities;
    static Map g;
    public static Double Rate;
    
    public static ActivityService instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
    boolean t;
    
    public ActivityService() {
        req = new ConnectionRequest();
    }
    
    public static ActivityService getInstance() {
        if (instance == null) {
            instance = new ActivityService();
        }
        return instance;
    }
    
    public boolean addActivity(Activity m) {
        String url = "http://localhost/scrumifyApi/web/app_dev.php/Meeting/Add?name=" + m.getAction();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.setPost(true);
        con.addResponseListener((NetworkEvent evt) -> {
            
            byte[] data = (byte[]) evt.getMetaData();
            String s = new String(data);
            System.out.println(s);
            if (!s.contains("erreur")) {
                Dialog.show("Confirmation", "success", "Ok", null);
                ArrayList<Activity> pr = parseActivities(s);
                m.setId(pr.get(0).getId());
                System.out.println(m.getId());
                t = true;
            } else {
                Dialog.show("Erreur", "date", "Ok", null);
                t = false;
            }
            
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return t;
    }
    
    public boolean viewActivity(Activity m) {
        
        String url = "http://localhost/scrumifyApi/web/app_dev.php/change_activity?id=" + m.getId();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.setPost(true);
        con.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) evt.getMetaData();
            String s = new String(data);
            System.out.println(s);
            if (!s.contains("erreur")) {
                Dialog.show("Confirmation", "success", "Ok", null);
                t = true;
                
            } else {
                Dialog.show("Erreur", "date", "Ok", null);
                t = false;
                
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(con);
        return t;
    }
    
    public ArrayList<Activity> parseActivities(String jsonText) {
        try {
            
            activities = new ArrayList<>();
            JSONParser j = new JSONParser();
            g = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            for (int i = 0; i < g.size(); i++) {
                
                Activity t = new Activity();
                float user_id = Float.parseFloat(g.get("user_id").toString());
                
                t.setUser_id((int) user_id);
                t.setAction(g.get("action").toString());
                float viewed = Float.parseFloat(g.get("viewed").toString());
                
                t.setViewed((int) viewed);
                
                activities.add(t);
            }
            
        } catch (IOException ex) {
            
        }
        return activities;
    }
    
    public ArrayList<Activity> getAllActivities(Map m) {
        ArrayList<Activity> listM = new ArrayList<>();
        ArrayList d = (ArrayList) m.get("root");
        
        ArrayList n = (ArrayList) d.get(0);
        System.out.println(n.size());
        for (int i = 0; i < n.size(); i++) {
            Map f = (Map) n.get(i);
            
            Activity t = new Activity();
            Map<Object, Object> user = (Map<Object, Object>) f.get("User");
            float user_id = Float.parseFloat(user.get("id").toString());
            float id = Float.parseFloat(f.get("id").toString());
            t.setId((int) id);
            t.setUser_id((int) user_id);
            t.setAction(f.get("action").toString());
            float viewed = Float.parseFloat(f.get("viewed").toString());
            
            t.setViewed((int) viewed);
            
            listM.add(t);
        }
        return listM;
        
    }
    
    public void deleteActivity(Activity m) {
        String url = "http://localhost/scrumifyApi/web/app_dev.php/delete_activity?id=" + m.getId();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.setPost(true);
        con.addResponseListener((NetworkEvent evt) -> {
            byte[] data = (byte[]) evt.getMetaData();
            String s = new String(data);
            if (s.contains("success")) {
                Dialog.show("Confirmation", "success", "Ok", null);
                
            } else {
                Dialog.show("Erreur", "erreur", "Ok", null);
                
            }
        });
        
        NetworkManager.getInstance().addToQueueAndWait(con);
        
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

    public User geAcOw(Map m) {
        User user = new User();
        ArrayList d = (ArrayList) m.get("root");
        
        Map f = (Map) d.get(0);
        
        User t = new User();
        float user_id = Float.parseFloat(f.get("id").toString());
        
        t.setId((int) user_id);
        t.setUsername(f.get("username").toString());
        
        return t;
        
    }
    
}
