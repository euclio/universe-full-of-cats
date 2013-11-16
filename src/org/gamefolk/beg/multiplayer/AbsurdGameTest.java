package org.gamefolk.beg.multiplayer;

import javax.net.ssl.ManagerFactoryParameters;

import org.absurdist.absurdengine.GameActivity;
import org.absurdist.absurdengine.GameView;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 *
 * @author sam
 */

public class AbsurdGameTest extends GameActivity
{
	private BluetoothManager manager;
	
	@Override
	public void onConfigurationChanged(Configuration newConfig){
		super.onConfigurationChanged(newConfig);
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        
        manager = new BluetoothManager(this);
        
        super.onCreate(savedInstanceState); 
    }
    
    @Override
    public void onDestroy() {
    	super.onDestroy();
    	manager.destroyReceiver();
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
