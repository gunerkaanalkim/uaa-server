package org.kaanalkim.authserver.controller.base;

import org.kaanalkim.authserver.model.base.AbstractEntity;
import org.kaanalkim.authserver.payload.mapper.AbstractDTO;
import org.kaanalkim.authserver.service.base.BaseCrudService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public abstract class AbstractController<T extends AbstractEntity, D extends AbstractDTO> {
    protected abstract <K extends BaseCrudService<T, D>> K getService();

    @PostMapping("save")
    public ResponseEntity<D> create(@Validated @RequestBody D d) {
        final D save = getService().save(d);
        return ResponseEntity.ok().body(save);
    }

    @PostMapping("save-all")
    public ResponseEntity<List<D>> createAll(@Validated @RequestBody List<D> all) {
        final List<D> ds = getService().saveAll(all);
        return ResponseEntity.ok().body(ds);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<D> getById(@PathVariable("id") Long oid) {
        final D d = getService().get(oid);

        return ResponseEntity.ok().body(d);
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
        List<D> allWithoutPage = getService().getAllWithoutPage();
        return ResponseEntity.ok().body(allWithoutPage);
    }

    @PutMapping("update")
    public ResponseEntity<D> update(@Validated @RequestBody D d) {
        final D update = getService().update(d);
        return ResponseEntity.ok().body(update);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<D> delete(@PathVariable("id") Long id) {
        final D delete = getService().delete(id);
        return ResponseEntity.ok().body(delete);
    }

    @PostMapping("delete-all")
    public ResponseEntity<List<D>> deleteAll(@Validated @RequestBody List<D> all) {
        final List<D> ds = getService().deleteAll(all);
        return ResponseEntity.ok().body(ds);
    }

}