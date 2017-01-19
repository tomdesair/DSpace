package org.dspace.katharsis.resource.item;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;

/**
 * TODO TOM UNIT TEST
 */
@JsonApiResource(type = "items")
public class ItemResource {

    @JsonApiId
    private String id;

    private String handle;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(final String handle) {
        this.handle = handle;
    }
}
