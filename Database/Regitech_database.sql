CREATE DATABASE STAGE;
-- \c STAGE;

-------------------------------------------------------------------------------------
-- //Thony
DROP TABLE client CASCADE;
DROP TABLE utilisateur CASCADE;
DROP TABLE type_user CASCADE;
DROP TABLE interlocuteur CASCADE;

DROP TABLE devis_produit  CASCADE;
DROP TABLE devis_detail CASCADE;

DROP TABLE bon_entree_produit CASCADE;
DROP TABLE bon_entree CASCADE;

DROP TABLE bon_sortie_produit CASCADE;
DROP TABLE bon_sortie CASCADE;

DROP TABLE reparation CASCADE;



------------------------------------------------------------------------------------------------
create table type_user(
      id serial primary key,
      designation varchar(100) unique not null
);

------------------------------------------------------------------------------------------------

create table client(
      id serial primary key,
      nom varchar(250) not null,
      adresse varchar(500),
      nif varchar(15),
      stat varchar(100),
      rcs varchar(100),
      cin varchar(100),
      permis varchar(100),
      passeport varchar(100),
      particulier boolean
);


------------------------------------------------------------------------------------------------
create table interlocuteur(
  id serial primary key,
  id_client INTEGER not null,
  nom VARCHAR(100) not null,
  contact VARCHAR(100) not null,
  email VARCHAR(100) not null,
  foreign key (id_client) references Client(id)
);

create or replace view client_inter as select client.id,client.adresse,client.nom,interlocuteur.nom as admins,interlocuteur.contact,interlocuteur.email,client.nif,client.stat,client.rcs,client.cin,client.permis,client.passeport,client.particulier from client JOIN interlocuteur ON client.id = interlocuteur.id_client;

------------------------------------------------------------------------------------------------



create table utilisateur(
      id serial primary key,
      id_type_user int not null,
      id_client int  null,
      nom varchar(250) not null,
      email varchar(150) not null,
      mdp varchar(50) not null,
      telephone varchar(50),
      foreign key (id_type_user) references type_user(id),
      foreign key (id_client) references Client(id)
);

--VIEW
create or replace VIEW
v_utilisateur as
select utilisateur.id, type_user.id as id_type_user,type_user.designation,utilisateur.nom,utilisateur.email
from utilisateur
join type_user on utilisateur.id_type_user = type_user.id
where id_client is null

------------------------------------------------------------------------------------------------

create table token(
      id int not null,
      token varchar(50) not null,
      dateco timestamp,
      foreign key(id) references utilisateur(id)
);

------------------------------------------------------------------------------------------------

--//DEVIS

CREATE SEQUENCE devis_seq ;
create table devis_detail(
  id_devis serial primary key not null,
  num_facture VARCHAR(100)  unique  not null DEFAULT 'AC_PF_' || to_char(nextval ('devis_seq'),'FM9990999') || '_'  || to_char(current_date,'DDMMYYYY'),
  dates DATE ,
  tech_id INTEGER,
  client_id INTEGER,
  objet VARCHAR(250),
  valide VARCHAR(250),
  disponibilite DATE,
  reglement VARCHAR(250),
  preconisaiton VARCHAR(250),
  garanti INTEGER,
  FOREIGN KEY (tech_id) REFERENCES utilisateur(id),
  FOREIGN KEY (client_id) REFERENCES client(id)
);
ALTER SEQUENCE devis_seq OWNED BY devis_detail.num_facture;



create table devis_produit(
  id serial primary key,
  num_facture VARCHAR(250),
  designation VARCHAR(250),
  quantite integer,
  prix_unitaire float,
  remise integer ,
  prix_total float
);


create or replace view v_temp as select devis_detail.num_facture,devis_detail.dates,devis_detail.tech_id,devis_detail.etat,devis_detail.objet,devis_detail.valide,devis_detail.disponibilite,devis_detail.reglement,devis_detail.preconisaiton,devis_detail.garanti,client_inter.nom,client_inter.adresse,client_inter.admins,client_inter.contact,client_inter.email,client_inter.nif,client_inter.stat,client_inter.rcs,client_inter.cin,client_inter.permis,client_inter.passeport,client_inter.particulier from  devis_detail join client_inter on devis_detail.client_id = client_inter.id;

create or replace view devis_client as select v_temp.num_facture,v_temp.dates,v_temp.etat,v_temp.tech_id,v_temp.objet,v_temp.valide,v_temp.disponibilite,v_temp.reglement,v_temp.preconisaiton,v_temp.garanti,v_temp.nom,v_temp.admins,v_temp.contact,v_temp.adresse,v_temp.email,v_temp.nif,v_temp.stat,v_temp.rcs,v_temp.cin,v_temp.permis,v_temp.passeport,v_temp.particulier,utilisateur.nom as tech,utilisateur.email as tech_mail,utilisateur.telephone as tech_contact from v_temp JOIN utilisateur ON v_temp.tech_id = utilisateur.id;
------------------------------------------------------------------------------------------------

