package hello;

import org.apache.commons.dbcp.BasicDataSource;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class ContextDataSource {
    private static Logger logger = LoggerFactory.getLogger(ContextDataSource.class);
    
    @Bean(name="dataSource",destroyMethod="close")
    public BasicDataSource dataSource() {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(System.getenv("ASOG_ENCRYPTION_KEY"));
        String url = encryptor.decrypt("N9E8Gx8OgK3sSTU5lvKukxCZK1JvDJ6yYH00Oo2QGgSArGdet42ZOjulkKYItVCCgMcccyM5U4c=");
        String id = encryptor.decrypt("Ma/9nT/AbenjlE85W0D+di1tNfRHyLTC");
        String pw = encryptor.decrypt("I7qV/0X33au3v+j7swW36uxKBFCrBbg4");
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("net.sf.log4jdbc.DriverSpy");
        dataSource.setUrl(url);
        dataSource.setUsername(id);
        dataSource.setPassword(pw);
        dataSource.setDefaultAutoCommit(true);
        dataSource.setMaxActive(30);
        dataSource.setMaxWait(4000);
        dataSource.setMaxIdle(10);

        return dataSource;

    }
    @Bean(name="transactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
    
}
