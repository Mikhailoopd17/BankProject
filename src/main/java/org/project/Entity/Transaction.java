package org.project.Entity;

import javax.persistence.*;
import javax.xml.crypto.Data;

@Entity
@Table(name = "transactions")
public class Transaction {
    private Long id;
    private Data date;
    private Float sum;

    private Client clientToTransaction;
    private Client clientAtTransaction;

    public Transaction() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Data getDate() {
        return date;
    }

    public void setDate(Data date) {
        this.date = date;
    }

    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public Client getClientToTransaction() {
        return clientToTransaction;
    }

    public void setClientToTransaction(Client clientToTransaction) {
        this.clientToTransaction = clientToTransaction;
    }

    public Client getClientAtTransaction() {
        return clientAtTransaction;
    }

    public void setClientAtTransaction(Client clientAtTransaction) {
        this.clientAtTransaction = clientAtTransaction;
    }
}
