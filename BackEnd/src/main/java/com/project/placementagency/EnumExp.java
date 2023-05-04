package com.project.placementagency;

enum Dept{
	
	CS("Smith","Block-A"),CIVIL("Alex","Block-B"),MECH("Barton","Block-C"),ETC("James","Block-D");
	
	String head;
	String loc;
	
	private Dept(String head,String loc) {
		this.head=head;
		this.loc=loc;
	}
}

public class EnumExp {

	public static void main(String[] args) {
		
		Dept[] d = Dept.values();
		for(Dept dp:d)
		System.out.println(dp+" "+dp.head+" "+dp.loc);
	}
}
