CREATE TABLE customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

CREATE TABLE account (
    id INT AUTO_INCREMENT PRIMARY KEY,
    balance DECIMAL(10, 2),
    customer_id INT,
    FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE role (
  id INT PRIMARY KEY AUTO_INCREMENT,
  role VARCHAR(10)
);

CREATE TABLE users (
  id INT PRIMARY KEY AUTO_INCREMENT,
  username VARCHAR(20),
  password VARCHAR(100),
  role_id INT,
  FOREIGN KEY (role_id) REFERENCES role(id)
);
