package com.concours.Model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.concours.Controller.CandidatController;
import com.concours.Repository.CandidatInfoRepository;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class CandidatControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CandidatInfoRepository candidatRepository;

    @InjectMocks
    private CandidatController candidatController;

    @Test
    public void testSaveCandidatWithSession() throws Exception {
        // Create a MockHttpSession with a session attribute
        MockHttpSession session = new MockHttpSession();
        session.setAttribute("id", 123L);

        // Create a CandidatInfo object
        //CandidatInfo candidatInfo = new CandidatInfo();
        //candidatInfo.setSex("Doe");
        //candidatInfo.setAdress("John");

        // Mock the CandidatRepository save method to return the input CandidatInfo object
        //when(candidatRepository.save(any(CandidatInfo.class))).thenReturn(candidatInfo);

        // Build the MockMvc object
        mockMvc = MockMvcBuilders.standaloneSetup(candidatController).build();

        // Perform a POST request to the /candidat endpoint with the CandidatInfo object and the session attribute
        mockMvc.perform(MockMvcRequestBuilders.post("/candidat")
                .contentType("application/json")
                .content("{ \"sex\": \"Doe\", \"adress\": \"John\" }")
                .session(session))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(123L));
    }
}

