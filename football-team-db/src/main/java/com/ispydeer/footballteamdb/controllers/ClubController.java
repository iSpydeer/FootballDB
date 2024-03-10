package com.ispydeer.footballteamdb.controllers;

import com.ispydeer.footballteamdb.domain.dto.ClubDto;
import com.ispydeer.footballteamdb.domain.entities.ClubEntity;
import com.ispydeer.footballteamdb.mappers.impl.ClubMapper;
import com.ispydeer.footballteamdb.services.ClubService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Optional;

@RestController
public class ClubController{

    private ClubMapper clubMapper;
    private ClubService clubService;

    public ClubController(ClubMapper clubMapper, ClubService clubService) {
        this.clubMapper = clubMapper;
        this.clubService = clubService;
    }

    @PostMapping(path = "/clubs")
    public ResponseEntity<ClubDto>  createClub(@RequestBody ClubDto clubDto){
        ClubEntity clubEntity = clubMapper.mapFrom(clubDto);
        ClubEntity savedEntity = clubService.save(clubEntity);
        return new ResponseEntity<>(clubMapper.mapTo(savedEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/clubs/{id}")
    public ResponseEntity<ClubDto> getClub(@PathVariable("id") Long id){
        Optional<ClubEntity> clubEntity = clubService.find(id);
        return clubEntity.map(foundClub -> {
            ClubDto clubDto = clubMapper.mapTo(foundClub);
            return new ResponseEntity<>(clubDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
