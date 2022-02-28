package com.helmes.proovitoo.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.helmes.proovitoo.dao.DataDao;
import com.helmes.proovitoo.dao.UserDao;
import com.helmes.proovitoo.model.Data;
import com.helmes.proovitoo.model.User;
import org.springframework.stereotype.Service;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class SelectService implements SelectServiceInterface {

    UserDao dao = new UserDao();
    DataDao dataDao = new DataDao();

    /**
     * Gets the constructed tree that resides in database
     * @return
     */
    public String loadTree() {
        String treeData = dataDao.getTree();
        return treeData;
    }

    /**
     * Gets user data such as (agreed, name, sectors selected)
     * @param principal
     * @return
     */
    public String loadDataForUser(Principal principal) {
        String json = "";
        UserDao dao = new UserDao();
        User user = dao.findUserByUserName(principal.getName());
        Data data = new Data();
        data.setSelectedSectors(bytesToStringList(user.getSectors_data()));
        data.setSelectedSectors(user.getSectors_data() != null ? bytesToStringList(user.getSectors_data()) : null);
        data.setAgreedToTerms(user.getAgreed_to_terms() != null ? user.getAgreed_to_terms() : false);
        data.setName(user.getName());
        ObjectMapper mapper = new ObjectMapper();
        try {
            json = mapper.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * Method that updates user data (agreed, name, sectors selected)
     * @param inputData
     * @param principal
     * @return
     */
    public Data updateUserData(Data inputData, Principal principal) {

        Data data = new Data();

        data.setAgreedToTerms(inputData.isAgreedToTerms());
        data.setName(inputData.getName());
        data.setSelectedSectors(inputData.getSelectedSectors());

        User user = dao.findUserByUserName(principal.getName());
        user.setAgreed_to_terms(data.isAgreedToTerms());
        user.setName(data.getName());
        user.setSectors_data(data.getSelectedSectors().toString().getBytes(StandardCharsets.UTF_8));
        dao.saveUser(user);
        return data;
    }

    /**
     * helper function that converts bytes array to List of strings
     * @param bytes
     * @return
     */
    public static List<String> bytesToStringList(byte[] bytes) {
        List<String> lines = new ArrayList<String>();

        if (bytes == null) {
            return lines;
        }

        BufferedReader r = null;

        try {
            r = new BufferedReader(
                    new InputStreamReader(
                            new ByteArrayInputStream(bytes),
                            "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }

        try {
            try {
                for (String line = r.readLine(); line != null; line = r.readLine()) {
                    lines.add(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                r.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return lines;
    }


}
