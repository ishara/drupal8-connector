 package org.mule.modules.drupal8.model;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.mule.modules.drupal8.model.mapper.WrapperDeserializer;
import org.mule.modules.drupal8.model.mapper.WrapperSerializer;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Node extends DrupalEntity {

	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("nid")
	private String nid;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("vid")
	private String vid;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("type")
	private String type;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("title")
	private String title;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("uid")
	private String uid;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("changed")
	private String changed;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("comment")
	private String comment;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("promote")
	private String promote;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("sticky")
	private String sticky;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("tnid")
	private String tnid;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("translate")
	private String translate;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("revision_timestamp")
	private String revision_timestamp;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("revision_uid")
	private String revision_uid;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("log")
	private String log;
	
	@JsonProperty("body")
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
	private List<Body> body = new ArrayList<Body>();
	
	@JsonProperty("field_image")
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
	private List<FieldImage> field_image = new ArrayList<FieldImage>();
	
	@JsonProperty("field_tags")
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
	private List<FieldTag> field_tags = new ArrayList<FieldTag>();
	
	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getVid() {
		return vid;
	}

	public void setVid(String vid) {
		this.vid = vid;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getChanged() {
		return changed;
	}

	public void setChanged(String changed) {
		this.changed = changed;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getPromote() {
		return promote;
	}

	public void setPromote(String promote) {
		this.promote = promote;
	}

	public String getSticky() {
		return sticky;
	}

	public void setSticky(String sticky) {
		this.sticky = sticky;
	}

	public String getTnid() {
		return tnid;
	}

	public void setTnid(String tnid) {
		this.tnid = tnid;
	}

	public String getTranslate() {
		return translate;
	}

	public void setTranslate(String translate) {
		this.translate = translate;
	}

	public String getRevision_timestamp() {
		return revision_timestamp;
	}

	public void setRevision_timestamp(
			String revision_timestamp) {
		this.revision_timestamp = revision_timestamp;
	}

	public String getRevision_uid() {
		return revision_uid;
	}

	public void setRevision_uid(String revision_uid) {
		this.revision_uid = revision_uid;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public List<Body> getBody() {
		return body;
	}

	public void setBody(List<Body> body) {
		this.body = body;
	}

	public List<FieldImage> getField_image() {
		return field_image;
	}

	public void setField_image(List<FieldImage> field_image) {
		this.field_image = field_image;
	}

	public List<FieldTag> getField_tags() {
		return field_tags;
	}

	public void setField_tags(List<FieldTag> field_tags) {
		this.field_tags = field_tags;
	}

}
