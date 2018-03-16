package com.wiatec.blive.common.data_source;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author patrick
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceHolder.getDataSources();
    }
}

