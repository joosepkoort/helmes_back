package com.helmes.proovitoo.model;

public class Mapping {

    private int parentId;
    private int childId;
    private String ItemName;

    public Mapping(int parentId, int childId, String itemName) {
        this.parentId = parentId;
        this.childId = childId;
        ItemName = itemName;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getChildId() {
        return childId;
    }

    public void setChildId(int childId) {
        this.childId = childId;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }
}