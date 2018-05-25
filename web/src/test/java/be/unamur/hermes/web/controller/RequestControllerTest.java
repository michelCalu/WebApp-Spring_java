package be.unamur.hermes.web.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import be.unamur.hermes.business.service.RequestService;
import be.unamur.hermes.dataaccess.entity.Request;
import be.unamur.hermes.web.WebApplication;

@RunWith(SpringRunner.class)
// @WebMvcTest(RequestController.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = WebApplication.class)
public class RequestControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private RequestService service;

    @Test
    @WithMockUser("00000000000_empl")
    public void test_getRequests_withDepartmentId() throws Exception {
	Mockito.when(service.findByDepartmentId(5L)).thenReturn(Collections.singletonList(new Request(1L)));
	mvc.perform(get("/requests?departmentId=5"). //
		contentType(MediaType.APPLICATION_JSON)). //
		andExpect(status().isOk()).//
		andExpect(jsonPath("$", hasSize(1)));
    }

}
