package org.project.Controller;

import org.project.Entity.Client;
import org.project.Entity.Converter;
import org.project.Entity.Scope;
import org.project.Entity.Transaction;
import org.project.Service.ClientService;
import org.project.Service.ScopeService;
import org.project.Service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Date;
import java.util.Random;

@Controller
public class TransactionController {
    final TransactionService transactionService;
    final ScopeService scopeService;
    final ClientService clientService;

    public TransactionController(TransactionService transactionService, ScopeService scopeService, ClientService clientService) {
        this.transactionService = transactionService;
        this.scopeService = scopeService;
        this.clientService = clientService;
    }

    @GetMapping("/transactions")
    public String allTransactions(Model model){
        model.addAttribute("transactions", transactionService.getAll());
        return "transactions";
    }

    @GetMapping("scope={id_scope}/new_tr")
    public String newTransactions(@PathVariable Long id_scope,
                                    Model model){
        model.addAttribute("scope", scopeService.getById(id_scope));
        model.addAttribute("transaction", new Transaction());
        return "new_transaction";
    }

    @PostMapping("/scope={id}/new_tr/add")
    public RedirectView addTransaction(@ModelAttribute("transaction") Transaction tr,
                               @ModelAttribute("id_to") String id_to,
                               @PathVariable Long id){
        RedirectView redirectView = new RedirectView();

        tr.setScopeAtTransaction(scopeService.getById(id));
        tr.setScopeToTransaction(scopeService.getById(Long.parseLong(id_to)));

        if(tr.transferIsActual()) {
            tr.transfer();
            tr.setDate(new Date());
            tr.setType("Перевод между счетами клиента");
            transactionService.save(tr);
        }
        redirectView.setUrl("/clients/"+tr.getScopeAtTransaction().getClient().getId());
        return redirectView;
    }

    @PostMapping("/scope={id}/receipt")
    public RedirectView receiptMoney(@ModelAttribute("transaction") Transaction tr,
                                     @PathVariable Long id) {
        RedirectView redirectView = new RedirectView();
        tr.setScopeToTransaction(scopeService.getById(id));

        Client defClient = new Client("BANKOMAT-"+new Random().nextInt(), "-");
        Scope defScope = new Scope("Another", "Рубль", defClient);

        tr.setScopeAtTransaction(defScope);

        if(tr.transferIsActual()) {
            tr.transfer();
            tr.setDate(new Date());
            tr.setType("Пополнение");
            clientService.save(defClient);
            scopeService.save(defScope);
            transactionService.save(tr);
        }
        redirectView.setUrl("/clients/"+tr.getScopeToTransaction().getClient().getId());
        return redirectView;

    }
}
