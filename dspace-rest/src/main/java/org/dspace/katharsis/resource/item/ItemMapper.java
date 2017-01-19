package org.dspace.katharsis.resource.item;


import org.apache.commons.lang.ObjectUtils;
import org.dspace.content.Item;

/**
 * TODO TOM UNIT TEST
 */
public class ItemMapper {

    public ItemResource map(Item item) {
        ItemResource itemResource = new ItemResource();

        if(item != null) {
            itemResource.setId(ObjectUtils.toString(item.getID()));
            itemResource.setHandle(item.getHandle());
        }

        return itemResource;
    }
}
