package com.wiatec.blive.common.data_source;

import java.lang.annotation.*;



@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DataSource {

    final String DATA_SOURCE_PANEL = "panelDataSource";
    final String DATA_SOURCE_BLIVE = "bliveDataSource";
    String name() default "";
}
