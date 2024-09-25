package com.example.sop.controllers;

import com.example.sop.services.dtos.PartDTO;
import com.example.sop.services.interfaces.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/parts")
public class PartController {

    private PartService partService;


    @Autowired
    public void setPartService(PartService partService) {
        this.partService = partService;
    }


    @PostMapping("/create")
    public ResponseEntity<EntityModel<PartDTO>> createPart(@RequestBody PartDTO partDTO) {
        PartDTO createdPart = partService.createPart(partDTO);

        EntityModel<PartDTO> resource = EntityModel.of(createdPart);

        resource.add(linkTo(methodOn(PartController.class).getPartById(createdPart.getId())).withSelfRel());
        resource.add(linkTo(methodOn(PartController.class).getAllParts()).withRel("allParts"));
        resource.add(linkTo(methodOn(PartController.class).deletePartById(createdPart.getId())).withRel("deletePart"));
        resource.add(linkTo(methodOn(PartController.class).changeQuantityOnStorage(createdPart.getId(), 0)).withRel("changeQuantity"));

        return new ResponseEntity<>(resource, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityModel<PartDTO>> changeQuantityOnStorage(@PathVariable UUID id, @RequestParam int newQuantityOnStorage) {
        PartDTO updatedPart = partService.changeQuantityOnStorage(id, newQuantityOnStorage);

        EntityModel<PartDTO> resource = EntityModel.of(updatedPart);

        resource.add(linkTo(methodOn(PartController.class).getPartById(id)).withSelfRel());
        resource.add(linkTo(methodOn(PartController.class).getAllParts()).withRel("allParts"));
        resource.add(linkTo(methodOn(PartController.class).deletePartById(updatedPart.getId())).withRel("deletePart"));
        resource.add(linkTo(methodOn(PartController.class).changeQuantityOnStorage(updatedPart.getId(), 0)).withRel("changeQuantity"));

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PartDTO>> getPartById(@PathVariable UUID id) {
        PartDTO part = partService.getPartById(id);

        EntityModel<PartDTO> resource = EntityModel.of(part);

        resource.add(linkTo(methodOn(PartController.class).getPartById(id)).withSelfRel());
        resource.add(linkTo(methodOn(PartController.class).getAllParts()).withRel("allParts"));
        resource.add(linkTo(methodOn(PartController.class).deletePartById(id)).withRel("deletePart"));
        resource.add(linkTo(methodOn(PartController.class).changeQuantityOnStorage(part.getId(), 0)).withRel("changeQuantity"));

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<PartDTO>>> getAllParts() {
        List<PartDTO> parts = partService.getAllParts();

        List<EntityModel<PartDTO>> resources = parts.stream()
                .map(part -> EntityModel.of(part,
                        linkTo(methodOn(PartController.class).getPartById(part.getId())).withSelfRel(),
                        linkTo(methodOn(PartController.class).deletePartById(part.getId())).withRel("deletePart"),
                        linkTo(methodOn(PartController.class).changeQuantityOnStorage(part.getId(), 0)).withRel("changeQuantity")))
                .toList();

        CollectionModel<EntityModel<PartDTO>> collectionModel = CollectionModel.of(resources);

        return new ResponseEntity<>(collectionModel, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePartById(@PathVariable UUID id) {
        partService.deletePartById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}