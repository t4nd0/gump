package com.gumpGameEngine.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

	private Dimension size;
	private String title;
	private boolean running;
	
	private Frame frame;
	private Canvas canvas;
	private Loop loop;
	private ObjectHandler handler;
	
	public Main() {
		size = new Dimension(640, 480);
		title = "iso_base// erste schritte";
		running = true;
		
		handler = new ObjectHandler();
		
		frame = new Frame(title);
		canvas = new Canvas();
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				running = false;
				e.getWindow().dispose();
				System.exit(0);
			}
		});
		frame.add(canvas);

		frame.setSize(size);
		frame.setVisible(true);

		loop = new Loop();
		loop.start();
	}
	
	class Loop extends Thread {
		@Override
		public void run() {
			final int ticks_per_second = 30;
			final float tick_duration = 1000000000/ticks_per_second;
			final int max_frameskip = 5;

			long current_moment;
			int tick_count;
			float interpolation;

			long theoretical_now = System.nanoTime();
			while(running) {
				tick_count = 0;
				current_moment = System.nanoTime();
				while(current_moment > theoretical_now && tick_count < max_frameskip) {
					handler.update();
					theoretical_now += tick_duration;
					tick_count++;
				}
				interpolation = (System.nanoTime()+tick_duration-theoretical_now)/tick_duration;
				handler.render(interpolation);
			}
		}
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
}
