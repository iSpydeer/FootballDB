package com.ispydeer.footballteamdb.utilities;

import com.ispydeer.footballteamdb.domain.entities.ClubEntity;

import java.sql.Date;
import java.time.LocalDate;

public final class TestDataCreator {
    private TestDataCreator(){};

    public static ClubEntity createClubEntityBarca(){
        return  ClubEntity.builder()
                .name("FC Barcelona")
                .abbreviation("BAR")
                .foundingDate(Date.valueOf(LocalDate.of(1898,10,29)))
                .totalTrophies(100)
                .build();
    }

    public static ClubEntity createClubEntityRealMadrid(){
        return  ClubEntity.builder()
                .name("Real Madrid")
                .abbreviation("RM")
                .foundingDate(Date.valueOf(LocalDate.of(1902,2,6)))
                .totalTrophies(99)
                .build();
    }


}
