package com.ispydeer.footballteamdb.repositories;

import com.ispydeer.footballteamdb.domain.entities.ClubEntity;
import com.ispydeer.footballteamdb.utilities.TestDataCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
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

    @Test
    public void testThatMultipleClubsCanBeRecalled(){
        ClubEntity clubEntityBarca = TestDataCreator.createClubEntityBarca();
        underTest.save(clubEntityBarca);
        ClubEntity clubEntityRealMadrid = TestDataCreator.createClubEntityRealMadrid();
        underTest.save(clubEntityRealMadrid);

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<ClubEntity> result = underTest.findAll(pageRequest).stream().toList();

        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(clubEntityBarca, clubEntityRealMadrid);
    }

    @Test
    public void testThatClubCanBeUpdated(){
        ClubEntity clubEntityBarca = TestDataCreator.createClubEntityBarca();
        underTest.save(clubEntityBarca);

        clubEntityBarca.setName("Update");
        underTest.save(clubEntityBarca);

        Optional<ClubEntity> result = underTest.findById(clubEntityBarca.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(clubEntityBarca);
    }

    @Test
    public void testThatClubCanBeDeleted(){
        ClubEntity clubEntityBarca = TestDataCreator.createClubEntityBarca();
        underTest.save(clubEntityBarca);
        underTest.deleteById(clubEntityBarca.getId());

        Optional<ClubEntity> result = underTest.findById(clubEntityBarca.getId());
        assertThat(result).isEmpty();
    }

}
