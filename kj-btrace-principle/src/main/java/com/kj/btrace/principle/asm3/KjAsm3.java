package com.kj.btrace.principle.asm3;

import java.io.IOException;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

public class KjAsm3 {

	public static void main(String[] args) throws IOException {
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		ClassAdapter ca = new ClassAdapter(cw);
		ClassReader cr = new ClassReader("");
		cr.accept(ca, 0);
		cw.toByteArray();
	}

}
