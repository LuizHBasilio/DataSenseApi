CREATE TABLE equipment_data (
    id SERIAL PRIMARY KEY,
    equipment_id VARCHAR(255) NOT NULL,
    timestamp TIMESTAMP WITH TIME ZONE NOT NULL,
    value NUMERIC(5, 2) NOT NULL
);