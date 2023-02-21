INSERT INTO order_status VALUES(1,'registered');
INSERT INTO order_status VALUES(2,'paid');
INSERT INTO order_status VALUES(3,'canceled');

INSERT INTO hotel_type VALUES(1,'Chain hotels');
INSERT INTO hotel_type VALUES(2,'Motels');
INSERT INTO hotel_type VALUES(3,'Resorts');
INSERT INTO hotel_type VALUES(4,'Inns');
INSERT INTO hotel_type VALUES(5,'All-suites');
INSERT INTO hotel_type VALUES(6,'Conference');

INSERT INTO offer_type VALUES(1,'rest');
INSERT INTO offer_type VALUES(2,'excursion');
INSERT INTO offer_type VALUES(3,'shopping');

INSERT INTO user_role VALUES(1,'user');
INSERT INTO user_role VALUES(2,'manager');
INSERT INTO user_role VALUES(3,'admin');

insert into hotel values(1,'Bucharest Grand Hotel', 1, 'Bucharest');
insert into hotel values(2,'Budapest Grand Hotel', 2, 'Budapest');
insert into hotel values(3,'Cairo Grand Hotel', 3, 'Cairo');
insert into hotel values(4,'Canberra Grand Hotel', 4, 'Canberra');
insert into hotel values(5,'Doha Grand Hotel', 5, 'Doha');
insert into hotel values(6,'Istanbul Grand Hotel', 6, 'Istanbul');
insert into hotel values(7,'Kyiv Grand Hotel', 1, 'Kyiv');
insert into hotel values(8,'London Grand Hotel', 2, 'London');
insert into hotel values(9,'Male Grand Hotel', 3, 'Male');
insert into hotel values(10,'NewYork Grand Hotel', 4, 'NewYork');
insert into hotel values(11,'Paris Grand Hotel', 5, 'Paris');
insert into hotel values(12,'Rijeka Grand Hotel', 6, 'Rijeka');
insert into hotel values(13,'Zagreb Grand Hotel', 1, 'Zagreb');


insert into offer values(1,'OF1',1,1,200,0.1,true,true,300);
insert into offer values(2,'OF2',2,2,200,0.15,false,true,400);
insert into offer values(3,'OF3',3,3,200,0.1,false,true,500);
insert into offer values(4,'OF4',4,1,200,0.15,false,true,300);
insert into offer values(5,'OF5',5,2,200,0.1,false,true,400);
insert into offer values(6,'OF6',6,3,200,0.2,false,true,500);
insert into offer values(7,'OF7',7,1,200,0.15,true,true,300);
insert into offer values(8,'OF8',8,2,200,0.1,false,true,400);
insert into offer values(9,'OF9',9,3,200,0.2,false,true,500);
insert into offer values(10,'OF10',10,1,200,0.15,false,true,300);
insert into offer values(11,'OF11',11,2,200,0.1,false,true,400);
insert into offer values(12,'OF12',12,3,200,0.2,false,true,500);
insert into offer values(13,'OF13',13,1,200,0.1,true,true,200);
