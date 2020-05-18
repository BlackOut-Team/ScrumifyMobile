/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionMeeting.Entities;


/**
 *
 * @author hp
 */
public class Meeting {
     private int id;
    private String name,place;
    private String type;
    private String meetingDate;
    private int sprint;

    public Meeting(){
    }
    public Meeting(String name, String place,String type,int sprint,String meetingDate) {
        this.name = name;
        this.place = place;
        this.sprint = sprint;
        this.type = type;
         this.meetingDate = meetingDate;
    }

    public Meeting(int id, String name, String place,String type,int sprint,String meetingDate) {
        this.id = id;
        this.name = name;
        this.place = place;
        this.type = type;
        this.meetingDate = meetingDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeetingDate() {
        return meetingDate;
    }

    public void setMeetingDate(String meetingDate) {
        this.meetingDate = meetingDate;
    }

    public int getSprint() {
        return sprint;
    }

    public void setSprint(int sprint) {
        this.sprint = sprint;
    }



    @Override
    public String toString() {
        return "meetings{" + "id=" + id + ", name=" + name + ", place=" + place + ", type=" + type + ", meetingDate=" + meetingDate + '}';
    }
}
