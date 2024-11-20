/*==============================================================*/
/* DBMS name:      MySQL 8.0                                    */
/* Created on:     11/9/2024 4:59:00 PM                         */
/*==============================================================*/
CREATE DATABASE Eduvault_DB;

Use Eduvault_DB;

-- Xóa các ràng buộc khóa ngoại trước khi xóa bảng
-- alter table DELETE_RECORDS 
--    drop foreign key FK_DELETE_DELETE_DOCUMENT;

-- alter table DELETE_RECORDS 
--    drop foreign key FK_DELETE_DELETE2_TRASH;

-- alter table DELETE_RECORDS 
--    drop foreign key FK_DELETE_DELETE3_FOLDER;

alter table DOCUMENT 
   drop foreign key FK_DOCUMENT_OF_TYPEOFDO;

alter table DOCUMENT 
   drop foreign key FK_DOCUMENT_RELATIONS_USER;

alter table DOCUMENT 
   drop foreign key FK_DOCUMENT_RELATIONS_FOLDER;

alter table FOLDER 
   drop foreign key FK_FOLDER_RELATIONS_USER;

alter table NOTIFICATION 
   drop foreign key FK_NOTIFICA_RELATIONS_TYPEOFNO;

alter table NOTIFICATION 
   drop foreign key FK_NOTIFICA_RELATIONS_USER;

-- Xóa các bảng nếu tồn tại
drop table if exists DELETE_RECORDS;
drop table if exists DOCUMENT;
drop table if exists FOLDER;
drop table if exists NOTIFICATION;
drop table if exists TRASH;
drop table if exists TYPEOFDOCUMENT;
drop table if exists TYPEOFNOTIFICATION;
drop table if exists USER;

/*==============================================================*/
/* Table: DOCUMENT                                              */
/*==============================================================*/
create table DOCUMENT
(
   DOC_ID               int not null,
   FOLDER_ID            int,
   USER_ID              int not null,
   TYPEDOC_ID           int not null,
   DOC_NAME             varchar(512) not null,
   SUMMARY              varchar(2000),
   CREATEDATE           datetime not null,
   DOC_PATH             varchar(1500) not null,
   isDeleted           smallint not null,
   primary key (DOC_ID)
);

/*==============================================================*/
/* Table: FOLDER                                                */
/*==============================================================*/
create table FOLDER
(
   FOLDER_ID            int not null,
   USER_ID              int not null,
   FOLDER_NAME          varchar(512) not null,
   FOLDER_CREATEAT      datetime not null,
   isDeleted           smallint not null,
   primary key (FOLDER_ID)
);

/*==============================================================*/
/* Table: NOTIFICATION                                          */
/*==============================================================*/
create table NOTIFICATION
(
   NOTI_ID              int not null,
   TYPENOTI_ID          int not null,
   USER_ID              int,
   NOTI_CONTENT         varchar(2048) not null,
   NOTI_READ            smallint,
   NOTI_TIME            datetime not null,
   primary key (NOTI_ID)
);

/*==============================================================*/
/* Table: TRASH                                                 */
/*==============================================================*/
create table TRASH
(
   TRASH_ID             int not null,
   ITEM_ID              int not null,
   ITEM_TYPE            varchar(10) not null,
   TRASH_DELETEAT       datetime not null,
   primary key (TRASH_ID)
);

/*==============================================================*/
/* Table: TYPEOFDOCUMENT                                        */
/*==============================================================*/
create table TYPEOFDOCUMENT
(
   TYPEDOC_ID           int not null,
   TYPEDOC_NAME         varchar(1024) not null,
   TYPEDOC_DESCRIPTION  varchar(2048),
   primary key (TYPEDOC_ID)
);

/*==============================================================*/
/* Table: TYPEOFNOTIFICATION                                    */
/*==============================================================*/
create table TYPEOFNOTIFICATION
(
   TYPENOTI_ID          int not null,
   TYPENOTI_NAME        varchar(1024) not null,
   primary key (TYPENOTI_ID)
);

/*==============================================================*/
/* Table: USER                                                  */
/*==============================================================*/
create table USER
(
   USER_ID              int not null,
   USERNAME             varchar(200) not null,
   PASSWORD             varchar(512) not null,
   EMAIL                varchar(512),
   FULLNAME             varchar(200) not null,
   AVATAR               varchar(600),
   USER_CREATEAT        datetime not null,
   primary key (USER_ID)
);

/*==============================================================*/
/* Khóa ngoại cho DELETE_RECORDS                                */
/*==============================================================*/
-- alter table DELETE_RECORDS add constraint FK_DELETE_DELETE_DOCUMENT foreign key (DOC_ID)
--       references DOCUMENT (DOC_ID) on delete restrict on update restrict;

-- alter table DELETE_RECORDS add constraint FK_DELETE_DELETE2_TRASH foreign key (TRASH_ID)
--       references TRASH (TRASH_ID) on delete restrict on update restrict;

-- alter table DELETE_RECORDS add constraint FK_DELETE_DELETE3_FOLDER foreign key (FOLDER_ID)
--       references FOLDER (FOLDER_ID) on delete restrict on update restrict;

/*==============================================================*/
/* Khóa ngoại cho DOCUMENT                                      */
/*==============================================================*/
alter table DOCUMENT add constraint FK_DOCUMENT_OF_TYPEOFDO foreign key (TYPEDOC_ID)
      references TYPEOFDOCUMENT (TYPEDOC_ID) on delete restrict on update restrict;

