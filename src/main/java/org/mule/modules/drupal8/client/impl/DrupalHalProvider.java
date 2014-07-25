package org.mule.modules.drupal8.client.impl;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.Version;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;
import org.mule.modules.drupal8.model.DrupalEntity;
import org.mule.modules.drupal8.model.Node;
import org.mule.modules.drupal8.model.mapper.ItemDeserializer;

@Provider
@Produces(value={"application/hal+json"})
@Consumes(value={"application/hal+json"})
public class DrupalHalProvider extends JacksonJsonProvider implements ContextResolver<ObjectMapper>
{
    private static final String NODE_ID = "nid";
    
    @Override
    public ObjectMapper getContext(Class<?> type)
    {
        ItemDeserializer deserializer = new ItemDeserializer();
        deserializer.registerItem(NODE_ID, Node.class);
        SimpleModule module = new SimpleModule("PolymorphicItemDeserializerModule", new Version(1,
                0, 0, null));
        module.addDeserializer(DrupalEntity.class, deserializer);

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(module);
        return mapper;
    }

}
