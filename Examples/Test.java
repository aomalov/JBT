package com.jbt.e.waitAndNotify;

public class Test {
	
	public static void main(String[] args) {
		
		Stack stack = new Stack(10);
		
		Producer p1 = new Producer(stack, 50, "P1");
		p1.start();
		
		Consumer c1 = new Consumer(stack, 200, "C1");
		c1.start();
	}

}
