package me.spthiel.djam.states;

import me.spthiel.djam.game.MapUpdater;
import me.spthiel.djam.map.Map;
import me.spthiel.djam.util.Resources;

import java.io.IOException;

public class IngameState extends GameState {

    public IngameState() {
        try {
            Map map = new Map(Resources.getFile(Resources.Type.MAPS,"test2.json"));
            MapUpdater updater = new MapUpdater(map);
            addDrawable(updater);
            addUpdateable(updater);
        } catch (IOException e) {
            e.printStackTrace();
        }
        game.setBorderactive(true);
    }

    @Override
    public void clearListeners() {

    }
}
