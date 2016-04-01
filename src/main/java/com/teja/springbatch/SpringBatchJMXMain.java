package com.teja.springbatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class SpringBatchJMXMain implements CommandLineRunner
{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception
    {
	System.out.println("running...");
	/*
	 * //different implementations for different scenarios are
         * neededList<CustomPojo> pojos =
         * jdbcTemplate.query("SELECT * FROM pojo", new RowMapper<CustomPojo>()
         * {
         * @Override public CustomPojo mapRow(ResultSet rs, int row) throws
         * SQLException { return new CustomPojo(rs.getString(1),
         * rs.getString(2)); } });
         * for (CustomPojo pojo : pojos) { System.out.println(pojo); }
         */
    }

    public static void main(String[] args)
    {
        SpringApplication.run(SpringBatchJMXMain.class, args);
    }
}
