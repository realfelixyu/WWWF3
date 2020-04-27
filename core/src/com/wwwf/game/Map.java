package com.wwwf.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {
    public TiledMap map;
    OrthogonalTiledMapRenderer renderer;
    public int tileWidth, tileHeight;
    public int mapWidthInTiles, mapHeightInTiles;
    public float mapWidthInMeters, mapHeightInMeters;
    public float metersPerTile = 1.0f;
    public float unitScale;

    public Map(String tmxFilename){
        map = loadTiledMap(tmxFilename);
        MapProperties properties = map.getProperties();
        tileWidth = properties.get("tilewidth",Integer.class);
        tileHeight = properties.get( "tileheight", Integer.class);
        mapWidthInTiles = properties.get("width",Integer.class);
        mapHeightInTiles = properties.get("height",Integer.class);
        unitScale = metersPerTile / (float) tileWidth;
        mapWidthInMeters = metersPerTile * mapWidthInTiles;
        mapHeightInMeters = metersPerTile * mapHeightInTiles;

    }
    private TiledMap loadTiledMap(String tmxFilename) {
        AssetManager assetManager = new AssetManager();
        assetManager.setLoader(TiledMap.class, new TmxMapLoader());
        assetManager.load(tmxFilename, TiledMap.class);
        assetManager.finishLoading();
        return assetManager.get(tmxFilename,TiledMap.class);

    }
}
