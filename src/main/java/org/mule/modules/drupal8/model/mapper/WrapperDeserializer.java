package org.mule.modules.drupal8.model.mapper;

import java.io.IOException;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.ObjectMapper;

public class WrapperDeserializer extends JsonDeserializer<Object> {
	@Override
	public Object deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.readValueAsTree();
		if (node.isArray() && node.size() == 1) {
			JsonNode inner = node.get(0);
			if (inner.size() == 1 && inner.has("value")) {
				return new ObjectMapper().convertValue(inner.get("value"), Object.class);
			}
		}
		return null;
	}

}
