package com.mb.game.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Markus on 03.02.2018.
 */
public class Level {

    private LevelStructure mLevelStructure;

    public LevelStructure getLevelStructure() {
        return mLevelStructure;
    }

    public void setLevelStructure(LevelStructure levelStructure) {
        mLevelStructure = levelStructure;
    }

    public void render(SpriteBatch batchRenderer) {
        batchRenderer.draw(mLevelStructure.getTexture(), 350, 0, 900, 900);
    }

    public void dispose() {
        mLevelStructure.dispose();
    }
}
