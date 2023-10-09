
package com.mjc.school.repository;

import com.mjc.school.repository.util.DBLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
@Configuration
@EnableJpaAuditing

/*@EnableTransactionManagement

@EntityScan
@ComponentScan*/
//@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RepoConfig {
    public static void main(String[] args) {

        SpringApplication.run(RepoConfig.class, args);

    }

  /*  @Bean
    @Autowired
    public CommandLineRunner CommandLineRunnerBean(DBLoader db) {
        return (args) -> {
            db.writeNews();
        };
    }*/

}
