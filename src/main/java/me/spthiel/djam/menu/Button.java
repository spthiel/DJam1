package me.spthiel.djam.menu;

import me.spthiel.djam.Main;
import me.spthiel.djam.Drawable;
import me.spthiel.djam.util.Vec2d;
import me.spthiel.djam.util.Vec2i;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.image.BufferedImage;

public class Button extends ComponentAdapter implements MouseMotionListener, Drawable, MouseListener {

    private BufferedImage texture, hoverTexture;
    private Vec2d xy, dxdy;
    private int x,y,dx,dy;
    private boolean hover = false;
    private Runnable onClick;
    private String text;

    public Button(BufferedImage texture, BufferedImage hoverTexture, Vec2d xy, Vec2d dxdy, String text, Runnable onClick) {
        this.texture = texture;
        this.hoverTexture = hoverTexture;
        this.xy = xy;
        this.dxdy = dxdy;
        this.onClick = onClick;
        updatePosition();
        this.text = text;
    }

    public Button(BufferedImage texture, BufferedImage hoverTexture, double x, double y, double dx, double dy, String text, Runnable onClick) {
        this.texture = texture;
        this.hoverTexture = hoverTexture;
        this.xy = new Vec2d();
        xy.x = x;
        xy.y = y;
        this.dxdy = new Vec2d();
        dxdy.x = dx;
        dxdy.y = dy;
        this.onClick = onClick;
        updatePosition();
        this.text = text;
    }

    public Button(BufferedImage texture, BufferedImage hoverTexture, int x, int y, int width, int height, String text, Runnable onClick) {
        this.texture = texture;
        this.hoverTexture = hoverTexture;
        this.xy = new Vec2d();
        xy.x = x/800.0;
        xy.y = y/800.0;
        this.dxdy = new Vec2d();
        dxdy.x = (x+width)/800.0;
        dxdy.y = (y+height)/800.0;
        this.onClick = onClick;
        updatePosition();
        this.text = text;
    }

    private boolean isInRect(int x, int y) {
        return x > this.x && x < this.dx && y > this.y && y < this.dy;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if(hover && !isInRect(e.getX(), e.getY())) {
            hover = !hover;
        } else if(!hover && isInRect(e.getX(), e.getY())) {
            hover = !hover;
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        if(hover) {
            g.drawImage(hoverTexture, x, y, dx-x, dy-y, null);
        } else {
            g.drawImage(texture, x, y, dx-x, dy-y, null);
        }
        g2d.setFont(getDesiredFont());
        Rectangle rect = getStringBounds(g2d, text);
        int startX = x+((dx-x)/2-rect.width/2);
        int startY = y+((dy-y)/2-rect.height/2);
        g2d.drawString(text, startX, startY);
    }

    private Rectangle getStringBounds(Graphics2D g2, String str) {
        FontRenderContext frc = g2.getFontRenderContext();
        GlyphVector gv = g2.getFont().createGlyphVector(frc, str);
        return gv.getPixelBounds(null, x, y);
    }

    private Font getDesiredFont() {
        return new Font(Font.SANS_SERIF, Font.BOLD, 20);
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(isInRect(e.getX(), e.getY())) {
            onClick.run();
        }
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
        super.componentResized(e);
        updatePosition();
    }

    private void updatePosition() {
        Vec2i xy = Main.getGame().translatePos(this.xy.x, this.xy.y);
        Vec2i dxdy = Main.getGame().translatePos(this.dxdy.x, this.dxdy.y);
        x = xy.x;
        y = xy.y;
        dx = dxdy.x;
        dy = dxdy.y;
    }
}
