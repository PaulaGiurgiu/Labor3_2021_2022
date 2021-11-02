package com.uni.repository;

import java.util.List;

public interface CrudRepository<T> {
    T findOne(int id);
    List<T> findAll();
    T save(T entity);
    void delete(T entity);
    T update(T entity);

}
