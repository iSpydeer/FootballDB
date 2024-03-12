package com.ispydeer.footballteamdb.services.impl;

import com.ispydeer.footballteamdb.domain.entities.PlayerEntity;
import com.ispydeer.footballteamdb.repositories.PlayerRepository;
import com.ispydeer.footballteamdb.services.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerServiceImpl implements PlayerService {

    PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    @Override
    public PlayerEntity save(PlayerEntity playerEntity) {
       return playerRepository.save(playerEntity);
    }

    @Override
    public Optional<PlayerEntity> find(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    public Page<PlayerEntity> findAll(Pageable pageable) {
        return playerRepository.findAll(pageable);
    }

    @Override
    public boolean exists(Long id) {
        return playerRepository.existsById(id);
    }

    @Override
    public PlayerEntity partialUpdate(Long id, PlayerEntity playerEntity) {
        playerEntity.setId(id);
        return playerRepository.findById(id).map(existingEntity -> {
            Optional.ofNullable(playerEntity.getFirstName()).ifPresent(existingEntity::setFirstName);
            Optional.ofNullable(playerEntity.getLastName()).ifPresent(existingEntity::setLastName);
            Optional.ofNullable(playerEntity.getBirthDate()).ifPresent(existingEntity::setBirthDate);
            Optional.ofNullable(playerEntity.getPosition()).ifPresent(existingEntity::setPosition);
            Optional.ofNullable(playerEntity.getClub()).ifPresent(existingEntity::setClub);
            return playerRepository.save(existingEntity);
        }).orElseThrow(RuntimeException::new);
    }

    @Override
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }
}
