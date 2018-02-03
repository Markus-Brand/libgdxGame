package com.mb.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mb.game.level.Level;
import com.mb.game.level.LevelReader;

import java.nio.IntBuffer;

public class WorkingTitleGame extends ApplicationAdapter {
	SpriteBatch batch;

	Viewport viewport;
	Camera camera;

	Level mLevel;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

		mLevel = LevelReader.readLevel("level1");

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
		mLevel.render(batch);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		mLevel.dispose();
	}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
    }
}
