package com.kj.btrace.principle;

import com.sun.tools.attach.VirtualMachine;

public class KjVirtualMachine {

	public static void attachAndLoadAgent(String pid, String path) throws Exception {
		VirtualMachine vm = VirtualMachine.attach(pid);
		vm.loadAgent(path);
	}

}
