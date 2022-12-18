package dfw.database;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@PropertySource("classpath:application.properties")
@PropertySource("classpath:env.properties")
public class PostgreConfig {

    @Bean
    @ConfigurationProperties("app.database")
    DataSource getDataSource() {
        return DataSourceBuilder
                .create()
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    @Bean
    JdbcTemplate getJdbcTemplate() {
        return new JdbcTemplate(this.getDataSource());
    }

    @Bean
    NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(this.getDataSource());
    }
}
