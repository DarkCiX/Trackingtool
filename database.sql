CREATE SEQUENCE bon_entree_id_entree_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE bon_entree_produit_id_be_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE bon_sortie_id_sortie_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE bon_sortie_produit_id_bsp_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE client_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE devis_detail_id_devis_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE devis_produit_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE devis_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE entree_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE interlocuteur_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE notation_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE reclamation_id_reclamation_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE reparation_id_reparation_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE sortie_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE type_user_id_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE utilisateur_id_seq START WITH 1 INCREMENT BY 1;

CREATE  TABLE client ( 
	id                   serial  NOT NULL ,
	nom                  varchar(250)  NOT NULL ,
	adresse              varchar(500)   ,
	nif                  varchar(15)   ,
	stat                 varchar(100)   ,
	rcs                  varchar(100)   ,
	cin                  varchar(100)   ,
	permis               varchar(100)   ,
	passeport            varchar(100)   ,
	particulier          boolean   ,
	CONSTRAINT client_pkey PRIMARY KEY ( id )
 );

CREATE  TABLE devis_produit ( 
	id                   serial  NOT NULL ,
	num_facture          varchar(250)   ,
	designation          varchar(250)   ,
	quantite             integer   ,
	prix_unitaire        float8   ,
	remise               integer   ,
	prix_total           float8   ,
	CONSTRAINT devis_produit_pkey PRIMARY KEY ( id )
 );

CREATE  TABLE interlocuteur ( 
	id                   serial  NOT NULL ,
	id_client            integer  NOT NULL ,
	nom                  varchar(100)  NOT NULL ,
	contact              varchar(100)  NOT NULL ,
	email                varchar(100)  NOT NULL ,
	CONSTRAINT interlocuteur_pkey PRIMARY KEY ( id )
 );

CREATE  TABLE notation ( 
	id                   serial  NOT NULL ,
	idutilisateur        integer  NOT NULL ,
	note                 integer DEFAULT 0  ,
	datenotation         timestamp DEFAULT now()  ,
	commentaire          text   ,
	CONSTRAINT pk_notation_id PRIMARY KEY ( id )
 );

CREATE  TABLE type_user ( 
	id                   serial  NOT NULL ,
	designation          varchar(100)  NOT NULL ,
	CONSTRAINT type_user_pkey PRIMARY KEY ( id ),
	CONSTRAINT type_user_designation_key UNIQUE ( designation ) 
 );

CREATE  TABLE utilisateur ( 
	id                   serial  NOT NULL ,
	id_type_user         integer  NOT NULL ,
	id_client            integer   ,
	nom                  varchar(250)  NOT NULL ,
	email                varchar(150)  NOT NULL ,
	mdp                  varchar(50)  NOT NULL ,
	telephone            varchar(50)   ,
	CONSTRAINT utilisateur_pkey PRIMARY KEY ( id )
 );

CREATE  TABLE bon_entree ( 
	id_entree            serial  NOT NULL ,
	num_be               varchar(100) DEFAULT ((('AC_BE_'::text || to_char(nextval('entree_seq'::regclass), 'FM9990999'::text)) || '_'::text) || to_char((('now'::text)::date)::timestamp with time zone, 'DDMMYYYY'::text)) NOT NULL ,
	dates                date   ,
	fournisseur          varchar(100)   ,
	phone                varchar(100)   ,
	etat                 varchar(100)   ,
	reparation           integer DEFAULT 0  ,
	sortie               integer DEFAULT 0  ,
	id_client            integer  NOT NULL ,
	motif                varchar(100)   ,
	recepteur            varchar(100)   ,
	observation          text   ,
	sign_recepteur       text   ,
	sign_client          text   ,
	num_achat            varchar(100)   ,
	CONSTRAINT bon_entree_pkey PRIMARY KEY ( id_entree )
 );

CREATE  TABLE bon_entree_produit ( 
	id_be                serial  NOT NULL ,
	id_bon_entre         integer   ,
	num_be               varchar(100)  NOT NULL ,
	reference            varchar(100)   ,
	designation          varchar(100)   ,
	quantite             integer   ,
	CONSTRAINT bon_entree_produit_pkey PRIMARY KEY ( id_be )
 );

CREATE  TABLE bon_sortie ( 
	id_sortie            serial  NOT NULL ,
	num_sortie           varchar(100) DEFAULT ((('AC_BS_'::text || to_char(nextval('sortie_seq'::regclass), 'FM9990999'::text)) || '_'::text) || to_char((('now'::text)::date)::timestamp with time zone, 'DDMMYYYY'::text)) NOT NULL ,
	num_bon_entree       varchar(100)   ,
	dates                date   ,
	fournisseur          varchar(100)   ,
	phone                varchar(100)   ,
	etat                 varchar(100) DEFAULT '0'::character varying  ,
	id_client            integer  NOT NULL ,
	motif                varchar(100)   ,
	recepteur            varchar(100)   ,
	observation          text   ,
	sign_responsable     text   ,
	sign_recepteur       text   ,
	CONSTRAINT bon_sortie_pkey PRIMARY KEY ( id_sortie ),
	CONSTRAINT bon_sortie_num_bon_entree_key UNIQUE ( num_bon_entree ) 
 );

CREATE  TABLE bon_sortie_produit ( 
	id_bsp               serial  NOT NULL ,
	id_bon_sortie        integer   ,
	num_sortie           varchar(100)   ,
	reference            varchar(100)   ,
	designation          varchar(100)   ,
	quantite             integer   ,
	CONSTRAINT bon_sortie_produit_pkey PRIMARY KEY ( id_bsp )
 );

CREATE  TABLE devis_detail ( 
	id_devis             serial  NOT NULL ,
	num_facture          varchar(100) DEFAULT ((('AC_PF_'::text || to_char(nextval('devis_seq'::regclass), 'FM9990999'::text)) || '_'::text) || to_char((('now'::text)::date)::timestamp with time zone, 'DDMMYYYY'::text)) NOT NULL ,
	dates                date   ,
	tech_id              integer   ,
	client_id            integer   ,
	objet                varchar(250)   ,
	valide               varchar(250)   ,
	disponibilite        date   ,
	reglement            varchar(250)   ,
	preconisaiton        varchar(250)   ,
	garanti              integer   ,
	etat                 integer DEFAULT 0  ,
	CONSTRAINT devis_detail_pkey PRIMARY KEY ( id_devis ),
	CONSTRAINT devis_detail_num_facture_key UNIQUE ( num_facture ) 
 );

CREATE  TABLE reclamation ( 
	id_reclamation       serial  NOT NULL ,
	dates                date   ,
	id_u                 integer  NOT NULL ,
	client               varchar(100)   ,
	sollicitant          varchar(100)   ,
	contact              varchar(100)   ,
	ref_facture          varchar(100)   ,
	envoyeur             varchar(100)   ,
	destinataire         varchar(100)   ,
	cc                   varchar(100)   ,
	objet                varchar(100)   ,
	message              varchar(100)   ,
	CONSTRAINT reclamation_pkey PRIMARY KEY ( id_reclamation )
 );

CREATE  TABLE reparation ( 
	id_reparation        serial  NOT NULL ,
	id_utilisateur       integer  NOT NULL ,
	id_bon_entree        integer  NOT NULL ,
	id_bon_entree_produit integer  NOT NULL ,
	avancement           integer   ,
	prevu                varchar(100)   ,
	dates                date   ,
	step                 varchar(100)   ,
	CONSTRAINT reparation_pkey PRIMARY KEY ( id_reparation )
 );

CREATE  TABLE token ( 
	id                   integer  NOT NULL ,
	token                varchar(50)  NOT NULL ,
	dateco               timestamp   
 );

ALTER TABLE bon_entree ADD CONSTRAINT bon_entree_id_client_fkey FOREIGN KEY ( id_client ) REFERENCES client( id );

ALTER TABLE bon_entree_produit ADD CONSTRAINT bon_entree_produit_id_bon_entre_fkey FOREIGN KEY ( id_bon_entre ) REFERENCES bon_entree( id_entree );

ALTER TABLE bon_sortie ADD CONSTRAINT bon_sortie_id_client_fkey FOREIGN KEY ( id_client ) REFERENCES client( id );

