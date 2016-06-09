create database db_loteria;
use db_loteria;

--drop database db_loteria;

create table tbl_sorteo(
    id int auto_increment,
    pozo int,
    numeros_sorteo varchar(60),
    rut_admin varchar(5),
    total_ganadores int,
    abierto bit,
    primary key(id)  
);

create table tbl_boleto(
    id int auto_increment,
    id_sorteo int,
    numeros_boleto varchar(60),
    aciertos int,
    premio int,
    primary key(id),
    foreign key(id_sorteo) references tbl_sorteo(id)
);

/*
update tbl_sorteo set estado=0 where id=2;
update tbl_sorteo set estado=0 where id=1;
update tbl_boleto set aciertos=3 where id=2;

select count(id) from tbl_sorteo
select count(id) from tbl_boleto where id_sorteo=4 and aciertos>=2;

select*from tbl_sorteo;
select*from tbl_boleto;

select*from tbl_boleto where id=4;


select id from tbl_sorteo;
select max(id) from tbl_sorteo;
select numeros_sorteo from tbl_sorteo where id=2;
select estado from tbl_sorteo where id=1;
select tbl_boleto.id as 'id_boleto', 
    tbl_boleto.numeros_boleto, 
    tbl_sorteo.numeros_sorteo as 'numeros sorteo',
    tbl_boleto.aciertos,
    tbl_sorteo.total_ganadores,
    tbl_sorteo.id as 'id_sorteo'
    from tbl_boleto inner join tbl_sorteo 
    on tbl_boleto.id_sorteo= tbl_sorteo.id;

select count(id) from tbl_boleto where id_sorteo=5 and aciertos=4; 

select count(id) from tbl_boleto where id_sorteo=1 and aciertos>=2
delete from tbl_boleto;
delete from tbl_sorteo;

drop database db_loteria;

select tbl_boleto.id as 'id_boleto' , tbl_boleto.id_sorteo, tbl_boleto.aciertos from tbl_boleto where tbl_boleto.id_sorteo=3;

select 
tbl_boleto.id as 'id_boleto', 
tbl_boleto.numeros_boleto, 
tbl_sorteo.numeros_sorteo as 'numeros sorteo' 
from tbl_boleto inner join tbl_sorteo on tbl_boleto.id_sorteo= tbl_sorteo.id where tbl_Sorteo.id=1

select tbl_boleto.id as 'id_boleto', tbl_boleto.aciertos, tbl_sorteo.pozo from tbl_boleto inner join tbl_sorteo on tbl_boleto.id_sorteo=tbl_sorteo.id;

*/
-- update tbl_boleto set premio=7000 where id=3;

-- select premio from tbl_boleto where id_sorteo=6 and aciertos=2;
-- select*from tbl_sorteo;
