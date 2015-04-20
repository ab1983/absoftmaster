package com.algoboss.integration.small.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import javax.sql.DataSource;

import org.apache.commons.collections.MapUtils;

import com.algoboss.integration.small.dao.ThreadLocalMapUtil;

public class DataSourceContextHolder {

	private static final String THREAD_VARIABLE_NAME = DataSourceContextHolder.class.getName();
	private static Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
	private static Map<String,Object> targetDataSourcesStr = new TreeMap<String,Object>();
	private static Map<String,Object> targetDataSourceMap = new TreeMap<String,Object>();
	
	
	public static boolean isSetted(String targetDataSourceKey){
		return targetDataSourcesStr.containsKey(targetDataSourceKey);
	}
	
	public static void setTargetDataSourceKey(String targetDataSourceKey){
		setTargetDataSource((DataSource)targetDataSourcesStr.get(targetDataSourceKey));
	}	
	
	public static void setTargetDataSourceKey(String targetDataSourceKey,DataSource targetDataSource){
		targetDataSourcesStr.put(targetDataSourceKey,targetDataSource);
		targetDataSources.put(targetDataSource, targetDataSource);
		setTargetDataSource(targetDataSource);
	}	
	
	private static void setTargetDataSource(DataSource targetDataSource) {
		targetDataSourceMap.put(getId(), targetDataSource);
		ThreadLocalMapUtil.setThreadVariable(THREAD_VARIABLE_NAME, targetDataSource);
	}

	public static Object getTargetDataSource() {
		//return  ThreadLocalMapUtil.getThreadVariable(THREAD_VARIABLE_NAME);
		return targetDataSourceMap.get(getId());
	}
	
	public static void clearTargetDataSource() {
		targetDataSourceMap.remove(getId());	
	}	
	
	public static void clearTargetDataSourceMap() {
		Object dsvalue = targetDataSourceMap.remove(getId());
		String dskey = "";
		for (Entry<String,Object> iterable_element : targetDataSourcesStr.entrySet()) {
			if(iterable_element.getValue().equals(dsvalue)){
				dskey = iterable_element.getKey();
			}
		}		
		targetDataSourcesStr.remove(dskey);
		if(dsvalue != null){
			targetDataSources.remove(dsvalue);
		}
	}		
	
	public static Map<Object, Object> getTargetDataSources() {
		return  targetDataSources;
	}
	
	public static String getId(){
		return String.valueOf(Thread.currentThread().getId());
	}
}
