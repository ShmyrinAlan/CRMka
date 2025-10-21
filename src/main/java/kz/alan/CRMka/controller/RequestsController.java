package kz.alan.CRMka.controller;

import kz.alan.CRMka.dto.RequestsDTO;
import kz.alan.CRMka.entities.Requests;
import kz.alan.CRMka.mappers.RequestMapper;
import kz.alan.CRMka.repositories.CoursesRepository;
import kz.alan.CRMka.repositories.OperatorsRepository;
import kz.alan.CRMka.repositories.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class RequestsController {
    @Autowired
    private RequestRepository repo;
    @Autowired
    private OperatorsRepository repoOp;
    @Autowired
    private CoursesRepository repoCo;

    @GetMapping("/")
    public String redirect() {
        return "redirect:/all";
    }

    @GetMapping("/all")
    public String index(Model model){
        model.addAttribute("requests",repo.findAll().stream().map(RequestMapper::toRequestsDTO).toList());
        model.addAttribute("date",LocalDate.now());
        return "all-requests";
    }

    @GetMapping("/new")
    public String newRequest(Model model){
        model.addAttribute("requests", repo.findAllByDateAfter(LocalDate.now().minusDays(1)).stream().map(RequestMapper::toRequestsDTO).toList());
        return "new-request";
    }

    @GetMapping("/done")
    public String doneRequest(Model model){
        model.addAttribute("requests", repo.findAllByStatus(true).stream().map(RequestMapper::toRequestsDTO).toList());
        return "done-request";
    }

    @GetMapping("/request")
    public String request(Model model, @RequestParam("id") Long id){
        if(repo.findById(id).isPresent()){
            model.addAttribute("request", RequestMapper.toRequestsDTO(repo.findById(id).get()));
            model.addAttribute("opers", repoOp.findAll());
        }else{
            return  "redirect:/all";
        }
        return "request";
    }
    @GetMapping("/request/add")
    public String request(Model model){
        model.addAttribute("request", new RequestsDTO());
        model.addAttribute("date", LocalDate.now());
        model.addAttribute("courses", repoCo.findAll());
        return "request-form";
    }
    @PostMapping("/request/add")
    public String addRequest(@ModelAttribute RequestsDTO requestsDTO){
        if (requestsDTO.getDate() == null) {
            requestsDTO.setDate(LocalDate.now());
        }
        repo.save(RequestMapper.toRequests(requestsDTO, repoOp));
        return "redirect:/all";
    }
    @PostMapping("/request/up")
    public String upRequest(@ModelAttribute RequestsDTO requestsDTO){
        if (requestsDTO.getDate() == null) {
            requestsDTO.setDate(LocalDate.now());
        }
        repo.save(RequestMapper.toRequests(requestsDTO, repoOp));
        return "redirect:/request?id=" + requestsDTO.getId();
    }

    @PostMapping("/request/status")
    public String statusRequest(@RequestParam("id") Long id, @RequestParam("act") boolean status){
        if(repo.findById(id).isPresent()){
            if(status){
                Requests requests = repo.findById(id).get();
                requests.setStatus(true);
                repo.save(requests);
            }
            else{
                repo.deleteById(id);
            }
        }
        return "redirect:/all";
    }

    @PostMapping("/request/operator/remove")
    public String removeOperator(@RequestParam("requestId") Long requestId,
                                 @RequestParam("operatorId") Long operatorId) {
        repo.findById(requestId).ifPresent(r -> {
            if (r.getOperators() != null) {
                r.getOperators().removeIf(op -> op.getId().equals(operatorId));
                repo.save(r);
            }
        });
        return "redirect:/request?id=" + requestId;
    }
}
