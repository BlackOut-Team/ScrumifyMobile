/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionTasks.Entities;

/**
 *
 * @author Hidaya
 */
public class Tasks {
     private int id , etat, priority ;
    private String title, description, status;

    public Tasks() {
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Tasks(int etat, String title, String description, String status) {
        this.etat = etat;
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Tasks{" + "id=" + id + ", etat=" + etat + ", priority=" + priority + ", title=" + title + ", description=" + description + ", status=" + status + '}';
    }

    public Tasks( String title, String description,int priority) {
       
        this.title = title;
        this.description = description;
         this.priority = priority;
    }

    public Tasks(int id, int etat, int priority, String title, String description, String status) {
        this.id = id;
        this.etat = etat;
        this.priority = priority;
        this.title = title;
        this.description = description;
        this.status = status;
    }

   
    
    
}
