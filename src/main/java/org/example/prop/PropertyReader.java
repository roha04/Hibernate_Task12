package org.example.prop;

import org.example.hibernate.HibernateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;

public class PropertyReader {
    private static final String FILE_PROPERTY = "hibernate.properties";

    private static Optional<Properties> getProperties() {
        try (InputStream input = PropertyReader.class.getClassLoader()
                .getResourceAsStream(FILE_PROPERTY)) {
            Properties props = new Properties();
            if (input == null) {
                HibernateUtil.getLogger().error("Sorry, unable to find {} file", FILE_PROPERTY);
                return null;
            }
            props.load(input);
            return Optional.of(props);
        } catch (IOException e) {
            HibernateUtil.getLogger().error("getProperties() Error: {}", e.getMessage());
            return Optional.empty();
        }
    }
    public static String getUrlConnectionPostgres() {
        return Objects.requireNonNull(getProperties())
                .map(prop -> prop.getProperty("hibernate.connection.url")).orElse(null);
    }
    public static String getUserConnectionPostgres() {
        return Objects.requireNonNull(getProperties())
                .map(properties -> properties.getProperty("hibernate.connection.username")).orElse(null);
    }
    public static String getPswConnectionPostgres() {
        return Objects.requireNonNull(getProperties())
                .map(properties -> properties.getProperty("hibernate.connection.password")).orElse(null);
    }
}