ALTER TABLE bon_sortie_produit ADD CONSTRAINT bon_sortie_produit_id_bon_sortie_fkey FOREIGN KEY ( id_bon_sortie ) REFERENCES bon_sortie( id_sortie );

ALTER TABLE devis_detail ADD CONSTRAINT devis_detail_client_id_fkey FOREIGN KEY ( client_id ) REFERENCES client( id );

ALTER TABLE devis_detail ADD CONSTRAINT devis_detail_tech_id_fkey FOREIGN KEY ( tech_id ) REFERENCES utilisateur( id );

ALTER TABLE interlocuteur ADD CONSTRAINT interlocuteur_id_client_fkey FOREIGN KEY ( id_client ) REFERENCES client( id );

ALTER TABLE notation ADD CONSTRAINT fk_notation_utilisateur FOREIGN KEY ( idutilisateur ) REFERENCES client( id );

ALTER TABLE reclamation ADD CONSTRAINT reclamation_id_u_fkey FOREIGN KEY ( id_u ) REFERENCES utilisateur( id );

ALTER TABLE reparation ADD CONSTRAINT reparation_id_bon_entree_fkey FOREIGN KEY ( id_bon_entree ) REFERENCES bon_entree( id_entree );

ALTER TABLE reparation ADD CONSTRAINT reparation_id_bon_entree_produit_fkey FOREIGN KEY ( id_bon_entree_produit ) REFERENCES bon_entree_produit( id_be );

ALTER TABLE reparation ADD CONSTRAINT reparation_id_utilisateur_fkey FOREIGN KEY ( id_utilisateur ) REFERENCES utilisateur( id );

ALTER TABLE token ADD CONSTRAINT token_id_fkey FOREIGN KEY ( id ) REFERENCES utilisateur( id );

ALTER TABLE utilisateur ADD CONSTRAINT utilisateur_id_client_fkey FOREIGN KEY ( id_client ) REFERENCES client( id );

ALTER TABLE utilisateur ADD CONSTRAINT utilisateur_id_type_user_fkey FOREIGN KEY ( id_type_user ) REFERENCES type_user( id );

CREATE OR REPLACE VIEW bon_e AS  SELECT bon_entree.num_be,
    bon_entree.dates,
    bon_entree.reparation,
    bon_entree.sortie,
    bon_entree.fournisseur,
    bon_entree.phone,
    bon_entree.etat,
    bon_entree.motif,
    bon_entree.recepteur,
    bon_entree.observation,
    bon_entree.sign_recepteur,
    bon_entree.sign_client,
    bon_entree.num_achat,
    client.nom,
    client.id,
    bon_entree.id_entree
   FROM (bon_entree
     JOIN client ON ((bon_entree.id_client = client.id)));

CREATE OR REPLACE VIEW bon_s AS  SELECT bon_sortie.id_sortie,
    bon_sortie.num_sortie,
    bon_sortie.num_bon_entree,
    bon_sortie.dates,
    bon_sortie.fournisseur,
    bon_sortie.phone,
    bon_sortie.etat,
    bon_sortie.motif,
    bon_sortie.recepteur,
    bon_sortie.observation,
    bon_sortie.sign_responsable,
    bon_sortie.sign_recepteur,
    client.nom,
    client.id
   FROM (bon_sortie
     JOIN client ON ((bon_sortie.id_client = client.id)));

CREATE OR REPLACE VIEW client_inter AS  SELECT client.id,
    client.adresse,
    client.nom,
    interlocuteur.nom AS admins,
    interlocuteur.contact,
    interlocuteur.email,
    client.nif,
    client.stat,
    client.rcs,
    client.cin,
    client.permis,
    client.passeport,
    client.particulier
   FROM (client
     JOIN interlocuteur ON ((client.id = interlocuteur.id_client)));

CREATE OR REPLACE VIEW devis_client AS  SELECT v_temp.num_facture,
    v_temp.dates,
    v_temp.etat,
    v_temp.tech_id,
    v_temp.objet,
    v_temp.valide,
    v_temp.disponibilite,
    v_temp.reglement,
    v_temp.preconisaiton,
    v_temp.garanti,
    v_temp.nom,
    v_temp.admins,
    v_temp.contact,
    v_temp.adresse,
    v_temp.email,
    v_temp.nif,
    v_temp.stat,
    v_temp.rcs,
    v_temp.cin,
    v_temp.permis,
    v_temp.passeport,
    v_temp.particulier,
    utilisateur.nom AS tech,
    utilisateur.email AS tech_mail,
    utilisateur.telephone AS tech_contact
   FROM (v_temp
     JOIN utilisateur ON ((v_temp.tech_id = utilisateur.id)));

CREATE OR REPLACE VIEW notationview AS  SELECT notation.id,
    notation.idutilisateur,
    notation.note,
    notation.datenotation,
    notation.commentaire
   FROM (notation
     JOIN ( SELECT notation_1.idutilisateur,
            max(notation_1.datenotation) AS datenotation
           FROM notation notation_1
          GROUP BY notation_1.idutilisateur) tab ON (((tab.idutilisateur = notation.idutilisateur) AND (tab.datenotation = notation.datenotation))));

CREATE OR REPLACE VIEW r_p AS  SELECT r_u_b.id_reparation,
    r_u_b.id_utilisateur,
    r_u_b.id_bon_entree,
    r_u_b.id_bon_entree_produit,
    r_u_b.avancement,
    r_u_b.prevu,
    r_u_b.dates,
    r_u_b.step,
    r_u_b.technicien,
    r_u_b.num_be,
    r_u_b.client,
    bon_entree_produit.reference,
    bon_entree_produit.designation
   FROM (r_u_b
     JOIN bon_entree_produit ON ((r_u_b.id_bon_entree_produit = bon_entree_produit.id_be)));

CREATE OR REPLACE VIEW r_u AS  SELECT reparation.id_reparation,
    reparation.id_utilisateur,
    reparation.id_bon_entree,
    reparation.id_bon_entree_produit,
    reparation.avancement,
    reparation.prevu,
    reparation.dates,
    reparation.step,
    utilisateur.nom AS technicien
   FROM (reparation
     JOIN utilisateur ON ((reparation.id_utilisateur = utilisateur.id)));

CREATE OR REPLACE VIEW r_u_b AS  SELECT r_u.id_reparation,
    r_u.id_utilisateur,
    r_u.id_bon_entree,
    r_u.id_bon_entree_produit,
    r_u.avancement,
    r_u.prevu,
    r_u.dates,
    r_u.step,
    r_u.technicien,
    bon_e.num_be,
    bon_e.nom AS client
   FROM (r_u
     JOIN bon_e ON ((r_u.id_bon_entree = bon_e.id_entree)));

CREATE OR REPLACE VIEW v_temp AS  SELECT devis_detail.num_facture,
    devis_detail.dates,
    devis_detail.tech_id,
    devis_detail.etat,
    devis_detail.objet,
    devis_detail.valide,
    devis_detail.disponibilite,
    devis_detail.reglement,
    devis_detail.preconisaiton,
    devis_detail.garanti,
    client_inter.nom,
    client_inter.adresse,
    client_inter.admins,
    client_inter.contact,
    client_inter.email,
    client_inter.nif,
    client_inter.stat,
    client_inter.rcs,
    client_inter.cin,
    client_inter.permis,
    client_inter.passeport,
    client_inter.particulier
   FROM (devis_detail
     JOIN client_inter ON ((devis_detail.client_id = client_inter.id)));

CREATE OR REPLACE VIEW v_utilisateur AS  SELECT utilisateur.id,
    type_user.id AS id_type_user,
    type_user.designation,
    utilisateur.nom,
    utilisateur.email
   FROM (utilisateur
     JOIN type_user ON ((utilisateur.id_type_user = type_user.id)))
  WHERE (utilisateur.id_client IS NULL);

