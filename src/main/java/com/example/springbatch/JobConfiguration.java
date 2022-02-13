package com.example.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job testJob() {
        return jobBuilderFactory.get("testJob")
            .start(testStep1())
            .next(testStep2())
            .build();
    }

    @Bean
    public Step testStep1() {
        return stepBuilderFactory.get("testStep1")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("============");
                System.out.println("테스트스텝1 실행");
                System.out.println("============");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }

    @Bean
    public Step testStep2() {
        return stepBuilderFactory.get("testStep2")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("============");
                System.out.println("테스트 스텝2 실행");
                System.out.println("============");                System.out.println("스텝2번");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }
}
