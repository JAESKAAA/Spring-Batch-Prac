package com.example.springbatch.chunkprac.config;


import com.example.springbatch.chunkprac.domain.Customer;
import com.example.springbatch.chunkprac.processor.CustomProcessor;
import com.example.springbatch.chunkprac.reader.CustomItemReader;
import com.example.springbatch.chunkprac.writer.CustomerWriter;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class chunkConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean("chunkJob")
    public Job chunkJob() {
        return jobBuilderFactory.get("chunkJob")
            .start(chunkStep1())
            .next(chunkStep2())
            .build();
    }

    @Bean("chunkStep1")
    public Step chunkStep1() {
        return stepBuilderFactory.get("chunkStep1")
            .<Customer, Customer>chunk(3)
            .reader(itemReader())
            .processor(itemProcessor())
            .writer(itemWriter())
            .build();
    }

    private ItemWriter<? super Customer> itemWriter() {
        return new CustomerWriter();
    }

    private ItemProcessor<? super Customer, ? extends Customer> itemProcessor() {
        return new CustomProcessor();
    }

    public ItemReader<Customer> itemReader() {
        return new CustomItemReader(Arrays.asList(new Customer("songja"),
            new Customer("jaeseok"),
            new Customer("dahye")));
    }

    @Bean("chunkStep2")
    public Step chunkStep2() {
        return stepBuilderFactory.get("chunkStep2")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("step2 has excuted");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }
}
