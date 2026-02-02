CREATE TABLE IF NOT EXISTS accounts (
    account_id BIGINT PRIMARY KEY,
    balance DECIMAL(20, 8) NOT NULL,
    CONSTRAINT chk_balance_non_negative CHECK (balance >= 0)
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    source_account_id BIGINT NOT NULL,
    destination_account_id BIGINT NOT NULL,
    amount DECIMAL(20, 8) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (source_account_id) REFERENCES accounts(account_id),
    FOREIGN KEY (destination_account_id) REFERENCES accounts(account_id),
    CONSTRAINT chk_amount_positive CHECK (amount > 0),
    CONSTRAINT chk_different_accounts CHECK (source_account_id != destination_account_id)
);

CREATE INDEX IF NOT EXISTS idx_source_account ON transactions(source_account_id);
CREATE INDEX IF NOT EXISTS idx_destination_account ON transactions(destination_account_id);
CREATE INDEX IF NOT EXISTS idx_created_at ON transactions(created_at);
