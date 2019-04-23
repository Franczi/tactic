package com.nerv.tactic.api;

import com.nerv.tactic.domain.model.ResponseMessage;
import com.nerv.tactic.domain.model.Step;
import com.nerv.tactic.service.StepService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = {"/api/steps"},
        produces = MediaType.APPLICATION_JSON_VALUE)
public class StepController {

    private final StepService service;

    @Autowired
    public StepController(StepService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<Step> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getStepById(@PathVariable("id") long id) {
        Optional<Step> step = service.getItemById(id);
        return step
                .<ResponseEntity>map(
                        step1 -> ResponseEntity.status(HttpStatus.OK).body(step1))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage("Not found resource with id: " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteStepById(@PathVariable("id") long id) {
        if (service.delete(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Item successfully deleted"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item with given id doesn't exist");
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Step step) {
        if (service.addStep(step))
            return ResponseEntity.status(HttpStatus.CREATED).body(step);
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage("Item sent doesn't contain data"));
    }
}
