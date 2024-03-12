package com.ispydeer.footballteamdb.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispydeer.footballteamdb.domain.datatypes.Position;
import com.ispydeer.footballteamdb.domain.entities.ClubEntity;
import com.ispydeer.footballteamdb.domain.entities.PlayerEntity;
import com.ispydeer.footballteamdb.services.ClubService;
import com.ispydeer.footballteamdb.services.PlayerService;
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
public class PlayerControllerIntegrationTests {

    private PlayerService playerService;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public PlayerControllerIntegrationTests(PlayerService playerService, MockMvc mockMvc) {
        this.playerService = playerService;
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCreatePlayerSuccessfullyReturnsHttpStatus201AndSavedPlayer() throws Exception{
        PlayerEntity playerEntityLM = TestDataCreator.createPlayerEntityLM();
        ClubEntity club = playerEntityLM.getClub();

        String json = objectMapper.writeValueAsString(playerEntityLM);
        System.out.println(json);
        mockMvc.perform(
                        MockMvcRequestBuilders.post("/players")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Lionel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Messi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value(Position.MIDFIELDER.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate").value("1987-06-24"));
    }

    @Test
    public void testThatGetPlayerSuccessfullyReturnsHttpStatus200AndFoundClub() throws Exception{
        PlayerEntity playerEntityLM = TestDataCreator.createPlayerEntityLM();
        playerService.save(playerEntityLM);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/players/1")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Lionel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Messi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value(Position.MIDFIELDER.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate").value("1987-06-24"));
    }

    @Test
    public void testThatGetPlayerUnsuccessfullyReturnsHttpStatus404() throws Exception{
        PlayerEntity playerEntityLM = TestDataCreator.createPlayerEntityLM();
        playerService.save(playerEntityLM);

        final int ID_OF_NOT_EXISTING_PLAYER = 99;
        mockMvc.perform(
                MockMvcRequestBuilders.get("/players/"+ID_OF_NOT_EXISTING_PLAYER)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatListsPlayersReturnsHttpStatus200AndListOfPlayers() throws Exception{
        PlayerEntity playerEntityLM = TestDataCreator.createPlayerEntityLM();
        playerService.save(playerEntityLM);

        mockMvc.perform(
                        MockMvcRequestBuilders.get("/players")
                                .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].firstName").value("Lionel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].lastName").value("Messi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].position").value(Position.MIDFIELDER.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].birthDate").value("1987-06-24"));
    }

    @Test
    public void testThatFullUpdatePlayerReturnsHttpStatus200WhenPlayerAlreadyExistsAndUpdatedPlayer() throws Exception {
        PlayerEntity playerEntityCR = TestDataCreator.createPlayerEntityCR();
        playerService.save(playerEntityCR);

        PlayerEntity playerEntityLM = TestDataCreator.createPlayerEntityLM();
        String json = objectMapper.writeValueAsString(playerEntityLM);

        mockMvc.perform(
                        MockMvcRequestBuilders.put("/players/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Lionel"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Messi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value(Position.MIDFIELDER.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate").value("1987-06-24"));

    }

    @Test
    public void testThatFullUpdatePlayerReturnsHttpStatus404WhenPlayerDoesntExist() throws Exception {
        final int ID_OF_NOT_EXISTING_PLAYER = 99;

        PlayerEntity playerEntityCR = TestDataCreator.createPlayerEntityCR();
        playerService.save(playerEntityCR);

        PlayerEntity playerEntityLM = TestDataCreator.createPlayerEntityLM();
        String json = objectMapper.writeValueAsString(playerEntityLM);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/players/"+ID_OF_NOT_EXISTING_PLAYER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatPartialUpdatePlayerReturnsHttpStatus200WhenPlayerAlreadyExistsAndUpdatedPlayer() throws Exception {
        PlayerEntity playerEntityLM = TestDataCreator.createPlayerEntityLM();
        playerService.save(playerEntityLM);

        playerEntityLM.setFirstName("Updated");
        String json = objectMapper.writeValueAsString(playerEntityLM);

        mockMvc.perform(
                        MockMvcRequestBuilders.patch("/players/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(json)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Messi"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.position").value(Position.MIDFIELDER.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.birthDate").value("1987-06-24"));

    }

    @Test
    public void testThatPartialUpdatePlayerReturnsHttpStatus404WhenPlayerDoesntExist() throws Exception {
        final int ID_OF_NOT_EXISTING_PLAYER = 99;

        PlayerEntity playerEntityLM = TestDataCreator.createPlayerEntityLM();
        playerService.save(playerEntityLM);

        playerEntityLM.setFirstName("Updated");
        String json = objectMapper.writeValueAsString(playerEntityLM);

        mockMvc.perform(
                MockMvcRequestBuilders.patch("/players/"+ID_OF_NOT_EXISTING_PLAYER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatDeletePlayerReturnsStatus204ForExistingPlayer() throws Exception{
        PlayerEntity playerEntityLM = TestDataCreator.createPlayerEntityLM();
        playerService.save(playerEntityLM);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/players/1")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void testThatDeletePlayerReturnsStatus204ForNonExistingPlayer() throws Exception{
        final int ID_OF_NOT_EXISTING_PLAYER = 99;

        PlayerEntity playerEntityLM = TestDataCreator.createPlayerEntityLM();
        playerService.save(playerEntityLM);

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/players/" + ID_OF_NOT_EXISTING_PLAYER)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
