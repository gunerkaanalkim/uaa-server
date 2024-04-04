package org.kaanalkim.authserver.repository;

import org.kaanalkim.authserver.model.Realm;
import org.kaanalkim.authserver.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RealmRepository extends BaseRepository<Realm, Long> {

}
