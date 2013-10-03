package com.alex.gl.core.widget.helper;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Aisks
 * Date: 03.10.13
 * Time: 20:18
 * To change this template use File | Settings | File Templates.
 */
public class FileUtils {

    private static Logger log = Logger.getLogger(FileUtils.class.getName());

    public static final String PROFILES_DAO = "profiles.dao";

    public static void saveSettings(Object object) {
        File file = new File(PROFILES_DAO);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
        } catch (Exception e) {
            log.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public static Object loadObject() {
        File file = new File(PROFILES_DAO);
        try {
            if (!file.exists()) {
                file.createNewFile();
            } else {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                return ois.readObject();
            }
        } catch (EOFException e) {
            log.log(Level.WARNING, "Profile is empty ;-)");
        } catch (Exception e) {
            log.log(Level.WARNING, e.getMessage(), e);
        }
        return null;
    }

}
