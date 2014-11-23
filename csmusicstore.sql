-- CPSC 304 Project Part 3
-- Creates a database for Allegro Music Store.
-- Creates tables for this database.

create database csmusicstore;
use csmusicstore;


drop table if exists Item;
create table Item
   	 (upc int not null,
	 title varchar(40) not null,
   	 type varchar(3) not null,
  	 category varchar(40) not null,
  	 company varchar(40) not null,
  	 year int not null,
  	 price float not null,
  	 stock int not null,
  	 Primary Key(upc));
 
drop table if exists LeadSinger;
create table LeadSinger
    (upc int not null,
    name varchar(40) not null,
    Primary Key(upc, name),
    Foreign Key(upc) References Item (upc));

drop table if exists HasSong;
create table HasSong
	(upc int not null,
	title varchar(40) not null,
	primary key (upc, title),
	foreign key (upc) references Item(upc));
    
drop table if exists Customer;
create table Customer (
	cid int not null,
	password varchar(40) not null,
	name varchar(40) not null,
	address varchar(40) not null,
	phone varchar(40) not null,
	PRIMARY KEY(cid)
);
   
drop table if exists Purchase;
create table Purchase (
	receiptId int not null,
	date char(20) not null,
	cid int not null,
	card_num int not null,
	expiryDate char(20) not null,
	expectedDate char(20),
	deliveredDate char(20),
	Primary Key(receiptId),
	foreign key (cid) references Customer(cid)
	);


drop table if exists PurchaseItem;
create table PurchaseItem
	(receiptId int not null,
	upc int not null,
	quantity int not null,
	Primary Key(upc, receiptId),
	Foreign Key(upc) References Item (upc),
	Foreign Key(receiptId) References Purchase(receiptId)
);

drop table if exists Returned;
create table Returned
	(retid int not null,
	date char(20) null,
	receiptId int not null,
	primary key(retid),
    Foreign Key(receiptId) References Purchase(receiptId)
    );

drop table if exists ReturnItem;
create table ReturnItem
	(retid int not null,
	upc int not null,
	quantity int not null,
	primary key (retid, upc),
	foreign key (retid) references returned(retid),
    foreign key (upc) references PurchaseItem(upc));

	
insert into Customer values (1, 'hello1', 'Korra', 'Republic City', '123');
insert into Customer values (2, 'hello2', 'Mako', '123 2nd Street', '456');
insert into Customer values (3, 'hello3', 'Asami', '123 3rd Street', '789');
insert into Customer values (4, 'hello4', 'Bolin', '123 4th Street', '321');
insert into Customer values (5, 'hello5', 'Tenzin', '123 5th Street', '654');
insert into Customer values (6, 'hello6', 'Aang', '123 6th Street', '987');
insert into Customer values (7, 'hello7', 'Katara', '123 7th Street', '135');
insert into Customer values (8, 'hello8', 'Toph', '123 8th Street', '246');
insert into Customer values (9, 'hello9', 'Sokka', '123 9th Street', '357');
insert into Customer values (10, 'hello10', 'Zuko', '123 10th Street', '468');

insert into Item values (1, 'LOK: Book 1', 'cd', 'instrumental', 'Nick', 2012, 12.99, 15);
insert into Item values (2, 'Transistor Soundtrack', 'cd', 'rap', 'Super Giant Games', 2014, 5.99, 10);
insert into Item values (3, 'ACDC videos', 'dvd', 'rock', 'The Company', 2000, 7.00, 12);
insert into Item values (4, 'My Head Is an Animal', 'cd', 'country', 'The Company 2', 2012, 10.00, 7);
insert into Item values (5, 'Night Visions', 'cd', 'new age', 'The Company', 2012, 15.00, 17);
insert into Item values (6, 'Thriller Music Video', 'dvd', 'pop','The Company 2', 1999, 3.00, 22);
insert into Item values (7, 'Bastion Original Soundtrack', 'cd', 'classical','Super Giant Games', 2010, 5.99, 13);
insert into Item values (8, 'Smooth Criminal Video', 'dvd', 'pop', 'The Company 2', 2000, 3.00, 7);

insert into LeadSinger values (1, 'Jeremy Zuckerman');
insert into LeadSinger values (2, 'Darren Korb');
insert into LeadSinger values (2, 'Ashley Barrett');
insert into LeadSinger values (3, 'ACDC');
insert into LeadSinger values (4, 'Of Monsters and Men');
insert into LeadSinger values (5, 'Imagine Dragons');
insert into LeadSinger values (6, 'Michael Jackson');
insert into LeadSinger values (7, 'Darren Korb');
insert into LeadSinger values (7, 'Ashley Barrett');
insert into LeadSinger values (8, 'Michael Jackson');

