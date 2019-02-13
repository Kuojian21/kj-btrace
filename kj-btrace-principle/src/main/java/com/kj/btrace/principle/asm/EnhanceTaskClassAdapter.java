//package com.kj.btrace.principle.asm;
//
//import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;
//import com.sun.xml.internal.ws.org.objectweb.asm.ClassVisitor;
//import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;
//import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;
//
//public class EnhanceTaskClassAdapter extends ClassAdapter implements Opcodes {
//
//	private String uniqueName;
//	private String enhancedSuperName;
//
//	public EnhanceTaskClassAdapter(ClassVisitor cv, String uniqueName) {
//		super(cv);
//		this.uniqueName = uniqueName;
//	}
//
//	@Override
//	public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
//		String enhancedName = name + "$EnhancedByASM$" + this.uniqueName;
//		this.enhancedSuperName = name;
//		super.visit(version, Opcodes.ACC_PUBLIC, enhancedName, signature, this.enhancedSuperName, interfaces);
//	}
//
//	@Override
//	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
//		if (!name.equals("run") && !name.equals("<init>")) {
//			return null;
//		}
//		if (access == Opcodes.ACC_ABSTRACT + Opcodes.ACC_PROTECTED) {
//			access = Opcodes.ACC_PUBLIC;
//		}
//		MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);
//		MethodVisitor wrappedMv = mv;
//		if (mv != null) {
//			if (name.equals("doJob")) {
//				wrappedMv = new EnhanceTaskMethodAddapter(mv);
//			} else if (name.equals("<init>")) {
//				wrappedMv = new EnhanceInitMethodAddapter(mv, this.enhancedSuperName);
//			}
//		}
//		return wrappedMv;
//	}
//}
