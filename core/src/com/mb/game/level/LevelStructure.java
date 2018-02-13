package com.mb.game.level;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

/**
 * TODO: Add class comment
 * @author Markus
 */
public class LevelStructure {
    private static BodyDef bodyDef;

    private static PolygonShape groundBox;

    static {
        bodyDef = new BodyDef();
        bodyDef.type = BodyType.StaticBody;
        groundBox = new PolygonShape();
        groundBox.setAsBox(0.5f, 0.5f);
    }

    private final World mWorld;
    private Texture mTexture;
    private Pixmap mPixmap;

    private Pixmap mSpriteSheet;
    private int mTileSize;
    private int mWidth;
    private int mHeight;

    public LevelStructure(World world) {
        mWorld = world;
    }

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
        int resultStartY = (mHeight - y - 1) * mTileSize;

        for(int pixelX = 0; pixelX < mTileSize; pixelX++) {
            for(int pixelY = 0; pixelY < mTileSize; pixelY++) {
                int pixelColor = mSpriteSheet.getPixel(atlasStartX + pixelX, atlasStartY + pixelY);
                mPixmap.drawPixel(resultStartX + pixelX, resultStartY + pixelY, pixelColor);
            }
        }

        addStaticBodyToWorld(x, y);
    }

    private void addStaticBodyToWorld(int startX, int startY) {
        bodyDef.position.set(startX + 0.5f, startY  + 0.5f);

        Body body = mWorld.createBody(bodyDef);
        body.createFixture(groundBox, 0.0f);
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
