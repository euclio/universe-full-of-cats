package org.gamefolk.beg.multiplayer;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import java.util.ArrayList;

import org.absurdist.absurdengine.GameView;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;

import org.absurdist.absurdengine.Sprite;
import org.absurdist.absurdengine.Timer;
import org.gamefolk.beg.R;
import org.absurdist.j_utils.Delegate;
import org.absurdist.j_utils.Point2D;

/**
 *
 * @author sam
 */

public class Game extends GameView
{
	private int faceFrame = 1;
	private Bitmap[] faceFrames = new Bitmap[2];
	private ArrayList activeBullets = new ArrayList();
	private ArrayList collidingBullets = new ArrayList();
	
	private ArrayList playerShip = new ArrayList();
	private Bitmap[] engineFrames = new Bitmap[8];
	private int engineFrame = 0;

	private Point2D destination = new Point2D(0, 0);
	private Rect destinationArea = new Rect(30, 30, 70, 70);

	private int engineSpeed = 5;

	private Sprite engine;
	private Sprite ship;
	private Sprite gun;
	private Boolean shipIsDead = false;
	private Timer engineAnimationTimer;

	private Sprite background;
	public Game(Context context)
	{
		super(context);
		
		//ridiculously bad code establishing an array of animation frames for the ENGINES.
		Bitmap engineTemp;
		
		engineTemp = BitmapFactory.decodeResource(MultiplayerActivity.res, R.drawable.engine2_frame1);
		engineFrames[0] = engineTemp.createScaledBitmap(engineTemp, 120, 110, false);
		engineTemp = BitmapFactory.decodeResource(MultiplayerActivity.res, R.drawable.engine2_frame2);
		engineFrames[1] = engineTemp.createScaledBitmap(engineTemp, 120, 110, false);
		engineTemp = BitmapFactory.decodeResource(MultiplayerActivity.res, R.drawable.engine2_frame3);
		engineFrames[2] = engineTemp.createScaledBitmap(engineTemp, 120, 110, false);
		engineTemp = BitmapFactory.decodeResource(MultiplayerActivity.res, R.drawable.engine2_frame4);
		engineFrames[3] = engineTemp.createScaledBitmap(engineTemp, 120, 110, false);
		engineTemp = BitmapFactory.decodeResource(MultiplayerActivity.res, R.drawable.engine2_frame5);
		engineFrames[4] = engineTemp.createScaledBitmap(engineTemp, 120, 110, false);
		engineTemp = BitmapFactory.decodeResource(MultiplayerActivity.res, R.drawable.engine2_frame6);
		engineFrames[5] = engineTemp.createScaledBitmap(engineTemp, 120, 110, false);
		engineTemp = BitmapFactory.decodeResource(MultiplayerActivity.res, R.drawable.engine2_frame7);
		engineFrames[6] = engineTemp.createScaledBitmap(engineTemp, 120, 110, false);
		engineTemp = BitmapFactory.decodeResource(MultiplayerActivity.res, R.drawable.engine2_frame8);
		engineFrames[7] = engineTemp.createScaledBitmap(engineTemp, 120, 110, false);
		engine = new Sprite(engineFrames[engineFrame], 100, 100);
		engineAnimationTimer = new Timer(.1f, this, new Delegate()
		{
			@Override
			public void function()
			{
				if (engineFrame < 7) {
					engineFrame++;
				}
				else {
					engineFrame = 0;
				}

				engine.bitmap = engineFrames[engineFrame];
			}
		});

		engineAnimationTimer.start();
		engineAnimationTimer.pause();
		
		
		//Ship body animations
		Bitmap bigShip = BitmapFactory.decodeResource(MultiplayerActivity.res, R.drawable.ship1);
		Bitmap smallShip = bigShip.createScaledBitmap(bigShip, 120, 110, false);
		ship = new Sprite(smallShip, 100, 100);
		
		Bitmap smallGun = BitmapFactory.decodeResource(MultiplayerActivity.res, R.drawable.gun3);
		Bitmap gunz = smallGun.createScaledBitmap(smallGun, 120, 110, false);
		gun = new Sprite(gunz,100,100);
		
		playerShip.add(ship);
		playerShip.add(engine);
		playerShip.add(gun);
		
		Bitmap tempBg = BitmapFactory.decodeResource(MultiplayerActivity.res, R.drawable.bg_small);
		Bitmap scaledTempBg = tempBg.createScaledBitmap(tempBg, 1920, 1080, false);
		background = new Sprite (scaledTempBg, 0, 0);
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		if (ship.bounds.contains((int)event.getX(), (int)event.getY()) && !shipIsDead){
			Bitmap bigBullet = BitmapFactory.decodeResource(MultiplayerActivity.res, R.drawable.pew);
			Bitmap smallBullet = bigBullet.createScaledBitmap(bigBullet, 10, 50, false);
			activeBullets.add(new Sprite (smallBullet, ship.bounds.centerX()-7, ship.bounds.top+5));
			
			//reuse this when it comes time to add bluetooth functionality 
			
			//collidingBullets.add(new Sprite (smallBullet, ship.bounds.centerX()-7, 0));
		}
		else{
		destination.set((int) event.getX(), (int) event.getY());
		destinationArea.set((int) event.getX() - 20, (int) event.getY() - 20, (int) event.getX() + 20, (int) event.getY() + 20);
		}
		return true;
	}

