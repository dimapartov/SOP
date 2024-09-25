package com.example.sop.services.interfaces;

import com.example.sop.services.dtos.PartDTO;

import java.util.List;
import java.util.UUID;


public interface PartService {

    PartDTO createPart(PartDTO partDTO);
    PartDTO changeQuantityOnStorage(UUID id, int newQuantityOnStorage);
    PartDTO getPartById(UUID id);
    List<PartDTO> getAllParts();
    void deletePartById(UUID id);

}