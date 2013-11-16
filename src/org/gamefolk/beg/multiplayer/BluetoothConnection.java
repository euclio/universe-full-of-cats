package org.gamefolk.beg.multiplayer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Queue;

import android.app.AlertDialog;
import android.bluetooth.BluetoothSocket;

public class BluetoothConnection {
	
	private static BluetoothSocket socket;
	private boolean isActive = false;
	
	public void initialize(BluetoothSocket socket){
		BluetoothConnection.socket = socket;
		isActive = true;
		new SocketReader().start();
	}
	
	public boolean isActive() {
		return isActive;
	}
	
	
	private class SocketReader extends Thread {
		private InputStream is;
		private OutputStream os;
		
		@Override
		public void run() {
			byte[] buffer = new byte[1024];
			int bytes;
			while(true) {
				try {
					bytes = is.read(buffer);
					System.out.println(buffer);
				} catch (IOException ioe) {
					
				}
			}
		}
		
		private void write(byte[] bytes) {
			try {
				os.write(bytes);
			} catch (IOException ioe){
				// Do nothing
			}
		}
	}
}
