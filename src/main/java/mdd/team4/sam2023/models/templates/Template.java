package mdd.team4.sam2023.models.templates;


import mdd.team4.sam2023.models.BaseEntity;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Template extends BaseEntity {
    String name;
    String description;

    public Template(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Template() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
