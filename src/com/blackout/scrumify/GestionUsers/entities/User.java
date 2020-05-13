/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionUsers.entities;

/**
 *
 * @author Amira Doghri
 */
public class User {
    int id;
    String name;

    public String getName() {
        return name;
    }

    public User(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    

    public User(int id) {
        this.id = id;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", name=" + name + '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
}
