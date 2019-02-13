package com.kj.btrace.sdk.test.agent;

import java.util.Random;

public class KjBtraceDemo {
	
	public static int add(int a, int b) {
		return a + b;
	}

	public static void main(String[] args) throws Exception {
		Random random = new Random();
		while (true) {
			int a = random.nextInt(100);
			int b = random.nextInt(100);
			int c = add(a, b);
			System.out.println("a:" + a);
			System.out.println("b:" + b);
			System.out.println("a+b:" + c);
			System.out.println(KjBtraceDemo.class.getName());
			Thread.sleep(2000);
		}
	}
}
