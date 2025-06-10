/* 0) 사용할 DB 선택 또는 생성 */
/* CREATE DATABASE IF NOT EXISTS todolist CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE todolist; */

/* 1) 기존 테이블 제거 */
DROP TABLE IF EXISTS todo;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS users;

/* 2) 사용자 테이블 */
CREATE TABLE users (
    users_no        INT AUTO_INCREMENT NOT NULL,
    users_id        VARCHAR(50)  NOT NULL,
    users_password  VARCHAR(100) NOT NULL,
    users_name      VARCHAR(30)  NOT NULL,
    users_role            VARCHAR(20)  NOT NULL DEFAULT 'ROLE_USER',
    PRIMARY KEY (users_no)
);

/* 3) 카테고리 테이블 */
CREATE TABLE category (
    category_no   INT AUTO_INCREMENT NOT NULL,
    category_name VARCHAR(50) NOT NULL,
    users_no      INT NOT NULL,
    PRIMARY KEY (category_no),
    CONSTRAINT FK_category_users FOREIGN KEY (users_no)
        REFERENCES users (users_no)
        ON DELETE CASCADE
);

/* 4) 할 일 테이블 */
CREATE TABLE todo (
    todo_no         INT AUTO_INCREMENT NOT NULL,
    todo_title      VARCHAR(255) NOT NULL,
    todo_content    VARCHAR(255),
    todo_completed  TINYINT(1)  DEFAULT 0,
    todo_created_at DATETIME    DEFAULT CURRENT_TIMESTAMP,
    users_no        INT NOT NULL,
    category_no     INT,
    PRIMARY KEY (todo_no),
    CONSTRAINT FK_todo_users    FOREIGN KEY (users_no)    REFERENCES users (users_no),
    CONSTRAINT FK_todo_category FOREIGN KEY (category_no) REFERENCES category (category_no)
);
