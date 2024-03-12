package com.ispydeer.footballteamdb.domain.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "club")
public class ClubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "club_id_sequence")
    private Long id;

    @Column(nullable = false)
    private String name;
    private String abbreviation;
    private Date foundingDate;
    private Integer totalTrophies;
}
