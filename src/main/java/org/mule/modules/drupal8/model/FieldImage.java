package org.mule.modules.drupal8.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonPropertyOrder({ "target_id", "revision_id", "alt", "title", "width", "height" })
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FieldImage
{
    @JsonProperty("target_id")
    private Object target_id;

    @JsonProperty("revision_id")
    private Object revision_id;

    @JsonProperty("display")
    private Object display;

    @JsonProperty("description")
    private Object description;

    @JsonProperty("alt")
    private Object alt;

    @JsonProperty("title")
    private Object title;

    @JsonProperty("width")
    private Object width;

    @JsonProperty("height")
    private Object height;

    public Object getTarget_id()
    {
        return target_id;
    }

    public void setTarget_id(Object target_id)
    {
        this.target_id = target_id;
    }

    public Object getRevision_id()
    {
        return revision_id;
    }

    public void setRevision_id(Object revision_id)
    {
        this.revision_id = revision_id;
    }

    public Object getAlt()
    {
        return alt;
    }

    public void setAlt(Object alt)
    {
        this.alt = alt;
    }

    public Object getTitle()
    {
        return title;
    }

    public void setTitle(Object title)
    {
        this.title = title;
    }

    public Object getWidth()
    {
        return width;
    }

    public void setWidth(Object width)
    {
        this.width = width;
    }

    public Object getHeight()
    {
        return height;
    }

    public void setHeight(Object height)
    {
        this.height = height;
    }

    public Object getDisplay()
    {
        return display;
    }

    public void setDisplay(Object display)
    {
        this.display = display;
    }

    public Object getDescription()
    {
        return description;
    }

    public void setDescription(Object description)
    {
        this.description = description;
    }

}
