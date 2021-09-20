package cz.istep.javatest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.istep.javatest.data.Hype;
import cz.istep.javatest.data.JavaScriptFramework;
import cz.istep.javatest.repository.JavaScriptFrameworkRepository;
import cz.istep.javatest.rest.FrameworkNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JavaScriptFrameworkControllerTest {

    Logger logger = LogManager.getLogger(JavaScriptFrameworkControllerTest.class);

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private JavaScriptFrameworkRepository frameworkRepository;

    @Before
    public void setUp() throws Exception {

        logger.info("Settings up!");

        JavaScriptFramework jsFramework1 = new JavaScriptFramework(1L, "React", "1.1b");
        JavaScriptFramework jsFramework2 = new JavaScriptFramework(2L, "VueJS", "12");
        JavaScriptFramework jsFramework3 = new JavaScriptFramework(3L, "Angular", "13");

        frameworkRepository.save(jsFramework1);
        frameworkRepository.save(jsFramework2);
        frameworkRepository.save(jsFramework3);

        logger.info("After set-up: " + frameworkRepository.findAll().toString());

    }

    @After
    public void tearDown() {

        logger.info("Tearing down!");

        frameworkRepository.deleteAll();

        logger.info("After tear-down: " + frameworkRepository.findAll().toString());
    }

    @Test
    public void frameworks() throws Exception {

        logger.info("frameworks() Test");

        mockMvc.perform(MockMvcRequestBuilders
                .get("/frameworks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[1].name", is("VueJS")));

    }

    @Test
    public void createFramework() throws Exception{

        logger.info("createframework(ZdenalJS) Test");

        JavaScriptFramework jsFramework = new JavaScriptFramework(4L, "ZdenalJS", "0.1 beta", new Hype(0, 99));

        mockMvc.perform(MockMvcRequestBuilders
                .post("/createFramework")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(jsFramework)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("ZdenalJS")));

    }

    @Test
    public void createFrameworkInvalid() throws Exception{

        JavaScriptFramework jsFramework = new JavaScriptFramework();

        // Blank name and version
        logger.info("createFrameworkInvalid() blank name and version Test");
//        mockMvc.perform(post("/createFramework")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(mapper.writeValueAsString(jsFramework)))
//                .andExpect(status().isBadRequest())
//                .andExpect(jsonPath("$.errors", hasSize(2)))
//                .andExpect(jsonPath("$.errors[0].field", is("version")))
//                .andExpect(jsonPath("$.errors[0].message", is("NotBlank")))
//                .andExpect(jsonPath("$.errors[1].field", is("name")))
//                .andExpect(jsonPath("$.errors[1].message", is("NotBlank")));

        // Blank version
        logger.info("createFrameworkInvalid() blank version Test");
        jsFramework.setName("BugJS");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/createFramework")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(jsFramework)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("version")))
                .andExpect(jsonPath("$.errors[0].message", is("NotBlank")));

        // Invalid version
        logger.info("createFrameworkInvalid() invalid version Test");
        jsFramework.setVersion("0.123456789 alpha");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/createFramework")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(jsFramework)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("version")))
                .andExpect(jsonPath("$.errors[0].message", is("Size")));

        // Blank name
        logger.info("createFrameworkInvalid() blank name Test");
        jsFramework.setName(null);
        jsFramework.setVersion("0");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/createFramework")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(jsFramework)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is("NotBlank")));

        // Invalid name
        logger.info("createFrameworkInvalid() invalid name Test");
        jsFramework.setName("CplusplusGoodJavaBetterPythonBestCplusplusGoodJavaBetterPythonBest");
        jsFramework.setVersion("0");
        mockMvc.perform(MockMvcRequestBuilders
                .post("/createFramework")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(jsFramework)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors[0].field", is("name")))
                .andExpect(jsonPath("$.errors[0].message", is("Size")));


    }

    @Test
    public void editFramework() throws Exception {

        logger.info("editFramework() Test");
        JavaScriptFramework jsFramework = new JavaScriptFramework(16L, "CoolJS", "0.9 beta");

        mockMvc.perform(MockMvcRequestBuilders
                .put("/editFramework/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(jsFramework)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("CoolJS")))
                .andExpect(jsonPath("$.version", is("0.9 beta")));


    }

    @Test
    public void deleteFramework() throws Exception {

        logger.info("deleteFramework() Test");

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/deleteFramework/13"))
                .andExpect(status().isOk());

    }

    @Test
    public void deleteFrameworkInvalid() throws Exception {

        logger.info("deleteFrameworkInvalid() Test");

        mockMvc.perform(MockMvcRequestBuilders
                .delete("/deleteFramework/4"))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof FrameworkNotFoundException))
                .andExpect(result -> assertEquals("Could not find framework 4", result.getResolvedException().getMessage()));

    }
}