package org.project.Service;

import org.project.Entity.Transaction;
import org.project.Repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionService {
    final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public void save(Transaction transaction){
        transactionRepository.save(transaction);
    }

    @Transactional
    public List<Transaction> getAll(){
        return (List<Transaction>) transactionRepository.findAll();
    }

}
