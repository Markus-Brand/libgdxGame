package com.mb.game.level.tiles;

/**
 * Created by Markus on 03.02.2018.
 */
public class StaticTile extends Tile {

    private int mOffset;

    public StaticTile(int mOffset) {
        this.mOffset = mOffset;
    }

    @Override
    public int getTileOffset() {
        return mOffset;
    }
}
