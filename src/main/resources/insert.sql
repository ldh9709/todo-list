INSERT INTO users (users_id, users_password, users_name)  VALUES ('qwe123', '{bcrypt}$2a$10$2Vj/MwAon9U1UFSHcF8DSe9VtT86qtIFmWo2.tocHk1Px1NAXFU3K', '이도현');

INSERT INTO category (category_name, users_no)  VALUES ('루틴', '1');

INSERT INTO category (category_name, users_no)  VALUES ('습관', '1');

INSERT INTO category (category_name, users_no)  VALUES ('필수', '1');

INSERT INTO todo (todo_title, todo_content, todo_completed, users_no, category_no)  VALUES ('아침 기상', '아침 7시 기상', '0', '1', '1');

INSERT INTO todo (todo_title, todo_content, todo_completed, users_no, category_no)  VALUES ('물 마시기', '하루 2리터', '0', '1', '2');

INSERT INTO todo (todo_title, todo_content, todo_completed, users_no, category_no)  VALUES ('공부', '코딩 공부', '0', '1', '3');
