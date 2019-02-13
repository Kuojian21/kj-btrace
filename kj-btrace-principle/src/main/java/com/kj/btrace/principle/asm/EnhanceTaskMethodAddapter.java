//package com.kj.btrace.principle.asm;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.sun.xml.internal.ws.org.objectweb.asm.MethodAdapter;
//import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;
//import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;
//
//public class EnhanceTaskMethodAddapter extends MethodAdapter implements Opcodes {
//
//    public EnhanceTaskMethodAddapter(MethodVisitor mv) {
//        super(mv);
//    }
//
//    @Override
//    public void visitEnd() {
//
//        // 没有1个参数，有返回值
//        // StringBuilder s = new StringBuilder();
//        this.visitCode();
//        this.visitTypeInsn(Opcodes.NEW, "java/lang/StringBuilder");
//        this.visitInsn(Opcodes.DUP);
//        this.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "()V");
//        this.visitVarInsn(Opcodes.ASTORE, 1);
//
//        int maxVariableCount = 0;// 调用方法最大参数个数
//        int totalReturnCount = 0;// 有返回结果的方法个数
//        List<Inner> innerList = new ArrayList<>();
//        int index = 2;
//        for (Inner inner : innerList) {
//            // AccountService account =
//            // (AccountService)applicationContext.getBean(AccountService.class);
//            this.visitFieldInsn(Opcodes.GETSTATIC, "com/netease/cron/Constant", "applicationContext", "Lorg/springframework/context/ApplicationContext;");
//            // visitLdcInsn(Type.getType(inner.getClassType()));//
//            // type需要替换//"Lcom/netease/cron/service/AccountService;"
//            this.visitLdcInsn(inner.getBeanId());
//            this.visitMethodInsn(Opcodes.INVOKEINTERFACE, "org/springframework/context/ApplicationContext", "getBean", "(Ljava/lang/String;)Ljava/lang/Object;");
//            this.visitTypeInsn(Opcodes.CHECKCAST, inner.getClassName());// type需要替换//"com/netease/cron/service/AccountService"
//            this.visitVarInsn(Opcodes.ASTORE, index);// 2
//
//            // s.append("com.netease.cron.service.Account.tests():");
//            this.mv.visitVarInsn(Opcodes.ALOAD, 1);
//            this.mv.visitLdcInsn(inner.getBeanId() + "." + inner.getMethodName() + "(" + this.getParameterValues(inner.getVariableValues()) + "):");
//            this.mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
//            this.mv.visitInsn(Opcodes.POP);
//
//            // boolean b = account.addPoint();
//            this.visitVarInsn(Opcodes.ALOAD, index);// 2
//            // 如果有参数，下面要加参数
//            // 加参数
//            String[] variableTypes = inner.getVariableTypes();
//            String[] varibaleValues = inner.getVariableValues();
//
//            for (int i = 0; i < variableTypes.length; i++) {
//                this.addVariable(variableTypes[i], varibaleValues[i]);
//            }
//            // 计算调用方法最大参数个数
//            if (maxVariableCount < variableTypes.length) {
//                maxVariableCount = variableTypes.length;
//            }
//
//            // "com/netease/cron/service/AccountService", "addPoint", "()Z"
//            // visitMethodInsn(INVOKEINTERFACE, inner.getClassName(),
//            // inner.getMethodName(), inner.getMethodDesc());
//            this.visitMethodInsn(Opcodes.INVOKEVIRTUAL, inner.getClassName(), inner.getMethodName(), inner.getMethodDesc());
//
//            if ("V".equals(inner.getReturnType())) {
//                // s.append("void;");
//                this.mv.visitVarInsn(Opcodes.ALOAD, 1);
//                this.mv.visitLdcInsn("void;");
//                this.mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
//                this.mv.visitInsn(Opcodes.POP);
//            } else {
//                this.visitVarInsn(this.getSTOREType(inner.getReturnType()), ++index);// ISTORE
//                // s.append(b);
//                // 如果没有返回值，应该不需要下面这段
//                this.visitVarInsn(Opcodes.ALOAD, 1);
//                this.visitVarInsn(this.getLoadType(inner.getReturnType()), index);// ILOAD
//                this.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(" + getStringBuilderAppendType(inner.getReturnType()) + ")Ljava/lang/StringBuilder;");// Z这个参数类型需要变化
//                this.visitInsn(Opcodes.POP);
//
//                // s.append(";");
//                this.mv.visitVarInsn(Opcodes.ALOAD, 1);
//                this.mv.visitLdcInsn(";");
//                this.mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(Ljava/lang/String;)Ljava/lang/StringBuilder;");
//                this.mv.visitInsn(Opcodes.POP);
//
//                // 计算有返回结果的方法个数
//                totalReturnCount++;
//            }
//        }
//
//        // return s.toString();
//        this.visitVarInsn(Opcodes.ALOAD, 1);
//        this.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
//        this.visitInsn(Opcodes.ARETURN);
//
//        // max stack和max local需要计算
//        int maxStack = 2 + innerList.size() + maxVariableCount;// 生成的StringBuilder+生成的Service实例个数+方法的最大参数数量+常量
//        int maxLocals = 2 + innerList.size() + totalReturnCount;// this+StringBuilder+生成的Service实例个数+有返回结果的方法个数
//        this.visitMaxs(maxStack, maxLocals);
//    }
//
//    private void addVariable(String type, String value) {
//
//        // --to do Boolean--
//
//        if ("Z".equals(type)) {
//            // boolean
//            if ("TRUE".equalsIgnoreCase(value)) {
//                this.visitInsn(Opcodes.ICONST_1);// boolean--true
//            } else if ("FALSE".equalsIgnoreCase(value)) {
//                this.visitInsn(Opcodes.ICONST_0);// boolean--false
//            } else {
//                throw new RuntimeException("Incorrect boolean value, type=" + type + ", value=" + value);
//            }
//        } else if ("I".equals(type)) {
//            // int
//            this.visitLdcInsn(new Integer(value));
//        } else if ("Ljava/lang/String;".equals(type)) {
//            // String
//            this.visitLdcInsn(value);
//        } else if ("F".equals(type)) {
//            // float
//            this.visitLdcInsn(new Float(value));
//        } else if ("J".equals(type)) {
//            // long
//            this.visitLdcInsn(new Long(value));
//        } else if ("D".equals(type)) {
//            // double
//            this.visitLdcInsn(new Double(value));
//        } else if ("S".equals(type)) {
//            // short
//            this.visitLdcInsn(new Short(value));
//        } else if ("B".equals(type)) {
//            // byte
//            this.visitLdcInsn(new Byte(value));
//        } else if ("C".equals(type)) {
//            // char
//            this.visitLdcInsn(value.charAt(0));
//        } else if ("Ljava/lang/Integer;".equals(type)) {
//            // Integer
//            if ("NULL".equalsIgnoreCase(value)) {
//                this.visitInsn(Opcodes.ACONST_NULL);// 空值
//            } else {
//                this.visitLdcInsn(new Integer(value));
//                this.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
//            }
//        } else if ("Ljava/lang/Float;".equals(type)) {
//            // Float
//            if ("NULL".equalsIgnoreCase(value)) {
//                this.visitInsn(Opcodes.ACONST_NULL);
//            } else {
//                this.visitLdcInsn(new Float(value));
//                this.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
//            }
//        } else if ("Ljava/lang/Double;".equals(type)) {
//            // Float
//            if ("NULL".equalsIgnoreCase(value)) {
//                this.visitInsn(Opcodes.ACONST_NULL);
//            } else {
//                this.visitLdcInsn(new Double(value));
//                this.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
//            }
//        } else if ("Ljava/lang/Short;".equals(type)) {
//            // Float
//            if ("NULL".equalsIgnoreCase(value)) {
//                this.visitInsn(Opcodes.ACONST_NULL);
//            } else {
//                this.visitLdcInsn(new Short(value));
//                this.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
//            }
//        } else if ("Ljava/lang/Long;".equals(type)) {
//            // Float
//            if ("NULL".equalsIgnoreCase(value)) {
//                this.visitInsn(Opcodes.ACONST_NULL);
//            } else {
//                this.visitLdcInsn(new Long(value));
//                this.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
//            }
//        } else if ("Ljava/lang/Boolean;".equals(type)) {
//            // Float
//            if ("NULL".equalsIgnoreCase(value)) {
//                this.visitInsn(Opcodes.ACONST_NULL);
//            } else {
//                this.visitLdcInsn(new Boolean(value));
//                this.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
//            }
//        } else if ("Ljava/lang/Byte;".equals(type)) {
//            // Float
//            if ("NULL".equalsIgnoreCase(value)) {
//                this.visitInsn(Opcodes.ACONST_NULL);
//            } else {
//                this.visitLdcInsn(new Byte(value));
//                this.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
//            }
//        } else if ("Ljava/lang/Character;".equals(type)) {
//            // Float
//            if ("NULL".equalsIgnoreCase(value)) {
//                this.visitInsn(Opcodes.ACONST_NULL);
//            } else {
//                this.visitLdcInsn(value.charAt(0));
//                this.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
//            }
//        } else {
//            throw new RuntimeException("Incorrect parameter type, type=" + type + ", value=" + value);
//        }
//    }
//
//    private int getLoadType(String returnType) {
//
//        if ("Z".equals(returnType) || "I".equals(returnType) || "B".equals(returnType) || "S".equals(returnType) || "C".equals(returnType)) {
//            return Opcodes.ILOAD;// boolean, int, byte, short, char
//        } else if ("Ljava/lang/String;".equals(returnType) || "Ljava/lang/Integer;".equals(returnType) || "Ljava/lang/Long;".equals(returnType) || "Ljava/lang/Boolean;".equals(returnType) || "Ljava/lang/Short;".equals(returnType) || "Ljava/lang/Float;".equals(returnType) || "Ljava/lang/Double;".equals(returnType) || "Ljava/lang/Character;".equals(returnType) || "Ljava/lang/Byte;".equals(returnType)) {
//            return Opcodes.ALOAD;// String, Integer, Long, Boolean, Short, Float, Double, Character, Byte
//        } else if ("F".equals(returnType)) {
//            return Opcodes.FLOAD;// float
//        } else if ("J".equals(returnType)) {
//            return Opcodes.LLOAD;// long
//        } else if ("D".equals(returnType)) {
//            return Opcodes.DLOAD;// double
//        } else {
//            throw new RuntimeException("CronModel is incorrect, returnType=" + returnType);
//        }
//    }
//
//    private int getSTOREType(String returnType) {
//
//        if ("Z".equals(returnType) || "I".equals(returnType) || "B".equals(returnType) || "S".equals(returnType) || "C".equals(returnType)) {
//            return Opcodes.ISTORE;// boolean, int, byte, short, char
//        } else if ("Ljava/lang/String;".equals(returnType) ||
//                "Ljava/lang/Integer;".equals(returnType) ||
//                "Ljava/lang/Long;".equals(returnType) ||
//                "Ljava/lang/Boolean;".equals(returnType) ||
//                "Ljava/lang/Short;".equals(returnType) ||
//                "Ljava/lang/Float;".equals(returnType) ||
//                "Ljava/lang/Double;".equals(returnType) ||
//                "Ljava/lang/Character;".equals(returnType) || "Ljava/lang/Byte;".equals(returnType)) {
//            return Opcodes.ASTORE;// String, Integer, Long, Boolean, Short, Float, Double, Character, Byte
//        } else if ("F".equals(returnType)) {
//            return Opcodes.FSTORE;// float
//        } else if ("J".equals(returnType)) {
//            return Opcodes.LSTORE;// long
//        } else if ("D".equals(returnType)) {
//            return Opcodes.DSTORE;// double
//        } else {
//            throw new RuntimeException("CronModel is incorrect, returnType=" + returnType);
//        }
//    }
//
//    private String getParameterValues(String[] parameterValues) {
//
//        StringBuilder s = new StringBuilder();
//        for (int i = 0, n = parameterValues.length; i < n; i++) {
//            s.append(parameterValues[i]);
//            if (i < n - 1) {
//                s.append(",");
//            }
//        }
//        return s.toString();
//    }
//
//    private String getStringBuilderAppendType(String returnType) {
//
//        if ("Ljava/lang/Integer;".equals(returnType) || "Ljava/lang/Long;".equals(returnType) || "Ljava/lang/Boolean;".equals(returnType) || "Ljava/lang/Short;".equals(returnType) || "Ljava/lang/Float;".equals(returnType) || "Ljava/lang/Double;".equals(returnType) || "Ljava/lang/Character;".equals(returnType) || "Ljava/lang/Byte;".equals(returnType)) {
//            // Integer, Long, Boolean, Short, Float, Double, Character, Byte
//            return "Ljava/lang/Object;";
//        } else if ("S".equals(returnType) || "B".equals(returnType)) {
//            return "I";
//        }
//
//        return returnType;
//    }
//}
