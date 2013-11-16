package org.gamefolk.beg.multiplayer;

import org.absurdist.absurdengine.GameActivity;
import org.absurdist.absurdengine.GameView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;

/**
 *
 * @author sam
 */

public class MultiplayerActivity extends GameActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density  = getResources().getDisplayMetrics().density;
        screenHeightDpi = outMetrics.heightPixels / density;
        screenWidthDpi = outMetrics.widthPixels / density;
        
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
    
    static float screenHeightDpi;
    static float screenWidthDpi;
    private Game world;
}