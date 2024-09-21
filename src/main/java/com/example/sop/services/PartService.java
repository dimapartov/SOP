package com.example.sop.services;

import com.example.sop.services.dtos.EmployeeDTO;
import com.example.sop.services.dtos.PartDTO;

import java.util.List;
import java.util.UUID;


public interface PartService {

    PartDTO createPart(PartDTO part);
    PartDTO updatePart(PartDTO part);
    PartDTO getPartById(UUID id);
    List<PartDTO> getAllParts();
    void deletePartById(UUID id);

}