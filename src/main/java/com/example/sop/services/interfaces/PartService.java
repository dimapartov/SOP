package com.example.sop.services.interfaces;

import com.example.sop.services.dtos.PartDTO;

import java.util.List;
import java.util.UUID;


public interface PartService {

    PartDTO createPart(PartDTO partDTO);

    PartDTO changeQuantityOnStorage(UUID partId, int newQuantityOnStorage);

    PartDTO getPartById(UUID partId);

    List<PartDTO> getAllParts();

    void deletePartById(UUID partId);

}