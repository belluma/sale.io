package capstone.backend;

import org.testcontainers.containers.PostgreSQLContainer;

public class CombinedTestContainer extends PostgreSQLContainer<CombinedTestContainer> {
    private static final String IMAGE_VERSION = "postgres:11.1";
    private static CombinedTestContainer container;

    private CombinedTestContainer() {
        super(IMAGE_VERSION);
    }

    public static CombinedTestContainer getInstance() {
        if (container == null) {
            container = new CombinedTestContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
