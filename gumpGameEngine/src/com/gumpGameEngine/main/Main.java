package com.gumpGameEngine.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

	private Dimension size;
	private String title;
	private boolean running;
	
	private Frame frame;
	private Canvas canvas;
	private Loop loop;
	
	public Main() {
		size = new Dimension(640, 480);
		title = "gumpGameEngine// first steps";
		running = true;
		
		frame = new Frame(title);
		
		canvas = new Canvas() {
			@Override
			public void paint(Graphics g) {
				g.dispose();
			}
		};

		canvas.setBackground(Color.BLUE);
		
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
			final int updates_per_second = 30;
			final float update_time = 1000000000/updates_per_second;
			final int max_frameskip = 5;

			long now;
			int update_count;
			float interpolation;

			long incremental_now = System.nanoTime();

				//debug
				int frameCount = 0;
				int uCount = 0;
				int lastSecondTime = (int) (incremental_now / 1000000000);
				//--
			
			while(running) {
				update_count = 0;
				now = System.nanoTime();
				while(now > incremental_now && update_count < max_frameskip) {
					update();
					incremental_now += update_time;
					update_count++;
					
						//debug
						uCount++;
						//--
					
				}
				
					//debug
		            int thisSecond = (int) (now / 1000000000);
		            if (thisSecond > lastSecondTime)
		            {
		               System.out.println("fps: "+frameCount);
		               System.out.println("ups: "+uCount);
		               frameCount = 0;
		               uCount = 0;
		               lastSecondTime = thisSecond;
		            }
	            	frameCount++;
		            //--
				
				interpolation = (System.nanoTime()+update_time-incremental_now)/update_time;
				render(interpolation);
			}
		}
	}

	public void update() {
		
	}
	
	public void render(float interpolation) {
		
	}
	
	public static void main(String[] args) {
		new Main();
	}
	
}
