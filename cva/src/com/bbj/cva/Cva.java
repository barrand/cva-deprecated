package com.bbj.cva;

import java.util.Iterator;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Cva implements ApplicationListener {
	Texture dropImage;
	Texture bucketImage;
	Rectangle bucket;
	Sound dropSound;
	Music rainMusic;
	OrthographicCamera camera;
	SpriteBatch batch;
	Array<Rectangle> raindrops;
	long lastDropTime;
	
	
	
	public static int STAGE_WIDTH = 1920;
	public static int STAGE_HEIGHT = 1080;

	@Override
	public void create() {
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.wav"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));

		rainMusic.setLooping(true);
		rainMusic.play();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, STAGE_WIDTH, STAGE_HEIGHT);

		batch = new SpriteBatch();

		bucket = new Rectangle();
		bucket.x = STAGE_WIDTH / 2 - 48 / 2;
		bucket.y = 20;
		bucket.width = 48;
		bucket.height = 48;

		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}

	@Override
	public void dispose() {
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
		batch.dispose();
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = (int) (touchPos.x - 48 / 2);
		}

		if (Gdx.input.isKeyPressed(Keys.LEFT))
			bucket.x -= (int) (200 * Gdx.graphics.getDeltaTime());
		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			bucket.x += (int) (200 * Gdx.graphics.getDeltaTime());

		if (bucket.x < 0)
			bucket.x = 0;
		if (bucket.x > STAGE_WIDTH - 48)
			bucket.x = STAGE_WIDTH - 48;

		if (TimeUtils.nanoTime() - lastDropTime > 1000000000)
			spawnRaindrop();

		Iterator<Rectangle> iter = raindrops.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();
			if (raindrop.y + 48 < 0)
				iter.remove();
		}

		batch.setProjectionMatrix(camera.combined);
		batch.begin();
		for (Rectangle raindrop : raindrops) {
			batch.draw(dropImage, raindrop.x, raindrop.y, 48, 48);
			if (raindrop.overlaps(bucket)) {
				dropSound.play();
				iter.remove();
			}
		}
		batch.draw(bucketImage, bucket.x, bucket.y, 48, 48);
		batch.end();
	}

	private void spawnRaindrop() {
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, STAGE_WIDTH - 48);
		raindrop.y = STAGE_HEIGHT - 200;
		raindrop.width = 48;
		raindrop.height = 48;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
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
