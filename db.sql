-- Create the database with UTF8 encoding and locale settings
CREATE DATABASE ct224_project
    ENCODING 'UTF8'
    LC_COLLATE 'en_US.utf8'
    LC_CTYPE 'en_US.utf8'
    TEMPLATE template0;

-- Create a user with a password
CREATE USER ct224u WITH PASSWORD <password>;

-- Grant all privileges on the database to the user
GRANT ALL PRIVILEGES ON DATABASE ct224_project TO ct224u;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO ct224u;

-- Create tables for DB ct224_project
CREATE TABLE SINHVIEN (
    MASV VARCHAR(8) PRIMARY KEY,
    TEN_SV VARCHAR(50) NOT NULL,
    MAT_KHAU VARCHAR(65) NOT NULL,
    TRANGTHAI_SV BOOLEAN NOT NULL DEFAULT TRUE,
    DEL_FLG BOOLEAN NOT NULL DEFAULT FALSE,
    CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE GIANGVIEN (
    MACB VARCHAR(8) PRIMARY KEY,
    TEN_CB VARCHAR(50) NOT NULL,
    MAT_KHAU VARCHAR(65) NOT NULL,
    TRANGTHAI_CB BOOLEAN NOT NULL DEFAULT TRUE,
    DEL_FLG BOOLEAN NOT NULL DEFAULT FALSE,
    CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE QUANTRIVIEN (
    MAQTV VARCHAR(8) PRIMARY KEY,
    MAT_KHAU VARCHAR(65) NOT NULL,
    CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE HKNK (
    HOCKI VARCHAR(1) NOT NULL,
    NIENKHOA VARCHAR(10) NOT NULL,
    TRANG_THAI_DKHP BOOLEAN NOT NULL DEFAULT FALSE,
    CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (HOCKI, NIENKHOA)
);

CREATE TABLE HOCPHAN (
    MAHP VARCHAR(8) PRIMARY KEY,
    TEN_HP VARCHAR(50) NOT NULL,
    SO_TC INT NOT NULL,
    DEL_FLG BOOLEAN NOT NULL DEFAULT FALSE,
    CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE LOPHP (
    MAHP VARCHAR(8) NOT NULL,
    NHOMHP VARCHAR(2) NOT NULL,
    HOCKI VARCHAR(1) NOT NULL,
    NIENKHOA VARCHAR(10) NOT NULL,
    MACB VARCHAR(8) NOT NULL,
    MAPH VARCHAR(8) NOT NULL,
    NGAY_HOC VARCHAR(10) NOT NULL,
    TIET_BAT_DAU INT NOT NULL,
    SO_TIET INT NOT NULL,
    SI_SO INT NOT NULL,
    DEL_FLG BOOLEAN NOT NULL DEFAULT FALSE,
    CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (MAHP, NHOMHP, HOCKI, NIENKHOA)
);

CREATE TABLE SINHVIEN_LOPHP (
    MAHP VARCHAR(8) NOT NULL,
    NHOMHP VARCHAR(2) NOT NULL,
    HOCKI VARCHAR(1) NOT NULL,
    NIENKHOA VARCHAR(10) NOT NULL,
    MASV VARCHAR(8) NOT NULL,
    DEL_FLG BOOLEAN NOT NULL DEFAULT FALSE,
    CREATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (MAHP, NHOMHP, HOCKI, NIENKHOA, MASV)
);