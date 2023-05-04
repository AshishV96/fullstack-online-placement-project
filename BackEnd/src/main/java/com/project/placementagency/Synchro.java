package com.project.placementagency;

public class Synchro {

	public static void main(String[] args) {
		
		Data d = new Data();
		Mythr1 t1 = new Mythr1(d);
		Mythr2 t2 = new Mythr2(d);
		t1.start();
		t2.start();
	}
}

class Mythr1 extends Thread{
	
	Data d;
	
	public Mythr1(Data d)
	{
		this.d=d;
	}
	
	public void run() {
		try {
			d.display("Hello");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Mythr2 extends Thread {
	
	Data d;
	
	public Mythr2(Data d)
	{
		this.d=d;
	}
	
	public void run() {
		try {
			d.display("GoodBye");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Data {
	
	synchronized void display(String str) throws InterruptedException
	{
//		synchronized (this) {
			
			for(int i=0;i<str.length();i++)
			{
				Thread.sleep(100);
				System.out.print(str.charAt(i));
			}
//		}
	}
}
