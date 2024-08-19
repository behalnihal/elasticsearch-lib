package org.nihal.config;

public class ConnectionParams {
    private final String host;
    private final int port;
    private final String scheme;
    private final String username;
    private final String password;
    private final String trustStorePath;
    private final String trustStorePassword;

    private ConnectionParams(Builder builder) {
        this.host = builder.host;
        this.port = builder.port;
        this.scheme = builder.scheme;
        this.username = builder.username;
        this.password = builder.password;
        this.trustStorePath = builder.trustStorePath;
        this.trustStorePassword = builder.trustStorePassword;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getScheme() {
        return scheme;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getTrustStorePath() {
        return trustStorePath;
    }

    public String getTrustStorePassword() {
        return trustStorePassword;
    }

    public static class Builder {
        private String host;
        private int port = 9200; // Default port
        private String scheme = "http"; // Default scheme
        private String username;
        private String password;
        private String trustStorePath;
        private String trustStorePassword;

        public Builder host(String host) {
            this.host = host;
            return this;
        }

        public Builder port(int port) {
            this.port = port;
            return this;
        }

        public Builder scheme(String scheme) {
            this.scheme = scheme;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder trustStorePath(String trustStorePath) {
            this.trustStorePath = trustStorePath;
            return this;
        }

        public Builder trustStorePassword(String trustStorePassword) {
            this.trustStorePassword = trustStorePassword;
            return this;
        }

        public ConnectionParams build() {
            if (host == null) {
                throw new IllegalStateException("Host cannot be null");
            }
            return new ConnectionParams(this);
        }
    }

    @Override
    public String toString() {
        return "ConnectionParams{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", scheme='" + scheme + '\'' +
                ", username='" + username + '\'' +
                ", password='********'" +
                ", trustStorePath='" + trustStorePath + '\'' +
                ", trustStorePassword='********'" +
                '}';
    }
}