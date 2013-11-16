package org.gamefolk.beg.multiplayer;

import org.absurdist.absurdengine.GameActivity;
import org.absurdist.absurdengine.GameView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 *
 * @author sam
 */

public class AbsurdGameTest extends GameActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        super.onCreate(savedInstanceState); 
    }
    
    @Override
    public void onStop()
    {
        super.onStop();
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
    }

    protected GameView initializeGame()
    {
        world = new Game(this);
        return world;
    }
    
    private Game world;
}