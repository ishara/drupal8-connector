package org.mule.modules.drupal8.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;

@JsonPropertyOrder({
    "value"
})
public class Role {

    @JsonProperty("value")
    private String value;
   
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
