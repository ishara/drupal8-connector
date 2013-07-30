
package org.mule.modules.drupal8.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonPropertyOrder({
    "value",
    "format",
    "summary"
})
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Body {

    @JsonProperty("value")
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String value;
    
    @JsonProperty("format")
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String format;
    
    @JsonProperty("summary")
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String summary;
   
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}
