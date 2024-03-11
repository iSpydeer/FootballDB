package com.ispydeer.footballteamdb.services.impl;

import com.ispydeer.footballteamdb.domain.entities.ClubEntity;
import com.ispydeer.footballteamdb.repositories.ClubRepository;
import com.ispydeer.footballteamdb.services.ClubService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Page<ClubEntity> findAll(Pageable pageable) {
        return clubRepository.findAll(pageable);
    }

    @Override
    public boolean exists(Long id) {
       return clubRepository.existsById(id);
    }

    @Override
    public ClubEntity partialUpdate(Long id, ClubEntity clubEntity) {
        clubEntity.setId(id);
        return clubRepository.findById(id).map(existingClub -> {
            Optional.ofNullable(clubEntity.getName()).ifPresent(existingClub::setName);
            Optional.ofNullable(clubEntity.getAbbreviation()).ifPresent(existingClub::setAbbreviation);
            Optional.ofNullable(clubEntity.getFoundingDate()).ifPresent(existingClub::setFoundingDate);
            Optional.ofNullable(clubEntity.getTotalTrophies()).ifPresent(existingClub::setTotalTrophies);
            return clubRepository.save(existingClub);
        }).orElseThrow(RuntimeException::new);

    }

    @Override
    public void delete(Long id) {
        clubRepository.deleteById(id);
    }
}
