package io.quarkus.it.artemis.core.withexternal.embedded;

import io.quarkus.artemis.test.ArtemisTestResource;

public class ExternallyDefinedArtemisTestResource extends ArtemisTestResource {
    public ExternallyDefinedArtemisTestResource() {
        super("artemis", "externally-defined");
    }
}
