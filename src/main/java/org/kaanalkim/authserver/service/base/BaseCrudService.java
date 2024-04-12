package org.kaanalkim.authserver.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseCrudService<T> {
    T save(T T);

    List<T> saveAll(List<T> T);

    T update(T T);

    T get(Long iT);

    Page<T> getAll(Pageable pageable);

    List<T> getAllWithoutPage();

    T delete(Long iT);

    List<T> deleteAll(List<T> T);

    Pageable getPaging(Integer pageNo, Integer pageSize, String column, String orTer);
}