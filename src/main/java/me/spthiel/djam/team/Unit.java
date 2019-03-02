package me.spthiel.djam.team;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.spthiel.djam.util.Resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Unit {

    public enum Units {

        SMOKER("Smoker.json"),
        EXPLODE("Explode.json");

        private Unit unit;

        Units(String url) {
            URL fileurl = Resources.getFile(Resources.Type.UNITS, url);
            ObjectMapper mapper = new ObjectMapper();
            try {
                unit = mapper.readValue(fileurl, Unit.class);
                System.out.println(unit);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public Unit(@JsonProperty("file") String filename, @JsonProperty("spritesize") int spritesize, @JsonProperty("sprites") List<Sprite> sprites) {
        URL file = Resources.getFile(Resources.Type.UNITS, filename);
        this.spritesize = spritesize;
        BufferedImage spritesheet;
        try {
            spritesheet = ImageIO.read(file);
            sprites.forEach(
                sprite -> {
                    if(sprite.base != null) {
                        sprite.baseSprites = new BufferedImage[(sprite.base.end-sprite.base.start)+1];
                        for(int i = sprite.base.start; i <= sprite.base.end; i++) {
                            sprite.baseSprites[i-sprite.base.start] = spritesheet.getSubimage(i*spritesize, 0, spritesize, spritesize);
                        }
                    }
                    if(sprite.overlay != null) {
                        sprite.overlaySprites = new BufferedImage[(sprite.overlay.end-sprite.overlay.start)+1];
                        for(int i = sprite.overlay.start; i <= sprite.overlay.end; i++) {
                            sprite.overlaySprites[i-sprite.overlay.start] = spritesheet.getSubimage(i*spritesize, 0, spritesize, spritesize);
                        }
                    }
                }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int width;
    public int height;
    public int spritesize;

    public String name;
    public int attackAccMelee;
    public int attackMinMelee;
    public int attackMaxMelee;
    public int attackMinRange;
    public int attackMaxRange;
    public int attackAccRange;
    public int speed;
    public int health;

    public static class Sprite {

        public String type;
        public Helper base;
        public Helper overlay;

        private BufferedImage[] baseSprites;
        private BufferedImage[] overlaySprites;

        private static class Helper {

            public int start;
            public int end;

        }

    }

}
