package me.spthiel.djam.map;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MapStruct {

    public int height;
    public boolean infinite;
    public List<LayerStruct> layers;
    public int nextlayerid;
    public int nextobjectid;
    public String orientation;
    public String renderorder;
    public String tiledversion;
    public int tileheight;
    public int tilewidth;
    public List<TilesetStruct> tilesets;
    public List<TilesetStruct> tilesetsDetailed;
    public String type;
    public float version;
    public int width;


    public static class LayerStruct {

        public LayerStruct(@JsonProperty("data") int[] data, @JsonProperty("width") int width) {
            this.width = width;
            if(data != null) {
                int height = data.length/width;
                this.data = new int[width][height];
                for(int x = 0; x < width; x++) {
                    for(int y = 0; y < height; y++) {
                        this.data[x][y] = data[x+y*width];
                    }
                }
            }
        }

        public int[][] data;
        public int height;
        public int id;
        public String name;
        public float opacity;
        public String type;
        public boolean visible;
        public int width;
        public int x;
        public int y;

        public String draworder;
        public List<ObjectStruct> objects;
        public List<LayerStruct> layers;

    }

    public static class ObjectStruct {

        public int height;
        public int id;
        public String name;
        public int rotation;
        public String type;
        public boolean visible;
        public int width;
        public int x;
        public int y;

        public boolean ellipse;
        public boolean point;

    }

    public static class TilesetStruct {

        public String source;
        public int firstgid;

    }

}
