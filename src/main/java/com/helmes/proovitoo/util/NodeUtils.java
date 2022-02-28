package com.helmes.proovitoo.util;

import com.helmes.proovitoo.model.Mapping;
import com.helmes.proovitoo.model.TreeNode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeUtils {
    /**
     * Helper to construct a nested tree from flat tree map.
     *
     * @param node
     * @param json
     */
    private static void writeJson(TreeNode node, StringBuilder json) {
        if (node.getChildren().isEmpty()) // no children. return just the node name
        {
            json.append("{ name: " + "\"").append(node.getNodeName()).append("\"}");
        } else {
            json.append("{ name: " + "\"").append(node.getNodeName()).append("\"" + ", children: [");

            List<TreeNode> children = node.getChildren();
            for (int i = 0; i < children.size(); i++) {
                TreeNode child = children.get(i);
                writeJson(child, json); // call recursively
                if (i != children.size() - 1) // skip , for the last child
                {
                    json.append(",");
                }
            }
            json.append("]}");
        }
    }

    /**
     * Insert the tree into the database
     * @param list
     * @throws SQLException
     */
    public void insertTreeIntoDatabase(List<Mapping> list) throws SQLException {
        String dbUrl = PropertyLoader.getProperty("javax.persistence.jdbc.url");
        Connection c = DriverManager.getConnection(dbUrl);
        Statement stmt = c.createStatement();
        list.forEach((x) -> {
            try {
                String nameWithQuotes = "'" + x.getItemName() + "'"; // insert to the database
                String in = "insert into tree (id, parent_id, child_id, name) " + "VALUES " + "(NEXT VALUE FOR seq1, " + x.getParentId() + ", " + x.getChildId() + ", " + nameWithQuotes + ");";
                stmt.execute(in);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        Map<Integer, TreeNode> map = new HashMap<Integer, TreeNode>();
        map.put(-1, new TreeNode("root")); // give index -1 for the root
        for (Mapping mapping : list)  // keep a map of nodes by child id
        {
            map.put(mapping.getChildId(), new TreeNode(mapping.getItemName()));
        }
        for (Mapping mapping : list) {
            if (mapping.getParentId() == mapping.getChildId()) {
                map.get(-1).addChild(map.get(mapping.getChildId())); // add to the root
            } else {
                TreeNode node = map.get(mapping.getParentId());
                TreeNode childNode = map.get(mapping.getChildId());
                node.addChild(childNode); // add to the relevant parent
            }
        }
        StringBuilder json = new StringBuilder();
        writeJson(map.get(-1), json); // root node is enough
    }

}
