package org.mule.modules.drupal8.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.mule.modules.drupal8.model.mapper.WrapperDeserializer;
import org.mule.modules.drupal8.model.mapper.WrapperSerializer;

public class User extends DrupalEntity {
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("uid")
	private String uid;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("preferred_langcode")
	private String preferred_langcode;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("preferred_admin_langcode")
	private String preferred_admin_langcode;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("name")
	private String name;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("pass")
	private String password;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("mail")
	private String mail;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("signature")
	private String signature;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("signature_format")
	private String signature_format;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("theme")
	private String theme;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("timezone")
	private String timezone;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("access")
	private String access;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("login")
	private String login;
	
	@JsonSerialize(using = WrapperSerializer.class, as = String.class, include=JsonSerialize.Inclusion.NON_NULL)
	@JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
	@JsonProperty("init")
	private String init;
	
	@JsonProperty("roles")
	private List<Role> roles = new ArrayList<Role>();
	
	@JsonProperty("user_picture")
	private List<UserPicture> user_picture = new ArrayList<UserPicture>();
	
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getPreferred_langcode() {
		return preferred_langcode;
	}

	public void setPreferred_langcode(String preferred_langcode) {
		this.preferred_langcode = preferred_langcode;
	}

	public String getPreferred_admin_langcode() {
		return preferred_admin_langcode;
	}

	public void setPreferred_admin_langcode(String preferred_admin_langcode) {
		this.preferred_admin_langcode =  preferred_admin_langcode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String pass) {
		this.password = pass;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getSignature_format() {
		return signature_format;
	}

	public void setSignature_format(String signature_format) {
		this.signature_format = signature_format;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getTimezone() {
		return timezone;
	}

	public void setTimezone(String timezone) {
		this.timezone = timezone;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getInit() {
		return init;
	}

	public void setInit(String init) {
		this.init = String.valueOf(init);
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<UserPicture> getUser_picture() {
		return user_picture;
	}

	public void setUser_picture(List<UserPicture> user_picture) {
		this.user_picture = user_picture;
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
