package com.ispydeer.footballteamdb.services;

import com.ispydeer.footballteamdb.domain.entities.ClubEntity;

import java.util.Optional;

public interface ClubService {

    public ClubEntity save(ClubEntity clubEntity);

    public Optional<ClubEntity> find(Long id);
}
