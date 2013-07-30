package org.mule.modules.drupal8.model.mapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.deser.std.StdDeserializer;
import org.codehaus.jackson.node.ObjectNode;
import org.mule.modules.drupal8.model.DrupalEntity;

public class ItemDeserializer extends StdDeserializer<DrupalEntity> {
   private Map<String, Class<? extends DrupalEntity>> registry = new HashMap<String, Class<? extends DrupalEntity>>();

   public ItemDeserializer() {
      super(DrupalEntity.class);
   }

   public void registerItem(String uniqueAttribute,
         Class<? extends DrupalEntity> itemClass) {
      registry.put(uniqueAttribute, itemClass);
   }

   @Override
   public DrupalEntity deserialize(JsonParser jp, DeserializationContext ctxt)
         throws IOException, JsonProcessingException {
      ObjectMapper mapper = (ObjectMapper) jp.getCodec();
      ObjectNode root = (ObjectNode) mapper.readTree(jp);
      Class<? extends DrupalEntity> itemClass = null;
      Iterator<Entry<String, JsonNode>> elementsIterator = root.getFields();
      while (elementsIterator.hasNext()) {
         Entry<String, JsonNode> element = elementsIterator.next();
         String name = element.getKey();
         if (registry.containsKey(name)) {
            itemClass = registry.get(name);
            break;
         }
      }
      if (itemClass == null)
         return null;
      return mapper.readValue(root, itemClass);
   }
}