INSERT INTO client( id, nom, adresse, nif, stat, rcs, cin, permis, passeport, particulier ) VALUES ( 1, 'ETP', 'Mahajanga,Antsahavola', '223 001 255 314', '456 235 654 000', '20FSAF313', null, null, null, false);
INSERT INTO client( id, nom, adresse, nif, stat, rcs, cin, permis, passeport, particulier ) VALUES ( 2, 'Rajao', 'Vakinankaratra,Antsirabe', null, null, null, '1465453454354', 'P1465453454354', 'PASS1465453454354', true);
INSERT INTO client( id, nom, adresse, nif, stat, rcs, cin, permis, passeport, particulier ) VALUES ( 3, 'SOCIAMAD', 'Antananrivo,Antsahavola', '999 001 333 314', '989 235 654 333', '20FSAF313', null, null, null, false);
INSERT INTO client( id, nom, adresse, nif, stat, rcs, cin, permis, passeport, particulier ) VALUES ( 4, 'Rindra', 'Mahajanga,Mampikony', null, null, null, '35143545335435', 'P35143545335435', 'PASS35143545335435', true);
INSERT INTO client( id, nom, adresse, nif, stat, rcs, cin, permis, passeport, particulier ) VALUES ( 5, 'LIKE', 'Antananrivo,Antanimena', '635 321 654 563', '111 235 222 666', '40JDF659', null, null, null, false);
INSERT INTO interlocuteur( id, id_client, nom, contact, email ) VALUES ( 1, 1, 'Rabe Ranary', '034 56 235 65', 'rajao@gmail.com');
INSERT INTO interlocuteur( id, id_client, nom, contact, email ) VALUES ( 2, 2, 'Rajao Nanahary', '034 63 55 58', 'Rabenanahary@gmail.com');
INSERT INTO interlocuteur( id, id_client, nom, contact, email ) VALUES ( 3, 3, 'Rasoa Ranzandry', '034 77 625 32', 'rasoaa@gmail.com');
INSERT INTO interlocuteur( id, id_client, nom, contact, email ) VALUES ( 4, 4, 'NirySoa Andria', '034 79 123 02', 'rajao@gmail.com');
INSERT INTO interlocuteur( id, id_client, nom, contact, email ) VALUES ( 5, 5, 'Faniry Ralombo', '034 14 005 39', 'faniryr@gmail.com');
INSERT INTO notation( id, idutilisateur, note, datenotation, commentaire ) VALUES ( 9, 1, 2, '2022-07-04 12:15:24 pm', 'Test insert first');
INSERT INTO notation( id, idutilisateur, note, datenotation, commentaire ) VALUES ( 10, 1, 4, '2022-07-04 12:15:43 pm', 'Test Modification 1');
INSERT INTO notation( id, idutilisateur, note, datenotation, commentaire ) VALUES ( 11, 3, 5, '2022-07-04 12:18:07 pm', 'Test insert first 2');
INSERT INTO notation( id, idutilisateur, note, datenotation, commentaire ) VALUES ( 12, 3, 2, '2022-07-04 12:18:21 pm', 'Test Modification 2');
INSERT INTO notation( id, idutilisateur, note, datenotation, commentaire ) VALUES ( 13, 3, 3, '2022-07-04 12:38:10 pm', 'Test Modification 3');
INSERT INTO notation( id, idutilisateur, note, datenotation, commentaire ) VALUES ( 14, 1, 2, '2022-07-05 10:54:38 am', 'Vous etes mauvais');
INSERT INTO type_user( id, designation ) VALUES ( 1, 'Administrateur');
INSERT INTO type_user( id, designation ) VALUES ( 2, 'Service Administratif');
INSERT INTO type_user( id, designation ) VALUES ( 3, 'Service Commercial');
INSERT INTO type_user( id, designation ) VALUES ( 4, 'Service Technique');
INSERT INTO type_user( id, designation ) VALUES ( 5, 'Client');
INSERT INTO utilisateur( id, id_type_user, id_client, nom, email, mdp, telephone ) VALUES ( 16, 4, null, 'Mamison Randriamalala', 'service@regitech-oi.com', '25d55ad283aa400af464c76d713c07ad', '034 12 710 05');

INSERT INTO utilisateur( id, id_type_user, id_client, nom, email, mdp, telephone ) VALUES ( 15, 1, null, 'Regitech Admin', 'admin@regitech-oi.com', '25d55ad283aa400af464c76d713c07ad', '034 00 000 00');


