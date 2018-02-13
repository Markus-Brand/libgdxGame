package com.mb.game.level;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;

/**
 * TODO: Add class comment
 * @author Markus
 */
public class Level {

    private LevelStructure mLevelStructure;
    private String mName;
    private World mWorld;

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

    public void setName(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public void setWorld(World world) {
        mWorld = world;
    }
}
