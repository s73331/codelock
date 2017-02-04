package com.github.s73331.awtchart;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

public class BarChartCaller {
	public static void main(String[] args) {
		Frame f = new Frame();
		int[] parsed = new int[args.length];
		for (int counter = 0; counter < args.length; counter++) {
			parsed[counter] = Integer.parseInt(args[counter]);
		}
		System.out.println(Arrays.toString(parsed));
		f.add(new BarChart(parsed));
		f.pack();
		f.setVisible(true);
		f.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
}
