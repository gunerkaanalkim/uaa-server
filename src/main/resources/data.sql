insert ignore into realm (id, created_by, created_date, last_modified_by, last_modified_date, code, description, name)
values (1, 'SYSTEM', curdate(), 'SYSTEM', curdate(), 'main', 'main', 'main');

insert ignore into users (id, created_by, created_date, last_modified_by, last_modified_date, email,
                          is_credentials_non_expired, is_enabled, is_non_expired, is_non_locked, name, password,
                          surname,
                          username, realm_id)
values (1, 'SYSTEM', curdate(), 'SYSTEM', curdate(), 'admin', true, true, true, true, 'Super',
        '$2a$10$SqSVJvndVIluKjr9oFV8CuYSR20U9F2NLAhwmZA1OAKWdnOsajVtm', 'Admin',
        'admin',
        1);

