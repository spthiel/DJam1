package me.spthiel.djam.util;

import java.io.File;
import java.net.URL;

public class Resources {

    public static enum Type {
        MAPS("maps/"),
        TEXTURES("textures/"),
        UNITS("units/"),
        NONE("");

        private String path;

        Type(String path) {
            this.path = path;
        }
    }

    public static URL getFile(Type type, String filename) {
        return Resources.class.getClassLoader().getResource(type.path + filename);
    }

}
