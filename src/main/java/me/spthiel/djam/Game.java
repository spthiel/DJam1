package me.spthiel.djam;

import me.spthiel.djam.util.KeyStates;
import me.spthiel.djam.util.Vec2i;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class Game extends JPanel {

    private JFrame window;

    private BufferedImage[] toDraw;
    private BufferedImage[] nextFrames;
    public boolean running = true;

    public Game() {

        window = new JFrame("Inkquisition");

        window.setExtendedState(Frame.MAXIMIZED_BOTH);
        window.setBounds(100,100,800,800);
        window.setResizable(true);

        window.add(this);
        window.setVisible(true);
        setVisible(true);

        nextFrames = new BufferedImage[5];
        toDraw = new BufferedImage[nextFrames.length];
        for(int i = 0; i < nextFrames.length; i++) {
            if(i == 0) {
                nextFrames[i] = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                toDraw[i] = nextFrames[i];
            } else {
                nextFrames[i] = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
                toDraw[i] = nextFrames[i];
            }
        }

        Thread loop = new Thread(() -> {
            long current;
            long last = System.currentTimeMillis();
            float delta;
            float timeperframe = 1000.0f/Main.MAXFPS;
            float timer = 0;
            float fpstimer = 0;

            while(running) {

                current = System.currentTimeMillis();
                delta = current - last;
                last = current;
                timer += delta;
                fpstimer += delta;

                if(fpstimer > 1000) {
                    System.out.println("Fps: " + fps);
                    fps = 0;
                    fpstimer -= 1000;
                }
                if(timer >= timeperframe) {
                    if(Main.getState() != null) {
                        Main.getState().draw();
                    }
                    update();
                    window.repaint();
                    timer -= timeperframe;
                }
            }
        });
        loop.start();

        window.addKeyListener(new KeyStates());

        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                running = false;
                window.dispose();
            }
        });
    }

    private int fps;

    public Graphics getGraphics(int layer) {
        return nextFrames[layer].getGraphics();
    }

    public void update() {
        for(int i = 0; i < nextFrames.length; i++) {
            toDraw[i] = nextFrames[i];
            if(i == 0) {
                nextFrames[i] = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            } else {
                nextFrames[i] = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.repaint();
        fps++;
        for(int i = 0; i < toDraw.length; i++) {
            g.drawImage(toDraw[i], 0, 0, null);
        }
    }

    public Vec2i translatePos(double x, double y) {
        Vec2i out = new Vec2i();
        out.x = (int)(getWidth()*x);
        out.y = (int)(getHeight()*y);
        return out;
    }
}
