package com.codename1.dropbox.services;


/**
 * 
 *  @author Chen
 */
public class DropboxService extends com.codename1.io.ConnectionRequest implements com.codename1.auth.oauth1.SignedService {

	public static final String THUMBNAILS = "thumbnails";

	public static final String METADATA = "metadata";

	public DropboxService(String method, String path) {
	}

	protected void readResponse(java.io.InputStream input) {
	}

	public void applyParameters(java.util.Hashtable target) {
	}
}
