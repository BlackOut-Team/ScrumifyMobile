/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.Userstory.Services;




import com.blackout.scrumify.Userstory.Entities.UserStory;
import com.blackout.scrumify.Feature.Entities.Features;
import com.blackout.scrumify.Feature.services.ServiceFeature;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Amine
 */
public class ServiceUserstory {
     public ArrayList<UserStory> userstory;
    static Map g;
    public static Double Rate;

    public static ServiceUserstory instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
                    boolean t;

    public ServiceUserstory() {
        req = new ConnectionRequest();
    }

    public static ServiceUserstory getInstance() {
        if (instance == null) {
            instance = new ServiceUserstory();
        }
        return instance;
    }

   public boolean ajoutUserstory(UserStory s) {
          String Url = "http://localhost/ScrumifyApi/web/app_dev.php/addUserstory?&description="+s.getDescription()+"&priority="+s.getPriority()+"&story_point="+s.getStory_point()+"&etat="+s.getEtat();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Url);
        con.setPost(true);
        con.addResponseListener((NetworkEvent evt) -> {

            byte[] data = (byte[]) evt.getMetaData();
            String sp = new String(data);
            System.out.println(s);
            if (!sp.contains("erreur")) {
                Dialog.show("Confirmation", "success", "Ok", null);
                ArrayList<UserStory> pr = parseUserstory(sp);
                s.setId(pr.get(0).getId());
                System.out.println(s.getId());
                 t= true;
            } else {
                Dialog.show("Erreur", "date", "Ok", null);
                t=false;
            }

        });
        NetworkManager.getInstance().addToQueueAndWait(con);
       return t;
     }
     

    public boolean editUserstory(Features pr , UserStory u) {
        String url = "http://localhost/scrumifyApi/web/app_dev.php/editUserstory/"+pr.getId()+"/" + u.getId() +"?etat=" + u.getEtat()+ "&description=" + u.getDescription() + "&priority=" + u.getPriority() + "&story_point="+u.getStory_point() ;
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

    public void archiveUserstory( UserStory sp) {
        String url = "http://localhost/scrumifyApi/web/app_dev.php/deleteMUserstory"  + sp.getId() ;
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

    public ArrayList<UserStory> getAllUserstory(Map m) {
        ArrayList<UserStory> listP = new ArrayList<>();
        ArrayList d = (ArrayList) m.get("root");

        if(!d.isEmpty()){
        for (int i = 0; i < d.size(); i++) {
            Map f = (Map) d.get(i);

            UserStory t = new UserStory();
             float id = Float.parseFloat(f.get("id").toString());
                t.setId((int) id);
                t.setEtat(((int) Float.parseFloat(f.get("etat").toString())));
                t.setPriority(((int) Float.parseFloat(f.get("etat").toString())));
                t.setStory_point(((int) Float.parseFloat(f.get("etat").toString())));
            
            

            listP.add(t);
        }
        }
        else {
            System.out.println("empty");
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
    
    public void deleteUserstory(UserStory s) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/scrumifyApi/web/app_dev.php/deleteMUserstory/"+s.getId();
     
              
                
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
    
    
    public void UpdateUserstory(UserStory s, int nb,int story_point,int priority ,String description) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/scrumifyApi/web/app_dev.php/editUserstory/"+s.getId()+"?description="+description+"&etat="+nb+"&story_point="+story_point+"&priority="+priority;
     
              
                
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
    
     public ArrayList<UserStory> parseUserstory(String jsonText) {
        try {

            userstory = new ArrayList<>();
            JSONParser j = new JSONParser();
            g = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            for (int i = 0; i < g.size(); i++) {

                UserStory t = new UserStory();
                float id = Float.parseFloat(g.get("id").toString());
                t.setId((int) id);
                t.setEtat(((int) Float.parseFloat(g.get("etat").toString())));
                t.setDescription(g.get("description").toString());
                t.setPriority(((int) Float.parseFloat(g.get("priority").toString())));
                t.setStory_point(((int) Float.parseFloat(g.get("story_point").toString())));
                
                
          
                userstory.add(t);
            }

        } catch (IOException ex) {

        }
        return userstory;
    }
      ArrayList<UserStory> listUserStory = new ArrayList<>();
    
    public ArrayList<UserStory> getList2(){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/ScrumifyApi/web/app_dev.php/allUserstory");
        con.setPost(true);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceUserstory ser = new ServiceUserstory();
                listUserStory = ser.parseUserstory(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listUserStory;
    }
  
}
