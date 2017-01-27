package com.github.s73331.codelock;

import java.awt.Button;
import java.awt.Color;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import com.github.s73331.codelock.Door;

@SuppressWarnings("serial")
public class UDPDoor extends Door {

	private class UDPDoorActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			input += e.getActionCommand();
			if (length == input.length()) {
				Button changedButton;
				if (checkCode()) {
					greenButton.setBackground(Color.GREEN);
					changedButton = greenButton;
				} else {
					redButton.setBackground(Color.RED);
					changedButton = redButton;
				}
				input = "";
				new Thread(new ButtonBackgroundSetter(changedButton, defaultColor, 2000)).start();
			}
		}
	}
	
	private int length;

	public UDPDoor(int length) {
		this.length = length;
		makeLayout(new UDPDoorActionListener());
	}

	protected boolean shouldCheckCode() {
		return length == input.length();
	}

	protected boolean checkCode() {
		boolean ret = false;
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(9001);

			byte[] bytes = input.getBytes();
			DatagramPacket packet = new DatagramPacket(bytes, input.length(), InetAddress.getLoopbackAddress(), 9002);
			socket.send(packet);
			
			packet.setData(new byte[16]);

			socket.receive(packet);
			String answer = new String(packet.getData(), 0, packet.getLength());
			if (answer.equals("OK")) {
				ret = true;
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		if (socket != null) {
			socket.close();
		}
		return ret;
	}

	public static Door createDoorWindow(String code) {
		UDPServer server = new UDPServer(code);
		new Thread(server).start();
		Door door;
		try {
			door = new UDPDoor(code.length());
		} catch (IllegalArgumentException iae) {
			System.err.println("error: IllegalArgumentException: " + iae);
			return null;
		}
		Frame f = new Frame();
		f.add(door);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(final WindowEvent e) {
				server.stop();
				System.exit(0);
			}
		});
		f.setSize(1000, 1000);
		f.setVisible(true);
		return door;
	}

	public static void main(String[] args) {
		if (args == null || args.length != 1) {
			System.err.println("error: wrong args");
			printUsage();
			return;
		}
		if (createDoorWindow(args[0]) == null) {
			printUsage();
		}
	}
}
