package com.nerv.tactic.base;

import java.util.List;

public interface BaseDao<T> {

    List<T> getAll();

    T getItemById(long id);
}
