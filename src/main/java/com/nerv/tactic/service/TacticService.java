package com.nerv.tactic.service;

import com.nerv.tactic.base.BaseService;
import com.nerv.tactic.domain.model.Tactic;
import com.nerv.tactic.domain.repository.TacticRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TacticService extends BaseService<TacticRepository> {

    @Autowired
    public TacticService(TacticRepository dao) {
        super(dao);
    }

    public List<Tactic> getAll() {
        return getRepository().findAll();
    }

    public Optional<Tactic> getItemById(long id) {
        if (getRepository().existsById(id)) {
            return getRepository().findById(id);
        }
        return null;
    }

    public boolean addTactic(Tactic tactic) {
        if (tactic == null) return false;
        getRepository().save(tactic);
        return true;
    }
}
