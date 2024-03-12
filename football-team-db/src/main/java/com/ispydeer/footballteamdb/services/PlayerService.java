package com.ispydeer.footballteamdb.services;

import com.ispydeer.footballteamdb.domain.entities.ClubEntity;
import com.ispydeer.footballteamdb.domain.entities.PlayerEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PlayerService {

    PlayerEntity save(PlayerEntity playerEntity);

    Optional<PlayerEntity> find(Long id);

    Page<PlayerEntity> findAll(Pageable pageable);

    boolean exists(Long id);

    PlayerEntity partialUpdate(Long id, PlayerEntity playerEntity);

    void delete(Long id);

}
