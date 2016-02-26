INSERT INTO USERS (login, name, company, location, email, created_at, type, fake, deleted, long, lat, country_code, state, city)
VALUES ('login_001', 'name001', 'com001', 'Entenhnausen, Germany','xx.xx@xx.xx', '2010-12-13 12:13:14', 'USR', 0, 0, null, null, 'de', 'Bavaria', 'Munich');

INSERT INTO USERS (login, name, company, location, email, created_at, type, fake, deleted, long, lat, country_code, state, city)
VALUES ('login_002', 'name002', 'com002', 'Entenhnausen, Germany','xx.xx@xx.yy', '2010-12-13 12:13:14', 'USR', 0, 0, null, null, 'de', 'Bavaria', 'Munich');



--INSERT INTO NAP_RETENTIONCLASSES (retclass_id, retclassname, retmode, retvalue, default_yn) VALUES (1, 'netapp-retention', 0, 0, 1);
--INSERT INTO NAP_POOLS (pool_id, poolname, pooltype, default_yn, high_watermark) VALUES (1, 'netapp', 'pool-type', 1, 90);
-- INSERT INTO NAP_VOLUMES (VOLUME_ID, VOLUMENAME, POOL_ID, MAX_SPACE, MAX_NODES, USED_SPACE, USED_NODES, DIRECTORY_COUNT, CREATE_COUNT, MAXCHILDSINDIR, BYTESPERCLUSTER, PATH, HIGH_WATERMARK, LOW_WATERMARK, READONLY_YN)
-- VALUES (1, 'Volume-01', 1, 1000000, 1000, 0, 0, 0, 0, 1000, 4096, '_data/netapp/volume01', 95, 85, 0);

-- INSERT INTO NAP_VOLUMES (VOLUME_ID, VOLUMENAME, POOL_ID, MAX_SPACE, MAX_NODES, USED_SPACE, USED_NODES, DIRECTORY_COUNT, CREATE_COUNT, MAXCHILDSINDIR, BYTESPERCLUSTER, PATH, HIGH_WATERMARK, LOW_WATERMARK, READONLY_YN)
-- VALUES (2, 'Volume-02', 1, 1000000, 1000, 0, 0, 0, 0, 1000, 4096, '_data/netapp/volume02', 95, 85, 0);
