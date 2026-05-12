-- TV Radio Management System Database Schema for XAMPP
-- Run this script in phpMyAdmin or MySQL command line

CREATE DATABASE IF NOT EXISTS tv_radio_management_system_db;
USE tv_radio_management_system_db;

-- Channels table
CREATE TABLE IF NOT EXISTS channels (
    channel_id INT AUTO_INCREMENT PRIMARY KEY,
    channel_name VARCHAR(100) NOT NULL,
    channel_type VARCHAR(20) NOT NULL,
    frequency VARCHAR(50),
    launch_date DATE,
    website VARCHAR(200),
    contact_email VARCHAR(100),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Employees table
CREATE TABLE IF NOT EXISTS employees (
    employee_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    department VARCHAR(50),
    hire_date DATE,
    salary DECIMAL(10, 2),
    email VARCHAR(100),
    phone VARCHAR(20),
    address VARCHAR(200),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Programs table
CREATE TABLE IF NOT EXISTS programs (
    program_id INT AUTO_INCREMENT PRIMARY KEY,
    program_name VARCHAR(100) NOT NULL,
    program_type VARCHAR(50),
    category VARCHAR(50),
    description TEXT,
    duration_min INT,
    target_audience VARCHAR(50),
    channel_id INT,
    production_cost DECIMAL(10, 2),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (channel_id) REFERENCES channels(channel_id) ON DELETE SET NULL
);

-- Program Schedules table (note: DAO uses 'program_schedule' singular)
CREATE TABLE IF NOT EXISTS program_schedule (
    schedule_id INT AUTO_INCREMENT PRIMARY KEY,
    program_id INT,
    employee_id INT,
    air_date DATE,
    start_time TIME,
    end_time TIME,
    repeat_pattern VARCHAR(50),
    status VARCHAR(50),
    expected_audience INT,
    actual_audience INT,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (program_id) REFERENCES programs(program_id) ON DELETE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE SET NULL
);

-- Program Assignments table
CREATE TABLE IF NOT EXISTS program_assignments (
    assignment_id INT AUTO_INCREMENT PRIMARY KEY,
    program_id INT,
    employee_id INT,
    assignment_role VARCHAR(50),
    assignment_date DATE,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (program_id) REFERENCES programs(program_id) ON DELETE CASCADE,
    FOREIGN KEY (employee_id) REFERENCES employees(employee_id) ON DELETE CASCADE
);

-- Advertisements table
CREATE TABLE IF NOT EXISTS advertisements (
    ad_id INT AUTO_INCREMENT PRIMARY KEY,
    advertiser_name VARCHAR(100) NOT NULL,
    product_category VARCHAR(50),
    ad_duration_sec INT,
    cost_per_airing DECIMAL(10, 2),
    total_budget DECIMAL(10, 2),
    contact_person VARCHAR(100),
    contact_phone VARCHAR(20),
    contact_email VARCHAR(100),
    start_date DATE,
    end_date DATE,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX idx_channel_type ON channels(channel_type);
CREATE INDEX idx_channel_active ON channels(is_active);
CREATE INDEX idx_employee_role ON employees(role);
CREATE INDEX idx_employee_active ON employees(is_active);
CREATE INDEX idx_program_channel ON programs(channel_id);
CREATE INDEX idx_program_active ON programs(is_active);
CREATE INDEX idx_schedule_program ON program_schedule(program_id);
CREATE INDEX idx_schedule_employee ON program_schedule(employee_id);
CREATE INDEX idx_schedule_date ON program_schedule(air_date);
CREATE INDEX idx_assignment_program ON program_assignments(program_id);
CREATE INDEX idx_assignment_employee ON program_assignments(employee_id);
CREATE INDEX idx_ad_active ON advertisements(is_active);
CREATE INDEX idx_ad_dates ON advertisements(start_date, end_date);

