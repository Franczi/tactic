package com.nerv.tactic.api;

import com.nerv.tactic.domain.model.ResponseMessage;
import com.nerv.tactic.domain.storage.StorageFileNotFoundException;
import com.nerv.tactic.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = {"/api/files"},
        produces = MediaType.APPLICATION_JSON_VALUE)
public class StorageController {
    private final StorageService service;

    @Autowired
    public StorageController(StorageService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<String>> allStoredFiles() {
        List<String> temp = service
                .loadAll()
                .map(Path::toString)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(temp);
    }

    @GetMapping("/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = service.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping
    public ResponseEntity handleFileUpload(@RequestParam("file") MultipartFile file) {
        service.store(file);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new ResponseMessage("\"You successfully uploaded \"" + file.getOriginalFilename() + "\"!\" "));
    }

    @DeleteMapping("/{filename:.+}")
    public ResponseEntity deleteFile(@PathVariable String filename) {
        boolean result = service.deleteResource(filename);
        return ResponseEntity
                .status(result ? HttpStatus.OK : HttpStatus.NOT_FOUND)
                .body(new ResponseMessage(result ? "\"You successfully deleted \"" + filename + "\"!\" " : "Error occurred during delete operation"));
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
