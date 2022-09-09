-- Insert Role
INSERT INTO role VALUES (1,"ADMIN");
-- #
INSERT INTO role VALUES (2,"BUYER");
-- #
INSERT INTO role VALUES (3,"SUPPLIER");
-- #
INSERT INTO account(address, age, create_at, email, enabled, full_name, is_deleted, password, updated_at, username) VALUES ("18 Ton That Thuyet",27,"2022-09-07 14:33:18","admin@vmo.com",0,"admin",0,"$2a$12$S7oZitr9f1qerDHqSAimpOJxw/GdLt/bL3h68x2BrFASgIszcVL4a","2022-09-07 14:33:18","admin");
-- #
INSERT INTO account(address, age, create_at, email, enabled, full_name, is_deleted, password, updated_at, username) VALUES ("18 Ton That Thuyet",20,"2022-09-07 14:33:18","buyer@vmo.com",0,"admin",0,"$2a$12$S7oZitr9f1qerDHqSAimpOJxw/GdLt/bL3h68x2BrFASgIszcVL4a","2022-09-07 14:33:18","buyer");
-- #
INSERT INTO account(address, age, create_at, email, enabled, full_name, is_deleted, password, updated_at, username) VALUES ("18 Ton That Thuyet",21,"2022-09-07 14:33:18","supplier@vmo.com",0,"admin",0,"$2a$12$S7oZitr9f1qerDHqSAimpOJxw/GdLt/bL3h68x2BrFASgIszcVL4a","2022-09-07 14:33:18","supplier");
-- #
INSERT INTO users_roles(user_id, role_id) VALUES (1,1);
-- #
INSERT INTO users_roles(user_id, role_id) VALUES (2,2);
-- #
INSERT INTO users_roles(user_id, role_id) VALUES (3,3);
-- #