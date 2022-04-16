package com.example.springbatch.chunkprac.writer;

import com.example.springbatch.chunkprac.domain.Customer;
import java.util.List;
import org.springframework.batch.item.ItemWriter;

public class CustomerWriter implements ItemWriter<Customer> {

    @Override
    public void write(List<? extends Customer> items) throws Exception {
        items.forEach(item -> System.out.println("item = " + item));
    }
}
