package com.project.placementagency;

class Mythread extends Thread {
	
	public void run() {
		
		while(true)
		{
			System.out.println("hi");
//			try {
//				Thread.sleep(10);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}	
	}
}

class Mythread1 extends Mythread implements Runnable {
	
	public void run() {
		
		while(true)
			System.out.println("hola");
	}
}

public class Test {

	public static void main(String[] args) throws Exception {
		
		Mythread t = new Mythread();
//		System.out.println(t.getName());
//		t.setDaemon(true);
		t.start();
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		Thread curr = Thread.currentThread();
//		curr.join();
		
		
		Mythread1 m = new Mythread1();
		Thread t1 = new Thread(m,"My Thread");
//		System.out.println(t1.getName());
		t1.start();
				
		while(true)
		{
//			Thread.sleep(100);
			System.out.println("hello");
//			Thread.yield();
		}
}
}
