/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionUsers.services;

import com.blackout.scrumify.GestionUsers.entities.User;
import com.blackout.scrumify.Utils.Session;
import com.codename1.components.ImageViewer;
import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkManager;
import com.codename1.io.Storage;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.URLImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author AmiraDoghri
 */
public class userService {

    static Map g;

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

    public boolean connecting(String login, String password) {

        Map m = getResponse("connect?username=" + login + "&password=" + password);

        ArrayList d = (ArrayList) m.get("root");

        String n = d.get(0).toString();
        System.out.println(n);
        if (n.equals("false")) {
            return false;
        } else {
            Object data = d.get(0);

            System.out.println(data);
            Storage session = new Storage();
            session.writeObject("session", data);
            Map f = (Map) Storage.getInstance().readObject("session");
            float id = Float.parseFloat(f.get("id").toString());
            Session s = new Session();

            System.out.println((Map) Storage.getInstance().readObject("session"));
            return true;

        }
    }

    public int registering(User u) {
        //String p = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(13));
        Map m = getResponse("connect/register?name=" + u.getName() + "&lastname=" + u.getLastname() + "&username=" + u.getUsername() + "&email=" + u.getEmail() + "&password=" + u.getPassword() + "&image=" + u.getAvatar());

        ArrayList d = (ArrayList) m.get("root");
        System.out.println(d);
        Map n = (Map) d.get(0);
        System.out.println(n);
        if (n.equals("false")) {
            return 0;
        } else {

            int id = (int) Float.parseFloat(n.get("id").toString());

            return id;

        }
    }

    public Boolean update(User u) {
        //String p = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(13));
        Map m = getResponse("connect/account/profile/edit/" + Session.u.getId() + "?name=" + u.getName() + "&lastname=" + u.getLastname() + "&username=" + u.getUsername() + "&email=" + u.getEmail() );
        ArrayList d = (ArrayList) m.get("root");
        System.out.println(d);
        Map n = (Map) d.get(0);
        System.out.println(n);
        if (n.equals("false")) {
            return false;
        } else {

            int id = (int) Float.parseFloat(n.get("id").toString());

            return true;

        }
    }
 public Boolean updateAvatar(String img) {
        //String p = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt(13));
        Map m = getResponse("connect/account/avatar/" + Session.u.getId() + "?image=" + img );
        ArrayList d = (ArrayList) m.get("root");
        System.out.println(d);
        Map n = (Map) d.get(0);
        System.out.println(n);
        if (n.equals("false")) {
            return false;
        } else {

            int id = (int) Float.parseFloat(n.get("id").toString());

            return true;

        }
    }
    public User profile() {
        User u = new User();
        Map m = getResponse("connect/account/profile?id="+Session.u.getId());
        ArrayList d = (ArrayList) m.get("root");
        System.out.println(d);
        Map n = (Map) d.get(0);
        System.out.println(n);
        if (n.equals("false")) {
            return null;
        } else {

            int id = (int) Float.parseFloat(n.get("id").toString());
            u.setId(id);
            u.setName(n.get("name").toString());
            u.setLastname(n.get("lastname").toString());
            u.setUsername(n.get("username").toString());
            u.setEmail(n.get("email").toString());
            u.setAvatar(n.get("image").toString());
            return u;

        }
    }

}
