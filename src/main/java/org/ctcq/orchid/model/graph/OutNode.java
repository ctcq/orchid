package org.ctcq.orchid.model.graph;

import org.ctcq.orchid.driver.EndpointDriver;
import org.ctcq.orchid.driver.OutputDriver;
import org.ctcq.orchid.driver.auth.AuthorizationDriver;
import org.ctcq.orchid.driver.exceptions.DriverException;
import org.ctcq.orchid.model.media.MediaData;
import org.ctcq.orchid.model.media.exceptions.MediaException;

public class OutNode extends EdgeNode {

    protected OutputDriver endpointDriver;

    public <T extends EndpointDriver & OutputDriver> OutNode(AuthorizationDriver auth, T endpointDriver) {
        super(auth, endpointDriver);
        this.endpointDriver = endpointDriver;
    }

    @Override
    protected void forward(MediaData media) throws MediaException, DriverException {
        this.endpointDriver.post(media);
    }
}