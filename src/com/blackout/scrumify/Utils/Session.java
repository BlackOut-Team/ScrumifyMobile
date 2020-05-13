/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.Utils;

import com.blackout.scrumify.GestionUsers.entities.User;
import com.codename1.io.Preferences;
import com.codename1.io.Storage;
import java.util.Map;

/**
 *
 * @author bhk
 */
public final class Session {
    public static final String BASE_URL="http://localhost/scrumifyApi/web/app_dev.php/";
    public static User u =new User() ;
    Preferences p ;
    
    private static Session instance;

   
 
    public static Session getInstace() {
            instance = new Session();     
        return instance;
    }

  

    public Session() {
                   Map f = (Map) Storage.getInstance().readObject("session");

                 float id = Float.parseFloat(f.get("id").toString());
             u.setId((int) id);           
             u.setName(f.get("username").toString());
                  p.set("user", u.getId());

             System.out.println(u.getId());

    }
    
   
     
    
}
