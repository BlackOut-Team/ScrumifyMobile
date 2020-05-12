/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.blackout.scrumify.Dropbox;
import com.blackout.scrumify.Dropbox.DropboxAccess;
import com.codename1.ui.Form;
import com.codename1.ui.Display;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.Button;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.tree.Tree;
import com.codename1.ui.tree.TreeModel;
import java.util.Vector;




/**
 *
 * @author AmiraDoghri
 */
public class Dropbox {
    
     public void showDropboxFilePicker(final boolean fileMode) {
        final Form previous = Display.getInstance().getCurrent();
        Form dropBoxFilePicker = new Form("Dropbox");
        dropBoxFilePicker.setScrollable(false);
        dropBoxFilePicker.setLayout(new BorderLayout());
        Button cancel = new Button("Cancel");
        dropBoxFilePicker.addComponent(BorderLayout.SOUTH, cancel);
        
        TreeModel model = new TreeModel() {
            public Vector getChildren(Object parent) {
                if(parent == null) {
                    parent = "";
                }
                return DropboxAccess.getInstance().getFiles((String)parent);
            }
            
            public boolean isLeaf(Object node) {
                return ((String)node).indexOf('.') > -1;
            }
        };
        Tree fileTree = new Tree(model) {
            @Override
            protected Button createNodeComponent(Object node, int depth) {
                Button b = super.createNodeComponent(node, depth);
                String nodeView = ((String)node);
                int l = nodeView.lastIndexOf('/');
                if(l > -1) {
                    nodeView = nodeView.substring(l + 1);
                }
                b.setText(nodeView);
                String nodeS = ((String)node).toLowerCase();
                if(nodeS.endsWith(".jpg") || nodeS.endsWith(".png") || nodeS.endsWith(".jpeg")) {
                    b.setIcon(DropboxAccess.getInstance().getThumbnailForImage((String)node, true, "xs"));
                }
                return b;
            }
        };
        dropBoxFilePicker.addComponent(BorderLayout.CENTER, fileTree);
        fileTree.addLeafListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                byte[] pickedFile = DropboxAccess.getInstance().downloadFile((String)evt.getSource());
                
                // do something with the file
            }

           
        });
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                previous.showBack();
            }
        });
        dropBoxFilePicker.show();
    }
}
