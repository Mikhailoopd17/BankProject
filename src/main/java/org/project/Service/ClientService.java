package org.project.Service;

import org.project.Entity.Client;
import org.project.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ClientService {
    final ClientRepository repository;

    public ClientService(ClientRepository repository) {
        this.repository = repository;
    }

    public Collection<Client> getAll(){
        return repository.getAll();
    }

    public Client getById(Long id){
        return repository.findById(id).get();
    }

    public void save(Client client){ repository.save(client);}
}
