CREATE TABLE IF NOT EXISTS abbonati (
    code VARCHAR(8) NOT NULL UNIQUE,
    magazineName VARCHAR(100) NOT NULL,
    secondName VARCHAR(50) NOT NULL,
    name VARCHAR(50) NOT NULL,
    address VARCHAR(100) NOT NULL, 
    gender VARCHAR(20) NOT NULL,
    city VARCHAR(100) NOT NULL,

    PRIMARY KEY(code)
);