package com.example.sop.controllers;

import com.example.sop.services.dtos.PartDTO;
import com.example.sop.services.interfaces.PartService;
import com.example.sopcontracts.controllers.PartApi;
import com.example.sopcontracts.dtos.PartRequest;
import com.example.sopcontracts.dtos.PartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
public class PartController implements PartApi {

    private PartService partService;

    @Autowired
    public void setPartService(PartService partService) {
        this.partService = partService;
    }

    private PartDTO mapToPartDTO(PartRequest partRequest) {
        return new PartDTO(partRequest.name(), partRequest.quantityOnStorage(), partRequest.price());
    }

    private PartResponse mapToPartResponse(PartDTO partDTO) {
        return new PartResponse(partDTO.getId(), partDTO.getName(), partDTO.getQuantityOnStorage(), partDTO.getPrice());
    }


    @Override
    public ResponseEntity<EntityModel<PartResponse>> createPart(PartRequest partRequest) {
        PartDTO createdPart = partService.createPart(mapToPartDTO(partRequest));

        PartResponse partResponse = mapToPartResponse(createdPart);
        EntityModel<PartResponse> createdPartEntity = EntityModel.of(partResponse);
        createdPartEntity.add(linkTo(methodOn(PartController.class).getPartById(partResponse.id())).withSelfRel());
        createdPartEntity.add(linkTo(methodOn(PartController.class).getAllParts()).withRel("allParts"));
        createdPartEntity.add(linkTo(methodOn(PartController.class).deletePartById(partResponse.id())).withRel("deletePart"));
        createdPartEntity.add(linkTo(methodOn(PartController.class).changeQuantityOnStorage(partResponse.id(), 0)).withRel("changeQuantity"));

        return ResponseEntity.created(createdPartEntity.getRequiredLink("self").toUri()).body(createdPartEntity);
    }

    @Override
    public ResponseEntity<EntityModel<PartResponse>> changeQuantityOnStorage(UUID partId, int newQuantityOnStorage) {
        PartDTO updatedPart = partService.changeQuantityOnStorage(partId, newQuantityOnStorage);

        PartResponse partResponse = mapToPartResponse(updatedPart);
        EntityModel<PartResponse> updatedPartEntity = EntityModel.of(partResponse);
        updatedPartEntity.add(linkTo(methodOn(PartController.class).getPartById(partId)).withSelfRel());
        updatedPartEntity.add(linkTo(methodOn(PartController.class).getAllParts()).withRel("allParts"));
        updatedPartEntity.add(linkTo(methodOn(PartController.class).deletePartById(partResponse.id())).withRel("deletePart"));
        updatedPartEntity.add(linkTo(methodOn(PartController.class).changeQuantityOnStorage(partResponse.id(), 0)).withRel("changeQuantity"));

        return ResponseEntity.ok(updatedPartEntity);
    }

    @Override
    public ResponseEntity<EntityModel<PartResponse>> getPartById(UUID partId) {
        PartDTO partDTO = partService.getPartById(partId);

        PartResponse partResponse = mapToPartResponse(partDTO);
        EntityModel<PartResponse> partResponseEntity = EntityModel.of(partResponse);
        partResponseEntity.add(linkTo(methodOn(PartController.class).getPartById(partId)).withSelfRel());
        partResponseEntity.add(linkTo(methodOn(PartController.class).getAllParts()).withRel("allParts"));
        partResponseEntity.add(linkTo(methodOn(PartController.class).deletePartById(partId)).withRel("deletePart"));
        partResponseEntity.add(linkTo(methodOn(PartController.class).changeQuantityOnStorage(partId, 0)).withRel("changeQuantity"));

        return ResponseEntity.ok(partResponseEntity);
    }

    @Override
    public ResponseEntity<CollectionModel<EntityModel<PartResponse>>> getAllParts() {
        List<EntityModel<PartResponse>> partResponseCollection = partService.getAllParts().stream()
                .map(partDTO -> {
                    PartResponse partResponse = mapToPartResponse(partDTO);
                    return EntityModel.of(partResponse,
                            linkTo(methodOn(PartController.class).getPartById(partResponse.id())).withSelfRel(),
                            linkTo(methodOn(PartController.class).deletePartById(partResponse.id())).withRel("deletePart"),
                            linkTo(methodOn(PartController.class).changeQuantityOnStorage(partResponse.id(), 0)).withRel("changeQuantity"));
                }).toList();

        CollectionModel<EntityModel<PartResponse>> partsCollectionModel = CollectionModel.of(partResponseCollection);
        return ResponseEntity.ok(partsCollectionModel);
    }

    @Override
    public ResponseEntity<String> deletePartById(UUID partId) {
        partService.deletePartById(partId);
        String deletionSucceededMessage = "Successfully deleted part with ID: " + partId;

        return ResponseEntity.ok(deletionSucceededMessage);
    }

}