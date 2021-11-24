CREATE TABLE ACCOUNT(
  account_number VARCHAR(10) NOT NULL,
  pin           VARCHAR(4) NOT NULL,
  balance       INTEGER,
  overdraft     INTEGER,
  PRIMARY KEY(account_number)
);

CREATE TABLE CASHRECORD(
  denomination INTEGER NOT NULL,
  count        INTEGER NOT NULL,
  PRIMARY KEY(denomination)
)
