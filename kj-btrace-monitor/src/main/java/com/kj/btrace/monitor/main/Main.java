package com.kj.btrace.monitor.main;

import org.slf4j.Logger;

import com.google.gson.Gson;
import com.kj.btrace.monitor.log.LogbackFactory;

public class Main {

	public static final Logger logger = LogbackFactory.getLogger(Main.class);

	private static final Gson gson = new Gson();

	public static void main(Object[] objs) {
		logger.info("{}", gson.toJson(objs));
	}

}
