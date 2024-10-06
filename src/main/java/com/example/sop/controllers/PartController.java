package com.example.sop.controllers;

import com.example.sop.services.dtos.PartDTO;
import com.example.sop.services.interfaces.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

        EntityModel<PartDTO> createdPartEntityModel = EntityModel.of(createdPart);

        createdPartEntityModel.add(linkTo(methodOn(PartController.class).getPartById(createdPart.getId())).withSelfRel());
        createdPartEntityModel.add(linkTo(methodOn(PartController.class).getAllParts()).withRel("allParts"));
        createdPartEntityModel.add(linkTo(methodOn(PartController.class).deletePartById(createdPart.getId())).withRel("deletePart"));
        createdPartEntityModel.add(linkTo(methodOn(PartController.class).changeQuantityOnStorage(createdPart.getId(), 0)).withRel("changeQuantity"));

        return ResponseEntity.created(createdPartEntityModel.getRequiredLink("self").toUri()).body(createdPartEntityModel);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<EntityModel<PartDTO>> changeQuantityOnStorage(@PathVariable UUID id, @RequestParam int newQuantityOnStorage) {
        PartDTO updatedPart = partService.changeQuantityOnStorage(id, newQuantityOnStorage);

        EntityModel<PartDTO> updatedPartEntityModel = EntityModel.of(updatedPart);

        updatedPartEntityModel.add(linkTo(methodOn(PartController.class).getPartById(id)).withSelfRel());
        updatedPartEntityModel.add(linkTo(methodOn(PartController.class).getAllParts()).withRel("allParts"));
        updatedPartEntityModel.add(linkTo(methodOn(PartController.class).deletePartById(updatedPart.getId())).withRel("deletePart"));
        updatedPartEntityModel.add(linkTo(methodOn(PartController.class).changeQuantityOnStorage(updatedPart.getId(), 0)).withRel("changeQuantity"));

        return ResponseEntity.ok(updatedPartEntityModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<PartDTO>> getPartById(@PathVariable UUID id) {
        PartDTO partById = partService.getPartById(id);

        EntityModel<PartDTO> partByIdEntityModel = EntityModel.of(partById);

        partByIdEntityModel.add(linkTo(methodOn(PartController.class).getPartById(id)).withSelfRel());
        partByIdEntityModel.add(linkTo(methodOn(PartController.class).getAllParts()).withRel("allParts"));
        partByIdEntityModel.add(linkTo(methodOn(PartController.class).deletePartById(id)).withRel("deletePart"));
        partByIdEntityModel.add(linkTo(methodOn(PartController.class).changeQuantityOnStorage(partById.getId(), 0)).withRel("changeQuantity"));

        return ResponseEntity.ok(partByIdEntityModel);
    }

    @GetMapping("/all")
    public ResponseEntity<CollectionModel<EntityModel<PartDTO>>> getAllParts() {
        List<EntityModel<PartDTO>> allPartsEntityModels = partService.getAllParts()
                .stream()
                .map(part -> EntityModel.of(part,
                        linkTo(methodOn(PartController.class).getPartById(part.getId())).withSelfRel(),
                        linkTo(methodOn(PartController.class).deletePartById(part.getId())).withRel("deletePart"),
                        linkTo(methodOn(PartController.class).changeQuantityOnStorage(part.getId(), 0)).withRel("changeQuantity")))
                .toList();

        CollectionModel<EntityModel<PartDTO>> allPartsCollectionModel = CollectionModel.of(allPartsEntityModels);

        return ResponseEntity.ok(allPartsCollectionModel);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePartById(@PathVariable UUID id) {
        partService.deletePartById(id);

        return ResponseEntity.noContent().build();
    }

}