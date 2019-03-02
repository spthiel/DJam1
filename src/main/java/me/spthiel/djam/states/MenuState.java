package me.spthiel.djam.states;

import me.spthiel.djam.Main;
import me.spthiel.djam.menu.Button;

import javax.imageio.ImageIO;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MenuState extends GameState implements MouseMotionListener, MouseListener, ComponentListener {

    private List<Button> buttons;

    public MenuState() {
        buttons = new ArrayList<>();
        try {
            addButton(new Button(Main.missingTexture, ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResource("textures/testunit.png"))), 0.1, 0.1, 0.9, 0.2, () -> System.out.println("I haz been clicked")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        game.addMouseListener(this);
        game.addMouseMotionListener(this);
        game.addComponentListener(this);
    }

    @Override
    public void clearListeners() {
        game.removeMouseListener(this);
        game.removeMouseMotionListener(this);
        game.removeComponentListener(this);
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(!this.isActive) {
            return;
        }
        buttons.forEach(button -> button.mouseMoved(e));
    }

    private void addButton(Button button) {
        buttons.add(button);
        addDrawable(button);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!this.isActive) {
            return;
        }
        buttons.forEach(button -> button.mouseClicked(e));
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void componentResized(ComponentEvent e) {
        if(!this.isActive) {
            return;
        }
        buttons.forEach(button -> button.componentResized(e));
    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
