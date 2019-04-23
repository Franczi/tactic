package com.nerv.tactic.api;

import com.nerv.tactic.domain.model.ResponseMessage;
import com.nerv.tactic.domain.model.Tactic;
import com.nerv.tactic.service.TacticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping(value = {"/api/tactics"},
        produces = MediaType.APPLICATION_JSON_VALUE)
public class TacticController {

    private final TacticService service;

    @Autowired
    public TacticController(TacticService service) {
        this.service = service;
    }

    @GetMapping
    public Collection<Tactic> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity getTacticById(@PathVariable("id") long id) {
        Optional<Tactic> tactic = service.getItemById(id);
        return tactic.<ResponseEntity>map(
                tactic1 -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(tactic1))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ResponseMessage("Not found resource with id: " + id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTacticById(@PathVariable("id") long id) {
        if (service.delete(id)) {
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Item successfully deleted"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item with given id doesn't exist");
        }
    }

    @PostMapping
    public ResponseEntity create(@RequestBody Tactic tactic) {
        if (service.addTactic(tactic))
            return ResponseEntity.status(HttpStatus.CREATED).body(tactic);
        else
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage("Item sent doesn't contain data"));
    }

}
