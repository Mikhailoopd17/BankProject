package org.project.Entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@Entity
@Table(name = "clients")
public class Client extends AbstractEntity {

    private String address;
    private Collection<Scope> scopes;

    public Client() {
    }

    public Client(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER)
    public Collection<Scope> getScopes() {
        return scopes;
    }

    public void setScopes(Collection<Scope> scopes) {
        this.scopes = scopes;
    }

    public String toString(){
        return this.name;
    }
}
