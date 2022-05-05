package com.example.springbatch.jobRepositoryPrac;

import com.example.springbatch.excutionContextPrac.tasklet.ExecutionTasklet1;
import com.example.springbatch.excutionContextPrac.tasklet.ExecutionTasklet2;
import com.example.springbatch.excutionContextPrac.tasklet.ExecutionTasklet3;
import com.example.springbatch.excutionContextPrac.tasklet.ExecutionTasklet4;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobRepositoryPrac {

    private final static String JOB_NAME = "JobRepositoryPrac";
    private final static String BEAN_PREFIX = JOB_NAME+"_";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final JobExecutionListener jobRepositoryListener;

    @Bean(JOB_NAME)
    public Job job() {
        return this.jobBuilderFactory.get(JOB_NAME)
            .start(step1())
            .next(step2())
            .listener(jobRepositoryListener)
            .build();
    }

    @Bean(BEAN_PREFIX+"step1")
    public Step step1() {
        return this.stepBuilderFactory.get(BEAN_PREFIX + "step1")
            .tasklet((contribution, chunkContext) -> RepeatStatus.FINISHED)
            .build();
    }
    @Bean(BEAN_PREFIX+"step2")
    public Step step2() {
        return this.stepBuilderFactory.get(BEAN_PREFIX + "step2")
            .tasklet(((contribution, chunkContext) -> null))
            .build();
    }
}
