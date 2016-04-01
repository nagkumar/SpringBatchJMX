package com.teja.springbatch;

import org.springframework.batch.item.file.FlatFileItemWriter;

import java.util.List;

public class CustomFlatFileItemWriter<CustomPojo> extends FlatFileItemWriter<CustomPojo>
{
    @Override
    public void write(List<? extends CustomPojo> arg0) throws Exception
    {
	super.write(arg0);
    }
}
