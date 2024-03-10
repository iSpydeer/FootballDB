package com.ispydeer.footballteamdb.services.impl;

import com.ispydeer.footballteamdb.domain.entities.ClubEntity;
import com.ispydeer.footballteamdb.repositories.ClubRepository;
import com.ispydeer.footballteamdb.services.ClubService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClubServiceImpl implements ClubService {

    private ClubRepository clubRepository;

    public ClubServiceImpl(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    @Override
    public ClubEntity save(ClubEntity clubEntity) {
        return clubRepository.save(clubEntity);
    }

    @Override
    public Optional<ClubEntity> find(Long id) {
        return clubRepository.findById(id);
    }
}
