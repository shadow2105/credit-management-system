USE java2;
-- SHOW TABLES;

-- DROP TABLE admins;
-- DROP TABLE customers;
-- DROP TABLE credit_accounts;
-- DROP TABLE transactions;

-- 1. creating tables and adding constraints

CREATE TABLE admins (
    user_id	VARCHAR(20),		
    first_name	VARCHAR(35)	NOT NULL,
    last_name	VARCHAR(35)	NOT NULL,
    passwd	VARCHAR(20)	NOT NULL );
    
ALTER TABLE admins
	ADD CONSTRAINT admins_user_id_pk
		PRIMARY KEY ( user_id );
   

CREATE TABLE customers (
    user_id	VARCHAR(20),		
    first_name	VARCHAR(35)	 NOT NULL,
    last_name	VARCHAR(35)	 NOT NULL,
    passwd	VARCHAR(20)	 NOT NULL, 
    address	VARCHAR(100)     NOT NULL,
    mobile_no	VARCHAR(10)	 NOT NULL UNIQUE,
    email 	VARCHAR(50)      NOT NULL UNIQUE,
    occupation  VARCHAR(30) );
    
ALTER TABLE customers
	ADD CONSTRAINT customers_user_id_pk
		PRIMARY KEY ( user_id );
      
      
CREATE TABLE credit_accounts (
    account_no		CHAR(16),
    credit_limit	NUMERIC(8, 2) NOT NULL,
    balance		NUMERIC(8, 2) NOT NULL, 
    user_id		VARCHAR(20)   NOT NULL );
    
ALTER TABLE credit_accounts
	ADD CONSTRAINT credit_accounts_account_no_pk
		PRIMARY KEY ( account_no );
        
            
CREATE TABLE transactions (
    transaction_id    VARCHAR(6),
    account_no	      CHAR(16),
    transaction_date  DATETIME	     NOT NULL,
    post_date         DATE,
    dscription        VARCHAR(100)   NOT NULL,
    transaction_type  CHAR(1)        NOT NULL,
    amount	      NUMERIC(10, 2) NOT NULL,
    closing_balance   NUMERIC(8, 2)  NOT NULL );
    
ALTER TABLE transactions
	ADD CONSTRAINT transactions_transaction_id_account_no_pk
		PRIMARY KEY ( transaction_id, account_no );
        
	-- adding Foreign Keys
    
-- user_id must be a valid customer
ALTER TABLE credit_accounts
	ADD CONSTRAINT credit_accounts_user_id_fk
		FOREIGN KEY ( user_id )				
			REFERENCES customers ( user_id );
        
-- account_no must be a valid credit_account
ALTER TABLE transactions
	ADD CONSTRAINT transactions_account_no_fk
		FOREIGN KEY ( account_no )				
			REFERENCES credit_accounts ( account_no );
    

-- 2. inserting values into tables

INSERT INTO admins VALUES ('admin1', 'Test', 'Admin', '123456');
INSERT INTO admins VALUES ('shadow99', 'Ashwin', 'Rajput', 'shadow');

-- SELECT * FROM admins;
-- SELECT * FROM customers;
-- SELECT * FROM credit_accounts;
-- SELECT * FROM transactions;

-- SELECT * FROM transactions WHERE account_no = '2816821598755183' AND MONTH(transaction_date) = '3' AND transaction_type = 'C' ORDER BY transaction_date;
-- SELECT closing_balance FROM transactions WHERE account_no = '9336937157713716' AND MONTH(transaction_date) = '3' ORDER BY transaction_date DESC LIMIT 1;
