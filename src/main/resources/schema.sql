-- Tabla de productos
CREATE TABLE IF NOT EXISTS products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    stock INT NOT NULL
);

-- Tabla de sucursales
CREATE TABLE IF NOT EXISTS branches (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

-- Tabla de franquicias
CREATE TABLE IF NOT EXISTS franchises (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL
);

-- Tabla de productos por sucursal
CREATE TABLE IF NOT EXISTS branch_products (
    id INT PRIMARY KEY AUTO_INCREMENT,
    branch_id INT NOT NULL,
    product_id INT NOT NULL,
    FOREIGN KEY (branch_id) REFERENCES branches(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

-- Tabla de franquicias por sucursal
CREATE TABLE IF NOT EXISTS franchise_branch (
    id INT PRIMARY KEY AUTO_INCREMENT,
    franchise_id INT NOT NULL,
    branch_id INT NOT NULL,
    FOREIGN KEY (franchise_id) REFERENCES franchises(id),
    FOREIGN KEY (branch_id) REFERENCES branches(id)
);
