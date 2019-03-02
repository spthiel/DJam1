package me.spthiel.djam.util;

import me.spthiel.djam.Main;
import me.spthiel.djam.game.MapUpdater;

import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawMap {

    public void draw(BufferedImage[][] data, Graphics g) {

        Rectangle bounds = Main.getGame().getBounds();

        int dx, dy, endx, endy, startx, starty;
        dx = (int)((bounds.width/2)/(MapUpdater.defaultTileSize*MapUpdater.zoom)+3);
        startx = (int)MapUpdater.cameraX-dx;
        if(startx < 0) {
            startx = 0;
        }
        dy = (int)((bounds.height/2)/(MapUpdater.defaultTileSize*MapUpdater.zoom)+3);
        starty = (int)MapUpdater.cameraY-dy;
        if(starty < 0) {
            starty = 0;
        }
        endx = (int)MapUpdater.cameraX+dx;
        if(endx > data.length) {
            endx = data.length;
        }
        endy = (int)(MapUpdater.cameraY+dy);
        if(endy > data[0].length) {
            endy = data[0].length;
        }

        for(int x = startx; x < endx; x++) {
            BufferedImage[] dataY = data[x];
            for(int y = starty; y < endy; y++) {
                if(dataY[y] != null) {
                    int viewx = bounds.width/2 - (int)((MapUpdater.cameraX-x)*MapUpdater.defaultTileSize*MapUpdater.zoom);
                    int viewy = bounds.height/2 - (int)((MapUpdater.cameraY-y)*MapUpdater.defaultTileSize*MapUpdater.zoom);

                    g.drawImage(dataY[x], viewx-1, viewy-1, (int)(MapUpdater.defaultTileSize*MapUpdater.zoom)+2, (int)(MapUpdater.defaultTileSize*MapUpdater.zoom)+2, null);
                }
            }
        };
    }

}
