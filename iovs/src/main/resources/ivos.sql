# Complete SQL (No vehicles bound to fences)
DROP DATABASE IF EXISTS ivos;
CREATE DATABASE ivos CHARACTER SET utf8;
USE ivos;

# User table
DROP TABLE IF EXISTS user;
CREATE TABLE user(
                     id bigint AUTO_INCREMENT PRIMARY KEY COMMENT 'User ID',
                     username varchar(20) COMMENT 'Username',
                     password varchar(20) COMMENT 'Password',
                     email varchar(50) COMMENT 'Email',
                     phone varchar(20) COMMENT 'Phone number',
                     age int COMMENT 'Age',
                     gender varchar(10) COMMENT 'Gender: Female 0, Male 1',
                     create_time datetime COMMENT 'Creation time',
                     update_time datetime COMMENT 'Update time',
                     status varchar(10) COMMENT 'Status: Enabled 0, Disabled 1',
                     level varchar(10) COMMENT 'Employee level',
                     parent_id bigint COMMENT 'Superior ID'
) COMMENT 'User table' auto_increment = 101;

INSERT INTO user(id,username, password, email, phone, age, gender, create_time, update_time, status, level,parent_id) VALUES
                                                                                                                          (101, 'shaoyun', '123456', 'tom@example.org', '13600001234', 58, '1', '2024-07-28 16:45:00', '2024-07-28 16:45:00', '1', '40', null),
                                                                                                                          (102, 'mike', '123456', 'tom@example.org', '13600001234', 38, '1', '2024-07-28 16:45:00', '2024-07-28 16:45:00', '0', '30', 101),
                                                                                                                          (103, 'tom', '123456', 'moly@example.org', '13622321234', 38, '1', '2023-12-28 16:45:00', '2024-07-28 16:45:00', '0', '30', 101),
                                                                                                                          (104, 'lucy', '123456', 'lucy@163.com', '13511112222', 43, '0', '2024-07-29 16:45:00', '2024-07-29 09:15:00', '1', '20', 102),
                                                                                                                          (105, 'lily', '123456', 'lily@example.co', '13433334444', 45, '1', '2024-07-30 16:45:00', '2024-07-30 11:00:00', '1', '20', 102),
                                                                                                                          (106, 'moly', '123456', 'mike@example.co', '13433332226', 40, '1', '2024-07-30 16:45:00', '2024-07-30 11:00:00', '1', '20', 103),
                                                                                                                          (107, 'rose', '123456', 'rose@qq.com', '13355556666', 31, '0', '2024-07-31 16:45:00', '2024-07-31 17:30:00', '1', '10', 104),
                                                                                                                          (108, 'linda', '123456', 'linda@qq.com', '13355556667', 14, '0', '2024-07-31 16:45:00', '2024-07-31 17:30:00', '1', '10', 105),
                                                                                                                          (109, 'baby', '123456', 'baby@gmail.com', '13277778888', 35, '1', '2024-05-01 16:45:00', '2024-05-01 13:45:00', '0', '10', 105),
                                                                                                                          (110, 'alice', '123456', 'alice@gmail.com', '13277778880', 25, '1', '2024-08-02 16:45:00', '2024-08-02 13:45:00', '0', '10', 106),
                                                                                                                          (111, 'bob', '123456', 'bob@gmail.com', '13277778880', 27, '1', '2024-06-10 16:45:00', '2024-06-10 13:45:00', '1', '10', 106),
                                                                                                                          (112, 'admin', 'admin', 'admin@163.com', '13600009999', 30, '1', '2024-01-01 16:45:00', '2024-01-01 16:45:00', '1', '10', 106);


# Vehicle table
DROP TABLE IF EXISTS vehicle;
CREATE TABLE vehicle
(
    id                   bigint auto_increment comment 'Vehicle ID' primary key,
    brand                varchar(30)  null comment 'Vehicle brand',
    license              varchar(20)  null comment 'License plate number',
    model                varchar(100) null comment 'Vehicle model',
    code                 varchar(100) null comment 'Vehicle identification number',
    displacement         varchar(20)  null comment 'Engine displacement',
    status               varchar(10)  null comment 'Vehicle status: Available 1, Occupied 2',
    type                 varchar(50)  null comment 'Vehicle type',
    color                varchar(10)  null comment 'Vehicle color',
    kilometers           varchar(10)  null comment 'Mileage',
    reg_time             datetime     null comment 'Registration time',
    buy_time             datetime     null comment 'Purchase time',
    price            varchar(10)  null comment 'Purchase price',
    battery_type         varchar(50)  null comment 'Battery type',
    create_time          datetime     null comment 'Creation time',
    update_time          datetime     null comment 'Update time',
    geofence_bind_status varchar(10)  null comment 'Geofence binding status: Bound 1, Unbound 0',
    geofence_id          bigint       null comment 'Geofence ID',
    constraint code unique (code),
    constraint license unique (license)
) comment 'Vehicle table' auto_increment=101 ;

