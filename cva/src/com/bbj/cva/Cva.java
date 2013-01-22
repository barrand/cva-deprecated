package com.bbj.cva;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.tiled.TileAtlas;
import com.badlogic.gdx.graphics.g2d.tiled.TileMapRenderer;
import com.badlogic.gdx.graphics.g2d.tiled.TiledLoader;
import com.badlogic.gdx.graphics.g2d.tiled.TiledMap;

public class Cva implements ApplicationListener {
	OrthographicCamera camera;
	SpriteBatch batch;
	SpriteBatch spriteBatch; // #6
	TiledMap tiledMap;
	TileMapRenderer tileMapRenderer;

	public static int STAGE_WIDTH = 1920;
	public static int STAGE_HEIGHT = 1080;

	@Override
	public void create() {

		camera = new OrthographicCamera();
		camera.setToOrtho(false, STAGE_WIDTH, STAGE_HEIGHT);
		tiledMap = TiledLoader.createMap("maps/thing");
		TileAtlas tileAtlas = new TileAtlas(tiledMap, Gdx.files.internal("maps"));
		tileMapRenderer = new TileMapRenderer(tiledMap, tileAtlas, 8, 8);
		tileMapRenderer.render(camera);
		batch = new SpriteBatch();


		spriteBatch = new SpriteBatch(); // #12
	}

	@Override
	public void dispose() {
		batch.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

//		if (Gdx.input.isTouched()) {
//			Vector3 touchPos = new Vector3();
//			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
//			camera.unproject(touchPos);
//			bucket.x = (int) (touchPos.x - 48 / 2);
//		}
//
//		if (Gdx.input.isKeyPressed(Keys.LEFT))
//			bucket.x -= (int) (200 * Gdx.graphics.getDeltaTime());
//		if (Gdx.input.isKeyPressed(Keys.RIGHT))
//			bucket.x += (int) (200 * Gdx.graphics.getDeltaTime());

		tileMapRenderer.render(camera);
		batch.setProjectionMatrix(camera.combined);
		batch.begin();
//		batch.draw(bucketImage, bucket.x, bucket.y, 48, 48);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}
}
