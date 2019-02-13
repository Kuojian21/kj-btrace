//package com.kj.btrace.principle.asm;
//
//import com.sun.xml.internal.ws.org.objectweb.asm.MethodAdapter;
//import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;
//import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;
//
//public class EnhanceInitMethodAddapter extends MethodAdapter implements Opcodes {
//
//    private String superClassName;
//
//    public EnhanceInitMethodAddapter(MethodVisitor mv, String superClassName) {
//        super(mv);
//        this.superClassName = superClassName;
//    }
//
//    @Override
//    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
//        if (this.superClassName != null && opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) {
//            owner = this.superClassName;
//        }
//		super.visitMethodInsn(opcode, owner, name, desc);
//    }
//}