INSERT INTO vehicle (id, brand, license, model, code, displacement, status, type, color, kilometers,
                     reg_time, buy_time, price, battery_type, create_time, update_time, geofence_bind_status, geofence_id) VALUES
                                                                                                                               (101, 'Mercedes', 'A22323', 'EV300', 'WBA9011', '2', '1', '10', '20', '35465', '2022-10-19 00:00:00', '2022-10-09 00:00:00', '350000', '60', '2022-10-09 00:00:00', '2024-05-19 19:37:28', '0', null),
                                                                                                                               (102, 'BMW', 'B28323', 'EV300', 'WBA9012', '2', '1', '10', '20', '35465', '2022-10-19 00:00:00', '2022-10-09 00:00:00', '350000', '60', '2022-10-09 00:00:00', '2024-05-19 19:44:50', '0', null),
                                                                                                                               (103, 'Audi', 'C29323', 'EV300', 'WBA9013', '2', '1', '10', '20', '35465', '2022-10-19 00:00:00', '2022-10-09 00:00:00', '350000', '60', '2022-10-09 00:00:00', '2023-12-26 00:00:00', '0', null),
                                                                                                                               (104, 'BMW', 'D96552', 'Mercedes X5', 'WBA9014', '2', '1', '30', '20', '1200', '2023-06-28 00:00:00', '2023-06-28 00:00:00', '119999', '60', '2022-10-09 00:00:00', '2024-05-19 19:39:56', '0', null),
                                                                                                                               (105, 'Peugeot', 'E56552', 'Peugeot X1', 'WBA9015', '3', '1', '20', '30', '12000', '2023-06-28 00:00:00', '2023-06-28 00:00:00', '1200000', '70', '2022-10-09 00:00:00', '2024-05-19 19:39:08', '0', null),
                                                                                                                               (106, 'Bentley', 'F56552', 'MUV', 'WBA9016', '3', '1', '20', '30', '32321', '2023-07-19 00:00:00', '2023-07-11 00:00:00', '24324', '50', '2022-10-09 00:00:00', '2024-05-19 19:41:35', '0', null),
                                                                                                                               (107, 'Tesla', 'G12345', 'model3', 'WBA9017', '3', '1', '10', '10', '2000', '2023-06-28 00:00:00', '2023-07-04 00:00:00', '200000', '10', '2022-10-09 00:00:00', '2024-05-19 19:42:59', '0', null),
                                                                                                                               (108, 'Buick', 'H56552', 'SUV', 'WBA9018', '2', '1', '10', '10', '21', '2023-07-08 00:00:00', '2023-07-03 00:00:00', '2000000', '20', '2022-10-09 00:00:00', '2024-05-19 19:41:47', '0', null),
                                                                                                                               (109, 'Mercedes', 'I56552', 'SUV', 'WBA9019', '2', '1', '10', '10', '20', '2023-07-26 00:00:00', '2023-07-19 00:00:00', '300000', '30', '2022-10-09 00:00:00', '2024-05-19 19:42:42', '0', null);

# Geofence table
DROP TABLE IF EXISTS geofence;
CREATE TABLE geofence
(
    id          bigint auto_increment comment 'Geofence ID' primary key,
    name        varchar(50) null comment 'Geofence name',
    status      varchar(10) null comment 'Geofence status: Enabled 1, Disabled 0',
    position    text        null comment 'Geofence coordinates',
    create_time datetime    null comment 'Creation time',
    update_time datetime    null comment 'Update time'
) comment 'Geofence table'   auto_increment=101;

