/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionActivity.Services;

import com.blackout.scrumify.GestionMeeting.Services.*;
import com.blackout.scrumify.GestionMeeting.Entities.Meeting;
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

    public ArrayList<Meeting> meetings;
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

    public boolean addMeeting(Meeting m) {
          String url = "http://localhost/scrumifyApi/web/app_dev.php/Meeting/Add?name=" + m.getName() + "&type=" + m.getType()+ "&place=" + m.getPlace()+ "&sprint_id=" + m.getSprint()+ "&date=" + m.getMeetingDate();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(url);
        con.setPost(true);
        con.addResponseListener((NetworkEvent evt) -> {

            byte[] data = (byte[]) evt.getMetaData();
            String s = new String(data);
            System.out.println(s);
            if (!s.contains("erreur")) {
                Dialog.show("Confirmation", "success", "Ok", null);
                ArrayList<Meeting> pr = parseMeetings(s);
                m.setId(pr.get(0).getId());
                System.out.println(m.getId());
                 t= true;
            } else {
                Dialog.show("Erreur", "date", "Ok", null);
                t=false;
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
       return t;
    }

    public boolean editMeeting(Meeting m) {
        
          String url = "http://localhost/scrumifyApi/web/app_dev.php/update_meeting/"+m.getId()+"?name=" + m.getName() + "&type=" + m.getType()+ "&place=" + m.getPlace()+ "&sprint_id=" + m.getSprint()+ "&date=" + m.getMeetingDate();
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
    public ArrayList<Meeting> parseMeetings(String jsonText) {
        try {

            meetings = new ArrayList<>();
            JSONParser j = new JSONParser();
            g = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            for (int i = 0; i < g.size(); i++) {

                Meeting t = new Meeting();
                float id = Float.parseFloat(g.get("id").toString());
                                float sp = Float.parseFloat(g.get("sprint").toString());

                t.setId((int) id);
                t.setName(g.get("name").toString());
                t.setPlace(g.get("place").toString());
                t.setType(g.get("type").toString());
                t.setSprint((int) sp);
                Map<String, Object> MapMeetingDate = (Map<String, Object>) g.get("meetingDate");

                float datedebut = Float.parseFloat(MapMeetingDate.get("timestamp").toString());
                String meetingDate = new SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date((long) (datedebut * 1000L)));
                t.setMeetingDate(meetingDate);

                meetings.add(t);
            }

        } catch (IOException ex) {

        }
        return meetings;
    }

    public ArrayList<Meeting> getAllMeetings(Map m) {
        ArrayList<Meeting> listM = new ArrayList<>();
        ArrayList d = (ArrayList) m.get("root");

        ArrayList n = (ArrayList) d.get(0);
        System.out.println(n.size());
        for (int i = 0; i < n.size(); i++) {
            Map f = (Map) n.get(i);

            Meeting t = new Meeting();
            float id = Float.parseFloat(f.get("id").toString());
            t.setId((int) id);
            t.setName(f.get("name").toString());
            t.setPlace(f.get("place").toString());
            t.setType(f.get("type").toString());
            
            Map<String, Object> MapMeetingDate = (Map<String, Object>) f.get("meetingDate");

            float datedebut = Float.parseFloat(MapMeetingDate.get("timestamp").toString());
            String meetingDate = new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date((long) (datedebut * 1000L)));
            t.setMeetingDate(meetingDate);

            listM.add(t);
        }
        return listM;

    }
    
    
    
     public void deleteMeeting(Meeting m) {
        String url = "http://localhost/scrumifyApi/web/app_dev.php/Meeting/delete/" + m.getId();
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