INSERT INTO product (category,currency,amount,issue)
VALUES
("LAPTOP", "USD", 600,"scratched screen"),
("LAPTOP", "EUR", 700,"scratched screen/overused charger "),
("LAPTOP", "RON", 200,"almost new"),
("HEADPHONE", "RON", 100,"almost new/missing case"),
("HEADPHONE", "EUR", 15,"slightly used"),
("PHONE", "RON", 1000,"slightly scratched screen"),
("PHONE", "RON", 1500,"slightly scratched screen/scratched cover"),
("KEYBOARD", "RON", 130,"almost new"),
("KEYBOARD", "RON", 100,"almost new"),
("PHONE", "RON", 500,"scratched screen/chipped edges/missing accessories")
;

INSERT INTO details (name, detail_value, product_id)
VALUES
("brand","ASUS", 1),
("model","ASUS1", 1),
("operating_system","WINDOWS", 1),
("memory_size","RAM16GB", 1),
("display","20", 1),

("brand","ASUS", 2),
("model","ASUS2", 2),
("operating_system","LINUX", 2),
("memory_size","RAM32GB", 2),
("display","16", 2),

("brand","DELL", 3),
("model","DELL1", 3),
("operating_system","LINUX", 3),
("memory_size","RAM8GB", 3),
("display","21", 3),

("brand","Skullcandy", 4),
("type","in-ear", 4),

("brand","JBL", 5),
("type","in-ear", 5),

("brand","SAMSUNG", 6),
("model","SAMSUNG 1", 6),
("operating_system","ANDROID", 6),
("memory_size","RAM4GB", 6),
("dualSim","yes", 6),

("brand","XIAOMI", 7),
("model","XIAOMI 1", 7),
("operating_system","ANDROID", 7),
("memory_size","RAM8GB", 7),
("dualSim","yes", 7),

("brand","LOGITECH", 8),
("type","wireless", 8),

("brand","LOGITECH", 9),
("type","with wire", 9),

("brand","HUAWEI", 10),
("model","XIAOMI 1", 10),
("operating_system","ANDROID", 10),
("memory_size","RAM4GB", 10),
("dualSim","no", 10);

INSERT INTO stock (product_id,number_of_items)
VALUES
 (1,8),
 (2, 7),
 (3,2),
 (4,14),
 (5,32),
 (6,5),
 (7,3),
 (8,0),
 (9,4),
 (10,13);