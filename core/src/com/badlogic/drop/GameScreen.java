package com.badlogic.drop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;


public class GameScreen implements Screen {
	final DropGame game;

	private Texture dropImage;
	private Texture bucketImage;
	private Sound dropSound;
	private Music rainMusic;

	private OrthographicCamera camera;

	private Rectangle bucket;

	private Array<Rectangle> raindrops;

	private long lastDropTime;

	private int dropsGathered;
	public GameScreen(final DropGame game) {
		this.game = game;

		// load the images for the droplet and the bucket, 64x64 pixels each
		dropImage = new Texture(Gdx.files.internal("droplet.png"));
		bucketImage = new Texture(Gdx.files.internal("bucket.png"));

		// load the drop sound effect and the rain background "music"
		dropSound = Gdx.audio.newSound(Gdx.files.internal("drop.mp3"));
		rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
		rainMusic.setLooping(true);

		// create the camera and the SpriteBatch
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// create a Rectangle to logically represent the bucket
		bucket = new Rectangle();
		bucket.x = 800 / 2 - 64 / 2; // center the bucket horizontally
		bucket.y = 20; // bottom left corner of the bucket is 20 pixels above
						// the bottom screen edge
		bucket.width = 64;
		bucket.height = 64;

		// Adding the Raindrops
		raindrops = new Array<Rectangle>();
		spawnRaindrop();
	}

	private void spawnRaindrop(){
		Rectangle raindrop = new Rectangle();
		raindrop.x = MathUtils.random(0, 800 - 64);
		raindrop.y = 480;
		raindrop.width = 64;
		raindrop.height = 64;
		raindrops.add(raindrop);
		lastDropTime = TimeUtils.nanoTime();
	}

	@Override
	public void render (float delta) {
		// clear the screen with a dark blue color. The
		// arguments to clear are the red, green,
		// blue and alpha component in the range [0,1]
		// of the color to be used to clear the screen.
		ScreenUtils.clear(0,0,0.2f,1);

		// the the camera to update its matrices
		camera.update();

		// tell the camera to render in the
		// coordinate system specified by the comera
		game.batch.setProjectionMatrix(camera.combined);

		// Rendering the Bucket
		// begin a new batch and draw the bucket and
		// all drops
		game.batch.begin();
		game.font.draw(game.batch, "Drops Collected: " + dropsGathered, 0, 480);

		game.batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
		for ( Rectangle raindrop : raindrops ){
			game.batch.draw(dropImage, raindrop.x, raindrop.y);
		}
		game.batch.end();

		// Making the Bucket Move (Touch/Mouse)

		if ( Gdx.input.isTouched() ){
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			camera.unproject(touchPos);
			bucket.x = (int) (touchPos.x - 64 /2);
		}

		// Making the Bucket Move (Keyboard)
		if ( Gdx.input.isKeyPressed(Input.Keys.LEFT) )
			bucket.x -= 200 * Gdx.graphics.getDeltaTime();
		if ( Gdx.input.isKeyPressed(Input.Keys.RIGHT) )
			bucket.x += 200 * Gdx.graphics.getDeltaTime();

		// screen limits
		if ( bucket.x < 0 ) bucket.x = 0;
		if ( bucket.x > 800 - 64 ) bucket.x = 800 - 64;

		// Adding the Raindrops
		if ( TimeUtils.nanoTime() - lastDropTime >1000000000 )
			spawnRaindrop();

		// move raindrops, remove any that are beneath the bottom edge of
		// the screen or that hit the bucket. In the later case we increase the
		// value or drops counter and add a sound effect.
		Iterator<Rectangle> iter = raindrops.iterator();
		while ( iter.hasNext() ){
			Rectangle raindrop = iter.next();
			raindrop.y -= 200 * Gdx.graphics.getDeltaTime();

			// if a raindrop is beneath the bottom edge of the screen
			if ( raindrop.y + 64 < 0 ) iter.remove();

			// if a raindrop hits the bucket...
			if ( raindrop.overlaps(bucket) ){
				dropsGathered++;
				dropSound.play();
				iter.remove();
			}
		}



	}

	@Override
	public void show() {
		// start the playback of the background music
		// when the screen is shown
		rainMusic.play();
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

	@Override
	public void hide() {

	}

	@Override
	public void dispose () {
		// Cleaning Up
		dropImage.dispose();
		bucketImage.dispose();
		dropSound.dispose();
		rainMusic.dispose();
	}
}