insert into HasSong values(1, 'Prologue');
insert into HasSong values(1, 'Air Tight');
insert into HasSong values(1, 'Greatest Change');
insert into HasSong values(2, 'The Spine');
insert into HasSong values(2, 'Paper Boats');
insert into HasSong values(3, 'Hells Bells');
insert into HasSong values(3, 'Money Talks');
insert into HasSong values(4, 'Dirty Paws');
insert into HasSong values(5, 'Radioactive');
insert into HasSong values(6, 'Thriller');
insert into HasSong values(7, 'Mother, Im Here');
insert into HasSong values(7, 'Setting Sail, Coming Home');
insert into HasSong values(8, 'Smooth Criminal');

insert into Purchase values (1, '01/01/14', 1, 50, '01/15/16', null, null);
insert into Purchase values (2, '01/05/14', 1, 50, '02/05/16', '02/11/14', '02/09/14');
insert into Purchase values (3, '01/01/14', 2, 51, '02/01/16', null, null);
insert into Purchase values (4, '01/01/14', 2, 51, '02/01/15', null, null);
insert into Purchase values (5, '01/03/14', 3, 52, '02/03/16', '01/10/14', '01/10/14');
insert into Purchase values (6, '02/20/14', 4, 53, '03/20/17', '02/25/14', '02/27/14');
insert into Purchase values (7, '04/01/14', 9, 58, '05/01/16', null, null);
insert into Purchase values (8, '05/05/14', 5, 54, '06/05/17', '06/11/14', '06/09/14');
insert into Purchase values (9, '06/01/14', 6, 55, '07/01/15', null, null);
insert into Purchase values (10, '07/01/14', 8, 57, '08/01/17', null, null);
insert into Purchase values (11, '08/03/14', 7, 56, '09/03/15', '09/10/14', '09/10/14');
insert into Purchase values (12, '09/20/14', 1, 50, '10/20/19', '10/25/14', '10/27/14');
insert into Purchase values (13, '01/01/14', 2, 51, '01/15/16', null, null);
insert into Purchase values (14, '01/01/14', 2, 51, '01/15/16', null, null);
insert into Purchase values (15, '01/01/14', 3, 52, '01/15/16', null, null);
insert into Purchase values (16, '01/01/14', 3, 52, '01/15/16', null, null);
insert into Purchase values (17, '01/01/14', 1, 50, '01/15/16', null, null);
insert into Purchase values (18, '01/01/14', 1, 50, '01/15/16', null, null);

insert into PurchaseItem values (1, 1, 1);
insert into PurchaseItem values (1, 2, 2);
insert into PurchaseItem values (2, 3, 10);
insert into PurchaseItem values (2, 1, 1);
insert into PurchaseItem values (2, 2, 2);
insert into PurchaseItem values (3, 3, 3);
insert into PurchaseItem values (3, 6, 1);
insert into PurchaseItem values (4, 4, 4);
insert into PurchaseItem values (4, 5, 1);
insert into PurchaseItem values (5, 7, 1);
insert into PurchaseItem values (6, 7, 1);
insert into PurchaseItem values (7, 3, 3);
insert into PurchaseItem values (7, 1, 3);
insert into PurchaseItem values (8, 6, 1);
insert into PurchaseItem values (9, 4, 4);
insert into PurchaseItem values (10, 5, 1);
insert into PurchaseItem values (11, 7, 1);
insert into PurchaseItem values (12, 7, 1);

insert into PurchaseItem values (13, 8, 1);
insert into PurchaseItem values (13, 7, 2);
insert into PurchaseItem values (14, 3, 10);
insert into PurchaseItem values (14, 1, 1);
insert into PurchaseItem values (15, 2, 2);
insert into PurchaseItem values (15, 8, 3);
insert into PurchaseItem values (16, 6, 1);
insert into PurchaseItem values (16, 4, 4);
insert into PurchaseItem values (17, 5, 1);
insert into PurchaseItem values (17, 7, 1);
insert into PurchaseItem values (18, 8, 1);
insert into PurchaseItem values (18, 3, 3);
insert into PurchaseItem values (12, 1, 3);
insert into PurchaseItem values (11, 6, 1);
insert into PurchaseItem values (1, 8, 4);
insert into PurchaseItem values (1, 5, 1);
insert into PurchaseItem values (7, 7, 1);
insert into PurchaseItem values (10, 7, 1);

insert into Returned values (1, '01/02/14', 1);
insert into Returned values (2, '01/03/14', 1);
insert into Returned values (3, '01/05/14', 3);
insert into Returned values (4, '01/07/14', 12);

insert into ReturnItem values (1, 1, 1);
insert into ReturnItem values (2, 2, 1);
insert into ReturnItem values (3, 3, 1);
insert into ReturnItem values (3, 6, 3);
insert into ReturnItem values (4, 7, 1);

show tables;
 