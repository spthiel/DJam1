package me.spthiel.djam.team;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Team {

    public static enum Colors {
        BLUE(new Team(""), new Color(0, 218, 255)),
        YELLOW(new Team(""), new Color(255, 255, 0)),
        RED(new Team(""), new Color(255, 0, 54)),
        GREEN(new Team(""), new Color(99, 255, 0));

        private Color color;

        Colors(Team team, Color color) {
            this.color = color;
        }
    }

    private BufferedImage[] sprites;

    public Team(String sprites) {

    }

}