INSERT INTO utilisateur( id, id_type_user, id_client, nom, email, mdp, telephone ) VALUES ( 17, 4, null, 'Tsilavina Andriatsila', 'service@regitech-oi.com', '55722118e8b7648e5385b65528a77712', '034 12 710 05');
INSERT INTO utilisateur( id, id_type_user, id_client, nom, email, mdp, telephone ) VALUES ( 18, 5, 1, 'ETP', 'etp@gmail.com', '5060bc256b49055830cb6ee2c22c436e', '034 00 001 01');
INSERT INTO utilisateur( id, id_type_user, id_client, nom, email, mdp, telephone ) VALUES ( 19, 5, 2, 'Rajao', 'rajao@gmail.com', 'a21225be9a6f7fe77e6f94e8e8371e9d', '034 00 001 02');
INSERT INTO utilisateur( id, id_type_user, id_client, nom, email, mdp, telephone ) VALUES ( 20, 5, 3, 'SOCIAMAD', 'sociamad@gmail.com', 'b6768f84a6f41f66cd972ec80b212e45', '034 00 001 03');
INSERT INTO utilisateur( id, id_type_user, id_client, nom, email, mdp, telephone ) VALUES ( 21, 5, 4, 'Rindra', 'rindra@gmail.com', 'ac0faeaf5d7fdff044adfe9a865fe941', '034 00 001 04');
INSERT INTO utilisateur( id, id_type_user, id_client, nom, email, mdp, telephone ) VALUES ( 22, 5, 5, 'LIKE', 'like@gmail.com', 'be1ab1632e4285edc3733b142935c60b', '034 00 001 05');
INSERT INTO bon_entree( id_entree, num_be, dates, fournisseur, phone, etat, reparation, sortie, id_client, motif, recepteur, observation, sign_recepteur, sign_client, num_achat ) VALUES ( 1, 'AC_BE_0001_29062022', '2022-06-29', 'REGITECH OCEAN INDIEN', '+261 32 12 710 08', '0', 1, 1, 1, 'Test ', 'Test', 'test', 'data:image/png;base64,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', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAS8AAACaCAYAAAD4rs6qAAAAAXNSR0IArs4c6QAAFuNJREFUeF7tnXvodktVx7+GWegxE7qcym5QFKfLKbpwhMj6I7GwO0oKXU2NCrta/lFeKqioPPVH2Q0sKwIrLDSL+qMsyNLgFJppFJaUFVpWWphFySdnwbTP8zx79t6z957Z+7vh5fe+v2f2zJrvzPN911qzZq0HyI8RMAJGoEMEHtChzBbZCBgBIyCTlzeBETACXSJg8upy2Sy0ETACJi/vASNgBLpEwOTV5bJZaCNgBExe3gNGwAh0iYDJq8tls9BGwAiYvLwHjIAR6BIBk1eXy2ahjYARMHl5DxgBI9AlAiavLpfNQhsBI2Dy8h4wAkagSwRMXl0um4U2AkbA5OU9YASMQJcImLy6XDYLbQSMgMnLe8AIGIEuETB5dblsFtoIGAGTl/eAETACXSJg8upy2Sy0ETACJi/vASNgBLpEwOTV5bJZaCNgBExe3gNGwAh0iYDJq8tls9BGwAiYvLwHjIAR6BIBk1eXy2ahjYARMHl5DxgBI9AlAiavLpfNQhsBI2Dy8h4wAkagSwRMXl0um4U2AkbA5OU9YASMQJcImLy6XDYLbQSMgMnLe8AIGIEuETB5dblsFtoIGAGTl/eAETACXSJg8upy2Sy0ETACJi/vASNgBLpEwOTV5bJZaCNgBExe3gNGwAh0iYDJq8tls9ATEPgESXdL+jBJn5He+xdJvyvp1yT99YS+3LQhBExeDS2GRVmMAAT1JZIeKelDJEFcY88bJD3XRDYGU3ufm7zaWxNL9C4N6fMT+YS2FLigNaEt8fNPJL1H+uBRku4agPc3qS1aFu+ElvVpkr4jezdeo7+fkfQjXoT2ETB5tb9GZ5HwCxJh8fO9Z076fyS9UdIfS3qtpN+U9KeJ6KJLtLHfSWM8R9KvSvoKSYz7oanRT0j6mpky+LWNEDB5bQS0h7mIAGbevUnD4u/x4ItCW0IT4icPn6ONQTS5Ofhvkv5W0n8l39algdC46Acie1YiLrSrbxw0/hxJv55+B7E92+vWLgImr3bX5siSYQpCIrlJ+LJksqEJYRLGQ5svT6QVv8McpB0mHgSXPxAbRMdP3uXvoVFFO/xcEOHwXT5/nKQXmsDa334mr/bX6EgSYpp9Q0ZaQUI/fOHUL7Qy3uH510RYtL1EOrdwgrxeI+nBg0ZoY/SHppc/aFyQKxrbx0t665EW4ShzMXkdZSXbngemHmQQpiGkBUGgOV16+AySw/cFaUEw/Mk1stIZ08frU19od2hxX5lMxoelTiCxn5L0BxmJvlTSZ0v6zMx0LR3T7TZAwOS1AcgnHgKz7fkTSAtye1Hm08IvBZHNIS1gz/vD34U8eV+QKv3nZiWfY5LeI+mjTV7t7l6TV7tr07NkaDuQVph8Y5oWc8V5Hs502kMs4ayfg0V+qniJuPI+GYv4sDsvOP2fKukn5wjgd9ZFwOS1Lr5n7B3tBu0pTD40G0y+aw/taB/O+0ungFNxpE/CISCwMeIa9s27yPJzku5IH9p0nLoCG7Q3eW0A8omGQNOCiHhKSCP3heEU/7yF2hbjTtG4bi0NfrI8fOMLkzl5ouVse6omr7bXpyfpICJMRZ6fHYQ2DOcx9G39tqTHL/BtRf85ceGch0zn+svQ3NDAfkXSF6cBvmlEi+xpvbqX1eTV/RI2MYGcuDjJu3aKiLARhsDfa/i2LhEXWl/JvcZb4AV5YTJGXBrtv1/SM5pA/eRCmLxOvgEqTL+UuIbaVs0I9lzjImYrDgqWTA8zFp/Xh6fwCQgMQiOin9PJuRrdEpn8boaAycvbYQkCOXHdMqlyJz5aESeLS04Sc5khKsxVHO1j5mrpXF8g6Usl/Zikr8teIoSCyPxa45TK43YXEDB5eVvMRaBU4wqNJXxhEFctreUXJD1BEvu4JqEQWY929fCBrGiPOPJ5QiObi5/fW4iAyWshgCd9PfxWmFA/JOm7ruCQnz7WJBeG+z5J357G/TtJj6i4Fv+bIvsvZbeIuaM54g/zsxMCJq+dgO94WGK2uLrDtR20L0ypS0/uh6oRu5WPkRMXp4rDnF9L4EXu+yTd6jc0M4dPLEF64bsmr4UAnux1ThG5GwhxQRjXLkivSVzhdwroaweQBjnf8uGFycz8P/Fke6CZ6Zq8mlmK5gXJNa5bxIVfCM0Fk6vWyR/g0C+nffwk6eC7Sap5YhkLgD+OC9tjPq3QviCvqVkuml/sHgQ0efWwSvvLmDvdb31ZISyIK4jmiyo55/PTyrelEIY1iCv8WSWmKAcPJFKsbRLvv9qdSGDy6mShdhYzzMVbhLHkPuGt6WGiQRL0TxJBCmus4SzPTd0SUzROHkuIbuflO+bwJq9jrmvtWXH6xjMMHcjHCV8UUfNoSjVKiuXR+C+R9Ng0YAm5TMUAQqSIxxRNKnDx92gq2hXaG/QKIB68iwh3uOW/yh35aDA1iCvv85clPSnhXDvkgm7DBIR4kb80Di0I7xapH3x77Dc9k9d+2PcycpDItTuL8cUfO4EsnW9uftLn81IlH36/hp8rz7Q6VaML8pr6XikWbncDAZOXt8cYAnH6dkm7yKPsa8Q8DYnrW1N6ZmRcK6MDUfpPHInruoZRELvJa2wXrfC5yWsFUA/U5Vcn8iBlzaMH88qj58cySZRAAnH9oaSPkvR2Sa9Mla8fKOkdkh40KBwbRWQJU4BgcZxPfUJrHPPnXes3fHI1iHuq7Kdvb/I6/Ra4CUB8Ob9b0jOzlsPCrXPqG3Jad3dy7lPB+pMrLAWE9g/pT9R8xPS8FIeVa42fK4kDgalP4LOGOTtVltO1N3mdbsknTTg0k/zLmfuIpgShQnhPSRV56OPSvUFiuH4+aVlfJenfJX3s4AAA0uMP78fPa/UZ88nmmhoBrk9LHy7RGoMATV6TtlWdxiavOjgetZdL5IUWg8ZUkub5WpVr8MLMe3OWpZRTxKhgHaXK5hALdRaJBUOTC6KDOKPM2XCtmA9hHhDx1Ej5MKunkPhR98rm8zJ5bQ55VwMOzaL4N6bYtZCIa4RFGAJXjCAKtKBraaMjXmwNQuBC99dLeoikf5T0ngNSQy7GJ9arJNwjbh5Y89phW5u8dgC9oyHzKzB8qblbyDM8XbtFWLzHqVyu1cQ9SfrKTxHzsAv6LI23KoH0GllCwpAQn6NRxnOtmnY+VpDXlMDWElndpgABk1cBSCduEulh0LTwEz10EGvFl5f0OHnaZTSsS4QFjMN6jrlZGCYY7WpedoYEybRaUlqNtpAYJBpmJhoY5AtBDck0yGuNwNkTb7uyqZu8ynA6c6sIxASDV0v6uKwgRRAC5AZhoVFd8xsNY7ggvDwVNH28Vyo8ey254ZR1YDyIFSKKGpK38o8N+6Ytf7gyxANxvVTSj0v6/fS7IK81TNwpcz1lW5PXKZd90qS/VtKPpjdeJ+lNkght4IlK2BDXLRMvzwpx6e5jmJG1Ljnjm4O44kQzDgPmmKFoY/RHHrN4IGhk/idJL54Z4DppEdz4/giYvLwrbiGQh0UQP3VnakxIw3cW1jDMs0JcqqUYpildj+XQGpM1SCYnLX5X4nwf2wn0iUaIxvjBGQ5UGPrFFKU/1oc/r4iAyasimAfsKk7+/l7SBwzmF9oHJtM1jSbPA3btRC5CL+Ze/4FM0IpyvxskCWnWIK1LyxoO/tDG/lPSYypWRDrgVqo/JZNXfUyP0mOczv23JK7o4JOiDNj7Jj8S1XV4IC5MqKFDO09MeK1Qa4yBKYl5VvrQFuLg/XgP+V6RAmHXIq2hfPmpKZ/5jmPpClZoZ/KqAOIBu4CY/iJFujM9tCsc3zkpoH1gkuUObb7M+JdoF47+kkIWJcGokBRjoWHlWhbBsox7q0r3WksU5MU9zE9Jg5jA1kJ70K/JayOgOxoGkuDL+D5J5jFzDhKD2CjGGg9aEKEGaGUR48Xf8W+9StI/J42JEAbIh98z7j2SSPCHby2u//B7Ps+vE5Wcbm4BeZi8hHaAAdog84TApkbrbyHvocYweR1qORdPBqJ4eSIPSARNJ8ICxjrnXeoofpmkB481zj6n5uIHFbSH5OIqz7VyawXdVGsCmb4lnbiG6RopctA8IbCtzNdqk+qpI5NXT6u1rqyYYmhCoeHg36LcfemTx3G9VdI3p0IZucbEJWs++4gs3CL6R2Phy/5XKZ6Mf0e6mxa1mLgNMAxQDQLjkOOuyrcEStfiFO1MXqdY5tFJ5rniaTw13mpYfAMivKV1hKOeGKnndnpKFybjJR9XfAYGmJRz4stGF+3sDUxe594BkM6LLlScnuJ0zvsoyTQB4hGC0WsSv6gcdOuUNAjMhWlX+o6ZvFYCtpNug0RwgP+lpE+aUSg2TtymVA26lVq6B+hizmOl4Dhx5bL3GqXaesBpVRlNXqvC23TneQYHTDe+iJBYaTYHNK7vTcUxmGjpZeoIXJ1qmrYEZmlVbTBC8yL0BF8YISF+KiFg8qoEZGfd5Nd+niqJIFJ+V2rGDc3N0veAKbSWsRCMViGlKMgPTNBQ+c8AAiN0pNc5N7kWJq8ml2V1ocJcJCoeIiI+qVQTyi9Z4+PC+T7lNJA7ku+/8B7j6gDdGOC3JH2WpCdL+ulCQfJrUlOIvrD7czYzeZ1v3fMcXRSe+L1kLpYUi82vw8zN1BAmV697b678gR3vY2I7Bmzhd6/XDbRw2qd+/bWpvBgmDCENBKKOpTFGO7s3aVmAN9f8icDOiKrvbSGW+uviyhSaLxqYnwUImLwWgNfhqxFf9eeSyNNFWucxJz1fWIgLzYy2UxL6DSEKra/URG0N4qX+utz/NSUcpTUcmpDH5NXEMmwiRJ7lgf/1ISHuI97SuvKirGhLY8GnYxOJQrW95nzH1OPkcEnesQgIdvjE2G4Z+dzktRDAjl4PrYEMEfz9ltaFhoS2led950u3NFK85yKtoTUuNXnz8InS8JKOttl2opq8tsN6z5Hy0y60BmKO8HU9XdIPZoJF3veogI2ZOMw1v2QeccrZo8kUdxbn+vty3ILEXbhjwW4yeS0Ar6NXw1EcCQMxB8lFz5F/nHoNL2bPPU28BUvIscTs2gP2PC7u4RU00Di4YC41+tsDk93HNHntvgSrC5BnKyXX+jMSYeH3Ij5raCJiFkFueWWfWkKSZoent323hqZUU5OrtT5d9dPbJuoK3EaEDSfz0yQRTf8xyUnPlweSosoODyYi/14zIynktdRntDWscQmbcWtqjGHK++L2zBU1ec0ErpPXQuuCMCAptCmSDRIlTlT9+6XEgZw44sRf6pC/BUuvYRKRHWKNE9L4j8WO+xlfKJPXDNA6eiW+HJiI3ybpkQPZx4JTa051aYBnTVlK+wpzcSwWrrS/YbtrCQ3n9neq90xex13uiKkKM43MnlF3kRz1XC7+pQ2n35vmFVorEK11OhqOezReTNI1Nd8Nl3qboUxe2+C8xyjhEI7KPJFnfa87deE76iG6Pi+EWyM04tb623E/89th8poJXOOvxf/oa5k7c6YfMrXuoIZk70vZNraIwwqi5D8VtC8/hQiYvAqB6qxZq74UThsxjYhtavHJc/FzEyGvD7mmvFRQ+sDKp5lryttE3yavJpahuhDh32rtFAvy+g9JD6k+4zodRhBtaS7+OqNKz0sZaUuK79Yas/t+TF7dL+H9JvA4SS+U9MbCeohbIdD6aWOcLE7JxV8Luzhc2fL0t5bsu/Vj8toN+tUG5sI1RLHWCdlcwcOUbfELGqmdmdse2mpvJ7Fz90DV90xeVeHcvbP8zlxraxuazdqnd1MWAef8s7Iki3umaJ6boXXKfA/VtrUNfihwd5hMmGYt5opqjbzyi+hR4bs0J/0aSxv+tj00vzXms3qfJq/VId50gMdI+g1Jr0l3GDcdfGSwVnJ5DVNar5E9Yw7uS7O0zhmz63dMXl0v3/2EJzMqObNajBlqwWGPb+n5WUrrtS+iT9ldEdG/xh3KKXJ009bk1c1SFQnastm4d/GNYUrrqSXbihZgQSM77SeCZ/KaCFjjze+S9GeS/kjSPQ3Kukc+L0gTbSsCTreImp8LfetBvHPntcp7Jq9VYN2109dL4hStxZAEHON3bJiMEMIiFz94cFUKvxu+pVafPci9VSxG5TJ5jULUXQNy0n9Lkpo0OGSPaOXZKof9peywSysfbYGhyWsCyiavCWB11DSi7BGZrAU4gbkQvfez9okjBEWSxTARe9C28jWBvIjwjwwge69X0+ObvJpenkXCQRQ4qR+WeiGOCFNyjdz0pYLGgUKtS8/4s6iCBFnxh3/zQFqYh2tnhy2dd0m7Fk5jS+Rspo3Jq5mlWEUQvswQ2JDE0MZwXO/xLHVK8yWHsPgZdSVjHpAipumaefjXwqz3grxr4XK1X5PX5pDvMiAkxpcDbYyKzzxcR+GLzh+yKGyVpHCO3wvZiWF7fMq5HyCiYdEf2iQ/e85E2toNhF026pRBTV5T0DpGW4iAGCfIYPhAAhAA/rH4+RZJb6hIDBFvlQdj4mCPJ8xcfhfaVZiDtIFo0ayQtQU/Xq1d4etBE5E0eU0E7EDNQxuDIHAQY4qVPJAaWlpoOUF0oc3F59EXfYcDmjGJRXu0pHdIelDJgJLCHOxdu7o1XZ80Fm6GaGbymgjYwZsH0aD1QDQfme5IQkj8++4V5v86SW9Lf/LuQ7MKbXCFoZvp0tH1M5bC5DUDNL/yfwjkzvIguyE0EF7uhwot7dPTyWeLdzD3WF77u2agbvKaAZpfqYJA1JRsKb9XlYnN6OTVScNtLYHkjKls94rJazusPdL/RyBCA9DMyGG11Wlna+sQJeEwnR/amnAty2Pyanl1ji9bhE20mDxxK/Qjj5dT4UxE3OQ1ETA3r4oAWgd+MMIjzlo5J8xnajaeVfuctalMXrNg80sVETiz+RinjMSu5bFuFeE9blcmr+OubU8zi5L3mJEUwTjL49TPC1ba5LUAPL9aDQFCKjAfubp0ptNHm4wLtpDJawF4frUqApFVgdNHQgaOdPXnElBhLttknLmNTF4zgfNrqyDwYkmPTY5rwid6vmg9BhDkzI2Fsx5UjOEz+rnJaxQiN9gYgbigzJcbDeyIBBaX0514cMHmMnktAM+vroZAaCUQGQ78IxFYnDAC3p4VuldbvK06NnlthbTHmYIA8V+cPGJWHekEkoOJ+1KWjZarGE1Zq93amrx2g94DjyCAhoLmRQDrUTSwMIntpK+w/U1eFUB0F6shkBNY7z6wiGXDz8W8jmQKr7YBbnVs8toFdg86AYGcwPjCkz6Ge4A9PREG8iZJn+prQHWWzuRVB0f3si4C+IrwfUW2V4I7ITEyrPagwYS5+HRJ1NX0UwEBk1cFEN3FZgigwWB+5UVEuGKD87vVS80Oi1hpe5i8VgLW3a6KACSG5pXn3ccnxu9e1pA2FsQFGA5GrbwlTF6VAXV3myJASAUE8SRJd2QjQ2RoaBDZHteM8NPdm6XKdljECtvC5LUCqO5yFwQo5xal0sKsRBDMyahPCZmt+TD+kyU9MQ3CySLaYI9FcNfEqUrfJq8qMLqTxhBA8+HiM3+GFY9wnkNo4SPD4R9Ofw4GHijp3SW9XBLkc82Xxhj0HfUlh/m4npOIqzFojiOOyes4a+mZXEYA0zKIrLQ25aWeID2evA5l3o7q3bR5s6TvafgA4TD7xOR1mKX0RAoRiDJteem2/FW0r7dLujMjqtwMjbZEyee1JVs97SyEpb9mJq/+1swS74fAsA7lfpJ4ZJm8vAmMgBHoEgGTV5fLZqGNgBEweXkPGAEj0CUCJq8ul81CGwEjYPLyHjACRqBLBExeXS6bhTYCRsDk5T1gBIxAlwiYvLpcNgttBIzAOwH41nTX70e7rwAAAABJRU5ErkJggg==', null);
INSERT INTO bon_entree_produit( id_be, id_bon_entre, num_be, reference, designation, quantite ) VALUES ( 1, 1, 'AC_BE_0001_29062022', 'hhe', 'jjj', 4);
INSERT INTO bon_sortie( id_sortie, num_sortie, num_bon_entree, dates, fournisseur, phone, etat, id_client, motif, recepteur, observation, sign_responsable, sign_recepteur ) VALUES ( 1, 'AC_BS_0001_29062022', 'AC_BE_0001_29062022', '2022-06-29', 'REGITECH OCEAN INDIEN', '+261 32 12 710 08', 'validé', 1, 'Test ', 'test', 'Ook', 'data:image/png;base64,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', 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAWEAAACaCAYAAAB43fm5AAAAAXNSR0IArs4c6QAAFLVJREFUeF7tnXuodV1Vxh/RtBA1wbAUMyUtrY/PUPuUkBRJJQivCFloWWpYZIWpKGL9EXkhNERQP81Eu4CYFf1RaKQYkVRa3sCiNLCLWJR+XUQT5bE5ZLBYe+91zl57zTnX/C14Oe973r3XGvM35nnO2GOOOeatxAUBCEAAAtUI3Krak3kwBCAAAQgIEWYSQAACEKhIABGuCJ9HQwACEECEmQMQgAAEKhJAhCvC59EQgAAEEGHmAAQgAIGKBBDhivB5NAQgAAFEmDkAAQhAoCIBRLgifB4NAQhAABFmDkAAAhCoSAARrgifR0MAAhBAhJkDEIAABCoSQIQrwufREIAABBBh5gAEIACBigQQ4YrweTQEIAABRJg5MDqBh0u6UdKTJN1G0udOAPlrSe+W9B5J/zk6PMZ/PgFE+HyG3KEvAhbd75Xkrw+Q9PVnmP/rkt5cRPmM2/DWkQkgwiN7f/9jt8BmwbXwTi9HtI5s/1jSrWf+/xOS/Mf3smh/SxHwp6XX+v9/oQjy/qkywlUJIMKr4uRmDRCwUFogI9LNJn2mCG6kFCy+170syj8i6Wck3bPcxGL8s5J+97o35X3jEUCEx/P5HkdsQXxsiUYdqcb1j0V0LbYWXv+5xPW4IsaOun2RprgE5Z3eExHeqWMHGZbF9zlFACO3a+F9VYlGHZlueb1c0s+XB1r4f5F88Zb4+3wWItyn37D6/9MAL0kLa87tOi97TophDa5Og9guf32upF9Z46bcY78EEOH9+navI/NH/1eWBTKP0eLr3OzWUe8xvj8o6TdLWuJH9+oIxrUOAUR4HY7c5fIEvODmCNMiHOLbQuQ7N3JHwX9SovJHXB4NT+iZACLcs/fGsN253udJ+klJd5TknK/F14tfLV9fKsbxM9aylxqwjQnSgBMw4SABR5ROPTgK9uWFLi+69bBT7WOS7ivpuy5YlcHU2QEBRHgHTtzhEBz9vmmSenixpPd2NFZH606fOCfcetTeEdb9mYoI78+nvY/I4uWyMwuxN1f4345+e7ucu36HpF8tJXS92Y+9GxGoJcK3l/QMSTdL+u+Nxspj2ibgTRYWrUg9WLwswD2kHubIehwfKNUbc9ul2/YG1m1GoJYI/5GkR1HCs5mfW3+QBcvVBI5+vfDmkrPa9b7nMvNY/gMRPhfj/t9fS4R/Q9JTJL1A0sv2j5kRHiGQBfj3igD3Gv3mYUaZmuuYiYT5EThIoJYIe6HCTVZcQ9l7xMP0uj4BR4v+yO5UhAU4aoCvf8d23uk8tnPbbujTY067HZI7t6SWCFt43ewEEd75BDsxvA9J+s6dfmR3NH8nSfdqbDff2DOuwdEjwg06ZRCT/lzSTZL+VtK37WzMURmxt+h+Z25qYziIcBt+GM2KSEfdIumbV66A+PGywcOR6D0qgY3xUSNcyQE9PbaWCJMT7mmWrGtr+N41wF6wWrPHb24l+WFJN6xr+qK7Oc/9z5K+TtKdV/4Fs8gAXtQXgVoiHLuJyAn3NV/OtTb8bgH2R/a1FmUtfH9Y0hu20SJ493ONveb7Y73jnaUM85q34W2jEKgtwo/nKJhRptpX+v+6D4SvNf3uErc/SKL79nJycg2wMUbXOtuuPZTa1eA41DNrizA5szGmmzdfuBeEL9eFO2pdIwr2fV8r6Xbl3s+S9PpKSGOHnB/PJ7xKTujxsbVE+I2Snl5+IL1hg2u/BLIAO/8b25I94o9IekKpkLgqAYu67+3rvyQ9bOX88lXscTrEO/48NnpFXIUcr1UtEY7cIIXs+56EsWvMo/RH8zgH7tOSvqEM/Sckve4KGLyxwwIcu9DcMvIxlWtxfbqyDxr9m8kvmSsMi5eOSqC2CJOO2O/Mc5TqHLCF17XA7q0bB186FeE2j/5l7O/565LLwust73eT9H+S/rR89F/y3ku9JvLAl6j2uJTN3LchArVF+Co/gA1hw5QTBHJ+9M2SHijpWyXdmFIPVz0CKDZAxKMtxj9c2RM51UJAUdkZvT4eEe7Vc+3anRvyWIAtVH9XRNgHYP52MT1SUj8t6dUnhpPzv35pC01xot7Z9iDA7c7H5i1DhJt3UVcG5gWqEGAPYLoG4LyuF7L89SGS3ndglL6fewznLmS1867+JeNfCv7qFITTEZyc0dU0bcvYWiIceTTSEW3Nh3OsyQI8FcpIT7g6wmeuhSgf83+OqD8v6bZl4cuCXKP+1uNzjjsqMjxG/33NHX/n8Oe9nRKoJcKR30OEO504M2bHx3OL05xQfkLSPctH918qi2vfIemjM/fKi3r/W7YAH7rvFgRtjxcSHbn7Yt5uQX2QZ9QS4ViUoaZyHxMtb0d2BGvBnV7xi/ez5ej6fylCPH1dVE34+1+Q9DUVI+Bnlp7A9y9GuiuaP8XNjW8fnmQUmxOoLcI5b7j54HngKgRyhcCp493zYpa3Gv/AxILpApz/u8Yc8S8Mpx4i8nUUbvFdY5ffKtC5yX4I1BbhFla59+PN7UfiTzReOHO+dGmFgCPhOxRTQ7TzEfdfkr6yiajGScsejyPxWAi0+PqXQPS82J4wT9w9gVoizEm0/U+tfDTR0rRS+P1Tku4q6ZOSnl9yrK4jjsu/nB15brXoNc357uWw0f5n2QAjqCXCRuuIx7k1H//C1R8BC6Q3X1zl00w+d8351vtNhv1vkp6xUWc9/xLxOYcW+0g7WHyd36bkrL/52K3FtUXY4Gra0K3jKhpu8fJHdouXRfM+R0rG/FoLtX3sfGo+d80bN1wlMfW/xd1i7UWwNUvRbIvPNXQ07pxvbiTktIOfifhWnFijPrqmAEbJUk0bRvX7dcedm+c4V+qocVopYLFzMxt/xJ876v3jkv5d0oOKEU5NPFHSg4uwu4wtLgu37z99hpv/uGm6I9dpysLi6gM23Z3tjiXKnbPDOWc33rHwsuB23RnB+84mUFMAOXH5bPdtegNHj65esMhagB0J50jVQueP935ddEuzSIaAOgqdXhbke0++6ftYwH0fi+lal6Nd2+J55z9b5ZvXsp/77JRATRGO9n80wG5/cuXNE3mjwrNLmZm3HofwejROJTjCtI8dmb7oyGkXL5T0ywcQ+L2Ovn3vyNta+L9R0teWe0fk61s4urW4WmzdY/hd5e8IbvtzbFgLa4rwkq2rwzqmoYFbAB2xTsvQwn9hqoXXEabF1699TolmQzzzkNxH+tsl+SSMt0l6ckPjxRQIbEoAEd4Ud5cPi51uuR9E7qHryDiOLnIu2P+XF7186rGrIG5dRh/1xLnhe8152KVTMHo/BGpO/vghdATlH3Su9gg4onXjdPd48JFUbymnWMSRVC+V9K9lAS770LlgR8S/I+lmSd9dmrC7/CwqECKSJhJuz+9YtCGBFkT4KnWmG6LhUUVc3XJyyTWtNshd1fx+C/Ab0o1cJ+6j6b/vQBOfJc/kNRDonkBNETY8Nmy0PYUspE4v+HKKwekGXx+Q5O3HvqLSwItwcTkP7O3MkZb4q1SS5te4eY8X1/xpyL+EuSAwLIEWRNjwa9sx7ARYOPC8CHeqR0Ruev7FkgvOFTCRT/770lv4loU28DII7JJAbfGLWuE7r7w7apfOqjSo2Grsxy8RYKcvHEF7N91dJtuac6UFpYmVHMpj2yLQigifaoHYFrUxrJmeJHFKgHNHNR9XdFPB5N4gsWEjWlmyGDvGHGKUCwjUFuGIsoiKFjhrw5fkRTUvuLny4djW3ryZw7vpnBP2DrncXS2qYXy/Q43fNxwij4JAGwRqi7B/eC3Ep37I26A1hhVTAbZ4Httx5k0Z9qEvi64X6JySsNhajGNrcz7eiEY5Y8wlRrmAQG0RXmAiL9mQQD5c07W+/uV4TIDd7DyqJ7wLzmIcYhv/tvmxsFf7pOQNUfIoCCwjgAgv4zTCqxy1uvTMkfCpQzVzvjgf+x5im2u/833J/Y8wkxjjlQggwlfCtdsXT4+rP3asfD6KKOeLD1U+RAXM0tM3dguZgUFgjgAizLwwgRDKJRGw871OWzhdYbGeVj7kgznjENBpfhjqEIBAIYAIMxXycfXHFuFyvtjpBueLY9Etzo7LYpsj48dvdGQR3oRAdwQQ4e5ctqrBIZ6+6bEywVwD7BpfR7i5oXtE0rnXcPSLpiZ4VZdxs70RQIT35tHl48l54Cye0ztESsHfd17XkfP0RI1pSVq0vyQNsdwfvHJQAojwoI5PZWPO7c41XjeZXAN8SKijJC1SDrkaIpepjUuakUPgCAFEeMzpkfO7c2Vj+URlR7OOhnOXtKAWUXKu/43UBGmIMecWo74iAUT4isB28vK5HG4emtMLzgNbgA8t1lmoPynp9imfnE/cYGvyTiYLw7gsAUT4snxbvPuxfG3uA3xqx5xP3PiesrHDgpsX+aiGaNHz2NQkAUS4Sbdc1ChvQ75xpi3ldMdcPidualA0XrJQx+u82873OLbId9GBcXMI9EgAEe7Ra9e3OdIF0x4Ojo59WKdTDN5s4dflCoj8xLmUQ7SopDfE9X3DOwclgAiP43gLbESruSY4R8C/JunHjiDJJyTHPfJmD/LA48wnRroSAUR4JZAd3MYnIz9fUq5ayMJ8qrdDrqiI0jPywB04HhPbJoAIt+2ftazLYvmwcoy9BdiHcTq6XZJGmJaeZQEnD7yWp7jPcAQQ4TFcPtfJLPK4sbh2KAdsQrEFOTf4yU1/ji3ijUGYUULgmgQQ4WuC6+htUcmQBTQE+FgdcAwxl7RFzTB54I4mAKa2TQARbts/51o316AnH19/qsl6zgNH7W9enDv1/nPt5/0Q2D0BRHi/Ls4521hIy814Tp2enN8fPYJdSeHddP5KX4j9zh1GtiEBRHhD2Bs/KudxHdFGWsFmnBJgv2YuZxwbPegLsbEzedx+CSDC+/RtPtHCAhxtK/11SSVDfn/kgUOUPyzphn1iY1QQ2J4AIrw980s/MW++cB7XVQyxSSMfPXTIjpwHjog5ouj/kfToUuJ26XFwfwgMQQAR3p+bIw0RJx5P0xLHRpyPJMp54DiFmTzw/uYLI6pMABGu7ICVHx+VC3GihdMKrywtKR0hH6sFnp64HGmMiKJP7ahbeSjcDgJjEECE9+XnOOXCaYRIQ1hcl7SWzLXD0QPiKlH0vkgyGghsRAAR3gj0Bo+ZdkgLUV1SyTBXOxz3mx5tv8FQeAQExiGACO/D1zmX6w0U0Zx9yUGbIbYmMbchY0kUvQ+KjAICFQggwhWgX+CRr5X0rNIL2HngnJZwRHzomtu8QWOeCziIW0LgEAFEuP+5EVuTvyjpQZKeIOnFCzqjZQGOqoe8OLckjdE/PUYAgcoEEOHKDjjz8dOo1VHvX0i6Szp8c+4ROQWRd8/lxblT1RRnms7bIQABE0CE+54HucevI9slmzLyIlwW4Lldcn3TwXoIdEAAEe7ASQdMDDGNFpVuWfm0E2mIaGvpW+YFt9wZjYW4fucElndIABHu0GnlNAx3M4t+wBbR2JQRvR6mI4uaX7/HUa//7StvU17SV6JPYlgNgUYJIMKNOuaIWbkczekEdzZzGmIa3eZbRNpi2sQ9C/CSvhL90cJiCDROABFu3EEz5uXeEG6s8/F0VL0j3On1fkmuHZ5uusgCHH0m+qOBxRDonAAi3JcD81FDrl5wNcNjj+SBI2/8D5IeWeqHPeJpnwinMI71leiLEtZCoCMCiHBHzppswrDlbzrSnOfQMUQIcF8+x9qdE0CE+3FwRMFOK/jvXpg71Jzn0DFEWYCXnLLcDx0shUCnBBDhfhwXi2tejPNmixslzbWXPLTrzd9/R6msQID78TuW7pwAItyHgx3ZegHO4uk88EuOpCFi4W4qtLlCgt1wffgdKwcggAj34eTYTvy60qjHVk+Pm88RsEvRoiewXxsCTFvKPvyNlQMRQITbd3auC36fpJtm0hBTAY4NG/6+F++cQ57WCLc/ciyEwAAEEOH2nRxlZm7M8+CSknCUGyVlud43Fu28gSMLMBFw+37GwkEJIMJtOz5HwbdIusOkO1ruhubWk96sYXEmAm7br1gHga8SQITbngwRBX9K0l0l5R6/uRlP7vlgAb5Z0pNIQbTtXKyDgAkgwu3Og28qmzNuW0zM1Q650iE348llaJ+T9NDSW6LdUWIZBAYngAi3OwEeKOkvk3nPk/RpST8n6YaZKNe5YdcBu/zM7S2fKumD7Q4PyyAAASLh9ufARyTdf8bM6CE8tzjnZjyuhqAXRPv+xUIIkI5oeA7ENuXPS3JK4vUlMv4zSRbnuPJZcbSjbNihmAaBOQKkI9qcF99fFuFuMzkBI1vr/K93zrlCwtfcFuY2R4dVEIDAVwkgwm1OhmdLeo2k35L0lBkTvRnDmzCc//WVz4prc0RYBQEIzBJAhNubGM4Bv1PS3WaqVyy6PsbIqQpfeXNGeyPBIghA4CQBRPgkok1f4M0Yv186nU3Pe4sG7k5DeAuy64RdR8wFAQh0TAARbst50YjdC28/VRrv2MLojOa/W5wtwFQ/tOU7rIHAtQggwtfCdrE3xQ45R73O8/rfPsbeaQj3jnhyOqLoYkZwYwhAYDsCiPB2rJc8KVpW/pOku6c3cBT9Enq8BgIdEkCE23LaWyX9UDLJ0e8rJL2tLTOxBgIQWIsAIrwWyXXu45yw/7g3hI+q/+w6t+UuEIBAqwQQ4VY9g10QgMAQBBDhIdzMICEAgVYJIMKtega7IACBIQggwkO4mUFCAAKtEkCEW/UMdkEAAkMQQISHcDODhAAEWiWACLfqGeyCAASGIIAID+FmBgkBCLRKABFu1TPYBQEIDEEAER7CzQwSAhBolQAi3KpnsAsCEBiCACI8hJsZJAQg0CoBRLhVz2AXBCAwBAFEeAg3M0gIQKBVAohwq57BLghAYAgCiPAQbmaQEIBAqwQQ4VY9g10QgMAQBBDhIdzMICEAgVYJIMKtega7IACBIQggwkO4mUFCAAKtEkCEW/UMdkEAAkMQQISHcDODhAAEWiWACLfqGeyCAASGIIAID+FmBgkBCLRKABFu1TPYBQEIDEEAER7CzQwSAhBolQAi3KpnsAsCEBiCACI8hJsZJAQg0CqBLwPDhtLIKezqXgAAAABJRU5ErkJggg==');
INSERT INTO bon_sortie_produit( id_bsp, id_bon_sortie, num_sortie, reference, designation, quantite ) VALUES ( 1, 1, 'AC_BS_0001_29062022', 'hhe', 'jjj', 4);
INSERT INTO devis_detail( id_devis, num_facture, dates, tech_id, client_id, objet, valide, disponibilite, reglement, preconisaiton, garanti, etat ) VALUES ( 1, 'AC_PF_0001_29062022', '2022-06-22', 16, 1, 'Test', 'Corect', '2022-06-22', 'cache', '12', 231, 1);
INSERT INTO reclamation( id_reclamation, dates, id_u, client, sollicitant, contact, ref_facture, envoyeur, destinataire, cc, objet, message ) VALUES ( 1, '2022-07-01', 18, 'ETP', 'et', '0974343', 'dfdfsgfdsdf', 'etp@gmail.com', 'nomenandrianinaantonio@gmail.com', 'nomenandrianinaantonio@gmail.com', 'ojj', 'yes');
INSERT INTO reclamation( id_reclamation, dates, id_u, client, sollicitant, contact, ref_facture, envoyeur, destinataire, cc, objet, message ) VALUES ( 2, '2022-07-01', 18, 'ETP', 'et', '0974343', 'dfdfsgfdsdf', 'etp@gmail.com', 'nomenandrianinaantonio@gmail.com', 'nomenandrianinaantonio@gmail.com', 'ojj', 'yes');
INSERT INTO reclamation( id_reclamation, dates, id_u, client, sollicitant, contact, ref_facture, envoyeur, destinataire, cc, objet, message ) VALUES ( 3, '2022-07-01', 18, 'ETP', 'et', '0974343', 'dfdfsgfdsdf', 'etp@gmail.com', 'nomenandrianinaantonio@gmail.com', 'nomenandrianinaantonio@gmail.com', 'ojj', 'yes');
INSERT INTO reparation( id_reparation, id_utilisateur, id_bon_entree, id_bon_entree_produit, avancement, prevu, dates, step ) VALUES ( 2, 17, 1, 1, 68, '0 jours', '2022-06-30', '"undefined;1;2;3;4;5"');
INSERT INTO reparation( id_reparation, id_utilisateur, id_bon_entree, id_bon_entree_produit, avancement, prevu, dates, step ) VALUES ( 1, 17, 1, 1, 0, '0 jours', '2022-06-30', null);
INSERT INTO token( id, token, dateco ) VALUES ( 16, 'bbfc6e44eb013b1ee7d8db331a93d93f', '2022-06-07 01:39:37 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, 'ba85ed8f7c0b7f7cb69c0f397a8bcadd', '2022-06-09 01:05:30 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, '79c64431be2cec136ae9f6ffc24a4157', '2022-06-09 01:28:48 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, '798cd72b1cb5644ec2965bbde5f14b16', '2022-06-09 02:38:20 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, 'fc03983e1ba14c9f2820532a77583948', '2022-06-09 02:59:33 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, '4d83c0ca863b67eaa2d640e311c19fdd', '2022-06-09 03:28:14 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, '9a2c86c1a06a91057ac6f4a89d7b34f6', '2022-06-09 04:11:13 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, '5396351d099c9d0a547d518073a3c3ee', '2022-06-09 04:11:36 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, '91137e0863bfad517e82a9a7980eecfa', '2022-06-16 06:05:16 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, '412fe6d80a26549e82e2c8afc3a0e33c', '2022-06-16 07:34:53 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, 'cc4a748b25c948f88494c98d984e41d1', '2022-06-17 08:14:30 am');
INSERT INTO token( id, token, dateco ) VALUES ( 16, 'd857ec1dc8d733148bc0c6a7aac44b55', '2022-06-19 10:55:38 am');
INSERT INTO token( id, token, dateco ) VALUES ( 16, '395e144e2b69a726bbd6d989064224bd', '2022-06-19 06:32:21 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, '301edf6e28c381027e79def1b64e9c28', '2022-06-19 07:40:57 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, 'b4698810c207edc7914d91efab9c4e4b', '2022-06-19 07:56:58 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, 'f8225890ac30213088b1500d363bccdc', '2022-06-19 08:20:42 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, 'f591e193f9afa44a8ba38630a8e4cf70', '2022-06-21 08:39:52 am');
INSERT INTO token( id, token, dateco ) VALUES ( 18, '061bfabf9e5ce59711c9f4dfc2b81e74', '2022-06-22 03:29:13 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 18, 'fd8c517aa6d07407cfc11eabeb285a01', '2022-06-22 04:56:20 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 18, 'fd8c517aa6d07407cfc11eabeb285a01', '2022-06-22 04:56:20 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 18, 'b761e7e42ec7c9da52e27f32641a3131', '2022-06-23 07:40:35 am');
INSERT INTO token( id, token, dateco ) VALUES ( 18, 'cd493a91a4751cc6f248d27cd4fc6709', '2022-06-23 07:41:34 am');
INSERT INTO token( id, token, dateco ) VALUES ( 16, '117df5eb3bb8b1a0f5749a06911cfe43', '2022-06-25 09:55:19 am');
INSERT INTO token( id, token, dateco ) VALUES ( 16, 'b8bcd701d001e44d2188c50b32a0bc3c', '2022-06-25 10:49:52 am');
INSERT INTO token( id, token, dateco ) VALUES ( 16, 'b34397fccdaa414ba44ad39f01cd8ce6', '2022-06-27 08:14:28 am');
INSERT INTO token( id, token, dateco ) VALUES ( 16, '2aef1db787efbc5e515d00f13cba0046', '2022-06-27 04:16:35 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, 'a67a33ef899d333333aa9d83497a3e39', '2022-06-28 08:30:23 am');
INSERT INTO token( id, token, dateco ) VALUES ( 16, '94f7e77ce7f23bfa2ca8303a0dd0adad', '2022-06-28 10:26:07 am');
INSERT INTO token( id, token, dateco ) VALUES ( 16, 'da142d497c1567b0bd2be94728e9379f', '2022-06-29 10:45:25 am');
INSERT INTO token( id, token, dateco ) VALUES ( 18, '5a52eab5f3f7f64d05189977081516e4', '2022-07-01 08:56:17 am');
INSERT INTO token( id, token, dateco ) VALUES ( 18, '2765ff664a8dbfad3448951534fa9c25', '2022-07-04 08:37:01 am');
INSERT INTO token( id, token, dateco ) VALUES ( 18, '2765ff664a8dbfad3448951534fa9c25', '2022-07-04 08:37:01 am');
INSERT INTO token( id, token, dateco ) VALUES ( 20, '61e1cc7024fc45958370bca9f3228244', '2022-07-04 12:17:40 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 16, '85b9218b53692aef1d784e1e2210330c', '2022-07-04 01:16:27 pm');
INSERT INTO token( id, token, dateco ) VALUES ( 18, 'd789d1902f1b390e592d5b7d3e22ab0a', '2022-07-11 11:01:48 am');


create table devis(
    id serial primary key,
    num VARCHAR(100)   unique   not null DEFAULT 'AC_PF_' || to_char(nextval ('devis_seq'),'FM9990999') || '_'   || to_char(current_date,'DDMMYYYY'),
    referent varchar(300) not null,
    id_client int not null,
    id_user int not null,
    nom_interloc varchar(200) not null,
    contact varchar(10),
    email varchar(100),
    objet varchar(300) not null,
    validite int not null check (validite >0),
    disponibilite varchar(300),
    reglement varchar(300),
    date date default current_date,
    garanti numeric,
    livraison varchar(500),
    preconisation text,
    etat numeric default 0 not null check(etat=1 or etat=0),
    foreign key (id_user) references utilisateur(id),
    foreign key (id_client) references client(id)
);


create or replace VIEW v_facture_details as select facture.id , SUM(details_produit_facture.prix_total,details_produit_facture.total_conso) as prix from facture join details_produit_facture on facture.id=details_produit_facture.id_mere;
