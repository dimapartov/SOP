package com.example.sop.services.impl;

import com.example.sop.models.Part;
import com.example.sop.repositories.PartRepository;
import com.example.sop.services.dtos.PartDTO;
import com.example.sopcontracts.exceptions.PartNotFoundException;
import com.example.sop.services.interfaces.PartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class PartServiceImpl implements PartService {

    private ModelMapper modelMapper;
    private PartRepository partRepository;


    @Autowired
    public PartServiceImpl(ModelMapper modelMapper, PartRepository partRepository) {
        this.modelMapper = modelMapper;
        this.partRepository = partRepository;
    }


    @Override
    public PartDTO createPart(PartDTO partDTO) {
        Part part = modelMapper.map(partDTO, Part.class);
        partRepository.saveAndFlush(part);
        return modelMapper.map(part, PartDTO.class);
    }

    @Override
    public PartDTO changeQuantityOnStorage(UUID partId, int newQuantityOnStorage) {
        Optional<Part> targetPart = partRepository.findById(partId);
        if (targetPart.isEmpty()) {
            throw new PartNotFoundException(partId);
        }
        targetPart.get().setQuantityOnStorage(newQuantityOnStorage);
        partRepository.saveAndFlush(targetPart.get());
        return modelMapper.map(targetPart, PartDTO.class);
    }

    @Override
    public PartDTO getPartById(UUID partId) {
        Optional<Part> requestedPart = partRepository.findById(partId);
        if (requestedPart.isEmpty()) {
            throw new PartNotFoundException(partId);
        }
        return modelMapper.map(requestedPart, PartDTO.class);
    }

    @Override
    public List<PartDTO> getAllParts() {
        List<Part> allParts = partRepository.findAll();
        return allParts.stream().map(part -> modelMapper.map(part, PartDTO.class)).toList();
    }

    @Override
    public void deletePartById(UUID partId) {
        Optional<Part> targetPart = partRepository.findById(partId);
        if (targetPart.isEmpty()) {
            throw new PartNotFoundException(partId);
        }
        partRepository.deleteById(partId);
    }

}