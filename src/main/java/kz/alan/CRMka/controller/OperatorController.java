package kz.alan.CRMka.controller;

import kz.alan.CRMka.dto.OperatorsDTO;
import kz.alan.CRMka.dto.RequestsDTO;
import kz.alan.CRMka.dto.shorts.RequestsDTOSh;
import kz.alan.CRMka.entities.Operators;
import kz.alan.CRMka.entities.Requests;
import kz.alan.CRMka.mappers.OperatorMapper;
import kz.alan.CRMka.mappers.RequestMapper;
import kz.alan.CRMka.repositories.CoursesRepository;
import kz.alan.CRMka.repositories.OperatorsRepository;
import kz.alan.CRMka.repositories.RequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/operators")
public class OperatorController {
    @Autowired
    private final OperatorsRepository repo;
    @Autowired
    private final RequestRepository repoRe;
    @Autowired
    private final CoursesRepository repoCo;

    @GetMapping("")
    public ResponseEntity<?> getOperators() {
        List<OperatorsDTO> operators = repo.findAll().stream().map(OperatorMapper::toOperatorsDTO).toList();
        if (operators.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(operators,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getOperator(@PathVariable Long id) {
        Optional<OperatorsDTO> op = repo.findById(id).map(OperatorMapper::toOperatorsDTO);
        if (op.isPresent()) return new ResponseEntity<>(op.get(),HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("")
    public ResponseEntity<?> createOperator(@RequestBody OperatorsDTO operatorsDTO) {
        repo.save(OperatorMapper.toOperators(operatorsDTO,repoRe));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOperator(@PathVariable(value = "id") Long id, @RequestBody OperatorsDTO operatorsDTO) {
        Operators op = repo.findById(id).orElse(null);
        if (op == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        operatorsDTO.setId(id);
        if(operatorsDTO.getRequests().isEmpty()) operatorsDTO.setRequests(op.getRequests().stream().map(RequestMapper::toShort).toList());
        else operatorsDTO.getRequests().stream().forEach(req -> {
            Optional<Requests> reqOp = repoRe.findById(req.getId());
            if (reqOp.isPresent()){
                Requests reqs = reqOp.get();
                List<Operators> cl = reqs.getOperators();
                if(cl.stream().filter(oper -> oper.getId() == id).toList().isEmpty()){
                    cl.add(repo.findById(id).get());
                    reqs.setOperators(cl);
                }else{
                    cl.remove(repo.findById(id).get());
                    reqs.setOperators(cl);
                }
                repoRe.save(reqs);
            }
        });
        return new ResponseEntity<>(repo.save(OperatorMapper.toOperators(operatorsDTO,repoRe)),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOperator(@PathVariable(value = "id") Long id) {
        repo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
