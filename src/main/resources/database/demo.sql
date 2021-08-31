drop table item;

select * from users;
select * from company;
select * from ITEM;
select * from UPLOAD_FILE;

select * from orders;

select * from ORDER_ITEM;

select * from DELIVERY;

insert into item(id, DTYPE, PRICE, STOCK_QUANTITY)
values (ITEM_SEQ.nextval,'F', 10000 , 10, 'aaa');


select table_name,constraint_name
from user_constraints;

SELECT
    OBJECT_NAME
     ,OBJECT_TYPE
FROM
    USER_OBJECTS
WHERE OBJECT_NAME LIKE '%item%';

select * from upload_file;

select * from users;

update users set role = 'ROLE_ADMIN' where username = 'admin';
commit;

select * from item;