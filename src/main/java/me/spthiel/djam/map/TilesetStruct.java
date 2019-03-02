package me.spthiel.djam.map;

public class TilesetStruct extends MapStruct.TilesetStruct {

        public int columns;
        public String image;
        public int imageheight;
        public int imagewidth;
        public int margin;
        public String name;
        public int spacing;
        public int tilecount;
        public String tiledversion;
        public int tileheight;
        public int tilewidth;
        public String type;
        public float version;

        public static class NullStruct extends TilesetStruct {}
}