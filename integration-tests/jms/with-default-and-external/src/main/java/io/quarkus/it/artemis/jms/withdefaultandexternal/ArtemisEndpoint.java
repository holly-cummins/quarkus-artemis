package io.quarkus.it.artemis.jms.withdefaultandexternal;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import io.smallrye.common.annotation.Identifier;

@Path("/artemis")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class ArtemisEndpoint {
    private final ArtemisProducerManager defaultProducer;
    private final ArtemisConsumerManager defaultConsumer;
    private final ArtemisProducerManager namedOneProducer;
    private final ArtemisConsumerManager namedOneConsumer;
    private final ArtemisProducerManager externallyDefinedProducer;
    private final ArtemisConsumerManager externallyDefinedConsumer;

    public ArtemisEndpoint(
            ArtemisProducerManager defaultProducer,
            ArtemisConsumerManager defaultConsumer,
            @Identifier("named-1") ArtemisProducerManager namedOneProducer,
            @Identifier("named-1") ArtemisConsumerManager namedOneConsumer,
            @Identifier("externally-defined") ArtemisProducerManager externallyDefinedProducer,
            @Identifier("externally-defined") ArtemisConsumerManager externallyDefinedConsumer) {
        this.defaultProducer = defaultProducer;
        this.defaultConsumer = defaultConsumer;
        this.namedOneProducer = namedOneProducer;
        this.namedOneConsumer = namedOneConsumer;
        this.externallyDefinedProducer = externallyDefinedProducer;
        this.externallyDefinedConsumer = externallyDefinedConsumer;
    }

    @POST
    public void defaultPost(String message) {
        defaultProducer.send(message);
    }

    @GET
    public String defaultGet() {
        return defaultConsumer.receive();
    }

    @POST
    @Path("/xa")
    @Transactional
    public void defaultPostXA(String message) throws Exception {
        defaultProducer.sendXA(message);
    }

    @POST
    @Path("named-1")
    public void namedOnePost(String message) {
        namedOneProducer.send(message);
    }

    @GET
    @Path("named-1")
    public String namedOneGet() {
        return namedOneConsumer.receive();
    }

    @POST
    @Path("named-1/xa")
    @Transactional
    public void namedOnePostXA(String message) throws Exception {
        namedOneProducer.sendXA(message);
    }

    @POST
    @Path("externally-defined")
    public void externallyDefinedPost(String message) {
        externallyDefinedProducer.send(message);
    }

    @POST
    @Path("externally-defined/xa")
    @Transactional
    public void externallyDefinedPostXA(String message) throws Exception {
        externallyDefinedProducer.sendXA(message);
    }

    @GET
    @Path("externally-defined")
    public String externallyDefinedGet() {
        return externallyDefinedConsumer.receive();
    }
}