INSERT INTO geofence (id, name, status, position, create_time, update_time) VALUES
                                                                                (101, 'Auto Parts Transport Fence', '1', '{"type":"rectangle","recPoints":"116.37883388478262-39.91607152946697,116.38896677093518-39.91607152946697,116.38896677093518-39.91186556472842,116.37883388478262-39.91186556472842"}', '2024-05-21 18:04:52',null),
                                                                                (102, 'Printing Material Transport Fence', '0', '{"type":"rectangle","recPoints":"116.37883388478262-39.91607152946697,116.38896677093518-39.91607152946697,116.38896677093518-39.91186556472842,116.37883388478262-39.91186556472842"}', '2024-05-21 18:04:52',null),
                                                                                (103, 'Chemical Material Transport Fence', '0', '{"type":"circle","latitude":"39.91059265566227","radius":"491","longitude":"116.43445696196046"}', '2024-05-21 18:05:42',null),
                                                                                (104, 'Electrical Transport Fence', '1', '{"type":"circle","latitude":"40.02103907882358","radius":"346","longitude":"116.35887799248964"}', '2024-05-22 10:05:32',null);


# Dictionary table
DROP TABLE IF EXISTS dict;
CREATE TABLE dict
(
    id          bigint auto_increment comment 'Auto-increment ID' primary key,
    name        varchar(64) null comment 'Dictionary name',
    code        varchar(64) null comment 'Dictionary code',
    remark      text        null comment 'Dictionary description',
    create_time datetime    null comment 'Creation time',
    update_time datetime    null comment 'Update time'
) comment 'Dictionary table' auto_increment=101;

INSERT INTO dict (id, name, code, remark, create_time, update_time) VALUES
                                                                        (101, 'Vehicle Color', 'vehicle_color', 'Manage vehicle colors', '2024-04-28 23:32:28',null),
                                                                        (102, 'Battery Type', 'battery_type', 'Manage battery types', '2024-04-28 23:33:22',null),
                                                                        (103, 'Vehicle Type', 'vehicle_type', 'Manage vehicle types', '2024-04-28 23:34:16',null),
                                                                        (104, 'Status', 'general_status', 'Manage enabled/disabled status', '2024-04-28 23:47:43',null),
                                                                        (105, 'Application Status', 'application_status', 'Manage application status', '2024-04-28 23:55:08',null),
                                                                        (106, 'Audit Status', 'audit_status', 'Manage audit status', '2024-04-29 00:05:36',null),
                                                                        (107, 'Employee Level', 'user_level', 'Manage employee levels', '2024-04-28 23:32:28',null);


# Dictionary item table
DROP TABLE IF EXISTS dict_option;
CREATE TABLE dict_option
(
    id          bigint auto_increment comment 'Dictionary item ID' primary key,
    dict_id     bigint      null comment 'Dictionary table ID',
    label       varchar(64) null comment 'Dictionary item name',
    value       varchar(64) null comment 'Dictionary item value',
    sort        int         null comment 'Sort order',
    remark      text        null comment 'Remarks',
    create_time datetime    null comment 'Creation time',
    update_time datetime    null comment 'Update time'
) comment 'Dictionary item table' auto_increment=101;

