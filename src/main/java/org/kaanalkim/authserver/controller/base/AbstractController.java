package org.kaanalkim.authserver.controller.base;

import org.kaanalkim.authserver.mapper.base.BaseMapper;
import org.kaanalkim.authserver.model.base.AbstractEntity;
import org.kaanalkim.authserver.payload.mapper.AbstractDTO;
import org.kaanalkim.authserver.service.base.BaseCrudService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractController<T extends AbstractEntity, D extends AbstractDTO> {
    protected abstract <K extends BaseCrudService<T>> K getService();

    protected abstract <M extends BaseMapper<T, D>> M getMapper();

    @PostMapping("save")
    public ResponseEntity<D> create(@Validated @RequestBody D d) {
        T entity = getMapper().toEntity(d);
        getService().save(entity);
        return ResponseEntity.ok().body(d);
    }

    @PostMapping("save-all")
    public ResponseEntity<List<D>> createAll(@Validated @RequestBody List<D> all) {
        List<T> entityList = all.stream().map(d -> getMapper().toEntity(d)).toList();
        getService().saveAll(entityList);
        return ResponseEntity.ok().body(all);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<D> getById(@PathVariable("id") Long oid) {
        T entity = getService().get(oid);
        D dto = getMapper().toDTO(entity);

        return ResponseEntity.ok().body(dto);
    }

    @GetMapping("get-all")
    public ResponseEntity<Page<T>> getAll(@RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @RequestParam(defaultValue = "id") String column,
                                          @RequestParam(defaultValue = "asc") String order) {
        final Page<T> all = getService().getAll(getService().getPaging(pageNo, pageSize, column, order));

        return ResponseEntity.ok().body(all);
    }

    @GetMapping("get-all-without-page")
    public ResponseEntity<List<D>> getAll() {
        List<T> allEntities = getService().getAllWithoutPage();
        List<D> dtoList = allEntities.stream().map(t -> getMapper().toDTO(t)).toList();
        return ResponseEntity.ok().body(dtoList);
    }

    @PutMapping("update")
    public ResponseEntity<D> update(@Validated @RequestBody D d) {
        T entity = getMapper().toEntity(d);
        T update = getService().update(entity);
        return ResponseEntity.ok().body(d);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<D> delete(@PathVariable("id") Long id) {
        T deletedEntity = getService().delete(id);
        D dto = getMapper().toDTO(deletedEntity);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("delete-all")
    public ResponseEntity<List<D>> deleteAll(@Validated @RequestBody List<D> all) {
        List<T> entityList = all.stream().map(d -> getMapper().toEntity(d)).toList();
        getService().deleteAll(entityList);

        return ResponseEntity.ok().body(all);
    }

}