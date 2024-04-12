package org.kaanalkim.authserver.repository.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, PK> extends JpaRepository<T, PK>, JpaSpecificationExecutor<T> {
}