package org.project.Repository;

import org.project.Entity.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ScopeRepository extends CrudRepository<Scope, Long> {
}
