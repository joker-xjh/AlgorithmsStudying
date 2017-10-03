package Algorithms;

import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
	
	private int capacity;
	
	private Queue<T> queue;
	
	public BlockingQueue(int capacity) {
		this.capacity = capacity;
		queue = new LinkedList<>();
	}
	
	public void put(T element) {
		synchronized(this) {
			try {
				while(queue.size() == capacity) {
					wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(queue.size() == 0) {
				notifyAll();
			}
			queue.offer(element);
		}
	}
	
	public T take() {
		synchronized (this) {
			try {
				while(queue.size() == 0 ) {
					wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(queue.size() == capacity) {
				notifyAll();
			}
			return queue.poll();
		}
	}
	
	
	
	
	

}
