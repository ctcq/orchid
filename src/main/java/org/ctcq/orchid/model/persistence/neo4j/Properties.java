package org.ctcq.orchid.model.persistence.neo4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Properties {
    
    @Value( "${neo4j.url}" )
    private String neo4jUrl;

}