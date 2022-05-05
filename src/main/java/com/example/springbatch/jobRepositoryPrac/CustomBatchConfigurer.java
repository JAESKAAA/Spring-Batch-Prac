package com.example.springbatch.jobRepositoryPrac;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomBatchConfigurer extends BasicBatchConfigurer {

    private final DataSource dataSource;
    /**
     * Create a new {@link BasicBatchConfigurer} instance.
     *  @param properties                    the batch properties
     * @param dataSource                    the underlying data source
     * @param transactionManagerCustomizers transaction manager customizers (or {@code null})
     */
    protected CustomBatchConfigurer(
        BatchProperties properties,
        TransactionManagerCustomizers transactionManagerCustomizers,
        DataSource dataSource) {
        super(properties, dataSource, transactionManagerCustomizers);
        this.dataSource = dataSource;
    }


    //하기의 옵션들을 오버라이드 했기 떄문에, JobRepository 생성시 옵션을 변경 할 수 있음
    @Override
    protected JobRepository createJobRepository() throws Exception {

        JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTransactionManager(getTransactionManager());
        factoryBean.setIsolationLevelForCreate("ISOLATION_READ_COMMITTED");
        //하기와 같이 메타테이블 접두사도 바꿀 수 있는데, 현재 BATCH로 만들어져 있어 에러발생할 것
        factoryBean.setTablePrefix("SYSTEM_");

        return factoryBean.getObject();
    }
}
