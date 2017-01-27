package com.github.s73331.codelock;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer implements Runnable {
	private static final int BUF_SIZE = 16;
	private boolean run = true;
	private String code;

	public UDPServer(String code) {
		this.code = code;
	}

	public void run() {
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket(9002);
			DatagramPacket packet = new DatagramPacket(new byte[BUF_SIZE], BUF_SIZE);
			while (run) {
				socket.receive(packet);
				String input = new String(packet.getData(), 0, packet.getLength());
				String response;
				if (code.equals(input)) {
					response = "OK";
				} else {
					response = "fail";
				}
				packet.setData(response.getBytes());
				socket.send(packet);
				packet.setData(new byte[BUF_SIZE]);
			}
		} catch (IOException e) {
			System.err.println(e);
		}
		if (socket != null) {
			socket.close();
		}
	}

	public void stop() {
		run = false;
	}
}
