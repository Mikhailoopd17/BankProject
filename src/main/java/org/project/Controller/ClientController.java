package org.project.Controller;

import org.project.Entity.Client;
import org.project.Entity.Scope;
import org.project.Service.ClientService;
import org.project.Service.ScopeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ClientController {

    final ClientService clientService;
    final ScopeService scopeService;

    public ClientController(ClientService clientService, ScopeService scopeService) {
        this.clientService = clientService;
        this.scopeService = scopeService;
    }

    @GetMapping("/clients")
    public String clients(Model model){
        List<Client> list = new ArrayList<>();
        for (Client cl:clientService.getAll()) {
            if(!cl.getName().toLowerCase().contains("bank") &&
                    !cl.getName().toLowerCase().contains("shop"))
                list.add(cl);
        }
        model.addAttribute("clients", list);
        model.addAttribute("new_client", new Client());
        return "clients";
    }

    @GetMapping("/clients/{id}")
    public String viewClient(@PathVariable Long id, Model model){
        Client client = clientService.getById(id);
        model.addAttribute(client);
        model.addAttribute("scope", new Scope());
        return "view";
    }

//    @GetMapping("/clients/new")
//    public String newClient(@ModelAttribute Client client, Model model){
//        model.addAttribute("client", client);
//        return "new_client";
//    }

    @PostMapping("/clients/new_client")
    public String addClient(@ModelAttribute("client") Client client){
        clientService.save(client);
        return "redirect:/clients";
    }
}
