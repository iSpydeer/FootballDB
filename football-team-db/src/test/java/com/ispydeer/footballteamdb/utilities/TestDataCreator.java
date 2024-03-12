package com.ispydeer.footballteamdb.utilities;

import com.ispydeer.footballteamdb.domain.datatypes.Position;
import com.ispydeer.footballteamdb.domain.entities.ClubEntity;
import com.ispydeer.footballteamdb.domain.entities.PlayerEntity;

import java.sql.Date;
import java.time.LocalDate;

public final class TestDataCreator {
    private TestDataCreator(){};

    public static ClubEntity createClubEntityBarca(){
        return  ClubEntity.builder()
                .name("FC Barcelona")
                .abbreviation("BAR")
                .foundingDate(Date.valueOf("1899-11-29"))
                .totalTrophies(100)
                .build();
    }

    public static ClubEntity createClubEntityRealMadrid(){
        return  ClubEntity.builder()
                .name("Real Madrid")
                .abbreviation("RM")
                .foundingDate(Date.valueOf("1902-02-06"))
                .totalTrophies(99)
                .build();
    }

    public static PlayerEntity createPlayerEntityLM(){
        return PlayerEntity.builder()
                .firstName("Lionel")
                .lastName("Messi")
                .birthDate(Date.valueOf("1987-06-24"))
                .position(Position.MIDFIELDER)
                .club(createClubEntityBarca())
                .build();
    }

    public static PlayerEntity createPlayerEntityCR(){
        return PlayerEntity.builder()
                .firstName("Cristiano")
                .lastName("Ronaldo")
                .birthDate(Date.valueOf("1985-02-05"))
                .position(Position.STRIKER)
                .club(createClubEntityRealMadrid())
                .build();
    }


}
