package com.wiatec.blive.common.data_source;

/**
 * @author patrick
 */
public class DataSourceHolder {

    private static final ThreadLocal<String> dataSources = new ThreadLocal<String>();

    public static void setDataSources(String dataSource) {
        dataSources.set(dataSource);
    }

    public static String getDataSources() {
        return dataSources.get();
    }
}
