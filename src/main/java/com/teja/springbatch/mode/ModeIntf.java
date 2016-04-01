package com.teja.springbatch.mode;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;

import javax.sql.DataSource;

public interface ModeIntf<CustomPojo>
{
    public ItemReader<CustomPojo> reader();

    public ItemWriter<CustomPojo> writer(final DataSource dataSource);
}
