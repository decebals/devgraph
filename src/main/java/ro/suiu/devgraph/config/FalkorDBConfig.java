package ro.suiu.devgraph.config;

import com.falkordb.Driver;
import com.falkordb.FalkorDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.falkordb.core.DefaultFalkorDBClient;
import org.springframework.data.falkordb.core.FalkorDBClient;
import org.springframework.data.falkordb.core.FalkorDBTemplate;
import org.springframework.data.falkordb.core.mapping.DefaultFalkorDBEntityConverter;
import org.springframework.data.falkordb.core.mapping.DefaultFalkorDBMappingContext;
import org.springframework.data.falkordb.core.mapping.FalkorDBEntityConverter;
import org.springframework.data.falkordb.core.mapping.FalkorDBMappingContext;
import org.springframework.data.falkordb.repository.config.EnableFalkorDBRepositories;
import org.springframework.data.mapping.model.EntityInstantiators;

import java.net.URI;

/**
 * FalkorDB Configuration.
 * Enables FalkorDB repositories when the 'falkordb' profile is active.
 */
@Configuration
@Profile("falkordb")
@EnableFalkorDBRepositories(basePackages = "ro.suiu.devgraph.falkordb.repository")
public class FalkorDBConfig {

    @Bean
    @ConditionalOnMissingBean
    public Driver falkorDBDriver(@Value("${spring.data.falkordb.uri}") String uri) {
        URI parsed = URI.create(uri);
        String host = parsed.getHost();
        int port = parsed.getPort() == -1 ? 6379 : parsed.getPort();

        return FalkorDB.driver(host, port);
    }

    @Bean
    @ConditionalOnMissingBean
    public FalkorDBClient falkorDBClient(Driver driver, @Value("${spring.data.falkordb.database:devgraph}") String database) {
        return new DefaultFalkorDBClient(driver, database);
    }

    @Bean
    @ConditionalOnMissingBean
    public FalkorDBMappingContext falkorDBMappingContext() {
        return new DefaultFalkorDBMappingContext();
    }

    @Bean
    @ConditionalOnMissingBean
    public FalkorDBEntityConverter falkorDBEntityConverter(FalkorDBMappingContext mappingContext, FalkorDBClient client) {
        return new DefaultFalkorDBEntityConverter(mappingContext, new EntityInstantiators(), client);
    }

    @Bean(name = "falkorDBTemplate")
    @ConditionalOnMissingBean(name = "falkorDBTemplate")
    public FalkorDBTemplate falkorDBTemplate(FalkorDBClient client, FalkorDBMappingContext mappingContext, FalkorDBEntityConverter converter) {
        return new FalkorDBTemplate(client, mappingContext, converter);
    }

}