package com.kj.btrace.test;

import com.kj.btrace.principle.KjVirtualMachine;

public class KjVirtualMachineTest {

	public static void main(String[] args) throws Exception {
		KjVirtualMachine.attachAndLoadAgent(args[0], args[1]);
	}

}
