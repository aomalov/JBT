package com.jbt.e.waitAndNotify;

public class Consumer extends Thread {

	private Stack stack;
	private long rate;

	public Consumer(Stack stack, long rate, String name) {
		super(name);
		this.stack = stack;
		this.rate = rate;
	}

	@Override
	public void run() {

		for (int i = 0; i < 20; i++) {
			int poppedElement = stack.pop();
			System.out.println("\t\t" + getName() + " popped " + poppedElement);
			try {
				Thread.sleep(rate);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("\t\t" + getName() + "finished");
	}

}
