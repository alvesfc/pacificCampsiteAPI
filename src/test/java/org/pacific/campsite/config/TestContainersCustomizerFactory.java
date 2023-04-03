package org.pacific.campsite.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.test.context.ContextConfigurationAttributes;
import org.springframework.test.context.ContextCustomizer;
import org.springframework.test.context.ContextCustomizerFactory;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

public class TestContainersCustomizerFactory implements ContextCustomizerFactory {

    private final Logger log = LoggerFactory.getLogger(TestContainersCustomizerFactory.class);
    private static PostgresTestContainer postgreSQLContainer;

    @Override
    public ContextCustomizer createContextCustomizer(Class<?> testClass, List<ContextConfigurationAttributes> configAttributes) {
        return (context, mergedConfig) -> {
            ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
            TestPropertyValues testValues = TestPropertyValues.empty();

            if (postgreSQLContainer == null) {
                log.info("Warming up the  Database");
                postgreSQLContainer = new PostgresTestContainer();

                beanFactory.initializeBean(postgreSQLContainer, PostgreSQLContainer.class.getName().toLowerCase());

                beanFactory.registerSingleton(PostgreSQLContainer.class.getName().toLowerCase(), postgreSQLContainer);

                ((DefaultSingletonBeanRegistry) beanFactory).registerDisposableBean(
                        PostgreSQLContainer.class.getName().toLowerCase(), postgreSQLContainer);
            }

            System.setProperty("spring.r2dbc.url", PostgresTestContainer.getPostgreSQLContainer().getJdbcUrl().replace("jdbc", "r2dbc"));
            System.setProperty("spring.liquibase.url", PostgresTestContainer.getPostgreSQLContainer().getJdbcUrl());

            testValues.applyTo(context);
        };
    }
}
