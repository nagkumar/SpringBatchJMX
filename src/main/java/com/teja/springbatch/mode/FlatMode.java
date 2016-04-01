package com.teja.springbatch.mode;

import com.teja.springbatch.CustomDelimitedAggregator;
import com.teja.springbatch.CustomFieldExtractor;
import com.teja.springbatch.CustomFlatFileItemWriter;
import com.teja.springbatch.CustomPojo;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

public class FlatMode implements ModeIntf<CustomPojo>
{
    public ItemReader<CustomPojo> reader()
    {
	FlatFileItemReader<CustomPojo> reader = new FlatFileItemReader<CustomPojo>();
	reader.setResource(new ClassPathResource("input.csv"));
	reader.setLineMapper(
		new DefaultLineMapper<CustomPojo>()
		{
		    {
			setLineTokenizer(
				new DelimitedLineTokenizer()
				{
				    {
					setNames(new String[]{"id", "description"});
				    }
				});
			setFieldSetMapper(
				new BeanWrapperFieldSetMapper<CustomPojo>()
				{
				    {
					setTargetType(CustomPojo.class);
				    }
				});
		    }
		}
	);
	return reader;
    }

    public ItemWriter<CustomPojo> writer(final DataSource aDataSource)
    {
	FlatFileItemWriter<CustomPojo> writer = new CustomFlatFileItemWriter<CustomPojo>();
	writer.setResource(new ClassPathResource("output.csv"));
	BeanWrapperFieldExtractor<CustomPojo> fieldExtractor = new CustomFieldExtractor<CustomPojo>();
	fieldExtractor.setNames(new String[]{"id", "description"});
	DelimitedLineAggregator<CustomPojo> delLineAgg = new CustomDelimitedAggregator<CustomPojo>();
	delLineAgg.setDelimiter(",");
	delLineAgg.setFieldExtractor(fieldExtractor);
	writer.setLineAggregator(delLineAgg);
	return writer;
    }
}