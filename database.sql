create user c##fortisbank identified by root;

grant connect, resource to c##fortisbank;

grant all privileges to c##fortisbank;

CREATE Table customer(  
    customerId NUMBER,
    fname VARCHAR2(50),
    lname VARCHAR2(50),
    pin NUMBER,
    PRIMARY KEY (customerId)
);

create table account(
    accountnumber NUMBER,
    balance float,
    openeddate Date,
    accounttype VARCHAR2(20),
    customerid number,
    PRIMARY KEY (accountnumber),
    FOREIGN KEY (customerid) REFERENCES customer(customerid)
);


CREATE TABLE checkingaccount(
    accountnumber NUMBER,
    freeLimitperMonth int,
    FOREIGN KEY (accountnumber) REFERENCES account(accountnumber)
);


CREATE TABLE savingaccount(
    accountnumber NUMBER,
    annualinterest FLOAT,
    annualgain float,
    extrafee float,
    FOREIGN KEY (accountnumber) REFERENCES account(accountnumber)
);


create table transaction(
    transactionNumber number,
    description varchar2(100),
    transDate date,
    transType varchar2(20),
    transAmount float,
    accountType varchar2(20),
    customerId NUMBER,
    FOREIGN KEY (customerId) REFERENCES customer(customerId),
    PRIMARY KEY (transactionNumber)
);


