package com.jbt.e.waitAndNotify;

public class Producer extends Thread {

	private Stack stack;
	private long rate;

	public Producer(Stack stack, long rate, String name) {
		super(name);
		this.stack = stack;
		this.rate = rate;
	}

	@Override
	public void run() {

		for (int i = 0; i < 20; i++) {
			int r = (int) (Math.random() * 26);
			stack.push(r);
			System.out.println(getName() + " pushed " + r);
			try {
				Thread.sleep(rate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println(getName() + " finished");
	}

}
