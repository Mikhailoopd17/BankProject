package org.project.Service;

import org.project.Entity.Scope;
import org.project.Repository.ScopeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScopeService {
    final ScopeRepository scopeRepository;

    public ScopeService(ScopeRepository scopeRepository){
        this.scopeRepository = scopeRepository;
    }

    @Transactional
    public void save(Scope scope){
        scopeRepository.save(scope);
    }

    @Transactional
    public void delete(Long id){
        scopeRepository.deleteById(id);
    }

    @Transactional
    public Scope getById(Long id){
        return scopeRepository.getOne(id);
    }
}
