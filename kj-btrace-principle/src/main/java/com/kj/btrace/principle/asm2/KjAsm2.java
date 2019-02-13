//package com.kj.btrace.principle.asm2;
//
//import java.io.IOException;
//
//import org.objectweb.asm.ClassReader;
//import org.objectweb.asm.ClassVisitor;
//import org.objectweb.asm.ClassWriter;
//import org.objectweb.asm.Opcodes;
//
//public class KjAsm2 {
//
//	public static void main(String[] args) throws IOException {
//		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
//		ClassVisitor ca = new ClassVisitor(Opcodes.ASM6, cw) {
//
//		};
//		ClassReader cr = new ClassReader("");
//		cr.accept(ca, 0);
//		cw.toByteArray();
//	}
//
//}
