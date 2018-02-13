package com.mb.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.mb.game.level.Level;
import com.mb.game.level.LevelReader;

import java.nio.IntBuffer;

/**
 * TODO: Add class comment
 * @author Markus
 */
public class WorkingTitleGame extends ApplicationAdapter {
	SpriteBatch batch;

	Viewport viewport;
	Camera camera;

	Level mLevel;

	World mWorld;
	Box2DDebugRenderer debugRenderer;
	private Fixture mFixture;

	@Override
	public void create () {
		Box2D.init();
		batch = new SpriteBatch();

		mWorld = new World(new Vector2(0, -10.0f), true);

		addDynamicBody();

		mLevel = LevelReader.readLevel("level1", mWorld);

		debugRenderer = new Box2DDebugRenderer();

		camera = new OrthographicCamera();
		viewport = new FitViewport(1600, 900, camera);
		viewport.apply();

		camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
	}

	private void addDynamicBody() {
		// First we create a body definition
		BodyDef bodyDef = new BodyDef();
		// We set our body to dynamic, for something like ground which doesn't move we would set it to StaticBody
		bodyDef.type = BodyDef.BodyType.DynamicBody;
		// Set our body's starting position in the world
		bodyDef.position.set(2.6f, 13.5f);

		// Create our body in the world using our body definition
		Body body = mWorld.createBody(bodyDef);

		// Create a circle shape and set its radius to 6
		CircleShape circle = new CircleShape();
		circle.setRadius(1f);

		// Create a fixture definition to apply our shape to
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = circle;
		fixtureDef.density = 0.5f;
		fixtureDef.friction = 0.4f;
		fixtureDef.restitution = 0.6f; // Make it bounce a little bit

		// Create our fixture and attach it to the body
		mFixture = body.createFixture(fixtureDef);

		// Remember to dispose of any shapes after you're done with them!
		// BodyDef and FixtureDef don't need disposing, but shapes do.
		circle.dispose();
	}


	@Override
	public void render () {
		if(Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
			mFixture.getBody().applyLinearImpulse(0.0f, 1.0f, mFixture.getBody().getPosition().x,  mFixture.getBody().getPosition().y, true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			mFixture.getBody().applyLinearImpulse(1.0f, 0.0f, mFixture.getBody().getPosition().x,  mFixture.getBody().getPosition().y, true);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.A)) {
			mFixture.getBody().applyLinearImpulse(-1.0f, 0.0f, mFixture.getBody().getPosition().x,  mFixture.getBody().getPosition().y, true);
		}

		mWorld.step(1 / 60.0f, 6, 2);
	    camera.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.setProjectionMatrix(camera.combined);
		batch.begin();
		mLevel.render(batch);
		batch.end();

		Camera debugCam = new OrthographicCamera(32, 32);
		debugRenderer.render(mWorld, debugCam.combined);
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
