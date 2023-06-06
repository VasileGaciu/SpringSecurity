INSERT INTO customer (first_name, last_name) VALUES ('John', 'Doe');
INSERT INTO customer (first_name, last_name) VALUES ('Jane', 'Smith');
INSERT INTO customer (first_name, last_name) VALUES ('Michael', 'Johnson');
INSERT INTO customer (first_name, last_name) VALUES ('Emily', 'Williams');
INSERT INTO customer (first_name, last_name) VALUES ('David', 'Brown');

-- Assuming the foreign key relationship is defined as 'customer_id' in the 'account' table

-- Account for John Doe
INSERT INTO account (balance, customer_id) VALUES (1500.00, 1);

-- Account for Jane Smith
INSERT INTO account (balance, customer_id) VALUES (2000.00, 2);

-- Account for Michael Johnson
INSERT INTO account (balance, customer_id) VALUES (2500.00, 3);

-- Account for Emily Williams
INSERT INTO account (balance, customer_id) VALUES (3000.00, 4);

-- Account for David Brown
INSERT INTO account (balance, customer_id) VALUES (3500.00, 5);

INSERT INTO role (role) VALUES ('user');
INSERT INTO role (role) VALUES ('admin');

-- Admin Users
INSERT INTO users (username, password, role_id)
VALUES ('John', '$2a$10$/wbAb7aOYpVetRBptgQDSO.VjT5dW4y58yek19VwxYW2uYhGAMEoq', 1);
INSERT INTO users (username, password, role_id)
VALUES ('Paul', '$2a$10$/wbAb7aOYpVetRBptgQDSO.VjT5dW4y58yek19VwxYW2uYhGAMEoq', 1);

-- User Users
INSERT INTO users (username, password, role_id)
VALUES ('Edward', '$2a$10$/wbAb7aOYpVetRBptgQDSO.VjT5dW4y58yek19VwxYW2uYhGAMEoq', 2);
INSERT INTO users (username, password, role_id)
VALUES ('Jane', '$2a$10$/wbAb7aOYpVetRBptgQDSO.VjT5dW4y58yek19VwxYW2uYhGAMEoq', 2);
