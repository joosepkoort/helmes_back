package com.helmes.proovitoo.model;

/**
 * Html tag
 */
public class HtmlRelation {
    public Integer id;
    public String value;
    public Integer parentId;
    public Integer whitespaces;

    public HtmlRelation(Integer id, String value, Integer parentId, Integer whitespaces) {
        this.id = id;
        this.value = value;
        this.parentId = parentId;
        this.whitespaces = whitespaces;
    }

    public Integer getWhitespaces() {
        return whitespaces;
    }

    public void setWhitespaces(Integer whitespaces) {
        this.whitespaces = whitespaces;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

}
