package com.ispydeer.footballteamdb.repositories;

import com.ispydeer.footballteamdb.domain.entities.ClubEntity;
import com.ispydeer.footballteamdb.utilities.TestDataCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ClubRepositoryIntegrationTest {
    private ClubRepository underTest;

    @Autowired
    public ClubRepositoryIntegrationTest(ClubRepository underTest) {
        this.underTest = underTest;
    }


    @Test
    public void testThatClubCanBeCreatedAndRecalled(){
        ClubEntity clubEntityBarca = TestDataCreator.createClubEntityBarca();
        underTest.save(clubEntityBarca);

        Optional<ClubEntity> result = underTest.findById(clubEntityBarca.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(clubEntityBarca);

    }
}
