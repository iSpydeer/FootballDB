package com.ispydeer.footballteamdb.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispydeer.footballteamdb.domain.entities.ClubEntity;
import com.ispydeer.footballteamdb.services.ClubService;
import com.ispydeer.footballteamdb.utilities.TestDataCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ClubControllerIntegrationTests {

    private ClubService clubService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;


    @Autowired
    public ClubControllerIntegrationTests( MockMvc mockMvc, ClubService clubService) {
        this.clubService = clubService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreateClubSuccessfullyReturnsHttpStatus201AndSavedClub() throws Exception{
        ClubEntity clubEntityBarca = TestDataCreator.createClubEntityBarca();

        String json = objectMapper.writeValueAsString(clubEntityBarca);
        mockMvc.perform(
                MockMvcRequestBuilders.post("/clubs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("FC Barcelona"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.abbreviation").value("BAR"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.foundingDate").value("1899-11-29"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalTrophies").value(100));
    }

    @Test
    public void testThatGetClubSuccessfullyReturnsHttpStatus200AndFoundClub() throws Exception{
        ClubEntity clubEntityBarca = TestDataCreator.createClubEntityBarca();
        clubService.save(clubEntityBarca);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/clubs/1")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("FC Barcelona"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.abbreviation").value("BAR"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.foundingDate").value("1899-11-29"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalTrophies").value(100));
    }

    @Test
    public void testThatGetClubUnsuccessfullyReturnsHttpStatus404() throws Exception{
        ClubEntity clubEntityBarca = TestDataCreator.createClubEntityBarca();
        clubService.save(clubEntityBarca);

        final int ID_OF_NOT_EXISTING_CLUB = 99;
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/clubs/" + ID_OF_NOT_EXISTING_CLUB)
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatListsClubsReturnsHttpStatus200AndListOfClubs() throws Exception{
        ClubEntity clubEntityBarca = TestDataCreator.createClubEntityBarca();
        clubService.save(clubEntityBarca);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/clubs")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value("FC Barcelona"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].abbreviation").value("BAR"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].foundingDate").value("1899-11-29"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].totalTrophies").value(100));
    }

    @Test
    public void testThatFullUpdateClubReturnsHttpStatus200WhenClubAlreadyExistsAndUpdatedClub() throws Exception {
        ClubEntity clubEntityBarca = TestDataCreator.createClubEntityBarca();
        clubService.save(clubEntityBarca);

        ClubEntity clubEntityRealMadrid = TestDataCreator.createClubEntityRealMadrid();
        String json = objectMapper.writeValueAsString(clubEntityRealMadrid);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/clubs/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Real Madrid"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.abbreviation").value("RM"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.foundingDate").value("1902-02-06"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalTrophies").value(99));

    }

    @Test
    public void testThatFullUpdateClubReturnsHttpStatus404WhenClubDoesntExist() throws Exception {
        final int ID_OF_NOT_EXISTING_CLUB = 99;

        ClubEntity clubEntityBarca = TestDataCreator.createClubEntityBarca();
        clubService.save(clubEntityBarca);

        ClubEntity clubEntityRealMadrid = TestDataCreator.createClubEntityRealMadrid();
        String json = objectMapper.writeValueAsString(clubEntityRealMadrid);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/clubs/"+ID_OF_NOT_EXISTING_CLUB)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatPartialUpdateClubReturnsHttpStatus200WhenClubAlreadyExistsAndUpdatedClub() throws Exception {
        ClubEntity clubEntityBarca = TestDataCreator.createClubEntityBarca();
        clubService.save(clubEntityBarca);

        clubEntityBarca.setName("Updated");
        String json = objectMapper.writeValueAsString(clubEntityBarca);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/clubs/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.abbreviation").value("BAR"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.foundingDate").value("1899-11-29"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalTrophies").value(100));

    }

    @Test
    public void testThatPartialUpdateClubReturnsHttpStatus404WhenClubDoesntExist() throws Exception {
        final int ID_OF_NOT_EXISTING_CLUB = 99;

        ClubEntity clubEntityBarca = TestDataCreator.createClubEntityBarca();
        clubService.save(clubEntityBarca);

        clubEntityBarca.setName("Updated");
        String json = objectMapper.writeValueAsString(clubEntityBarca);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/clubs/"+ID_OF_NOT_EXISTING_CLUB)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatDeleteClubReturnsStatus204ForExistingClub() throws Exception{
        ClubEntity clubEntityBarca = TestDataCreator.createClubEntityBarca();
        clubService.save(clubEntityBarca);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/clubs/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeleteClubReturnsStatus204ForNonExistingClub() throws Exception{
        final int ID_OF_NOT_EXISTING_CLUB = 99;

        ClubEntity clubEntityBarca = TestDataCreator.createClubEntityBarca();
        clubService.save(clubEntityBarca);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/clubs/" + ID_OF_NOT_EXISTING_CLUB)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
