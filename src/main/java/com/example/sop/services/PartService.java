package com.example.sop.services;

import com.example.sop.services.dtos.PartDTO;

import java.util.List;
import java.util.UUID;


public interface PartService {

    PartDTO createPart(PartDTO partDTO);
    PartDTO updatePart(UUID id, PartDTO partDTO);
    PartDTO getPartById(UUID id);
    List<PartDTO> getAllParts();
    void deletePartById(UUID id);

}