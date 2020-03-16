package org.project.Entity;

import javax.persistence.*;

@Entity
@Table(name = "scopes")
public class Scope extends AbstractEntity {
    private String currency;
    private Float capasity= 0f;
    private Client client;

    public Scope() {
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

    public void receiptMoney(float sum){
        if(sum >0)
            this.capasity += sum;
    }

    public void transferMoney(float sum){
        if(this.capasity > sum || this.capasity == sum)
            this.capasity -= sum;
    }
}
