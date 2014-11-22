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
    foreign key (upc) references Item(upc));

	
insert into Customer values (1, 'hello1', 'Avatar Korra', 'Republic City', '123');
insert into Customer values (2, 'hello2', 'G. Grumpy', '123 2nd Street', '456');
insert into Customer values (3, 'hello3', 'R. Rusty', '123 3rd Street', '789');
insert into Customer values (4, 'hello4', 'A. Braca', '123 4th Street', '321');
insert into Customer values (5, 'hello5', 'D. Abra', '123 5th Street', '654');
insert into Customer values (6, 'hello6', 'S. Uper', '123 6th Street', '987');
insert into Customer values (7, 'hello7', 'C. AliFragi', '123 7th Street', '135');
insert into Customer values (8, 'hello8', 'L. Istic', '123 8th Street', '246');
insert into Customer values (9, 'hello9', 'E.X. Pial', '123 9th Street', '357');
insert into Customer values (10, 'hello10', 'I. Docious', '123 10th Street', '468');

insert into Item values (1, 'LOK: Book 1', 'CD', 'Instrumental', 'Nick', 2012, 12.99, 15);
insert into Item values (2, 'Transistor Soundtrack', 'CD', 'Instrumental', 'Super Giant Games', 2014, 5.99, 10);
insert into Item values (3, 'ACDC videos', 'DVD', 'Rock', 'The Company', 2000, 7.00, 12);
insert into Item values (4, 'My Head Is an Animal', 'CD', 'Pop', 'The Company 2', 2012, 10.00, 7);
insert into Item values (5, 'Night Visions', 'CD', 'Rock', 'The Company', 2012, 15.00, 17);
insert into Item values (6, 'Thriller Music Video', 'DVD', 'Pop','The Company 2', 1999, 3.00, 22);
insert into Item values (7, 'Bastion Original Soundtrack', 'CD', 'Instrumental','Super Giant Games', 2010, 5.99, 13);
insert into Item values (8, 'Smooth Criminal Video', 'DVD', 'Pop', 'The Company 2', 2000, 3.00, 7);

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





	
 show tables;
 