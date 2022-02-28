package com.helmes.proovitoo.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helmes.proovitoo.model.Relation;
import com.helmes.proovitoo.util.JpaUtil;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

public class DataDao implements DataDaoInterface {

    @Override
    public String getTree() {

        String returnString = "";

        EntityManager em = JpaUtil.getFactory().createEntityManager();
        em.getTransaction().begin();
        List<Relation> results = em.createNativeQuery("select * from tree").getResultList();
        List<Relation> relationships = new ArrayList<>();
        for (Object result : results) {
            Object[] results2 = (Object[]) result;
            Integer parentId = (Integer) results2[1];
            Integer childId = (Integer) results2[2];
            String name = (String) results2[3];
            Relation relation = new Relation();
            relation.setId(String.valueOf(childId));
            relation.setParentId(String.valueOf(parentId));
            relation.setName(name);
            relationships.add(relation);
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            returnString = mapper.writeValueAsString(relationships);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        em.getTransaction().commit();
        em.close();
        return returnString;
    }


}
