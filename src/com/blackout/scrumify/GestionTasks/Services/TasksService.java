/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.GestionTasks.Services;

import com.blackout.scrumify.GestionTasks.Entities.Tasks;
import com.blackout.scrumify.Utils.Statics;
import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 *
 * @author Hidaya
 */
public class TasksService {
    
     public ArrayList<Tasks> tasks;
    
    public static TasksService instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    private TasksService() {
         req = new ConnectionRequest();
    }

    public static TasksService getInstance() {
        if (instance == null) {
            instance = new TasksService();
        }
        return instance;
    }
    
     public boolean addTask(Tasks t) {
        String url = Statics.BASE_URL + "/tasks/" + t.getTitle() + "/" + t.getDescription()+ "/" + t.getPriority();
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
     public ArrayList<Tasks> parseTasks(String jsonText){
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Tasks t = new Tasks();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setPriority(((int)Float.parseFloat(obj.get("priority").toString())));
                t.setTitle(obj.get("title").toString());
                t.setDescription(obj.get("description").toString());

                tasks.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return tasks;
    }
    
    public ArrayList<Tasks> getAllTasks(){
        String url = Statics.BASE_URL+"/tasks/";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }

}
