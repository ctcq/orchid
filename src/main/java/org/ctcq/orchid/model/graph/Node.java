package org.ctcq.orchid.model.graph;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.ctcq.orchid.driver.exceptions.DriverException;
import org.ctcq.orchid.model.media.MediaData;
import org.ctcq.orchid.model.media.exceptions.MediaException;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

/**
 * Abstract superclass for all nodes types.
 */
@NodeEntity
public abstract class Node {
    
    @Id @GeneratedValue
    private Integer id;

    private String name;

    public Node() {
        // Empty constructor
    }

    public Node(String name) {
        this.name = name;
        this.nodesOut = new HashSet<>();
    }

    /**
     * Accept a {@link MediaData} instance from a previous node and process it.
     * MAY be chained into {@link #forward(MediaData)} if the {@link MediaData} does not persist.
     * @param media The media to be processed.
     * @throws MediaException If the MediaData can not be validated.
     */
    protected abstract void accept(MediaData media) throws MediaException, DriverException;

    /**
     * Forward {@link MediaData} proviously processed by {@link #accept(MediaData)}.
     * MAY be directly called from {@link #accept(MediaData)} if the {@link MediaData} does not persist. 
     * @param media The processed media that is to be forwarded.
     * @throws MediaException If the MediaData can not be validated.
     * @throws DriverException If an OutputDriver cannot send the MediaData.
     */
    protected abstract void forward (MediaData media) throws MediaException, DriverException;

    // Relationships

    @JsonIgnoreProperties({"nodesIn"})
    @Relationship(type = "NODES_OUT", direction = Relationship.OUTGOING)
    private Set<Node> nodesOut;

    @JsonIgnoreProperties({"nodesOut"})
    @Relationship(type = "NODES_IN", direction = Relationship.INCOMING)
    private Set<Node> nodesIn;

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Node> getNodesOut() {
        return nodesOut;
    }

    public void setNodesOut(Set<Node> nodesOut) {
        this.nodesOut = nodesOut;
    }

    @Override
    public String toString() {
        return "Node [id=" + id + ", name=" + name + ", nodesIn=" + nodesOut + "]";
    }
}