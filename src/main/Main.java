package main;

import gui.AuthenticationFrame;
import storage.Authentication;
import storage.FileManager;
import storage.LostFoundManager;

public class Main {

    public static void main(String[] args) {

    	
        FileManager fileManager = new FileManager();

        //Loading Users
        Authentication auth = new Authentication(fileManager);

        //Loading Items
        LostFoundManager manager = new LostFoundManager(fileManager);

        //Running AuthenticationFrame
        new AuthenticationFrame(auth,manager).setVisible(true);
        
        
    }
}

