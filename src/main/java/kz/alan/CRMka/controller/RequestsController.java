package kz.alan.CRMka.controller;

import kz.alan.CRMka.dto.RequestsDTO;
import kz.alan.CRMka.entities.Requests;
import kz.alan.CRMka.mappers.RequestMapper;
import kz.alan.CRMka.repositories.CoursesRepository;
import kz.alan.CRMka.repositories.OperatorsRepository;
import kz.alan.CRMka.repositories.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/requests")
public class RequestsController {
    @Autowired
    private RequestRepository repo;
    @Autowired
    private OperatorsRepository repoOp;
    @Autowired
    private CoursesRepository repoCo;

    @GetMapping("")
    public ResponseEntity<?> getRequests() {
        List<RequestsDTO> requests = repo.findAll().stream().map(RequestMapper::toRequestsDTO).toList();
        if (requests.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(requests, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRequests(@PathVariable Long id) {
        Optional<RequestsDTO> requests = repo.findById(id).map(RequestMapper::toRequestsDTO);
        return requests.isPresent() ? new ResponseEntity<>(requests.get(), HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("")
    public ResponseEntity<?> addRequest(@RequestBody RequestsDTO requestsDTO){
        requestsDTO.setDate(LocalDate.now());
        repo.save(RequestMapper.toRequests(requestsDTO, repoOp, repoCo));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRequest(@PathVariable(value = "id") Long id, @RequestBody RequestsDTO requestsDTO){
        Requests oldRequests = repo.findById(id).orElse(null);
        if (oldRequests == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        requestsDTO.setId(id);
        requestsDTO.setDate(oldRequests.getDate());
        repo.save(RequestMapper.toRequests(requestsDTO, repoOp, repoCo));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteRequest(@PathVariable(value = "id") Long id){
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
