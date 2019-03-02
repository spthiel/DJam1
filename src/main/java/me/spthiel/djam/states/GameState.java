package me.spthiel.djam.states;

import me.spthiel.djam.Game;
import me.spthiel.djam.Main;
import me.spthiel.djam.Drawable;
import me.spthiel.djam.Updateable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GameState {

    private List<Drawable> drawables;
    private List<Updateable> updateables;
    Game game;
    boolean isActive;

    public GameState() {
        this.game = Main.getGame();
        drawables = new ArrayList<>();
        updateables = new ArrayList<>();
    }

    public void addDrawable(Drawable drawable) {
        drawables.add(drawable);
    }

    public void draw() {
        Graphics g = game.getGraphics(0);
        drawables
                .stream()
                .filter(Drawable::isActive)
                .forEach(updateable -> updateable.draw(g));
    }

    public void addUpdateable(Updateable updateable) {
        updateables.add(updateable);
    }

    public void update() {
        updateables
                .stream()
                .filter(Updateable::isActive)
                .forEach(Updateable::update);
    }

    public void setActive(boolean bool) {
        isActive = bool;
    }

    abstract public void clearListeners();

}
