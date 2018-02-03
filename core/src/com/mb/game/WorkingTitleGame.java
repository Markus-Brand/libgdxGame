package com.mb.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.nio.IntBuffer;

public class WorkingTitleGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	Viewport viewport;
	Camera camera;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture stone = new Texture("stone.png");
		stone.getTextureData().prepare();
		Pixmap map = stone.getTextureData().consumePixmap();
		int levelSize = 32;
        Pixmap level = new Pixmap(levelSize * 32, levelSize * 32, Pixmap.Format.RGBA8888);
        stone.getTextureData().prepare();
		for(int i = 0; i < levelSize;  i++) {
		    for(int o = 0; o < levelSize; o++) {
		        if(((map.getPixel(i, o) & 0xFF000000) >> 24) > 78) {
                    level.drawPixmap(map, i * 32, o * 32);
                }
            }
        }
        img = new Texture(level);

		camera = new OrthographicCamera();
		viewport = new FitViewport(1600, 900, camera);
		viewport.apply();

		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
	}

	@Override
	public void render () {
	    camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
		batch.begin();
		batch.draw(img, 350, 0, 900, 900);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }
}
