package com.ispydeer.footballteamdb.controllers;

import com.ispydeer.footballteamdb.domain.dto.PlayerDto;
import com.ispydeer.footballteamdb.domain.entities.PlayerEntity;
import com.ispydeer.footballteamdb.mappers.impl.PlayerMapper;
import com.ispydeer.footballteamdb.services.PlayerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.http.HttpResponse;
import java.util.Optional;

@RestController
public class PlayerController {

    private PlayerService playerService;
    private PlayerMapper playerMapper;

    public PlayerController(PlayerService playerService, PlayerMapper playerMapper) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }

    @PostMapping(path = "/players")
    public ResponseEntity<PlayerDto> createPlayer(@RequestBody PlayerDto playerDto){
        PlayerEntity playerEntity = playerMapper.mapFrom(playerDto);
        PlayerEntity savedEntity = playerService.save(playerEntity);
        return new ResponseEntity<>(playerMapper.mapTo(savedEntity), HttpStatus.CREATED);
    }

    @GetMapping(path = "/players/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable("id") Long id){
        Optional<PlayerEntity> playerEntity = playerService.find(id);
        return playerEntity.map(foundPlayer -> {
            PlayerDto playerDto = playerMapper.mapTo(foundPlayer);
            return new ResponseEntity<>(playerDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path = "/players")
    public Page<PlayerDto> getPlayers(Pageable pageable){
        Page<PlayerEntity> players = playerService.findAll(pageable);
        return players.map(playerMapper::mapTo);
    }

    @PutMapping(path = "/players/{id}")
    public ResponseEntity<PlayerDto> fullUpdate(@PathVariable("id") Long id, @RequestBody PlayerDto playerDto){
        if(!playerService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playerDto.setId(id);
        PlayerEntity playerEntity = playerMapper.mapFrom(playerDto);
        PlayerEntity updatedPlayerEntity = playerService.save(playerEntity);
        PlayerDto updatedPlayerDto = playerMapper.mapTo(updatedPlayerEntity);
        return new ResponseEntity<>(updatedPlayerDto, HttpStatus.OK);
    }

    @PatchMapping(path = "/players/{id}")
    public ResponseEntity<PlayerDto> partialUpdate(@PathVariable("id") Long id, @RequestBody PlayerDto playerDto){
        if(!playerService.exists(id)){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        playerDto.setId(id);
        PlayerEntity playerEntity = playerMapper.mapFrom(playerDto);
        PlayerEntity updatedPlayerEntity = playerService.partialUpdate(id, playerEntity);
        PlayerDto updatedPlayerDto = playerMapper.mapTo(updatedPlayerEntity);
        return new ResponseEntity<>(updatedPlayerDto, HttpStatus.OK);
    }

    @DeleteMapping(path="/players/{id}")
    public ResponseEntity<PlayerDto> deletePlayer(@PathVariable("id") Long id){
        playerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
