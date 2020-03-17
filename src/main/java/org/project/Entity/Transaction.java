package org.project.Entity;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Transaction {
    private Long id;
    private Date date;
    private Float sum;
    private String type;

    private Scope scopeToTransaction;
    private Scope scopeAtTransaction;

    public Transaction(){
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "sum_transaction")
    public Float getSum() {
        return sum;
    }

    public void setSum(Float sum) {
        this.sum = sum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @ManyToOne(optional = false, cascade = {CascadeType.PERSIST})
    @JoinColumn(name = "scopes_id_to")
    public Scope getScopeToTransaction() {
        return scopeToTransaction;
    }

    public void setScopeToTransaction(Scope scopeToTransaction) {
        this.scopeToTransaction = scopeToTransaction;
    }

    @ManyToOne(optional = false, cascade =  {CascadeType.PERSIST})
    @JoinColumn(name = "scopes_id_at")
    public Scope getScopeAtTransaction() {
        return scopeAtTransaction;
    }

    public void setScopeAtTransaction(Scope scopetAtTransaction) {
        this.scopeAtTransaction = scopetAtTransaction;
    }



    public boolean transferIsActual(){
        return (this.scopeAtTransaction.getCapasity()>this.sum ||
                this.scopeAtTransaction.getCapasity().equals(this.sum));
    }

    public void transfer(){
        float sumConvert = sum;
        Converter converter = new Converter(this.scopeAtTransaction, this.scopeToTransaction);
        if(converter.isConverted())
            sumConvert = converter.toConverted(this.sum);
        this.scopeAtTransaction.transferMoney(this.sum);
        this.scopeToTransaction.receiptMoney(sumConvert);
    }

    public String formatId(){
        return String.format("%08d", this.id);
    }

    public String formatDate(){
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        return formatForDateNow.format(this.date);
    }
}
