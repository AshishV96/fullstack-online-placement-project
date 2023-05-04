package com.project.placementagency;

class Mythreads extends Thread {
	
	public void run() {
		
//		while(true)
//		{
			System.out.println("hi");
//		}	
	}
}

public class States {

	public static void main(String[] args) throws InterruptedException {
		
		Mythreads m = new Mythreads();
		System.out.println(m.getState());
		m.start();
		System.out.println(m.getState());
		m.wait();
		System.out.println(m.getState());
		Thread.sleep(100);
		System.out.println(m.getId());
		System.out.println(m.getState());
	}
}
