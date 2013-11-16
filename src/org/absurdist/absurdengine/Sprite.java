package org.absurdist.absurdengine;

import android.graphics.Bitmap;
import android.graphics.Rect;
import org.absurdist.j_utils.Point2D;

/**
 *
 * @author sam
 */

// simple graphic holder class. Sprites have a bitmap, location, and bounding rectangle
public class Sprite 
{    
    public Sprite(Bitmap bitmap, int x, int y) 
    {
        this.bitmap = bitmap;
        location = new Point2D(x, y);
        setBounds();
    }
    
    public void translate(int dx, int dy)
    {
    	location.set(location.x += dx, location.y += dy);
    	setBounds();
    }
    
    private void setBounds()
    {
    	bounds.set(location.x, location.y, location.x + bitmap.getWidth(), location.y + bitmap.getHeight());
    }
    
    public Bitmap bitmap;
    public Point2D location;
    public Rect bounds = new Rect();
}