package org.mule.modules.drupal8.model;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.mule.modules.drupal8.model.mapper.WrapperDeserializer;
import org.mule.modules.drupal8.model.mapper.WrapperSerializer;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TaxonomyTerm extends DrupalEntity
{
    @JsonSerialize(using = WrapperSerializer.class, as = String.class, include = JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
    @JsonProperty("name")
    private String name;

    @JsonSerialize(using = WrapperSerializer.class, as = String.class, include = JsonSerialize.Inclusion.NON_NULL)
    @JsonDeserialize(using = WrapperDeserializer.class, as = String.class)
    @JsonProperty("weight")
    private String weight;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getWeight()
    {
        return weight;
    }

    public void setWeight(String weight)
    {
        this.weight = weight;
    }

}
