package org.project;

import org.project.Entity.Client;
import org.project.Repository.ClientRepository;
import org.project.Service.ClientService;
import org.project.Service.TransactionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Scheduled;

@SpringBootApplication
public class BankSpringBootApplication {
    public static void main (String[] args){
        SpringApplication.run(BankSpringBootApplication.class, args);
    }
}
