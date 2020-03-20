package org.project.Entity;

import javax.persistence.*;
import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Entity
public class Transaction {
    private Long id;
    private Date date;
    private Float sum;
    private String type;

    private Scope scopeToTransaction;
    private Scope scopeAtTransaction;

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

    @ManyToOne(optional = false)
    @JoinColumn(name = "scopes_id_to")
    public Scope getScopeToTransaction() {
        return scopeToTransaction;
    }

    public void setScopeToTransaction(Scope scopeToTransaction) {
        this.scopeToTransaction = scopeToTransaction;
    }

    @ManyToOne(optional = false)
    @JoinColumn(name = "scopes_id_at")
    public Scope getScopeAtTransaction() {
        return scopeAtTransaction;
    }

    public void setScopeAtTransaction(Scope scopetAtTransaction) {
        this.scopeAtTransaction = scopetAtTransaction;
    }


    public boolean transferIsActual() {
        return (this.scopeAtTransaction.getCapasity() > this.sum ||
                this.scopeAtTransaction.getCapasity().equals(this.sum));
    }

    public void transfer() {
        float sumConvert = sum;
        Converter converter = new Converter(this.scopeAtTransaction, this.scopeToTransaction);
        if (converter.isConverted())
            sumConvert = converter.toConverted(this.sum);
        this.scopeAtTransaction.transferMoney(this.sum);
        this.scopeToTransaction.receiptMoney(sumConvert);
    }

    public String formatId() {
        return String.format("%08d", this.id);
    }

    public String formatDate() {
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss");
        formatForDateNow.setTimeZone(TimeZone.getTimeZone("Asia/Yekaterinburg"));
        return formatForDateNow.format(this.date);
    }

    public boolean isContainsFirstDate(String date1) throws ParseException {
        if (date1.equals(""))
            return true;
        else {
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
            formatForDateNow.setTimeZone(TimeZone.getTimeZone("Asia/Yekaterinburg"));
            Date ddate1 = formatForDateNow.parse(date1);

            return !(this.date.getTime() < ddate1.getTime());
        }
    }

    public boolean isContainsSecondDate(String date2, Date dateTr) throws ParseException {
        if (date2.equals(""))
            return true;
        else {
            SimpleDateFormat formatForDateNow = new SimpleDateFormat("yyyy-MM-dd");
            Date ddate1 = formatForDateNow.parse(date2);

            return !(this.date.getTime() > ddate1.getTime());
        }
    }

    public boolean isContainsNumberFilter(Long id_at, Long id_to) {
        if (id_at == null && id_to == null)
            return true;
        else {
            if (id_at == null)
                id_at = 0l;
            if (id_to == null)
                id_to = 0l;
            return (this.scopeAtTransaction.getClient().getId().equals(id_at) ||
                    this.scopeToTransaction.getClient().getId().equals(id_to));
        }
    }

    public boolean isFilter(Long id_at, Long id_to, String datef, String dates) throws ParseException {
        if (id_at == null && id_to == null &&
                datef.equals("") && dates.equals("")) {
            return true;
        } else {
            return (isContainsNumberFilter(id_at, id_to) &&
                    isContainsFirstDate(datef) &&
                    isContainsSecondDate(dates, this.date));
        }
    }

}
