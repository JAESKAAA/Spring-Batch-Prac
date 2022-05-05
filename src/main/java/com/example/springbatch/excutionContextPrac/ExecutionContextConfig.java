package com.example.springbatch.excutionContextPrac;

import com.example.springbatch.excutionContextPrac.tasklet.ExecutionTasklet1;
import com.example.springbatch.excutionContextPrac.tasklet.ExecutionTasklet2;
import com.example.springbatch.excutionContextPrac.tasklet.ExecutionTasklet3;
import com.example.springbatch.excutionContextPrac.tasklet.ExecutionTasklet4;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ExecutionContextConfig {

    private final static String JOB_NAME = "ExecutionPracJob";
    private final static String BEAN_PREFIX = JOB_NAME+"_";

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ExecutionTasklet1 executionTasklet1;
    private final ExecutionTasklet2 executionTasklet2;
    private final ExecutionTasklet3 executionTasklet3;
    private final ExecutionTasklet4 executionTasklet4;

    @Bean(JOB_NAME)
    public Job executionJob() {
        return this.jobBuilderFactory.get(JOB_NAME)
            .start(executionStep1())
            .next(executionStep2())
            .next(executionStep3())
            .next(executionStep4())
            .build();
    }

    @Bean(BEAN_PREFIX+"step1")
    public Step executionStep1() {
        return this.stepBuilderFactory.get(BEAN_PREFIX + "step1")
            .tasklet(executionTasklet1)
            .build();
    }
    @Bean(BEAN_PREFIX+"step2")
    public Step executionStep2() {
        return this.stepBuilderFactory.get(BEAN_PREFIX + "step2")
            .tasklet(executionTasklet2)
            .build();
    }
    @Bean(BEAN_PREFIX+"step3")
    public Step executionStep3() {
        return this.stepBuilderFactory.get(BEAN_PREFIX + "step3")
            .tasklet(executionTasklet3)
            .build();
    }
    @Bean(BEAN_PREFIX+"step4")
    public Step executionStep4() {
        return this.stepBuilderFactory.get(BEAN_PREFIX + "step4")
            .tasklet(executionTasklet4 )
            .build();
    }

}