INSERT INTO dict_option (id, dict_id, label, value, sort, remark, create_time, update_time) VALUES
                                                                                                (101, 101, 'White', '10', 1, 'Vehicle color', '2024-04-28 23:38:16',null),
                                                                                                (102, 101, 'Gray', '20', 2, 'Vehicle color', '2024-04-28 23:38:16',null),
                                                                                                (103, 101, 'Black', '30', 3, 'Vehicle color', '2024-04-28 23:38:16',null),
                                                                                                (104, 101, 'Silver', '40', 4, 'Vehicle color', '2024-04-28 23:38:16',null),
                                                                                                (105, 101, 'Red', '50', 5, 'Vehicle color', '2024-04-28 23:38:16',null),
                                                                                                (106, 101, 'Green', '60', 6, 'Vehicle color', '2024-04-28 23:38:16',null),
                                                                                                (107, 102, 'Lead-acid Battery', '10', 1, 'Battery type', '2024-04-28 23:38:16',null),
                                                                                                (108, 102, 'Nickel-metal Hydride Battery', '20', 2, 'Battery type', '2024-04-28 23:38:16',null),
                                                                                                (109, 102, 'Sodium-sulfur Battery', '30', 3, 'Battery type', '2024-04-28 23:38:16',null),
                                                                                                (110, 102, 'Lithium-ion Battery', '40', 4, 'Battery type', '2024-04-28 23:38:16',null),
                                                                                                (111, 102, 'Air Battery', '50', 5, 'Battery type', '2024-04-28 23:38:16',null),
                                                                                                (112, 102, 'Ternary Lithium Battery', '60', 6, 'Battery type', '2024-04-28 23:38:16',null),
                                                                                                (113, 102, 'Alkaline Fuel Cell', '70', 7, 'Battery type', '2024-04-28 23:38:16',null),
                                                                                                (114, 103, 'Sedan', '10', 1, 'Vehicle type', '2024-04-28 23:38:16',null),
                                                                                                (115, 103, 'Bus', '20', 2, 'Vehicle type', '2024-04-28 23:38:16',null),
                                                                                                (116, 103, 'Truck', '30', 3, 'Vehicle type', '2024-04-28 23:38:16',null),
                                                                                                (117, 103, 'Trailer', '40', 4, 'Vehicle type', '2024-04-28 23:38:16',null),
                                                                                                (118, 104, 'Enabled', '1', 1, 'General status', '2024-04-28 23:38:16',null),
                                                                                                (119, 104, 'Disabled', '0', 2, 'General status', '2024-04-28 23:38:16',null),
                                                                                                (120, 105, 'Initiated', '10', 1, 'Worker just initiated application, can be canceled', '2024-04-28 23:38:16',null),
                                                                                                (121, 105, 'Canceled', '20', 2, 'Worker canceled application', '2024-04-28 23:38:16',null),
                                                                                                (122, 105, 'Under Review', '30', 3, 'At least one manager approved', '2024-04-28 23:38:16',null),
                                                                                                (123, 105, 'Rejected', '40', 4, 'Status after manager rejection', '2024-04-28 23:38:16',null),
                                                                                                (124, 105, 'Approved', '50', 5, 'All managers approved, vehicle can be assigned', '2024-04-28 23:38:16',null),
                                                                                                (125, 105, 'Vehicle Assigned', '60', 6, 'Assigned available vehicle to worker', '2024-04-28 23:38:16',null),
                                                                                                (126, 105, 'Work Order Completed', '70', 7, 'Worker returned vehicle', '2024-04-28 23:38:16',null),
                                                                                                (127, 106, 'Pending My Review', '10', 1, 'Application pending current manager review', '2024-04-28 23:38:16',null),
                                                                                                (128, 106, 'Pending Others Review', '20', 2, 'Wait for previous managers before current manager reviews', '2024-04-28 23:38:16',null),
                                                                                                (129, 106, 'Reviewed', '30', 3, 'Current manager approved', '2024-04-28 23:38:16',null),
                                                                                                (130, 106, 'Rejected', '40', 4, 'Current manager rejected', '2024-04-28 23:38:16',null),
                                                                                                (131, 107, 'Staff', '10', 1, null, '2024-05-28 23:38:16',null),
                                                                                                (132, 107, 'Manager', '20', 2, 'Project manager can review regular staff applications', '2024-05-28 23:38:16',null),
                                                                                                (133, 107, 'Director', '30', 3, 'Director can review manager applications', '2024-05-28 23:38:16',null),
                                                                                                (134, 107, 'CEO', '40', 4, 'CEO can review director applications', '2024-05-28 23:38:16',null);

# Vehicle application table
DROP TABLE IF EXISTS application;
create table application
(
    id               bigint auto_increment comment 'Vehicle application ID' primary key,
    user_id          bigint       null comment 'Applicant',
    username         varchar(10)  null comment 'Applicant name',
    start_time       datetime     null comment 'Vehicle start time',
    end_time         datetime     null comment 'Vehicle end time',
    img_url          varchar(100) null comment 'Driver license image',
    departure_addr   varchar(100) null comment 'Departure address',
    destination_addr varchar(100) null comment 'Destination address',
    reason           varchar(100) null comment 'Application reason',
    remark           varchar(100) null comment 'Other remarks',
    status           varchar(10)  null comment 'Application status: Initiated 10, Canceled 20, Under Review 30, Rejected 40, Approved 50, Vehicle Assigned 60, Work Order Completed 70',
    vehicle_id       bigint       null comment 'Assigned vehicle information',
    reject_reason    varchar(100) null comment 'Rejection reason',
    create_time      datetime     null comment 'Creation time',
    update_time      datetime     null comment 'Update time'
) comment 'Vehicle application table' auto_increment=101;

