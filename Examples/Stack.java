package com.jbt.e.waitAndNotify;

import java.util.ArrayList;
import java.util.List;

public class Stack {

	private int max;
	private List<Integer> list = new ArrayList<>();

	public Stack(int max) {
		super();
		this.max = max;
	}

	public synchronized void push(Integer element) { // for producers

		while (list.size() >= max) { // stack is full
			try {
				wait(); // until notified by consumer thread
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		list.add(element);
		notifyAll(); // notify consumer threads
	}

	public synchronized Integer pop() { // for consumers

		while (list.size() == 0) { // stack is empty
			try {
				wait(); // until notified by producer thread
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		notifyAll(); // notify producer threads
		Integer x = list.get(list.size() - 1);
		list.remove(list.size()-1);
		return x;
	}

}
