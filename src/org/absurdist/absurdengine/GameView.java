package org.absurdist.absurdengine;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author sam
 */

/*
 * A GameView encapsulates game logic and rendering methods
 */

public class GameView extends ViewGroup implements Observer
{    
    public GameView(Context context)
    {
        super(context);
        this.setWillNotDraw(false);
    }
        
    @Override
    protected void onLayout (boolean changed, int l, int t, int r, int b)
    {
        for (int c = 0; c < getChildCount(); c++) {
            getChildAt(c).layout(t, t, r, b);
        }
    }

    @Override
    // called when timers fire
    public void update(Observable timer, Object id)
    {
        // execute the method of the firing timer
        timers.get((Integer)id).method.function();
    }
        
    // called RunnerThread.FPS times per second.
    public void update()
    {
        // step all running timers running on this GameView
        for (Timer t : timers) {
            t.tic();
        }        

        updateGame();
    }   
    
    // override to implement game logic
    protected void updateGame() {}

    // list of timers running (or paused) on this GameView
    protected ArrayList<Timer> timers = new ArrayList<Timer>();
}
