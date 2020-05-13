/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.Dropbox;
import com.blackout.scrumify.auth.oauth1.OAuth1;
import com.blackout.scrumify.auth.oauth1.SignedService;
import com.blackout.scrumify.dropbox.services.DownloadUploadService;
import com.blackout.scrumify.dropbox.services.DropboxProvider;
import com.blackout.scrumify.dropbox.services.DropboxService;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Display;
import com.codename1.ui.Image;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.html.DocumentInfo;
import java.util.Hashtable;
import java.util.Vector;
/**
 *
 * @author AmiraDoghri
 */
public class DropboxAccess {
    private static String CALLBACK = "http://www.codenameone.com";
    private static String consumerSecret = "rhm6b48dzsl166g";
    private static String consumerKey = "4wwgb8kt70pr31r";
    private OAuth1 oauth;
    private static DropboxAccess instance = new DropboxAccess();
    
    private ActionListener errorListener;
    
    public static void setCALLBACK(String CALLBACK) {
        DropboxAccess.CALLBACK = CALLBACK;
    }

    public static void setConsumerKey(String consumerKey) {
        DropboxAccess.consumerKey = consumerKey;
    }

    public static void setConsumerSecret(String consumerSecret) {
        DropboxAccess.consumerSecret = consumerSecret;
    }

    private DropboxAccess() {
        NetworkManager.getInstance().addDefaultHeader("Accept-Language", "en-us");
        DocumentInfo.setDefaultEncoding("UTF-8");
    }

    public static DropboxAccess getInstance() {
        return instance;
    }

    /**
     * Authenticate the user to use the dropbox api
     *
     * @param al the ActionListener to receive the callback response if the
     * authentication succeeded the event will contain the access token else the
     * event will be null
     */
    public void showAuthentication(final ActionListener al) {
        OAuth1.register(CALLBACK, new DropboxProvider(), consumerKey, consumerSecret);
        oauth = new OAuth1(CALLBACK) {

            public void onAuthenticated() {
                Display.getInstance().callSerially(new Runnable() {

                    public void run() {
                        ActionEvent evt = new ActionEvent(oauth.getAccessToken());
                        al.actionPerformed(evt);
                    }
                });
            }

            public void onDeniedAccess() {
                Display.getInstance().callSerially(new Runnable() {

                    public void run() {
                        al.actionPerformed(null);
                    }
                });
            }
        };
        if(errorListener != null){
            oauth.setErrorListener(errorListener);
        }
        oauth.authenticate(Display.getInstance().getCurrent());
    }

    /**
     * Return the directory files under the given dir
     *
     * @param the dir to list the Files for example: "Public"
     */
    public Vector getFiles(String dir) {
        final Vector files = new Vector();
        DropboxService service = new DropboxService(DropboxService.METADATA, dir);
        service.addResponseListener(new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                NetworkEvent e = (NetworkEvent) evt;
                Hashtable data = (Hashtable) e.getMetaData();
                Vector contents = (Vector) data.get("contents");
                for (int i = 0; i < contents.size(); i++) {
                    Hashtable content = (Hashtable) contents.get(i);
                    String path = (String) content.get("path");
                    files.add(path);
                }
            }
        });
        oauth.signRequest((SignedService) service);
        if(errorListener != null){
            service.addResponseCodeListener(errorListener);
        }
        NetworkManager.getInstance().addToQueueAndWait(service);
        return files;
    }

    /**
     * Gets a thumbnail for an image
     * @param jpeg if true "jpeg" format if false "png"
     * @param size 'xs'(32x32s),'s'(64x64),'m'(128x128), 'l'(640x480), 'xl'(1024x768)
     */ 
    public Image getThumbnailForImage(String filePath, boolean jpeg, String size) {
        // "jpg", "jpeg", "png", "tiff", "tif", "gif", and "bmp".
        if (filePath.endsWith("png") || filePath.endsWith("jpg") || filePath.endsWith("jpeg")
                || filePath.endsWith("tiff") || filePath.endsWith("tif") || filePath.endsWith("gif")
                || filePath.endsWith("bmp")) {
            final Image[] ret = new Image[1];
            DropboxService service = new DropboxService(DropboxService.THUMBNAILS, filePath);
            service.addResponseListener(new ActionListener() {

                public void actionPerformed(ActionEvent evt) {
                    NetworkEvent e = (NetworkEvent) evt;
                    byte[] data = (byte[]) e.getMetaData();
                    ret[0] = Image.createImage(data, 0, data.length);
                }
            });
            service.addArgument("format", jpeg ? "jpeg" : "png");
            service.addArgument("size", size);
            if(errorListener != null){
                service.addResponseCodeListener(errorListener);
            }
            oauth.signRequest((SignedService) service);
            NetworkManager.getInstance().addToQueueAndWait(service);

            return ret[0];

        } else {
            throw new IllegalArgumentException("thumbnail is supported on \"jpg\", "
                    + "\"jpeg\", \"png\", \"tiff\", \"tif\", \"gif\", and \"bmp\" types only");
        }

    }

    public byte[] downloadFile(String filePath) {
        DownloadUploadService req = new DownloadUploadService(true, filePath);
        oauth.signRequest((SignedService) req);
        if(errorListener != null){
            req.addResponseCodeListener(errorListener);
        }
        NetworkManager.getInstance().addToQueueAndWait(req);
        return req.getFileData();
    }

    /**
     * set an error listener to handle the errors yourself
     */ 
    public void setErrorListener(ActionListener errorListener) {
        this.errorListener = errorListener;
    }
}
