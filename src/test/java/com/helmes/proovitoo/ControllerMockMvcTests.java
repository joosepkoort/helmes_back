package com.helmes.proovitoo;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
class ControllerMockMvcTests {
    public static String bearerToken = "";

    @Test
    void contextLoads() {
    }

    @Autowired
    private MockMvc mockMvc;

    @Test
    protected void testAccess() throws Exception {
       this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().is(401));
    }

    @Test
    protected void testLoginSiteAccessDenied() throws Exception {
        this.mockMvc.perform(get("/api/login")).andDo(print()).andExpect(status().is(401));
    }

    @Test
    protected void testUserLogin() throws Exception {
        String json = "{ \"userName\": \"userhelmes\",\n" + "\"password\": \"userhelmes\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/login")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andReturn();

        bearerToken = mvcResult.getResponse().getContentAsString();
    }

    @Test
    @WithMockUser(username = "userhelmes", password = "userhelmes", roles = "USER")
    protected void testUserGetTreeData() throws Exception {
        String json = "{ \"userName\": \"userhelmes\",\n" + "\"password\": \"userhelmes\"}";
        this.mockMvc
                .perform(get("/api/tree").header(HttpHeaders.AUTHORIZATION,
                        bearerToken))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "userhelmes", password = "userhelmes", roles = "USER")
    protected void testUserGetPersonalData() throws Exception {
        String json = "{ \"userName\": \"userhelmes\",\n" + "\"password\": \"userhelmes\"}";
        this.mockMvc
                .perform(get("/api/data").header(HttpHeaders.AUTHORIZATION,
                        bearerToken))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "userhelmes", password = "userhelmes", roles = "USER")
    protected void testUpdateDataUserAgreed() throws Exception {
        String json = "\n" +
                "{ \"name\" : \"bba\", \n" +
                "  \"selectedSectors\" : [\"65\",\"66\",\"67\"],\n" +
                "  \"agreedToTerms\": \"true\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/update")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }
    @Test
    @WithMockUser(username = "userhelmes", password = "userhelmes", roles = "USER")
    protected void testUpdateDataUserDisagreed() throws Exception {
        String json = "\n" +
                "{ \"name\" : \"bba\", \n" +
                "  \"selectedSectors\" : [\"65\",\"66\",\"67\"],\n" +
                "  \"agreedToTerms\": \"false\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/update")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }
    @Test
    @WithMockUser(username = "userhelmes", password = "userhelmes", roles = "USER")
    protected void testUpdateDataNameEmpty() throws Exception {
        String json = "\n" +
                "{ \"name\" : \"\", \n" +
                "  \"selectedSectors\" : [\"65\",\"66\",\"67\"],\n" +
                "  \"agreedToTerms\": \"false\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/update")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }
    @Test
    @WithMockUser(username = "userhelmes", password = "userhelmes", roles = "USER")
    protected void testUpdateDataSelectedSectorsNull() throws Exception {
        String json = "\n" +
                "{ \"name\" : \"\", \n" +
                "  \"selectedSectors\" : null,\n" +
                "  \"agreedToTerms\": \"false\"}";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/update")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is(400)).andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andReturn();
    }
}
