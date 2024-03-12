package com.ispydeer.footballteamdb.repositories;

import com.ispydeer.footballteamdb.domain.entities.ClubEntity;
import com.ispydeer.footballteamdb.domain.entities.PlayerEntity;
import com.ispydeer.footballteamdb.utilities.TestDataCreator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class PlayerRepositoryIntegrationTests {

    private PlayerRepository underTest;

    @Autowired
    public PlayerRepositoryIntegrationTests(PlayerRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatPlayerCanBeCreatedAndRecalled(){
        PlayerEntity playerEntity = TestDataCreator.createPlayerEntityLM();
        underTest.save(playerEntity);

        Optional<PlayerEntity> result = underTest.findById(playerEntity.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(playerEntity);

    }

    @Test
    public void testThatMultiplePlayersCanBeRecalled(){
        PlayerEntity playerEntityLM = TestDataCreator.createPlayerEntityLM();
        underTest.save(playerEntityLM);
        PlayerEntity playerEntityCR = TestDataCreator.createPlayerEntityCR();
        underTest.save(playerEntityCR);

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<PlayerEntity> result = underTest.findAll(pageRequest).stream().toList();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(playerEntityLM, playerEntityCR);
    }

    @Test
    public void testThatClubCanBeUpdated(){
        PlayerEntity playerEntityLM = TestDataCreator.createPlayerEntityLM();
        underTest.save(playerEntityLM);

        playerEntityLM.setFirstName("Update");
        underTest.save(playerEntityLM);

        Optional<PlayerEntity> result = underTest.findById(playerEntityLM.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(playerEntityLM);
    }

    @Test
    public void testThatClubCanBeDeleted(){
        PlayerEntity playerEntityLM = TestDataCreator.createPlayerEntityLM();
        underTest.save(playerEntityLM);
        underTest.deleteById(playerEntityLM.getId());

        Optional<PlayerEntity> result = underTest.findById(playerEntityLM.getId());
        assertThat(result).isEmpty();
    }

}
