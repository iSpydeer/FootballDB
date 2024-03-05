package com.ispydeer.footballteamdb.repositories;

import com.ispydeer.footballteamdb.domain.entities.ClubEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClubRepository extends CrudRepository<ClubEntity, Long> {
}