-- //Bon Entree

CREATE SEQUENCE entree_seq ;
create table bon_entree(
  id_entree serial primary key,
  num_be VARCHAR(100)  not null DEFAULT 'AC_BE_' || to_char(nextval ('entree_seq'),'FM9990999') || '_'  || to_char(current_date,'DDMMYYYY'),
  dates DATE ,
  fournisseur VARCHAR(100),
  phone VARCHAR(100),
  etat VARCHAR(100),
  reparation INT DEFAULT 0,
  sortie INT DEFAULT 0,
  id_client INTEGER not null,
  motif VARCHAR(100),
  recepteur VARCHAR(100),
  observation TEXT,
  sign_recepteur TEXT,
  sign_client TEXT,
  num_achat VARCHAR(100),
  FOREIGN KEY (id_client) REFERENCES client(id)
);
ALTER SEQUENCE entree_seq OWNED BY bon_entree.num_be;

create table bon_entree_produit(
  id_be serial primary key not null,
  id_bon_entre integer,
  num_be VARCHAR(100) not null,
  reference VARCHAR(100),
  designation VARCHAR(100),
  quantite int,
  FOREIGN KEY (id_bon_entre) REFERENCES bon_entree(id_entree)
);
--VIEW
create or replace view bon_E as select bon_entree.num_be,bon_entree.dates,bon_entree.reparation,bon_entree.sortie,bon_entree.fournisseur,bon_entree.phone,bon_entree.etat,bon_entree.motif,bon_entree.recepteur,bon_entree.observation,bon_entree.sign_recepteur,bon_entree.sign_client,bon_entree.num_achat,client.nom,client.id,bon_entree.id_entree from  bon_entree join client on bon_entree.id_client = client.id;

------------------------------------------------------------------------------------------------

--//Bon de sortie

CREATE SEQUENCE sortie_seq ;
create table bon_sortie(
  id_sortie serial primary key,
  num_sortie VARCHAR(100)  not null DEFAULT 'AC_BS_' || to_char(nextval ('sortie_seq'),'FM9990999') || '_'  || to_char(current_date,'DDMMYYYY'),
  num_bon_entree VARCHAR(100) unique,
  dates DATE ,
  fournisseur VARCHAR(100),
  phone VARCHAR(100),
  etat VARCHAR(100) DEFAULT "0",
  id_client INTEGER not null,
  motif VARCHAR(100),
  recepteur VARCHAR(100),
  observation TEXT,
  sign_responsable TEXT,
  sign_recepteur TEXT,
  FOREIGN KEY (id_client) REFERENCES client(id)
);
ALTER SEQUENCE sortie_seq OWNED BY bon_sortie.num_sortie;

create table bon_sortie_produit(
  id_bsp serial primary key not null,
  id_bon_sortie integer,
  num_sortie VARCHAR(100),
  reference VARCHAR(100),
  designation VARCHAR(100),
  quantite int,
  FOREIGN KEY (id_bon_sortie) REFERENCES bon_sortie(id_sortie)
);

--VIEW
create or replace view bon_S as select bon_sortie.id_sortie,bon_sortie.num_sortie,bon_sortie.num_bon_entree,bon_sortie.dates,bon_sortie.fournisseur,bon_sortie.phone,bon_sortie.etat,bon_sortie.motif,bon_sortie.recepteur,bon_sortie.observation,bon_sortie.sign_responsable,bon_sortie.sign_recepteur,client.nom,client.id from  bon_sortie join client on bon_sortie.id_client = client.id;
------------------------------------------------------------------------------------------------

--//Réparation
create table reparation(
  id_reparation serial primary key not null,
  id_utilisateur INTEGER not null,
  id_bon_entree INTEGER not null,
  id_bon_entree_produit INTEGER not null,
  avancement INTEGER,
  prevu VARCHAR(100),
  dates DATE,
  step VARCHAR(100),
  FOREIGN KEY (id_utilisateur) REFERENCES utilisateur(id),
  FOREIGN KEY (id_bon_entree) REFERENCES bon_entree(id_entree),
  FOREIGN KEY (id_bon_entree_produit) REFERENCES bon_entree_produit(id_be)
);

--VIEW
CREATE OR REPLACE VIEW R_U as SELECT reparation.* , utilisateur.nom as technicien from reparation JOIN utilisateur ON reparation.id_utilisateur = utilisateur.id;
CREATE OR REPLACE VIEW R_U_B as SELECT R_U.*,bon_E.num_be,bon_E.nom as client from R_U JOIN bon_E ON R_U.id_bon_entree = bon_E.id_entree;
CREATE OR REPLACE VIEW R_P as SELECT R_U_B.*,bon_entree_produit.reference,bon_entree_produit.designation FROM R_U_B JOIN  bon_entree_produit ON R_U_B.id_bon_entree_produit = bon_entree_produit.id_be;

------------------------------------------------------------------------------------------------

