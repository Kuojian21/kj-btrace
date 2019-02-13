package com.kj.btrace.monitor.log;

import java.net.URL;

import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.spi.LoggerFactoryBinder;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

public class LogbackFactory {

	static {
		try {
			JoranConfigurator joranConfigurator = new JoranConfigurator();
			LoggerFactoryBinder binder = StaticLoggerBinder.getSingleton();
			ILoggerFactory logFactory = binder.getLoggerFactory();
			if (logFactory instanceof LoggerContext) {
				LoggerContext context = (LoggerContext) logFactory;
				joranConfigurator.setContext(context);
				context.reset();
				URL resource = LogbackFactory.class.getClassLoader().getResource("logback/monitor.xml");
				if (resource != null) {
					joranConfigurator.doConfigure(resource);
				}
			}
		} catch (JoranException e) {
			e.printStackTrace();
		}
	}

	public static Logger getLogger(Class<?> clazz) {
		return StaticLoggerBinder.getSingleton().getLoggerFactory().getLogger(clazz.getName());
	}

	public static Logger getLogger(String name) {
		return StaticLoggerBinder.getSingleton().getLoggerFactory().getLogger(name);
	}

}
