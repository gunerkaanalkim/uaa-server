insert ignore into realm (id, created_by, created_date, last_modified_by, last_modified_date, code, description, name)
values (1, 'system', '2024-07-26 19:10:20.000000', 'system', '2024-07-26 19:10:20.000000', 'main', 'main', 'main');

insert ignore into users (id, created_by, created_date, last_modified_by, last_modified_date, email,
                          is_credentials_non_expired, is_enabled, is_non_expired, is_non_locked, name, password,
                          surname,
                          username, realm_id)
values (1, 'kaanalkim', '2024-03-27 14:22:37.000000', 'kaanalkim1', '2024-04-12 14:49:39.000000',
        'g.kaanalkim@gmail.com1',
        true, true, true, true, 'Kaan', '$2a$10$tOg.MgZc82mIbQCzEHn2gOpcD975kBBVFfg8j2GZ2hmwMkKCaagPC', 'Alkim', 'kaanalkim',
        1);