alter table DOCUMENT add constraint FK_DOCUMENT_RELATIONS_USER foreign key (USER_ID)
      references USER (USER_ID) on delete restrict on update restrict;

alter table DOCUMENT add constraint FK_DOCUMENT_RELATIONS_FOLDER foreign key (FOLDER_ID)
      references FOLDER (FOLDER_ID) on delete restrict on update restrict;

/*==============================================================*/
/* Khóa ngoại cho FOLDER                                        */
/*==============================================================*/
alter table FOLDER add constraint FK_FOLDER_RELATIONS_USER foreign key (USER_ID)
      references USER (USER_ID) on delete restrict on update restrict;

/*==============================================================*/
/* Khóa ngoại cho NOTIFICATION                                  */
/*==============================================================*/
alter table NOTIFICATION add constraint FK_NOTIFICA_RELATIONS_TYPEOFNO foreign key (TYPENOTI_ID)
      references TYPEOFNOTIFICATION (TYPENOTI_ID) on delete restrict on update restrict;

alter table NOTIFICATION add constraint FK_NOTIFICA_RELATIONS_USER foreign key (USER_ID)
      references USER (USER_ID) on delete restrict on update restrict;


/*==============================================================*/
/* Dữ liệu mẫu cho TABLE: TYPEOFDOCUMENT                       */
/*==============================================================*/
INSERT INTO TYPEOFDOCUMENT (TYPEDOC_ID, TYPEDOC_NAME, TYPEDOC_DESCRIPTION)
VALUES
(1, 'PDF', 'Portable Document Format'),
(2, 'Word', 'Microsoft Word Document'),
(3, 'Excel', 'Microsoft Excel Spreadsheet');

/*==============================================================*/
/* Dữ liệu mẫu cho TABLE: USER                                 */
/*==============================================================*/
INSERT INTO USER (USER_ID, USERNAME, PASSWORD, EMAIL, FULLNAME, AVATAR, USER_CREATEAT)
VALUES
(1, 'admin', 'admin123', 'admin@example.com', 'Admin User', 'admin_avatar.png', '2024-11-18 08:00:00'),
(2, 'johndoe', 'password123', 'johndoe@example.com', 'John Doe', 'johndoe_avatar.png', '2024-11-18 08:30:00'),
(3, 'janedoe', 'password456', 'janedoe@example.com', 'Jane Doe', 'janedoe_avatar.png', '2024-11-18 09:00:00');

/*==============================================================*/
/* Dữ liệu mẫu cho TABLE: TYPEOFNOTIFICATION                   */
/*==============================================================*/
INSERT INTO TYPEOFNOTIFICATION (TYPENOTI_ID, TYPENOTI_NAME)
VALUES
(1, 'Document Upload'),
(2, 'Document Delete'),
(3, 'Folder Creation');

/*==============================================================*/
/* Dữ liệu mẫu cho TABLE: NOTIFICATION                         */
/*==============================================================*/
INSERT INTO NOTIFICATION (NOTI_ID, TYPENOTI_ID, USER_ID, NOTI_CONTENT, NOTI_READ, NOTI_TIME)
VALUES
(1, 1, 1, 'New document has been uploaded to your folder.', 0, '2024-11-18 11:15:00'),
(2, 2, 1, 'Your document has been successfully deleted.', 1, '2024-11-18 11:20:00'),
(3, 3, 2, 'New folder created for your documents.', 0, '2024-11-18 11:30:00');

/*==============================================================*/
/* Dữ liệu mẫu cho TABLE: TRASH                                */
/*==============================================================*/
INSERT INTO TRASH (TRASH_ID, ITEM_ID, ITEM_TYPE, TRASH_DELETEAT)
VALUES
(1, 1, 'DOCUMENT', '2024-11-18 11:00:00'),
(2, 2, 'DOCUMENT', '2024-11-18 11:10:00'),
(3, 3, 'FOLDER', '2024-11-18 11:15:00');



/*==============================================================*/
/* Dữ liệu mẫu cho TABLE: FOLDER                               */
/*==============================================================*/
INSERT INTO FOLDER (FOLDER_ID, USER_ID, FOLDER_NAME, FOLDER_CREATEAT, isDeleted)
VALUES
(1, 1, 'Folder 1', '2024-11-18 09:00:00', 0),
(2, 1, 'Folder 2', '2024-11-18 09:30:00', 0),
(3, 2, 'Folder 3', '2024-11-18 10:00:00', 0);

/*==============================================================*/
/* Dữ liệu mẫu cho TABLE: DOCUMENT                             */
/*==============================================================*/
INSERT INTO DOCUMENT (DOC_ID, FOLDER_ID, USER_ID, TYPEDOC_ID, DOC_NAME, SUMMARY, CREATEDATE, DOC_PATH, isDeleted)
VALUES
(1, 1, 1, 1, 'Document 1', 'Summary of Document 1', '2024-11-18 10:00:00', 'documents/doc1.pdf', 0),
(2, 1, 1, 2, 'Document 2', 'Summary of Document 2', '2024-11-18 10:30:00', 'documents/doc2.docx', 0),
(3, 2, 1, 3, 'Document 3', 'Summary of Document 3', '2024-11-18 11:00:00', 'documents/doc3.xlsx', 0);

DESCRIBE DOCUMENT;



