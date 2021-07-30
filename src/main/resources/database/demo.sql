drop table item;

select * from ITEM;
select * from UPLOAD_FILE;

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