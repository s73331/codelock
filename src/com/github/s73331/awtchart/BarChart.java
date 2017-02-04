package com.github.s73331.awtchart;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;

@SuppressWarnings("serial")
public class BarChart extends Canvas {
	private static final int PIXEL_SCALE = 10;
	private static final int SCALE = 10;

	private int max = 0;
	private double[] values;

	public BarChart(int[] input) {
		if (input == null) {
			throw new IllegalArgumentException("input must not be null");
		} else if (input.length < 2) {
			throw new IllegalArgumentException("input's length must be greater than 1");
		}
		for (int value : input) {
			if (value < 0) {
				throw new IllegalArgumentException("no value must be less than 0");
			}
			max = Math.max(max, value);
		}
		values = new double[input.length];
		for (int counter = 0; counter < values.length; counter++) {
			values[counter] = (double) input[counter] / max;
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(120 * PIXEL_SCALE, (int) (values.length * 2.1 * PIXEL_SCALE));
	}

	@Override
	public void paint(Graphics g) {
		for (int counter = 0; counter * SCALE <= max; counter++)
			g.drawString(Integer.toString(counter * SCALE), (int) ((counter + 0.05) * SCALE * PIXEL_SCALE),
					(int) ((values.length + 0.1) * PIXEL_SCALE * 2));
		for (int counter = 0; counter < values.length; counter++) {
			g.drawRect(PIXEL_SCALE, counter * 2 * PIXEL_SCALE, (int) (values[counter] * 1000), PIXEL_SCALE);
		}
	}
}
