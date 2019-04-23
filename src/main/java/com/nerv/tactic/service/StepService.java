package com.nerv.tactic.service;

import com.nerv.tactic.base.BaseService;
import com.nerv.tactic.domain.model.Step;
import com.nerv.tactic.domain.repository.StepRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class StepService extends BaseService<StepRepository> {

    @Autowired
    public StepService(StepRepository repository) {
        super(repository);
    }

    public Collection<Step> getAll() {
        return getRepository().findAll();
    }

    public Optional<Step> getItemById(long id) {
        if (getRepository().existsById(id)) {
            return getRepository().findById(id);
        }
        return null;
    }

    public boolean addStep(Step step) {
        if (step == null) return false;
        getRepository().save(step);
        return true;
    }
}
