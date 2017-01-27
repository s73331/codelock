package com.github.s73331.codelock;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class Door extends Panel {
	protected class ButtonBackgroundSetter implements Runnable {
		private Button button;
		private Color color;

		public ButtonBackgroundSetter(Button button, Color color, int span) {
			if (button == null) {
				throw new IllegalArgumentException("button must not be null");
			}
			if (color == null) {
				throw new IllegalArgumentException("color must not be null");
			}
			if (span < 1) {
				throw new IllegalArgumentException("span must be greater than 1");
			}
			this.button = button;
			this.color = color;
		}

		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return;
			}
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					button.setBackground(color);
				}
			});
		}
	}

	private class DoorActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			input += e.getActionCommand();
			if (code.length() == input.length()) {
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

	private static final String[] signs = { "1", "2", "3", "4", "5", "6", "7", "8", "9", "", "0", "" };

	protected static final Color defaultColor = Color.WHITE;

	protected static void printUsage() {
		System.err.println("usage: java Door $code");
		System.err.println("e.g. : java Door 1234");
	}

	protected String code;
	protected String input = "";
	protected Button greenButton;
	protected Button redButton;

	protected Door() {		
	}

	public Door(String code) {
		makeLayout(new DoorActionListener());
		if (code == null) {
			throw new IllegalArgumentException("code must not be null");
		}
		if (!code.matches("\\d+")) {
			throw new IllegalArgumentException("code must only contain digits");
		}
		this.code = code;
	}
	
	protected void makeLayout(ActionListener listener){
		for (int i = 0; i < 12; i++) {
			setLayout(new GridLayout(4, 3));
			Button b = new Button(signs[i]);
			if (i < 9 || i == 10) {
				b.addActionListener(listener);
			} else if (i == 9) {
				greenButton = b;
			} else if (i == 11) {
				redButton = b;
			}
			b.setBackground(defaultColor);
			add(b);
		}
	}

	protected boolean shouldCheckCode() {
		return code.length() == input.length();
	}

	protected boolean checkCode() {
		return code.equals(input);
	}

	public static Door createDoorWindow(String code) {
		Door door;
		try {
			door = new Door(code);
		} catch (IllegalArgumentException iae) {
			System.err.println("error: IllegalArgumentException: " + iae);
			return null;
		}
		Frame f = new Frame();
		f.add(door);
		f.addWindowListener(new WindowAdapter() {
			public void windowClosing(final WindowEvent e) {
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
