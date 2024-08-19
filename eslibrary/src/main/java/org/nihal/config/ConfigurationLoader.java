package org.nihal.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigurationLoader {
    private static final String DEFAULT_PROPERTIES_FILE = "application.properties";

    public static ConnectionParams loadFromProperties() throws IOException {
        Properties props = new Properties();
        try (InputStream is = ConfigurationLoader.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES_FILE)) {
            if (is == null) {
                throw new IOException("application.properties not found in the classpath");
            }
            props.load(is);
        }

        return new ConnectionParams.Builder()
                .host(props.getProperty("search.host"))
                .port(Integer.parseInt(props.getProperty("search.port", "9200")))
                .scheme(props.getProperty("search.scheme", "http"))
                .username(props.getProperty("search.username"))
                .password(props.getProperty("search.password"))
                .trustStorePath(props.getProperty("search.trustStorePath"))
                .trustStorePassword(props.getProperty("search.trustStorePassword"))
                .build();
    }
}
