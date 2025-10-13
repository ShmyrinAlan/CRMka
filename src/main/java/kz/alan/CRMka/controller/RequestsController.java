package kz.alan.CRMka.controller;

import kz.alan.CRMka.dto.RequestDTO;
import kz.alan.CRMka.entities.Requests;
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

    @GetMapping("/")
    public String redirect() {
        return "redirect:/all";
    }

    @GetMapping("/all")
    public String index(Model model){
        model.addAttribute("requests",repo.findAll().stream().map(RequestDTO::new).toList());
        model.addAttribute("date",LocalDate.now());
        return "all-requests";
    }

    @GetMapping("/new")
    public String newRequest(Model model){
        model.addAttribute("requests", repo.findAllByDateAfter(LocalDate.now().minusDays(1)).stream().map(RequestDTO::new).toList());
        return "new-request";
    }

    @GetMapping("/done")
    public String doneRequest(Model model){
        model.addAttribute("requests", repo.findAllByStatus(true).stream().map(RequestDTO::new).toList());
        return "done-request";
    }

    @GetMapping("/request")
    public String request(Model model, @RequestParam("id") Long id){
        if(repo.findById(id).isPresent()){
            model.addAttribute("request",new RequestDTO(repo.findById(id).get()));
        }else{
            return  "redirect:/all";
        }
        return "request";
    }
    @GetMapping("/request/add")
    public String request(Model model){
        model.addAttribute("request", new RequestDTO());
        model.addAttribute("date", LocalDate.now());
        return "request-form";
    }
    @PostMapping("/request/add")
    public String addRequest(@ModelAttribute RequestDTO requestDTO){
        if (requestDTO.getDate() == null) {
            requestDTO.setDate(LocalDate.now());
        }
        repo.save(new Requests(requestDTO.getId(), requestDTO.getUserName(), requestDTO.getCourseName(), requestDTO.getDescription(), requestDTO.getPhone(), requestDTO.getDate(), requestDTO.isStatus()));
        return "redirect:/all";
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
}
