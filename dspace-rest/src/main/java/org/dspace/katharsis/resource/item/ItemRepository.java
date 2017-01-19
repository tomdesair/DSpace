package org.dspace.katharsis.resource.item;

import io.katharsis.queryspec.QuerySpec;
import io.katharsis.resource.list.ResourceList;
import org.dspace.content.Item;
import org.dspace.content.service.ItemService;
import org.dspace.core.Context;
import org.dspace.katharsis.common.DSpaceResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO TOM UNIT TEST
 */
@Component
public class ItemRepository extends DSpaceResourceRepository<ItemResource> {

    @Autowired
    private ItemService itemService;

    public ItemRepository() {
        super(ItemResource.class);
    }

    public ResourceList<ItemResource> findAll(final QuerySpec querySpec) {
        List<ItemResource> result = new LinkedList<>();

        try {
            Context context = createContext();
            Iterator<Item> itemIt = itemService.findAll(context);

            ItemMapper mapper = new ItemMapper();

            while(itemIt != null && itemIt.hasNext()) {
                result.add(mapper.map(itemIt.next()));
            }

        } catch (SQLException e) {
            //TODO something usefull
            e.printStackTrace();
        }

        return querySpec.apply(result);
    }
}
