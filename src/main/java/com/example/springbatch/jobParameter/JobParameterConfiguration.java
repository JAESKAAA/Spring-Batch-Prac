package com.example.springbatch.jobParameter;

import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobParameterConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job paramTest() {
        return jobBuilderFactory.get("paramTest")
            .start(paramStep1())
            .next(paramStep2())
            .build();
    }

    @Bean
    public Step paramStep1() {
        return stepBuilderFactory.get("testStep1")
            .tasklet(((contribution, chunkContext) -> {
                JobParameters jobParameters = contribution.getStepExecution().getJobExecution()
                    .getJobParameters();

                //스텝에서도 설정한 파라미터를 받아 올 수 있음
                String name = jobParameters.getString("name");
                Long seq = jobParameters.getLong("seq");
                Date date = jobParameters.getDate("date");
                Double age = jobParameters.getDouble("age");



                Map<String, Object> jobParameters1 = chunkContext.getStepContext()
                    .getJobParameters();



                System.out.println("============");
                System.out.println("name : " +name);
                System.out.println("seq : " +seq);
                System.out.println("date : " +date);
                System.out.println("============");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }

    @Bean
    public Step paramStep2() {
        return stepBuilderFactory.get("testStep2")
            .tasklet(((contribution, chunkContext) -> {
                System.out.println("============");
                System.out.println("Param테스트 스텝2 실행");
                System.out.println("============");                System.out.println("스텝2번");
                return RepeatStatus.FINISHED;
            }))
            .build();
    }
}
