package org.pacific.campsite.config;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;

public class PostgresTestContainer  implements InitializingBean, DisposableBean {

    private static PostgreSQLContainer postgreSQLContainer;

    @Override
    public void destroy() {
        if (null != postgreSQLContainer && postgreSQLContainer.isRunning()) {
            postgreSQLContainer.stop();
        }

    }

    @Override
    public void afterPropertiesSet() {
        if (null == postgreSQLContainer) {
            postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.2"))
                    .withDatabaseName("pacificCampsiteApiDB")
                    .withUsername("postgres")
                    .withPassword("postgres")
                    .withExposedPorts(5432)
                    .withAccessToHost(true)
                    .withTmpFs(Collections.singletonMap("/testtmpfs", "rw"))
                    .withReuse(true);

        }
        if (!postgreSQLContainer.isRunning()) {
            postgreSQLContainer.start();
        }

    }

    public static PostgreSQLContainer getPostgreSQLContainer() {
        return postgreSQLContainer;
    }
}
