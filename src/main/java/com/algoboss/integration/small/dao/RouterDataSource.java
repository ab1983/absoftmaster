package com.algoboss.integration.small.dao;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class RouterDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		setTargetDataSources(DataSourceContextHolder.getTargetDataSources());
		afterPropertiesSet();
		return DataSourceContextHolder.getTargetDataSource();
	}
}
