/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.Feature.services;

import com.blackout.scrumify.Feature.Entities.Features;
import com.blackout.scrumify.GestionMeeting.Entities.Meeting;
import com.blackout.scrumify.GestionProjets.Entities.Project;
import com.blackout.scrumify.GestionProjets.Services.ServiceProjet;
import com.blackout.scrumify.GestionSprints.Entities.Sprint;
import com.blackout.scrumify.Userstory.Entities.UserStory;
import com.blackout.scrumify.Userstory.Services.ServiceUserstory;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Amine
 */
public class ServiceFeature {
    
    public ArrayList<Features> features;
    static Map g;
    public static Double Rate;

    public static ServiceFeature instance = null;
    public boolean resultOK;
    private ConnectionRequest req;
                    boolean t;

    public ServiceFeature() {
        req = new ConnectionRequest();
    }

    public static ServiceFeature getInstance() {
        if (instance == null) {
            instance = new ServiceFeature();
        }
        return instance;
    }
   
     public boolean ajoutFeature(Features s) {
          String Url = "http://localhost/ScrumifyApi/web/app_dev.php/addFeature?&name="+s.getName()+"&etat="+s.getEtat()+"&sprint_id"+s.getSprint_id();
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl(Url);
        con.setPost(true);
        con.addResponseListener((NetworkEvent evt) -> {

            byte[] data = (byte[]) evt.getMetaData();
            String sp = new String(data);
            System.out.println(s);
            if (!sp.contains("erreur")) {
                Dialog.show("Confirmation", "success", "Ok", null);
                ArrayList<Features> pr = parseFeatures(sp);
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
     
     
     
     public ArrayList<Features> parseFeatures(String json) {

        ArrayList<Features> listFeatures = new ArrayList<>();

        try {
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            /*
                On doit convertir notre réponse texte en CharArray à fin de
            permettre au JSONParser de la lire et la manipuler d'ou vient 
            l'utilité de new CharArrayReader(json.toCharArray())
            
            La méthode parse json retourne une MAP<String,Object> ou String est 
            la clé principale de notre résultat.
            Dans notre cas la clé principale n'est pas définie cela ne veux pas
            dire qu'elle est manquante mais plutôt gardée à la valeur par defaut
            qui est root.
            En fait c'est la clé de l'objet qui englobe la totalité des objets 
                    c'est la clé définissant le tableau de tâches.
            */
            Map<String, Object> features = j.parseJSON(new CharArrayReader(json.toCharArray()));
                       
            
            /* Ici on récupère l'objet contenant notre liste dans une liste 
            d'objets json List<MAP<String,Object>> ou chaque Map est une tâche                
            */
            List<Map<String, Object>> list = (List<Map<String, Object>>) features.get("root");
            List<Map<String, Object>> list1 = (List<Map<String, Object>>) list.get(0);

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list1) {
                //Création des tâches et récupération de leurs données
                Features O = new Features();

                
               Map<String, String> x = new HashMap<>();
                O.setId((int) Float.parseFloat(obj.get("id").toString()) );
                
                O.setName(obj.get("name").toString());
                O.setEtat((int) Float.parseFloat(obj.get("etat").toString()));
                O.setIsDeleted((int) Float.parseFloat(obj.get("isDeleted").toString()));
              
               if(O.getIsDeleted()==0)   
               listFeatures.add(O);

            }

        } catch (IOException ex) {
        }
        
        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
        */
        System.out.println(listFeatures);
        return listFeatures;

    }
     
      ArrayList<Features> listFeatures = new ArrayList<>();
    
    public ArrayList<Features> getList2(){       
        ConnectionRequest con = new ConnectionRequest();
        con.setUrl("http://localhost/ScrumifyApi/web/app_dev.php/allFeature");
        con.setPost(true);
        con.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                ServiceFeature ser = new ServiceFeature();
                listFeatures = ser.parseFeatures(new String(con.getResponseData()));
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(con);
        return listFeatures;
    }
    public void deleteFeature(Features s) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/scrumifyApi/web/app_dev.php/deleteM/"+s.getId();
     
              
                
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
    
    
    public void UpdateFeature(Features s, int nb ,String name) {
        ConnectionRequest con = new ConnectionRequest();// création d'une nouvelle demande de connexion
        String Url = "http://localhost/scrumifyApi/web/app_dev.php/editFeature/"+s.getId()+"?name="+name+"&etat="+nb;
     
              
                
        con.setUrl(Url);// Insertion de l'URL de notre demande de connexion

        con.addResponseListener((e) -> {
            String str = new String(con.getResponseData());//Récupération de la réponse du serveur
            System.out.println(str);//Affichage de la réponse serveur sur la console

        });
        NetworkManager.getInstance().addToQueueAndWait(con);// Ajout de notre demande de connexion à la file d'attente du NetworkManager
    }
    
     public ArrayList<Features> getAllFeatures(Map m) {
        ArrayList<Features> listM = new ArrayList<>();
        ArrayList d = (ArrayList) m.get("root");

        ArrayList n = (ArrayList) d.get(0);
        System.out.println(n.size());
        for (int i = 0; i < n.size(); i++) {
            Map f = (Map) n.get(i);

            Features t = new Features();
            float id = Float.parseFloat(f.get("id").toString());
            t.setId((int) id);
            t.setName(f.get("name").toString());
            t.setEtat((int) Float.parseFloat(f.get("etat").toString()));
         
            
            listM.add(t);
        }
        return listM;

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
