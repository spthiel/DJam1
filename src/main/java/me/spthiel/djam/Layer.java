package me.spthiel.djam;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Layer extends JPanel {

    private BufferedImage currentFrame;
    private BufferedImage nextFrame;
    private int imagetype;
    private int id;

    public Layer(int id, int imagetype) {
        setVisible(true);

        this.imagetype = imagetype;
        this.id = id;

        nextFrame = new BufferedImage(1, 1, imagetype);
        currentFrame = nextFrame;
    }

    public void update() {
        currentFrame = nextFrame;
        nextFrame = new BufferedImage(getWidth(), getHeight(), imagetype);
        if(imagetype == BufferedImage.TYPE_INT_RGB) {
            Graphics g = nextFrame.getGraphics();
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    public Graphics getGraphicsCurrent() {
        return nextFrame.getGraphics();
    }

    @Override
    protected void paintComponent(Graphics g) {
        //setBackground(new Color(0,0,0,0));
        g.drawImage(currentFrame, 0, 0, null);
    }

    private void resize(Rectangle rect) {
        setBounds(rect);
    }
}
