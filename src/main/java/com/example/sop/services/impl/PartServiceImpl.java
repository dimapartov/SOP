package com.example.sop.services.impl;

import com.example.sop.models.Employee;
import com.example.sop.models.Part;
import com.example.sop.repositories.PartRepository;
import com.example.sop.services.PartService;
import com.example.sop.services.dtos.EmployeeDTO;
import com.example.sop.services.dtos.PartDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


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
    public PartDTO updatePart(PartDTO partDTO) {
        Optional<Part> targetPart = partRepository.findById(partDTO.getId());
        if (targetPart.isEmpty()) {
            throw new RuntimeException("Part not found");
        }
        targetPart.get().setName(partDTO.getName());
        targetPart.get().setQuantityOnStorage(partDTO.getQuantityOnStorage());
        targetPart.get().setPrice(partDTO.getPrice());
        partRepository.saveAndFlush(targetPart.get());
        return modelMapper.map(targetPart, PartDTO.class);
    }

    @Override
    public PartDTO getPartById(UUID id) {
        Optional<Part> requestedPart = partRepository.findById(id);
        if (requestedPart.isEmpty()) {
            throw new RuntimeException("Part not found");
        }
        return modelMapper.map(requestedPart, PartDTO.class);
    }

    @Override
    public List<PartDTO> getAllParts() {
        List<Part> allParts = partRepository.findAll();
        return allParts.stream()
                .map(part -> modelMapper.map(part, PartDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deletePartById(UUID id) {
        partRepository.deleteById(id);
    }

}