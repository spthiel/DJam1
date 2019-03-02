package me.spthiel.djam;

import me.spthiel.djam.util.KeyStates;
import me.spthiel.djam.util.Vec2i;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class Game extends JPanel {

    private JFrame window;

    private JPanel[] border = new JPanel[4];

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

        nextFrames = new BufferedImage[4];
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

        border = new JPanel[4];

        for (int i = 0; i < border.length; i++) {
            JPanel panel = border[i] = new JPanel();
            panel.setVisible(true);
        }

        window.add(border[0], BorderLayout.LINE_START);
        border[0].setPreferredSize(new Dimension(230,600));
        window.add(border[1], BorderLayout.PAGE_START);
        border[1].setPreferredSize(new Dimension(600,50));
        window.add(border[2], BorderLayout.LINE_END);
        border[2].setPreferredSize(new Dimension(230,600));
        window.add(border[3], BorderLayout.PAGE_END);
        border[3].setPreferredSize(new Dimension(600,50));


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
                    repaint();
                    if(Main.getState() != null) {
                        Main.getState().draw();
                    }
                    update();
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

    public JPanel[] getBorders() {
        return border;
    }

    public void setBorderactive(boolean bool) {

        for (int i = 0; i < border.length; i++) {
            border[i].setVisible(bool);
        }
    }

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
        for (BufferedImage bufferedImage : toDraw) {
            g.drawImage(bufferedImage, 0, 0, null);
        }
    }

    public Vec2i translatePos(double x, double y) {
        Vec2i out = new Vec2i();
        out.x = (int)(getWidth()*x);
        out.y = (int)(getHeight()*y);
        return out;
    }
}
