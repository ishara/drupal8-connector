
package org.mule.modules.drupal8.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonPropertyOrder({
    "target_id",
    "revision_id"
})
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FieldTag {

    @JsonProperty("target_id")
    private String target_id;
    
    @JsonProperty("revision_id")
    private Object revision_id;
    
    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }

    public Object getRevision_id() {
        return revision_id;
    }

    public void setRevision_id(Object revision_id) {
        this.revision_id = revision_id;
    }

}
