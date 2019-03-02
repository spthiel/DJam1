package me.spthiel.djam;

import me.spthiel.djam.states.GameState;
import me.spthiel.djam.states.IngameState;
import me.spthiel.djam.states.MenuState;
import me.spthiel.djam.team.Unit;
import me.spthiel.djam.util.Resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

    public static int MAXFPS = 60;
    public static BufferedImage missingTexture;
    public static BufferedImage missingTextureAlt;
    private static ClassLoader classLoader = Main.class.getClassLoader();

    static {
        try {
            missingTexture = ImageIO.read(Resources.getFile(Resources.Type.TEXTURES,"default_texture.png"));
            missingTextureAlt = ImageIO.read(Resources.getFile(Resources.Type.TEXTURES,"default_texture_alt.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Game game;
    private static GameState state;

    public static void main(String[] args) {
        game = new Game();
        setState(new MenuState());
        Object t = Unit.Units.SMOKER;

        Thread updateLoop = new Thread(() -> {

            long current;
            long last = System.currentTimeMillis();
            float delta;
            float timeperframe = 1000.0f/MAXFPS;
            float timer = 0;
            float upstimer = 0;
            int ups = 0;

            while(game.isRunning()) {

                current = System.currentTimeMillis();
                delta = current - last;
                last = current;
                timer += delta;
                upstimer += delta;

                if(upstimer > 1000) {
                    System.out.println("Ups: " + ups);
                    ups = 0;
                    upstimer -= 1000;
                }
                if(timer >= timeperframe) {
                    state.update();
                    ups++;
                    timer -= timeperframe;
                }
            }

        });
        updateLoop.start();
    }

    public static Game getGame() {
        return game;
    }

    public static void setState(GameState state) {
        if(Main.state != null) {
            Main.state.setActive(false);
            Main.state.clearListeners();
        }
        Main.state = state;
        state.setActive(true);
    }

    public static GameState getState() {
        return state;
    }
}
