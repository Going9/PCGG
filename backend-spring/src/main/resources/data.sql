--delete from authority;
insert into authority (authority_name) values ('ROLE_ADMIN');
insert into authority (authority_name) values ('ROLE_USER');

INSERT INTO user (activated, email, password, name, nickname) values (1, 'ssafy1', '$2a$10$0k4ub.MV3ZCdI8xBB0H/sOb2OtMuqMJ2YjMb8/Fa7xEzAj.7CCH3m', 'test', 'test');
INSERT INTO user (activated, email, password, name, nickname) values (1, 'ssafy2', '$2a$10$0k4ub.MV3ZCdI8xBB0H/sOb2OtMuqMJ2YjMb8/Fa7xEzAj.7CCH3m', 'test', 'test');

INSERT INTO user_authority (id, authority_name) values (1, 'ROLE_USER'), (2, 'ROLE_USER');

INSERT INTO peripheral_keyboard (name, lprice, hprice, brand, image_source, link, extinct) VALUES ('키보드1', 10000, null, 'aa', 'image path url1', 'link1', 0);
INSERT INTO peripheral_keyboard (name, lprice, hprice, brand, image_source, link, extinct) VALUES ('키보드2', 10000, null, 'aa', 'image path url2', 'link2', 0);

INSERT INTO part_type_ns values('cpu'),('gpu'),('ram'),('power'),('mainboard'),('cooler'),('chassis'),('ssd'),('laptop');
INSERT INTO usage_ns values('가성비사무'),('고성능사무'),('캐주얼게임'),('중사양게임'),('고사양게임'),('일반영상편집'),('전문영상편집'),('3D디자인'),('일반방송'),('캐주얼게임방송'),('고성능게임방송'),('고사양개발');

INSERT INTO part_cpu (name, price, image_source, extinct, socket_info, ddr4, ddr5, integrated_graphics, cooler_included) VALUES ('cpu', 100, 'IS', true, '소켓정보', true, true, true, true);
INSERT INTO part_mainboard (name, price, image_source, extinct, grade, memory_spec, size, pcie3, pcie4, pcie5, m2_count) VALUES ('mainboard', 200, 'IS', false, '점수', '스펙', 30, true, true, true, 1);
INSERT INTO part_ssd (name, price, image_source, extinct, pcie_ver, reading_speed, writing_speed) VALUES ('ssd', 300, 'IS', false, 1, 3, 5);
INSERT INTO part_ram (name, price, image_source, extinct, memory_spec, memory_clock, heat_sink) VALUES ('ram', 400, 'IS', false, 'ms', 3, false);
INSERT INTO part_gpu (name, price, image_source, extinct, needed_power, width, thickness) VALUES ('gpu', 500, 'IS', true, true, 1.1, 2.2);
INSERT INTO part_chassis (name, price, image_source, extinct, extended_atx, standard_atx, micro_atx, mini_itx, width, depth, max_power_depth, max_gpu_depth, max_cooler_depth) VALUES ('chassis', 600, 'IS', true, true, true, true, true, 1.1, 2.2, 3.3, 4.4, 5.5);
INSERT INTO part_power (name, price, image_source, extinct, size, grade, output, depth, free_warranty_period) VALUES ('power', 100, 'IS', true, 1, 'grade', 101, 3.3, 5);
INSERT INTO part_cooler (name, price, image_source, extinct, form, type, free_warranty_period, height, fan_count, max_fan_noise) VALUES ('cooler', 100, 'IS', true, '소켓정보', '타입', 3, 3.3, 5, 5.1);

INSERT INTO peripheral_type_ns(name) VALUES ('keyboard'), ('monitor'), ('printer'), ('mouse'), ('etc');
