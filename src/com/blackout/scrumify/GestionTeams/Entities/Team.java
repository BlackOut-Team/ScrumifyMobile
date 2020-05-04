package com.blackout.scrumify.GestionTeams.Entities;


import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Iheb
 */
public class Team {
    
    private int id,etat,ind;
    String name ; 
    private String created,updated;

    public Team(int id,String name ,String created, String updated, int etat, int ind ) {
        this.id = id;
        this.name=name;
        this.etat = etat;
        this.ind = ind;
        this.created = created;
        this.updated = updated;
    }

    public Team(String name ,String created, String updated, int etat, int ind) {
        this.name=name;
        this.etat = etat;
        this.ind = ind;
        this.created = created;
        this.updated = updated;
    }

    public Team(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Team( String name, String created, String updated) {
       
        this.name = name;
        this.created = created;
        this.updated = updated;
    }
    
        public Team( String name, int etat,int ind) {
       
        this.name = name;
        this.etat = etat;
        this.ind = ind;
    }
    
    
   

    public Team() {
      
    }

    public int getId() {
        return id;
    }

    public int getEtat() {
        return etat;
    }

    public int getInd() {
        return ind;
    }

    public Team(String name) {
        this.name = name;
    }

    public String getCreated() {
        return created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setInd(int ind) {
        this.ind = ind;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Team{" + "id=" + id + ", etat=" + etat + ", ind=" + ind + ", name=" + name + ", created=" + created + ", updated=" + updated + '}';
    }
    
 
    
    

}