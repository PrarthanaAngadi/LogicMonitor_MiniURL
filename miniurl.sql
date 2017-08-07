
CREATE SCHEMA `miniurl` ;

use miniurl;

create table linkinfo(
	shortUrl  char(6) Primary Key,
    longUrl  varchar(2083),
    isBlackListed bool,
    creation_timestamp timestamp DEFAULT current_timestamp
);

create unique index longUrl_index
on linkinfo(longUrl);
