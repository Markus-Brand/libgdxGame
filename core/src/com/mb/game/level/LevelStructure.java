package com.mb.game.level;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

/**
 * Created by Markus on 03.02.2018.
 */
public class LevelStructure {

    private Texture mTexture;
    private Pixmap mPixmap;

    private Pixmap mSpriteSheet;
    private int mTileSize;
    private int mWidth;
    private int mHeight;

    public void setSpriteSheet(Pixmap spriteSheet) {
        mSpriteSheet = spriteSheet;
    }

    public void setTileSize(int tileSize) {
        mTileSize = tileSize;
    }

    public void setWidth(int width) {
        mWidth = width;
    }

    public void setHeight(int height) {
        mHeight = height;
    }

    public void addTile(int x, int y, int spriteIndex) {
        ensurePixmapExists();

        int tilesPerRow = mSpriteSheet.getWidth() / mTileSize;
        int atlasStartX = (spriteIndex % tilesPerRow) * mTileSize;
        int atlasStartY = (spriteIndex / tilesPerRow) * mTileSize;

        int resultStartX = x * mTileSize;
        int resultStartY = y * mTileSize;

        for(int pixelX = 0; pixelX < mTileSize; pixelX++) {
            for(int pixelY = 0; pixelY < mTileSize; pixelY++) {
                int pixelColor = mSpriteSheet.getPixel(atlasStartX + pixelX, atlasStartY + pixelY);
                mPixmap.drawPixel(resultStartX + pixelX, resultStartY + pixelY, pixelColor);
            }
        }
    }

    private void ensurePixmapExists() {
        if(mPixmap == null) {
            mPixmap = new Pixmap(mTileSize * mWidth, mTileSize * mHeight, Pixmap.Format.RGBA8888);
        }
    }

    public Texture getTexture() {
        if(mTexture == null) {
            mTexture = new Texture(mPixmap);
        }
        return mTexture;
    }

    public void dispose() {
        mTexture.dispose();
        mSpriteSheet.dispose();
        mPixmap.dispose();
    }
}
