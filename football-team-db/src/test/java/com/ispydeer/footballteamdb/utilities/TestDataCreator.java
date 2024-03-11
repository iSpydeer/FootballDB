package com.ispydeer.footballteamdb.utilities;

import com.ispydeer.footballteamdb.domain.entities.ClubEntity;

import java.sql.Date;
import java.time.LocalDate;

public final class TestDataCreator {
    private TestDataCreator(){};

    public static ClubEntity createClubEntityBarca(){
        return  ClubEntity.builder()
                .id(1L)
                .name("FC Barcelona")
                .abbreviation("BAR")
                .foundingDate(Date.valueOf("1989-02-29"))
                .totalTrophies(100)
                .build();
    }

    public static ClubEntity createClubEntityRealMadrid(){
        return  ClubEntity.builder()
                .id(2L)
                .name("Real Madrid")
                .abbreviation("RM")
                .foundingDate(Date.valueOf("1902-02-06"))
                .totalTrophies(99)
                .build();
    }


}
