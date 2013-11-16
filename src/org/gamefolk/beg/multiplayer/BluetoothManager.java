package org.gamefolk.beg.multiplayer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import android.bluetooth.*;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.Activity;
import android.app.AlertDialog;

public class BluetoothManager {
	private static final String SERVICE_NAME = "BEG";
	private static final UUID SERVICE_UUID = UUID.fromString("25c90c25-fe80-4e39-b664-f91ff2f41b98");
	
	private final Activity bluetoothActivity;
	private BluetoothServerSocket serverSocket;
	private BluetoothSocket socket;
	private BluetoothAdapter adapter;
	
	private BroadcastReceiver receiver;
	
	private boolean isServer = true;
	private boolean isConnected = false;
	private boolean isInitialized = false;
	
    public BluetoothManager (Activity bluetoothActivity) {
    	
    	this.bluetoothActivity = bluetoothActivity;
    	
    	receiver = new BroadcastReceiver () {
    		private List<BluetoothDevice> devices = new ArrayList<BluetoothDevice>();
    		
        	public void onReceive(Context context, Intent intent) {
        		String action = intent.getAction();
        		if (BluetoothDevice.ACTION_FOUND.equals(action)) {
        			BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        			devices.add(device);	
        			System.out.println("Found device:" + device.getName());
        		} else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
        			List<String> deviceNames = new ArrayList<String>();
        			for (BluetoothDevice device : devices) {
        				deviceNames.add(device.getName());
        			}
        			
        			System.out.println("Finished finding devices");
        			
        			String[] deviceNamesArray = new String[deviceNames.size()];
        			for (int i = 0; i < deviceNames.size(); i++){
        				deviceNamesArray[i] = deviceNames.get(i);
        			}
        			
        			
        			AlertDialog.Builder builder = new AlertDialog.Builder(BluetoothManager.this.bluetoothActivity);
        			builder.setTitle("Pick a device to pair with");
        			builder.setItems(deviceNamesArray, new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							new ClientThread(devices.get(which)).start();	
						}
					}).show();
        		}
        	}
        };
    	
        initializeBluetooth();

        new ServerThread().start();
    }
    
    public void destroyReceiver() {
    	bluetoothActivity.unregisterReceiver(receiver);
    }
    
    public boolean isConnected() {
    	return isConnected;
    }

    private void initializeBluetooth() {
        adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            // Device does not support bluetooth
        }

        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 0);
        bluetoothActivity.startActivity(discoverableIntent);
        
        System.out.println("Looking for devices");
        adapter.startDiscovery();
        
        
        IntentFilter foundFilter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        bluetoothActivity.registerReceiver(receiver, foundFilter);
        
        IntentFilter finishedDiscoveryFilter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        bluetoothActivity.registerReceiver(receiver, finishedDiscoveryFilter);
        
        
        
        try {
        	serverSocket = adapter.listenUsingInsecureRfcommWithServiceRecord(
        			SERVICE_NAME, SERVICE_UUID);
        } catch (IOException ioe) {
        	throw new RuntimeException("SOMETHING WEIRD");
        }
    }
    
    private class ServerThread extends Thread {
    	private BluetoothServerSocket serverSocket;
    	
    	public ServerThread() {
    		try {
    			serverSocket = adapter.listenUsingRfcommWithServiceRecord(SERVICE_NAME, SERVICE_UUID);
    		} catch (IOException ioe) {
    			
    		}
    	}
    	
    	public void run() {
    		socket = null;
    		while(true) {
    			try {
    				socket = serverSocket.accept();
    				if (socket != null) {
        				System.out.println("Server Connected yo");
        				serverSocket.close();
        			}
    			} catch (IOException ioe){
    				break;
    			}
    		}
    	}
    }
    
    private class ClientThread extends Thread {
    	private BluetoothSocket socket;
    	private BluetoothDevice device;
    	
    	public ClientThread(BluetoothDevice device){
    		this.device = device;
    		try {
    			socket = device.createRfcommSocketToServiceRecord(SERVICE_UUID);
    		} catch (IOException ioe){
    			
    		}
    	}
    	
    	
    	@Override
    	public void run() {
    		adapter.cancelDiscovery();
    		
    		try {
    			socket.connect();
    		} catch (IOException ioe){
    			try {
    				socket.close();
    			} catch (IOException closingIOE){
    				
    			}
    			return;
    		}
    		handleConnection();
    	}
    }
    
    private void handleConnection() {
    	
    }
}