	@Override
	public void onDraw(Canvas canvas)
	{	
		
		canvas.drawBitmap(background.bitmap, background.location.x, background.location.y, null);
		//Draw all bullets currently on screen
		Sprite temp;
		for(int i = 0; i < activeBullets.size(); i++){
			temp = (Sprite)activeBullets.get(i);
			canvas.drawBitmap(temp.bitmap, temp.location.x, temp.location.y, null);
		}
		
		for(int i = 0; i < collidingBullets.size(); i++){
			temp = (Sprite)collidingBullets.get(i);
			canvas.drawBitmap(temp.bitmap, temp.location.x, temp.location.y, null);
		}
		if (!shipIsDead){
			for (int i = 0; i < playerShip.size(); i++){
				temp = (Sprite)playerShip.get(i);
				canvas.drawBitmap(temp.bitmap, temp.location.x, temp.location.y, null);
			}
		}
		
		
	}

	@Override
	protected void updateGame()
	{
		if (!Rect.intersects(ship.bounds, destinationArea) && !shipIsDead)
		{
			if (!engineAnimationTimer.isRunning()) {
				engineAnimationTimer.resume();
			}

			int dx = 0;
			int dy = 0;

			if (ship.location.x > destination.x)
				dx =  engineSpeed * -1;
			else if (ship.location.x < destination.x)
				dx = engineSpeed;

			if (ship.location.y > destination.y)
				dy = engineSpeed * -1;
			else if (ship.location.y < destination.y)
				dy = engineSpeed;
			
			Sprite temp0;
			for (int i = 0; i < playerShip.size(); i++){
				temp0 = (Sprite)playerShip.get(i);
				temp0.translate(dx, dy);
			}
		}
		else
		{
			if (engineAnimationTimer.isRunning()) {
				engineAnimationTimer.pause();
			}
		}

		Sprite temp;
		for(int i = 0; i < activeBullets.size(); i++){
			temp = (Sprite)activeBullets.get(i);

			if (temp.bounds.top > 0 && temp.bounds.bottom < this.getHeight()){
				temp.translate(0, -10);
			}
			else{activeBullets.remove(i);}
		}
		Sprite temp2;
		for(int i = 0; i < collidingBullets.size(); i++){
			temp2 = (Sprite)collidingBullets.get(i);

			if (temp2.bounds.top >= 0 && temp2.bounds.bottom < this.getHeight()){
				temp2.translate(0, 10);
				if (Rect.intersects(ship.bounds, temp2.bounds)){
					shipIsDead = true;
					collidingBullets.remove(i);
				}
			}
			else{collidingBullets.remove(i);}
		}
	}


}