package com.github.s73331.codelock;

import java.awt.Button;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class Door extends Panel {
	private class ButtonResetter implements Runnable {
		private Button button;

		public ButtonResetter(Button button) {
			this.button = button;
		}

		public void run() {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				return;
			}
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					button.setBackground(buttonDefault);
				}
			});
		}
	}
	
	private static Color buttonDefault;

	private static void printUsage() {
		System.err.println("usage: java Door $code");
		System.err.println("e.g. : java Door 1234");
	}

	private ActionListener al = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			input += e.getActionCommand();
			if (code.length() == input.length()) {
				Button changedButton;
				if (code.equals(input)) {
					greenButton.setBackground(Color.GREEN);
					changedButton = greenButton;
				} else {
					redButton.setBackground(Color.RED);
					changedButton = redButton;
				}
				new Thread(new ButtonResetter(changedButton)).start();
			}
		}
	};
	private String code;
	private String input = "";
	private Button greenButton;
	private Button redButton;

	public Door(String code) {
		if (code == null) {
			throw new IllegalArgumentException("code must not be null");
		}
		if (!code.matches("\\d+")) {
			throw new IllegalArgumentException("code must only contain digits");
		}
		this.code = code;
		for (int i = 0; i < 12; i++) {
			Button b = new Button();
			if (i < 10) {
				b.setLabel(Integer.toString(i));
				b.addActionListener(al);
			} else if (i == 10) {
				greenButton = b;
			} else if (i == 11) {
				redButton = b;
				buttonDefault = b.getBackground();
			}
			add(b);
		}
	}

	public static void main(String[] args) {
		if ("".equals(args) || args.length != 1) {
			System.err.println("error: wrong args");
			printUsage();
			return;
		}
		try {
			Panel p = new Door(args[0]);
			Frame f = new Frame();
			f.add(p);
			f.addWindowListener(new WindowAdapter() {
				public void windowClosing(final WindowEvent e) {
					System.exit(0);
				}
			});
			f.setSize(1000, 1000);
			f.setVisible(true);
		} catch (IllegalArgumentException iae) {
			System.err.println("error: IllegalArgumentException: " + iae);
			printUsage();
		}
	}
}
