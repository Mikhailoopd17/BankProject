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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
public class TransactionController {
    private final TransactionService transactionService;
    private final ScopeService scopeService;
    private final ClientService clientService;

    public TransactionController(TransactionService transactionService, ScopeService scopeService, ClientService clientService) {
        this.transactionService = transactionService;
        this.scopeService = scopeService;
        this.clientService = clientService;
    }

    @GetMapping("/transactions")
    public String allTransactions(Model model){
        model.addAttribute("date_f");
        model.addAttribute("date_s");
        model.addAttribute("id_client_at");
        model.addAttribute("id_client_to");

        model.addAttribute("transactions", transactionService.getAll());
        return "transactions";
    }

    @RequestMapping("/transactions/")
    public String transactionsFilter(@RequestParam("date_f") String datef,
                                     @RequestParam("date_s") String dates,
                                     @RequestParam("id_at") Long id_at,
                                     @RequestParam("id_to") Long id_to,
                                     Model model) throws ParseException {


        List<Transaction> filterList = new ArrayList<>();
        for (Transaction tr: transactionService.getAll()) {
            if(tr.isFilter(id_at, id_to,datef,dates))
                filterList.add(tr);
        }
        model.addAttribute("transactions", filterList);
        return "/transactions";
    }

    @RequestMapping("scope={id_scope}/new_tr")
    public String newTransactions(@PathVariable Long id_scope,
                                    Model model){
        model.addAttribute("scope", scopeService.getById(id_scope));
        model.addAttribute("transaction", new Transaction());
        model.addAttribute("clients", clientService.getAll());
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
        Scope defScope = new Scope("AnotherBank", "Рубль", defClient);

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

    @PostMapping("/scope={id}/transfer")
    public RedirectView transferMoney(@ModelAttribute("transaction") Transaction tr,
                                     @PathVariable Long id) {
        RedirectView redirectView = new RedirectView();
        tr.setScopeAtTransaction(scopeService.getById(id));

        Client defClient = new Client("SHOP-Magazine-"+new Random().nextInt(), "-");
        Scope defScope = new Scope("AnotherShop", "Рубль", defClient);

        tr.setScopeToTransaction(defScope);

        if(tr.transferIsActual()) {
            tr.transfer();
            tr.setDate(new Date());
            tr.setType("Списание средст(покупка)");
            clientService.save(defClient);
            scopeService.save(defScope);
            transactionService.save(tr);
        }
        redirectView.setUrl("/clients/"+tr.getScopeAtTransaction().getClient().getId());
        return redirectView;
    }

    @PostMapping("/scope={id}/another_client")
    public RedirectView transferAnotherClient(@ModelAttribute("transaction") Transaction tr,
                                       @ModelAttribute("id_to1") String id_to,
                                       @PathVariable Long id){
        RedirectView redirectView = new RedirectView();

        tr.setScopeAtTransaction(scopeService.getById(id));
        tr.setScopeToTransaction(scopeService.getById(Long.parseLong(id_to)));

        if(tr.transferIsActual()) {
            tr.transfer();
            tr.setDate(new Date());
            tr.setType("Перевод на счет другому клиенту");
            transactionService.save(tr);
        }
        redirectView.setUrl("/clients/"+tr.getScopeAtTransaction().getClient().getId());
        return redirectView;
    }
}
