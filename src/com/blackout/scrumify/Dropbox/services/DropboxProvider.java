/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.dropbox.services;


import com.blackout.scrumify.auth.oauth1.PlainText;
import com.blackout.scrumify.auth.oauth1.ServiceProvider;
import com.blackout.scrumify.auth.oauth1.SigningImplementation;

/**
 *
 * @author Chen
 */
public class DropboxProvider extends ServiceProvider{

	static final String BASEURL = "https://api.dropbox.com/1/oauth";
	

	public DropboxProvider() {
		super("Dropbox", 
                        BASEURL + "/EY3scmLP_5AAAAAAAAAAnFhGMc6hU3D-clbVNYESvf5-gMzjbN7HKX6cJJkXERiX", 
                        BASEURL + "/EY3scmLP_5AAAAAAAAAAnFhGMc6hU3D-clbVNYESvf5-gMzjbN7HKX6cJJkXERiX",
                        "https://www.dropbox.com/1/oauth/authorize", 
                        BASEURL + "/EY3scmLP_5AAAAAAAAAAnFhGMc6hU3D-clbVNYESvf5-gMzjbN7HKX6cJJkXERiX", 
                        new PlainText());
	}
    
}
