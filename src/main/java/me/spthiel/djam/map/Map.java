package me.spthiel.djam.map;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.spthiel.djam.util.Resources;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

public class Map {

    public MapStruct map;
    public TilesetStruct[] tilesets;
    public BufferedImage[] blocks;

    public Map(URL filepath) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        File file = new File(filepath.getFile());

        map = mapper.readValue(filepath, MapStruct.class);
        tilesets = map.tilesets.stream()
                .map(tilesetStruct -> {
                    TilesetStruct newTilesetStruct;
                    try {
                        newTilesetStruct = mapper.readValue(Resources.getFile(Resources.Type.MAPS, tilesetStruct.source), TilesetStruct.class);
                    } catch (IOException e) {
                        newTilesetStruct = new TilesetStruct.NullStruct();
                        e.printStackTrace();
                    }
                    newTilesetStruct.firstgid = tilesetStruct.firstgid;
                    newTilesetStruct.source = filepath + "../" + tilesetStruct.source;
                    return newTilesetStruct;
                })
                .toArray(TilesetStruct[]::new);

        TilesetStruct highest = Arrays.stream(tilesets)
                .max((tileset, tileset2) -> Integer.compare(tileset.firstgid+tileset.tilecount,tileset2.firstgid+tileset.tilecount))
                .orElse(new TilesetStruct.NullStruct());

        int highestid = highest.firstgid+highest.tilecount;

        blocks = new BufferedImage[highestid];

        Arrays.stream(tilesets)
                .filter(tileset -> !(tileset instanceof TilesetStruct.NullStruct))
                .forEach(tileset -> {
                    try {
                        URL url = Resources.getFile(Resources.Type.TEXTURES, tileset.image);
                        BufferedImage image = ImageIO.read(url);
                        int id = tileset.firstgid;
                        for(int y = 0; y < tileset.imageheight/tileset.tileheight; y++) {
                            for(int x = 0; x < tileset.imagewidth/tileset.tilewidth; x++) {
                                BufferedImage subimage = image.getSubimage(x*tileset.tilewidth, y*tileset.tileheight, tileset.tilewidth, tileset.tileheight);
                                blocks[id++] = subimage;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

    }

    public BufferedImage getBlock(int id) {
        if(id == 0) {
            return null;
        }
        if(id > blocks.length) {
            return null;
        }
        return blocks[id];
    }

}
