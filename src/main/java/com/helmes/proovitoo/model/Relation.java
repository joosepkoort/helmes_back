package com.helmes.proovitoo.model;

/**
 * Describes a single tree relation. the parentId, name and Id
 */
public class Relation {
    String id;
    String parentId;
    String name;

    public Relation() {

    }
    public Relation(String id, String value, String parentId) {
        this.id = id;
        this.name = value;
        this.parentId = parentId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

}
