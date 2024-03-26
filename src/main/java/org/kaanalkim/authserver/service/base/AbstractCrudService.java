package org.kaanalkim.authserver.service.base;

import org.kaanalkim.authserver.exception.ObjectNotFoundById;
import org.kaanalkim.authserver.model.base.AbstractEntity;
import org.kaanalkim.authserver.model.enums.ErrorCode;
import org.kaanalkim.authserver.payload.mapper.AbstractDTO;
import org.kaanalkim.authserver.repository.base.BaseRepository;
import org.kaanalkim.authserver.service.mapper.BaseMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.beans.FeatureDescriptor;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractCrudService<T extends AbstractEntity, D extends AbstractDTO> extends AbstractService {
    protected abstract <K extends BaseRepository<T, Long>> K getRepository();

    protected abstract <M extends BaseMapper<T, D>> M getMapper();

    public D save(D d) {
        final T t = getMapper().toEntity(d);
        getRepository().save(t);
        logger.info("{} : {} saved successfully! {}", className, getName(t.getClass()), t);
        return getMapper().toDTO(t);
    }

    public List<D> saveAll(List<D> all) {
        final List<T> collect = all.stream().map(d -> getMapper().toEntity(d)).collect(Collectors.toList());

        final List<T> tList = getRepository().saveAll(collect);
        logger.info("{}, list saved successfully! Size: {}", className, tList.size());
        return all;
    }

    public D update(D d) {
        final T persistedEntity = getRepository().findById(d.getId()).orElseThrow(() -> new ObjectNotFoundById(super.getErrorMessage(ErrorCode.NOT_FOUND, d.getId().toString())));
        final T preparedEntity = getMapper().toEntity(d);

        BeanUtils.copyProperties(preparedEntity, persistedEntity, this.getNullPropertyNames(preparedEntity));

        getRepository().save(persistedEntity);
        logger.info("{} : {} updated successfully! {}", className, super.getName(d.getClass()), d);

        return d;
    }

    public D get(Long id) {
        validateId(id);

        final T t = getRepository().findById(id).orElseThrow(() -> new ObjectNotFoundById(super.getErrorMessage(ErrorCode.NOT_FOUND, id.toString())));

        logger.info("{} : {} object is found by id: {}, object is: {}", className, super.getName(t.getClass()), id, t);

        return getMapper().toDTO(t);
    }

    //TODO convert to DTO
    public Page<T> getAll(Pageable pageable) {
        final Page<T> all = getRepository().findAll(pageable);
        logger.info("{} : {} object found.", className, all.getContent().size());
        return all;
    }

    public List<D> getAllWithoutPage() {
        List<T> all = getRepository().findAll();
        logger.info("{} : {} object found.", className, all.size());

        return all.stream().map(t -> getMapper().toDTO(t)).collect(Collectors.toList());
    }

    public D delete(Long id) {
        validateId(id);

        final T t = getRepository().findById(id).orElseThrow(() -> new ObjectNotFoundById(super.getErrorMessage(ErrorCode.NOT_FOUND, id.toString())));

        getRepository().deleteById(id);
        logger.info("{} : Object deleted by id {} successfully!", className, id);

        return getMapper().toDTO(t);
    }

    public List<D> deleteAll(List<D> all) {
        all.forEach(ent -> validateId(ent.getId()));
        List<Long> idList = all.stream().map(AbstractDTO::getId).collect(Collectors.toList());

        getRepository().deleteAllById(idList);
        logger.info("{} : All objects deleted successfully!", className);

        return all;
    }

    public Pageable getPaging(Integer pageNo, Integer pageSize, String column, String order) {
        int calculatedPageNumber = pageNo - 1;

        if (calculatedPageNumber <= 0) {
            calculatedPageNumber = 0;
        }

        return getPager(calculatedPageNumber, pageSize, column, order);
    }

    private String[] getNullPropertyNames(T t) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(t);
        return Stream.of(wrappedSource.getPropertyDescriptors())
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }
}