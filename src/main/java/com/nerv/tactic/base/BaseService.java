package com.nerv.tactic.base;

import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseService<T extends JpaRepository> {
    private T repository;

    public BaseService(T repository) {
        this.repository = repository;
    }

    public T getRepository() {
        return repository;
    }

    public boolean delete(Object id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
