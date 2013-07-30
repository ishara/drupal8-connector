package org.mule.modules.drupal8.model;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.mule.modules.drupal8.model.mapper.WrapperDeserializer;
import org.mule.modules.drupal8.model.mapper.WrapperSerializer;

@JsonPropertyOrder({ "nid", "uuid", "vid", "type", "langcode", "title", "uid",
		"status", "created", "changed", "comment", "promote", "sticky", "tnid",
		"translate", "revision_timestamp", "revision_uid", "log", "body",
		"field_image", "field_tags" })
public class DrupalEntity {

	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("uuid")
	private String uuid;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("langcode")
	private String langcode;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("status")
	private String status;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("created")
	private String created;
	
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();


	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid =  uuid;
	}

	public String getLangcode() {
		return this.langcode;
	}

	public void setLangcode(String langcode) {
		this.langcode = langcode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getCreated() {
		return created;
	}


	public void setCreated(String created) {
		this.created = created;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperties(String name, Object value) {
		this.additionalProperties.put(name, value);
	}	
	
}
