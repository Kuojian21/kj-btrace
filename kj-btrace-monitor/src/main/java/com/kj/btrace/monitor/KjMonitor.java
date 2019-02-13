package com.kj.btrace.monitor;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.URL;

import com.sun.btrace.AnyType;
import com.sun.btrace.BTraceUtils;
import com.sun.btrace.BTraceUtils.Sys;
import com.sun.btrace.annotations.BTrace;
import com.sun.btrace.annotations.Kind;
import com.sun.btrace.annotations.Location;
import com.sun.btrace.annotations.OnMethod;
import com.sun.btrace.annotations.ProbeClassName;
import com.sun.btrace.annotations.ProbeMethodName;

@BTrace(trusted = true)
public class KjMonitor {

	private volatile static ClassLoader classLoader;

	@OnMethod(
					clazz = "/com.kj.btrace.sdk.*/",
					method = "/.*/",
					location = @Location(value = Kind.ENTRY))
	public static void entry(
					@ProbeClassName String pcn, @ProbeMethodName String pmn, AnyType[] anyTypes) {
		main("com.kj.btrace.monitor.main.Main", new Object[] { pcn, pmn, anyTypes });
		BTraceUtils.println(pcn + ":" + pmn);
	}

	public static void main(String boot, Object[] objs) {
		if (classLoader == null) {
			synchronized (KjMonitor.class) {
				if (classLoader == null) {
					try {
						String btracePath = Sys.Env.getenv("BTRACE_PATH");
						if (btracePath == null || btracePath.length() <= 0) {
							throw new RuntimeException("NO BTRACE_PATH");
						} else {
							Class<?> clazz = Class.forName("java.net.URLClassLoader");
							Constructor<?> constructor = clazz
											.getConstructor(new Class[] { URL[].class, ClassLoader.class });
							classLoader = (ClassLoader) constructor.newInstance(
											new URL[] { new File(btracePath).toURI().toURL() },
											Thread.currentThread().getContextClassLoader());
						}
					} catch (Exception e) {
						BTraceUtils.println(e);
						Sys.exit(0);
					}
				}
			}
		}

		try {
			Class<?> main = classLoader.loadClass(boot);
			Method method = main.getDeclaredMethod("main", new Class[] { Object[].class });
			method.invoke(main, new Object[] { objs });
		} catch (Exception e) {
			BTraceUtils.println(e);
			Sys.exit(0);
		}
	}

}
