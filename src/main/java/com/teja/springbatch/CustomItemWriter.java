package com.teja.springbatch;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class CustomItemWriter implements ItemWriter<CustomPojo>
{
    @Override
    public void write(List<? extends CustomPojo> pojo) throws Exception
    {
	System.out.println("writing Pojo " + pojo);
    }
}
