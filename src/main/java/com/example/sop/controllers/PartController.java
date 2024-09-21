package com.example.sop.controllers;

/*
import com.example.sop.services.PartService;
import com.example.sop.services.dtos.PartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/parts")
public class PartController {

    private PartService partService;

    @Autowired
    public void setPartService(PartService partService) {
        this.partService = partService;
    }

    // Создание новой запчасти
    @PostMapping("/create")
    public ResponseEntity<EntityModel<PartDTO>> createPart(@RequestBody PartDTO partDTO) {
        PartDTO createdPart = partService.createPart(partDTO);
        EntityModel<PartDTO> resource = EntityModel.of(createdPart);
        resource.add(linkTo(methodOn(PartController.class).getPartById(createdPart.getId())).withSelfRel());
        resource.add(linkTo(methodOn(PartController.class).getAllParts()).withRel("all-parts"));
        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    // Обновление существующей запчасти
    @PutMapping("/update/{id}")
    public ResponseEntity<EntityModel<PartDTO>> updatePart(@PathVariable UUID id, @RequestBody PartDTO partDTO) {
        partDTO.setId(id);
        PartDTO updatedPart = partService.updatePart(partDTO.getId(),partDTO);
        EntityModel<PartDTO> resource = EntityModel.of(updatedPart);
        resource.add(linkTo(methodOn(PartController.class).getPartById(id)).withSelfRel());
        resource.add(linkTo(methodOn(PartController.class).getAllParts()).withRel("all-parts"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    // Получение информации о запчасти по ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PartDTO>> getPartById(@PathVariable UUID id) {
        PartDTO part = partService.getPartById(id);
        EntityModel<PartDTO> resource = EntityModel.of(part);
        resource.add(linkTo(methodOn(PartController.class).getPartById(id)).withSelfRel());
        resource.add(linkTo(methodOn(PartController.class).getAllParts()).withRel("all-parts"));
        resource.add(linkTo(methodOn(PartController.class).deletePartById(id)).withRel("delete"));
        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    // Получение всех запчастей
    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<PartDTO>>> getAllParts() {
        List<PartDTO> parts = partService.getAllParts();
        List<EntityModel<PartDTO>> resources = parts.stream()
                .map(part -> EntityModel.of(part,
                        linkTo(methodOn(PartController.class).getPartById(part.getId())).withSelfRel(),
                        linkTo(methodOn(PartController.class).getAllParts()).withRel("all-parts")))
                .toList();

        CollectionModel<EntityModel<PartDTO>> collectionModel = CollectionModel.of(resources);
        collectionModel.add(linkTo(methodOn(PartController.class).getAllParts()).withSelfRel());
        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    // Удаление запчасти по ID
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePartById(@PathVariable UUID id) {
        partService.deletePartById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}*/