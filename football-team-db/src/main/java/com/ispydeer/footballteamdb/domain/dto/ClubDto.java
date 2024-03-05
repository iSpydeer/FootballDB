package com.ispydeer.footballteamdb.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClubDto {
    private Integer id;
    private String name;
    private String abbreviation;
    private Date foundingDate;
    private int totalTrophies;
}
