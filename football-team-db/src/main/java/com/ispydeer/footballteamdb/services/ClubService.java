package com.ispydeer.footballteamdb.services;

import com.ispydeer.footballteamdb.domain.entities.ClubEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ClubService {

    ClubEntity save(ClubEntity clubEntity);

    Optional<ClubEntity> find(Long id);

    Page<ClubEntity> findAll(Pageable pageable);

    boolean exists(Long id);

    ClubEntity partialUpdate(Long id, ClubEntity clubEntity);

    void delete(Long id);


}
