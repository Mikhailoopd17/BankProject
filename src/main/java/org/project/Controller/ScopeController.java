package org.project.Controller;

import org.project.Entity.Client;
import org.project.Entity.Scope;
import org.project.Service.ClientService;
import org.project.Service.ScopeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class ScopeController {
    final ScopeService scopeService;
    final ClientService clientService;

    public ScopeController(ScopeService scopeService, ClientService clientService){
        this.scopeService = scopeService;
        this.clientService = clientService;
    }

//    @GetMapping("/scopes/new")
//    public String newScope(@ModelAttribute Scope scope, Model model){
//        model.addAttribute("client", scope);
//        return "new_scope";
//    }

    @PostMapping("/clients/{id_client}/newscope")
    public String addScope(@ModelAttribute("scope") Scope scope,
                           @PathVariable Long id_client){
        Client client = clientService.getById(id_client);
        scope.setClient(client);
        scopeService.save(scope);
        return "redirect:/clients/"+scope.getClient().getId();
    }

    @RequestMapping("/clients/{id}/scope={id_scope}/del")
    public RedirectView deleteScope(@PathVariable Long id,
                                    @PathVariable Long id_scope){
        scopeService.delete(id_scope);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("/clients/"+id);
        return redirectView;
    }
}
