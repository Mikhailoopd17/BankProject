package org.project.Repository;

import org.project.Entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface ClientRepository extends CrudRepository<Client, Long> {
    @Query("from Client")
    Collection<Client> getAll();


//    @Query("select cl from Client cl where cl.id = (#{id})")
//    Client getClientById(Long id);
}
