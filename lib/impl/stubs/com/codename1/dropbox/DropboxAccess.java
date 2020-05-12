package com.codename1.dropbox;


/**
 *  This is the main access API to the dropbox API
 *  https://www.dropbox.com/developers/core/docs
 *  This class encapsulates the Network access and provide simple methods to acess the 
 *  Dropbox servers.
 *  
 *  @author Chen Fishbein
 */
public class DropboxAccess {

	public static void setCALLBACK(String CALLBACK) {
	}

	public static void setConsumerKey(String consumerKey) {
	}

	public static void setConsumerSecret(String consumerSecret) {
	}

	public static DropboxAccess getInstance() {
	}

	/**
	 *  Authenticate the user to use the dropbox api
	 * 
	 *  @param al the ActionListener to receive the callback response if the
	 *  authentication succeeded the event will contain the access token else the
	 *  event will be null
	 */
	public void showAuthentication(com.codename1.ui.events.ActionListener al) {
	}

	/**
	 *  Return the directory files under the given dir
	 * 
	 *  @param the dir to list the Files for example: "Public"
	 */
	public java.util.Vector getFiles(String dir) {
	}

	/**
	 *  Gets a thumbnail for an image
	 *  @param jpeg if true "jpeg" format if false "png"
	 *  @param size 'xs'(32x32s),'s'(64x64),'m'(128x128), 'l'(640x480), 'xl'(1024x768)
	 */
	public com.codename1.ui.Image getThumbnailForImage(String filePath, boolean jpeg, String size) {
	}

	public byte[] downloadFile(String filePath) {
	}

	/**
	 *  set an error listener to handle the errors yourself
	 */
	public void setErrorListener(com.codename1.ui.events.ActionListener errorListener) {
	}
}
