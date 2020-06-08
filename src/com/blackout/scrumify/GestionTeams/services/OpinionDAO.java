/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionTeams.services;

import com.codename1.messaging.Message;
import com.codename1.ui.Display;

/**
 *
 * @author Iheb
 */
public class OpinionDAO {
    public OpinionDAO(){
    
    Message m = new Message("Body of message");
    
Display.getInstance().sendMessage(new String[] {"scrumify.application@gmail.com"}, " Report Problem ", m);
        
        
    }
}
