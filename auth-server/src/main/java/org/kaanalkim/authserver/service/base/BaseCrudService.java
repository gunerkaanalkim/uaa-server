package org.kaanalkim.authserver.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BaseCrudService<T, D> {
    D save(D d);

    List<D> saveAll(List<D> d);

    D update(D d);

    D get(Long id);

    Page<T> getAll(Pageable pageable);

    List<D> getAllWithoutPage();

    D delete(Long id);

    List<D> deleteAll(List<D> d);

    Pageable getPaging(Integer pageNo, Integer pageSize, String column, String order);
}