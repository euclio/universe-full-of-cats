package org.absurdist.absurdengine;

import java.util.Observable;
import org.absurdist.j_utils.Delegate;

/**
 *
 * @author sam
 */

/*
 * A Timer schedules a method associated with a GameView to be repeated over
 * a certain amount of time
 */

public class Timer extends Observable
{    
    public Timer(float time, GameView context, Delegate method)
    {
        System.out.println("timer start id:" + curId);
        this.time = time;
        this.context = context;
        this.method = method;

        id = curId;
        curId++;
        
        delay = time;  
    }
    
    public void tic()
    {
        if (running)
        {
            delay -= delayInc;
            
            if (delay <= 0) {
                setChanged();
                notifyObservers(id);
                delay = time;
            }
        }
    }
   
    public void start()
    {
        running = true;
        addObserver(context);
        context.timers.add(this);   
    }
    
    public void end()
    {
        running = false;
        this.deleteObservers();
        context.timers.remove(this);
    }
    
    public void resume()
    {
        running = true;
    }
    
    public void pause()
    {
        running = false;
    }
    
    public static void reset()
    {
        curId = 0;
    }
    
    public boolean isRunning()
    {
    	return running;
    }
    
    private GameView context;
    private boolean running;
    private float delay;
        
    private final int id;
    private final float time;
    private final float delayInc = (float)1/RunnerThread.FPS;
    
    public Delegate method;
    
    public static int curId = 0;
}