INSERT INTO application (id, user_id, username, start_time, end_time, img_url, departure_addr,
                         destination_addr, reason, remark, status, vehicle_id, reject_reason, create_time, update_time) VALUES
                                                                                                                            (101, 112, 'admin', '2024-05-08 00:00:00', '2024-05-09 00:00:00', '/2024/05/24/16866304-fa55-42e8-a1dc-2b14962f6f50.png', 'Beijing', 'Shanghai', 'Business trip', 'No high-speed train tickets available', '30', null, null, '2024-05-24 23:49:28', '2024-05-24 23:55:58'),
                                                                                                                            (102, 112, 'admin', '2024-05-15 00:00:00', '2024-05-16 00:00:00', '/2024/05/24/dbf44761-9c79-436e-9c1c-56b2d6446a96.png', 'Shanghai', 'Beijing', 'Return trip', 'Drive the car back on the way', '10', null, null, '2024-05-24 23:50:08', '2024-05-24 23:57:05'),
                                                                                                                            (103, 112, 'admin', '2024-05-28 00:00:00', '2024-05-29 00:00:00', '/2024/05/24/a193c739-c42e-454b-8c98-f657a4f1d7a3.png', 'Wanshou Road', 'Xuezhiyuan', 'Moving', 'Need a truck', '40', null, 'C1 license cannot drive trucks', '2024-05-24 23:51:08', '2024-05-24 23:54:35'),
                                                                                                                            (104, 106, 'moly', '2024-05-08 10:58:43', '2024-05-08 16:58:43', '/2024/05/24/a3f75734-0151-40fa-af05-d981ff3def92.png', 'Xuezhiyuan', 'Dazhong Temple', 'Pick up belongings', 'Round trip same day', '70', 101, null, '2024-05-24 23:59:33', '2024-05-25 10:12:33'),
                                                                                                                            (105, 106, 'moly', '2024-05-08 14:58:43', '2024-05-08 16:58:43', '/2024/05/25/7814de14-d1af-453c-a064-a8d2d005abd4.png', 'Dazhong Temple', 'Xuezhiyuan', 'Return trip after packing', 'Previous vehicle returned', '60', 102, null, '2024-05-25 00:01:12', '2024-05-25 12:02:33'),
                                                                                                                            (106, 103, 'tom', '2024-05-07 16:06:03', '2024-05-08 17:18:03', '/2024/05/25/6f733919-4a1e-4c6b-84a8-6f5fd18cbdce.png', 'Xuezhiyuan', 'Lychee Building', 'Meeting', 'Meeting', '50', null, null, '2024-05-25 00:06:46', '2024-05-25 00:16:33'),
                                                                                                                            (107, 103, 'tom', '2024-05-15 00:07:44', '2024-05-16 00:00:00', '/2024/05/25/337f0ed0-5251-4377-9780-1ca07f332c12.png', 'Lychee Building', 'Xuezhiyuan', 'Return for class', 'Attending class', '20', null, null, '2024-05-25 00:08:32', '2024-05-25 00:10:32');


# Audit table
DROP TABLE IF EXISTS audit;
CREATE TABLE audit(
                      id bigint PRIMARY KEY AUTO_INCREMENT COMMENT 'Audit ID',
                      application_id bigint COMMENT 'Application ID',
                      audit_user_id bigint COMMENT 'Auditor ID',
                      audit_status varchar(10) COMMENT 'Audit status: Pending My Review 10, Pending Others Review 20, Reviewed 30, Rejected 40',
                      audit_sort int(10) COMMENT 'Audit order',
                      create_time datetime COMMENT 'Creation time',
                      update_time datetime COMMENT 'Update time'
) comment 'Audit table' auto_increment=101;

INSERT INTO audit (id, application_id, audit_user_id, audit_status, audit_sort, create_time, update_time) VALUES
                                                                                                              (101, 101, 106, '30', 0,'2024-05-24 23:49:28','2024-05-24 23:52:28'),
                                                                                                              (102, 101, 103, '10', 1,'2024-05-24 23:49:28','2024-05-24 23:55:58'),
                                                                                                              (103, 102, 106, '10', 0,'2024-05-24 23:50:08','2024-05-24 23:53:38'),
                                                                                                              (104, 102, 103, '20', 1,'2024-05-24 23:50:08','2024-05-24 23:57:05'),
                                                                                                              (105, 103, 106, '30', 0,'2024-05-24 23:51:08','2024-05-24 23:52:19'),
                                                                                                              (106, 103, 103, '40', 1,'2024-05-24 23:51:08','2024-05-24 23:54:35'),
                                                                                                              (107, 104, 103, '30', 0,'2024-05-24 23:59:33','2024-05-25 00:02:54'),
                                                                                                              (108, 104, 101, '30', 1,'2024-05-24 23:59:33','2024-05-25 00:05:33'),
                                                                                                              (109, 105, 103, '30', 0,'2024-05-25 00:01:12','2024-05-25 00:12:31'),
                                                                                                              (110, 105, 101, '30', 1,'2024-05-25 00:01:12','2024-05-25 00:06:29'),
                                                                                                              (111, 106, 101, '30', 0,'2024-05-25 00:06:46','2024-05-25 00:16:33');