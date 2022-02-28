package com.helmes.proovitoo.model;

import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String nodeName;
    private List<TreeNode> children = new ArrayList<>();

    public TreeNode(String nodeName) {
        this.nodeName = nodeName;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public void addChild(TreeNode node) {
        this.children.add(node);
    }
}