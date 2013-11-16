package org.absurdist.absurdengine;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;

/**
 *
 * @author sam
 */

public abstract class GameActivity extends Activity
{    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // provide static access to the program's resources
        res = getResources();
        
        // get an instance of the GameView subclass implemented in the program
        game = initializeGame();
        
        // explicitly bind handler to UI thread's message queue. 
        // this should happen implicitly, but sometimes it doesn't
        gameHandler = new Handler(getMainLooper());
               
        gameRunner = new RunnerThread(this);
        gameRunner.start();
        
        setContentView(game);
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        
        if (gameRunner.paused()) {
            gameRunner.unpause();
        }
        else {
            Timer.reset();
        }
    }
    
    @Override
    public void onPause()
    {
        super.onPause();
        
        if (isFinishing()) {
            gameRunner.finish();
        }
        else {
            gameRunner.pause();
        }
    }
    
    public void updateGame() 
    {
        // run in update thread
        game.update();
        
        gameHandler.post (
            new Runnable()
            {
                public void run() {
                    // post to UI thread
                    game.invalidate();  
                }
            }
        );   
    }
    
    protected abstract GameView initializeGame();
    
    private RunnerThread gameRunner;
    private Handler gameHandler;
    private GameView game;
    
    public static Resources res;
}