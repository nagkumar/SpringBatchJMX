package com.teja.springbatch;

import com.teja.springbatch.mode.FlatMode;
import com.teja.springbatch.mode.ModeIntf;
import com.teja.springbatch.mode.MySQLMode;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableBatchProcessing
public class SpringBatchJMXMainConfig
{
    private static final ModeIntf[] OPTIONS =
	    new ModeIntf[]
		    {
			    new MySQLMode(),
			    new FlatMode()
		    };
    private static final ModeIntf<CustomPojo> mode = OPTIONS[0];

    @Bean
    public ItemReader reader()
    {
	return mode.reader();
    }


    @Bean
    public ItemWriter writer(final DataSource dataSource)
    {
	return mode.writer(dataSource);
    }

    @Bean
    public ItemProcessor<CustomPojo, CustomPojo> processor()
    {
	return new CustomItemProcessor();
    }

    @Bean
    public Job importUserJob(JobBuilderFactory jobs, Step s1)
    {
	return jobs.get("importUserJob").incrementer(new RunIdIncrementer())
		.flow(s1).end().build();
    }

    /**
     * the step 1 contains a reader a processor and a writer using a chunk of 10
     */
    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory,
		      ItemReader<CustomPojo> reader, ItemWriter<CustomPojo> writer,
		      ItemProcessor<CustomPojo, CustomPojo> processor)
    {
	return stepBuilderFactory.get("step1").<CustomPojo, CustomPojo>chunk(10).reader(reader)
		.processor(processor).writer(writer).build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(final DataSource aDataSource)
    {
	return new JdbcTemplate(aDataSource);
    }

    @Bean
    public DataSource mySQLDataSource() throws SQLException
    {
	final DriverManagerDataSource dataSource = new DriverManagerDataSource();
	dataSource.setDriverClassName("com.mysql.jdbc.Driver");
	dataSource
		.setUrl("jdbc:mysql://localhost/tejasbt");
	dataSource.setUsername("root");
	dataSource.setPassword("abc123");
	return dataSource;
    }

    @Bean
    public JobLauncherTestUtils jobLauncherTestUtils()
    {
	return new JobLauncherTestUtils();
    }
}