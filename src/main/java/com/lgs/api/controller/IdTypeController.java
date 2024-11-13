package com.lgs.api.controller;

import com.lgs.api.request.IdTypeRequest;
import com.lgs.api.response.IdTypeResponse;
import com.lgs.api.service.IdTypeIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/idtypes")
public class IdTypeController {

    @Autowired
    private IdTypeIdService idTypeService;

    @PostMapping("/")
    public ResponseEntity<?> addIdType(@RequestBody IdTypeRequest request) {
        idTypeService.addIdType(request);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IdTypeResponse> idTypeById(@PathVariable Long id) {
        IdTypeResponse idTypeResponse = idTypeService.idTypeById(id);
        return ResponseEntity.ok(idTypeResponse);
    }

    @GetMapping("/")
    public ResponseEntity<List<IdTypeResponse>> findAllIdTypes() {
        List<IdTypeResponse> idTypeResponses = idTypeService.findAll();
        return ResponseEntity.ok(idTypeResponses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateIdType(@PathVariable Long id, @RequestBody IdTypeRequest request) {
        idTypeService.updateIdType(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIdType(@PathVariable Long id) {
        idTypeService.deleteIdType(id);
        return ResponseEntity.ok().build();
    }
}