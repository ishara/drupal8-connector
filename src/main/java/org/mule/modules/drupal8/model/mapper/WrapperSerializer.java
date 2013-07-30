package org.mule.modules.drupal8.model.mapper;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

public class WrapperSerializer extends SerializerBase<Object> {

	protected WrapperSerializer() {
		super(Object.class);
	}

	@Override
	public void serialize(Object value, JsonGenerator jgen,
			SerializerProvider sp) throws IOException,
			JsonProcessingException {
		jgen.writeStartArray();
		jgen.writeStartObject();
		jgen.writeObjectField("value", value);
		jgen.writeEndObject();
		jgen.writeEndArray();
	}

}
