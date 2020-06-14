/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionActivity.Entities;

/**
 *
 * @author hp
 */
public class Activity {
     int id;
     int  user_id;
    String action;
    int viewed;
   

    public Activity(int id,int user_id, String action, int viewed) {
        this.id = id;
        this.user_id =user_id;
        this.action = action;
        this.viewed = viewed;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Activity() {
    }
    

    public Activity(String action, int viewed) {
        this.action = action;
        this.viewed = viewed;
    }
 public Activity(String action, int viewed, int user_id) {
        this.action = action;
        this.viewed = viewed;
        this.user_id = user_id;
    }
 public Activity(int id, String action, int viewed, int user_id, String username) {
        this.id = id;
        this.action = action;
        this.viewed = viewed;
        this.user_id = user_id;
    }

    public int getId() {
        return id;
    }

    public String getAction() {
        return action;
    }

    public int getViewed() {
        return viewed;
    }

    public void setId(int id) {
        this.id = id;
    }

  
    public void setAction(String action) {
        this.action = action;
    }

    public void setViewed(int viewed) {
        this.viewed = viewed;
    }

    @Override
    public String toString() {
        return "Activity{" + "id=" + id + ", action=" + action + ", viewed=" + viewed + '}';
    }
    
}


