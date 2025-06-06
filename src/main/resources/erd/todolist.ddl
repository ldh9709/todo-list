-- 기존 테이블 제거
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS todo;
DROP TABLE IF EXISTS users;

-- 사용자 테이블
CREATE TABLE users (
    users_no        INT AUTO_INCREMENT NOT NULL,
    users_id        VARCHAR(50) NOT NULL,
    users_password  VARCHAR(100) NOT NULL,
    users_name      VARCHAR(30) NOT NULL,
    CONSTRAINT IDX_users_PK PRIMARY KEY (users_no)
);

-- 카테고리 테이블
CREATE TABLE category (
    category_no     INT AUTO_INCREMENT NOT NULL,
    category_name   VARCHAR(50) NOT NULL,
    users_no      	INT NOT NULL,
    CONSTRAINT IDX_category_PK PRIMARY KEY (category_no)
);

-- 할 일 테이블
CREATE TABLE todo (
    todo_no         INT AUTO_INCREMENT NOT NULL,
    todo_title      VARCHAR(255) NOT NULL,
    todo_content    VARCHAR(255),
    todo_completed  TINYINT(1) DEFAULT 0,
    todo_created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    users_no        INT NOT NULL,
    category_no     INT,
    CONSTRAINT IDX_todo_PK PRIMARY KEY (todo_no),
    CONSTRAINT IDX_todo_FK0 FOREIGN KEY (users_no) REFERENCES users (users_no),
    CONSTRAINT IDX_todo_FK1 FOREIGN KEY (category_no) REFERENCES category (category_no)
);
