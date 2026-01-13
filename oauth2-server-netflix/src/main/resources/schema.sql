CREATE TABLE ACCOUNTS (
        id int PRIMARY KEY,
        full_name varchar(255) NOT NULL,
        email_id varchar(255) UNIQUE NOT NULL,
        email_verified boolean NOT NULL,
        password varchar(500) NOT NULL,
        password_last_set_date DATE,
        country VARCHAR(100)
);

CREATE TABLE PLANS (
    account_id int PRIMARY KEY,
    current_plan_type varchar(50),
    plan_expires_on DATE,
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES ACCOUNTS(id)
);