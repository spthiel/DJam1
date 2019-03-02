package me.spthiel.djam.game;

import me.spthiel.djam.Main;
import me.spthiel.djam.Drawable;
import me.spthiel.djam.Updateable;
import me.spthiel.djam.map.Map;
import me.spthiel.djam.util.KeyStates;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MapUpdater implements Updateable, Drawable {

    public static int defaultTileSize = 100;
    private static float movepertick = 0.1f;

    private Map map;
    private float cameraX = 0, cameraY = 0;
    private float zoom = 1;

    public MapUpdater(Map map) {
        this.map = map;
    }

    @Override
    public void update() {
        if(KeyStates.isPressed(KeyEvent.VK_D)) {
            cameraX += movepertick;
        }
        if(KeyStates.isPressed(KeyEvent.VK_A)) {
            cameraX -= movepertick;
        }
        if(KeyStates.isPressed(KeyEvent.VK_S)) {
            cameraY += movepertick;
        }
        if(KeyStates.isPressed(KeyEvent.VK_W)) {
            cameraY -= movepertick;
        }
    }

    @Override
    public void draw(Graphics g) {

        if(cameraX < 0) {
            cameraX = 0;
        }
        if(cameraX > map.map.width) {
            cameraX = map.map.width;
        }
        if(cameraY < 0) {
            cameraY = 0;
        }
        if(cameraY > map.map.height) {
            cameraY = map.map.height;
        }

        drawMap(g);
        drawSidebars(g);
    }

    private void drawMap(Graphics g) {

        Rectangle bounds = Main.getGame().getBounds();

        int dx, dy, endx, endy, startx, starty;
        dx = (int)((bounds.width/2)/(defaultTileSize*zoom)+3);
        startx = (int)cameraX-dx;
        if(startx < 0) {
            startx = 0;
        }
        dy = (int)((bounds.height/2)/(defaultTileSize*zoom)+3);
        starty = (int)cameraY-dy;
        if(starty < 0) {
            starty = 0;
        }
        endx = (int)cameraX+dx;
        if(endx > map.map.width) {
            endx = map.map.width;
        }
        endy = (int)(cameraY+dy);
        if(endy > map.map.height) {
            endy = map.map.height;
        }

        int finalDx = dx;
        int finalDy = dy;
        int finalStartx = startx;
        int finalEndx = endx;
        int finalEndy = endy;
        int finalStarty = starty;
        final boolean[] b = {false};
        map.map.layers.stream()
                .filter(layer -> layer.type.equalsIgnoreCase("tilelayer"))
                .forEach(layer -> {
                            for(int x = finalStartx; x < finalEndx; x++) {
                                int[] dataY = layer.data[x];
                                for(int y = finalStarty; y < finalEndy; y++) {
                                    if(dataY[y] != 0) {
                                        int viewx = bounds.width/2 - (int)((cameraX-x)*defaultTileSize*zoom);
                                        int viewy = bounds.height/2 - (int)((cameraY-y)*defaultTileSize*zoom);

                                        g.drawImage(map.getBlock(dataY[y]), viewx-1, viewy-1, (int)(defaultTileSize*zoom)+2, (int)(defaultTileSize*zoom)+2, null);
                                    }
                                }
                            }
                        }
                );
    }

    private void drawSidebars(Graphics g) {

        Rectangle rect = Main.getGame().getBounds();
        g.setColor(new Color(0x111111));
        g.fillRect(0, 0, rect.width, 40);
        g.fillRect(0, rect.height-40, rect.width, 50);
        g.fillRect(0, 0, 200, rect.height);
        g.fillRect(rect.width-200, 0, 200, rect.height);

    }

    @Override
    public boolean isActive() {
        return true;
    }
}
