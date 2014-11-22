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
	expectedDate char(20) not null,
	deliveredDate char(20) not null,
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

drop table if exists returned;
create table returned
	(retid int not null,
	date char(20) null,
	recId int not null,
	primary key(retid),
    Foreign Key(recId) References Purchase(receiptId)
    );

drop table if exists ReturnItem;
create table ReturnItem
	(retid int not null,
	upc int not null,
	quantity int not null,
	primary key (retid, upc),
	foreign key (retid) references returned(retid),
    foreign key (upc) references Item(upc));

 show tables;
 