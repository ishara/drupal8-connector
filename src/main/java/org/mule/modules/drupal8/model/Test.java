package org.mule.modules.drupal8.model;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.module.SimpleModule;
import org.codehaus.jackson.map.ser.std.SerializerBase;

public class Test {

	public static void main(String[] args) throws JsonParseException,
			JsonMappingException, IOException {

		SimpleModule module = new SimpleModule("DrupalEntityModule",
				new Version(1, 0, 0, null));

		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(module);
		TestModel model = mapper
				.readValue(
						"{\"nid\":[{\"value\":1}], \"uuid\": [ { \"value\": \"7940ff6e-e92b-4861-9d0b-119c846f6208\" } ]}",
						TestModel.class);
		System.out.println(model.getNid() + model.getUuid());
		StringWriter w = new StringWriter();
		mapper.writeValue(new JsonFactory().createJsonGenerator(w), model);
		w.close();
		System.out.println("back::: " + w.toString());
	}
}

class TestModel {
	@JsonSerialize(using = Serializer.class, as = Integer.class)
	@JsonDeserialize(using = Deserializer.class, as = Integer.class)
	private Integer nid;

	private String uuid;

	public Integer getNid() {
		return nid;
	}

	public void setNid(Integer nid) {
		this.nid = nid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(List<Map<String, Object>> uuid) {
		this.uuid = uuid.get(0).get("value").toString();
	}
}

class Nid {
	private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

class Deserializer extends JsonDeserializer<Object> {
	@Override
	public Object deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.readValueAsTree();
		JsonNode value = null;
		if (node.isArray() && node.size() == 1) {
			JsonNode inner = node.get(0);
			if (inner.size() == 1 && inner.has("value")) {
				ctxt.getClass();
				value = inner.get("value");
				return new ObjectMapper().convertValue(value, Object.class);
			}
		}

		return null;
	}
}

class Serializer extends SerializerBase<Object> {

	protected Serializer() {
		super(Object.class);
	}

	@Override
	public void serialize(Object arg0, JsonGenerator jgen,
			SerializerProvider arg2) throws IOException,
			JsonProcessingException {
		jgen.writeStartArray();
		jgen.writeStartObject();
		jgen.writeObjectField("value", arg0);
		jgen.writeEndObject();
		jgen.writeEndArray();

	}
}