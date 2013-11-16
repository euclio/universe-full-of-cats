package org.gamefolk.beg.multiplayer;

import org.absurdist.absurdengine.GameView;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
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

	private Point2D destination = new Point2D(0, 0);
	private Rect destinationArea = new Rect(30, 30, 70, 70);

	private int faceSpeed = 5;

	private Sprite face;
	private Sprite ship;
	private Timer animationTimer;
	
	public Game(Context context)
	{
		super(context);

		//faceFrames[0] = BitmapFactory.decodeResource(AbsurdGameTest.res, R.drawable.face0);
		//faceFrames[1] = BitmapFactory.decodeResource(AbsurdGameTest.res, R.drawable.face1);

		//face = new Sprite(faceFrames[faceFrame], 50, 50);
		/*animationTimer = new Timer(.5f, this, new Delegate()
		{
			@Override
			public void function()
			{
				if (faceFrame == 0) {
					faceFrame = 1;
				}
				else {
					faceFrame = 0;
				}

				face.bitmap = faceFrames[faceFrame];
			}
		});

		animationTimer.start();
		animationTimer.pause();*/
		Bitmap bigShip = BitmapFactory.decodeResource(AbsurdGameTest.res, R.drawable.ship1);
		Bitmap smallShip = bigShip.createScaledBitmap(bigShip, 120, 110, false);
		ship = new Sprite(smallShip, 100, 100);
	}

	public boolean onTouchEvent(MotionEvent event)
	{
		destination.set((int) event.getX(), (int) event.getY());
		destinationArea.set((int) event.getX() - 20, (int) event.getY() - 20, (int) event.getX() + 20, (int) event.getY() + 20);

		return true;
	}

	@Override
	public void onDraw(Canvas canvas)
	{
		canvas.drawBitmap(ship.bitmap, ship.location.x, ship.location.y, null);
	}

	@Override
	protected void updateGame()
	{
		if (!Rect.intersects(ship.bounds, destinationArea))
		{
			/*if (!animationTimer.isRunning()) {
				animationTimer.resume();
			}*/

			int dx = 0;
			int dy = 0;

			if (ship.location.x > destination.x)
				dx =  faceSpeed * -1;
			else if (ship.location.x < destination.x)
				dx = faceSpeed;

			if (ship.location.y > destination.y)
				dy = faceSpeed * -1;
			else if (ship.location.y < destination.y)
				dy = faceSpeed;

			ship.translate(dx, dy);
		}
		else
		{
			//if (animationTimer.isRunning()) {
			//	animationTimer.pause();
			//}
		}
	}
	
	
}