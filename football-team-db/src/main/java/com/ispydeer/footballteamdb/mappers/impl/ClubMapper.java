package com.ispydeer.footballteamdb.mappers.impl;

import com.ispydeer.footballteamdb.domain.dto.ClubDto;
import com.ispydeer.footballteamdb.domain.entities.ClubEntity;
import com.ispydeer.footballteamdb.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClubMapper implements Mapper<ClubEntity, ClubDto> {
    private ModelMapper modelMapper;

    public ClubMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public ClubDto mapTo(ClubEntity clubEntity) {
        return modelMapper.map(clubEntity, ClubDto.class);
    }

    @Override
    public ClubEntity mapFrom(ClubDto clubDto) {
        return modelMapper.map(clubDto, ClubEntity.class);
    }
}
