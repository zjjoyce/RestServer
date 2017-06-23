package com.asiainfo.ocmanager.rest.constant;

import java.util.Arrays;
import java.util.List;

public class Constant {

	// data factory const
	public final static String DATAFACTORY_URL = "dataFactory.url";
	public final static String DATAFACTORY_TOKEN = "dataFactory.token";

	// ocdp service name list
	public static final List<String> list = Arrays.asList("hdfs", "hbase", "hive", "mapreduce", "spark", "kafka");

}