--//Reclamation
CREATE TABLE reclamation(
  id_reclamation serial PRIMARY key not null,
  dates DATE,
  id_u INTEGER not null,
  client VARCHAR(100),
  sollicitant VARCHAR(100),
  contact VARCHAR(100),
  ref_facture VARCHAR(100),
  envoyeur VARCHAR(100),
  destinataire VARCHAR(100),
  cc VARCHAR(100),
  objet VARCHAR(100),
  message VARCHAR(100),
  FOREIGN KEY (id_u) REFERENCES Utilisateur(id)
);

 ---------------------------------------------------------------------------------------------------DATA----------------------------------------------------------------------------------------------

INSERT INTO utilisateur (id_type_user,nom,email,mdp,telephone) VALUES (4,'Mamison Randriamalala','service@regitech-oi.com',md5('12345678'),'034 12 710 05');
INSERT INTO utilisateur (id_type_user,nom,email,mdp,telephone) VALUES (4,'Tsilavina Andriatsila','service@regitech-oi.com',md5('00001111'),'034 12 710 05');
INSERT INTO utilisateur (id_type_user,id_client,nom,email,mdp,telephone) VALUES (5,1,'ETP','d',md5('etp'),'034 00 001 01');
INSERT INTO utilisateur (id_type_user,id_client,nom,email,mdp,telephone) VALUES (5,2,'Rajao','rajao@gmail.com',md5('rajao'),'034 00 001 02');
INSERT INTO utilisateur (id_type_user,id_client,nom,email,mdp,telephone) VALUES (5,3,'SOCIAMAD','sociamad@gmail.com',md5('sociamad'),'034 00 001 03');
INSERT INTO utilisateur (id_type_user,id_client,nom,email,mdp,telephone) VALUES (5,4,'Rindra','rindra@gmail.com',md5('rindra'),'034 00 001 04');
INSERT INTO utilisateur (id_type_user,id_client,nom,email,mdp,telephone) VALUES (5,5,'LIKE','like@gmail.com',md5('like'),'034 00 001 05');

INSERT INTO utilisateur (id_type_user,nom,email,mdp,telephone) VALUES (1,'Administrateur','executive-assistant@regitech-oi.com',md5('2022REGITECHOI'),'0000000000');
INSERT INTO utilisateur (id_type_user,nom,email,mdp,telephone) VALUES (4,'Technicien 1','servicetechnique1@regitech-oi.com',md5('12345678'),'034 12 710 05');
------------------------------------------------------------------------------------------------

insert into type_user(designation) values('Administrateur');
insert into type_user(designation) values('Service Administratif');
insert into type_user(designation) values('Service Commercial');
insert into type_user(designation) values('Service Technique');
insert into type_user(designation) values('Client');

-----------------------------------------------------------------------------------------------

INSERT INTO Client (nom,adresse,nif,stat,rcs,cin,permis,passeport,particulier) VALUES('ETP','Mahajanga,Antsahavola','223 001 255 314','456 235 654 000','20FSAF313',null,null,null,false);
INSERT INTO Client (nom,adresse,nif,stat,rcs,cin,permis,passeport,particulier) VALUES('Rajao','Vakinankaratra,Antsirabe',null,null,null,'1465453454354','P1465453454354','PASS1465453454354',true);
INSERT INTO Client (nom,adresse,nif,stat,rcs,cin,permis,passeport,particulier) VALUES('SOCIAMAD','Antananrivo,Antsahavola','999 001 333 314','989 235 654 333','20FSAF313',null,null,null,false);
INSERT INTO Client (nom,adresse,nif,stat,rcs,cin,permis,passeport,particulier) VALUES('Rindra','Mahajanga,Mampikony',null,null,null,'35143545335435','P35143545335435','PASS35143545335435',true);
INSERT INTO Client (nom,adresse,nif,stat,rcs,cin,permis,passeport,particulier) VALUES('LIKE','Antananrivo,Antanimena','635 321 654 563','111 235 222 666','40JDF659',null,null,null,false);

------------------------------------------------------------------------------------------------

insert into interlocuteur(id_client,nom,contact,email) values(1,'Rabe Ranary','034 56 235 65','rajao@gmail.com');
insert into interlocuteur(id_client,nom,contact,email) values(2,'Rajao Nanahary','034 63 55 58','Rabenanahary@gmail.com');
insert into interlocuteur(id_client,nom,contact,email) values(3,'Rasoa Ranzandry','034 77 625 32','rasoaa@gmail.com');
insert into interlocuteur(id_client,nom,contact,email) values(4,'NirySoa Andria','034 79 123 02','rajao@gmail.com');
insert into interlocuteur(id_client,nom,contact,email) values(5,'Faniry Ralombo','034 14 005 39','faniryr@gmail.com');

create or replace view notationView as select notation.* from notation inner join (select idutilisateur,max(datenotation) as datenotation from notation group by idutilisateur) as tab on tab.idutilisateur=notation.idutilisateur and tab.datenotation=notation.datenotation
















