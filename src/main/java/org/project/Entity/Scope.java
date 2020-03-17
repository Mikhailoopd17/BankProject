package org.project.Entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "scopes")
public class Scope extends AbstractEntity {
    private String currency;
    private Float capasity= 0f;
    private Client client;

    private Collection<Transaction> transfers;

    private Collection<Transaction> receipts;


    public Scope() {
    }
    public Scope(String name, String currency, Client client) {
        this.name = name;
        this.currency = currency;
        this.capasity = 5000000f;
        this.client = client;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Float getCapasity() {
        return capasity;
    }

    public void setCapasity(Float capasity) {
        this.capasity = capasity;
    }

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "clients_id")
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @OneToMany(mappedBy = "scopeAtTransaction", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    public Collection<Transaction> getTransfers() {
        return transfers;
    }

    public void setTransfers(Collection<Transaction> transfers) {
        this.transfers = transfers;
    }

    @OneToMany(mappedBy = "scopeToTransaction", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    public Collection<Transaction> getReceipts() {
        return receipts;
    }

    public void setReceipts(Collection<Transaction> receipts) {
        this.receipts = receipts;
    }

    //поступление средств
    public void receiptMoney(float sum){
        if(sum>0)
            this.capasity += sum;
    }

    //списание средств
    public void transferMoney(float sum){
        if(sum>0 && (sum < this.capasity || sum == this.capasity))
            this.capasity -= sum;
    }

    public String formatId(){
        return String.format("%08d", this.id);
    }
}
