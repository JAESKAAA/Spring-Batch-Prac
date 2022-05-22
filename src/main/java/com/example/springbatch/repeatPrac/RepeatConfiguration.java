package com.example.springbatch.repeatPrac;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.batch.repeat.exception.SimpleLimitExceptionHandler;
import org.springframework.batch.repeat.policy.SimpleCompletionPolicy;
import org.springframework.batch.repeat.support.RepeatTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RepeatConfiguration {


    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean("repeatPrac")
    public Job chunkJob() {
        return jobBuilderFactory.get("repeatPrac")
            .start(chunkStep1())
            .build();
    }

    @Bean("repeatStep1")
    public Step chunkStep1() {
        return stepBuilderFactory.get("repeatStep1")
            .<String, String>chunk(5)
            .reader(itemReader())
            .processor(itemProcessor())
            .writer(itemWriter())
            .build();
    }

    public ItemReader<String> itemReader() {
        return new ItemReader<String>() {
            int i = 0;
            @Override
            public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
                i++;
                return i > 3 ? null : "item" + i;
            }
        };
    }

    private ItemProcessor<? super String, String> itemProcessor() {
        return new ItemProcessor<String, String>() {

            RepeatTemplate repeatTemplate = new RepeatTemplate();


            @Override
            public String process(String item) throws Exception {
//                repeatTemplate.setCompletionPolicy(new SimpleCompletionPolicy(3));

                repeatTemplate.setExceptionHandler(simpleLimitExceptionHandler());

                repeatTemplate.iterate(repeatContext -> {
                    System.out.println("repeatTemplate is testing");
                    throw new RuntimeException("Exception is occurred");
//                    return RepeatStatus.CONTINUABLE;
                });
                return item;
            }
        };
    }

    private ItemWriter<? super String> itemWriter() {
        return item -> System.out.println("item = " + item);
    }

    @Bean
    public SimpleLimitExceptionHandler simpleLimitExceptionHandler() {
        return new SimpleLimitExceptionHandler(3);
    }
}

