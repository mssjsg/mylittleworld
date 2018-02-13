package com.maksing.quietworld.tools;

import com.badlogic.gdx.tools.texturepacker.TexturePacker;

public class AtlasGenerator {
    public static void main(String[] args) {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 512;
        settings.maxHeight = 1024;
        settings.debug = false;
        settings.paddingX = 0;
        settings.paddingY = 0;

//        TexturePacker.process(settings, "raw_assets/styles", "../app/assets/styles", "uiskin.pack");
        TexturePacker.process(settings, "raw_assets/images", "../app/assets/images", "images.pack");
        TexturePacker.process(settings, "raw_assets/tile", "../app/assets/images", "tiles.pack");
    }
}
