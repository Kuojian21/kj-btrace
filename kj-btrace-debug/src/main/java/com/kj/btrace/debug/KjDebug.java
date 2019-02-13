package com.kj.btrace.debug;

import com.sun.btrace.AnyType;
import com.sun.btrace.BTraceUtils;
import com.sun.btrace.BTraceUtils.Sys;
import com.sun.btrace.annotations.*;
import static com.sun.btrace.BTraceUtils.*;
import java.lang.reflect.Method;

@BTrace(trusted = true)
public class KjDebug {

	@OnMethod(clazz = "com.kj.btrace.sdk.test.agent.KjBtraceDemo", location = @Location(value = Kind.LINE, line = -1))
	public static void line(

					@ProbeClassName String pcn, @ProbeMethodName String pmn) {
		println("line:");
	}

	@OnMethod(
					clazz = "com.kj.btrace.sdk.test.agent.KjBtraceDemo",
					method = "add",
					location = @Location(value = Kind.CALL))
	public static void call(

					@ProbeClassName String pcn, @ProbeMethodName String pmn, @Return String call) {
		println("call:" + call);
	}

	@OnMethod(
					clazz = "com.kj.btrace.sdk.test.agent.KjBtraceDemo",
					method = "underscoreName",
					location = @Location(value = Kind.ENTRY))
	public static void in(
					@ProbeClassName String pcn, @ProbeMethodName String pmn, AnyType[] anyTypes) {
		printArray(anyTypes);
	}

	@OnMethod(
					clazz = "+org.springframework.jdbc.core.BeanPropertyRowMapper",
					method = "underscoreName",
					location = @Location(value = Kind.RETURN))
	public static void out(

					@ProbeClassName String pcn, @ProbeMethodName String pmn, @Return String result) {
		println("pcn:" + pcn + ":pmn:" + pmn + "result:" + result);
	}

	@OnTimer(100000)
	public static void timer() {
		com.sun.btrace.BTraceUtils.Threads.jstackAll();
	}

}
