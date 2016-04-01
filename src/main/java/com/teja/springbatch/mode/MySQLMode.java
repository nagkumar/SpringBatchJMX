package com.teja.springbatch.mode;

import com.teja.springbatch.CustomItemReader;
import com.teja.springbatch.CustomPojo;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class MySQLMode implements ModeIntf<CustomPojo>
{
    public ItemReader<CustomPojo> reader()
    {
	CustomItemReader reader = new CustomItemReader();
	List<CustomPojo> pojos = new ArrayList<CustomPojo>();
	pojos.add(new CustomPojo("1", "desc1"));
	pojos.add(new CustomPojo("2", "desc2"));
	pojos.add(new CustomPojo("3", "desc3"));
	reader.setPojos(pojos);
	reader.setIterator(reader.getPojos().iterator());
	return reader;
    }

    public ItemWriter<CustomPojo> writer(final DataSource aDataSource)
    {
	JdbcBatchItemWriter<CustomPojo> writer = new JdbcBatchItemWriter<CustomPojo>();
	writer.setSql("INSERT INTO pojo (id, description) VALUES (:id, :description)");
	writer.setDataSource(aDataSource);
	writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<CustomPojo>());
	return writer;
    }
}