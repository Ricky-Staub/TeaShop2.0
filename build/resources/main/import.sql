insert into rank(id,title,seeds,reduction) values('805fc890-247d-4d61-a169-e6408f9e231c','bronze',0,0.1);
insert into rank(id,title,seeds,reduction) values('13e45fae-016c-4342-abb4-744ffc5628e9','silver',20,0.96);
insert into rank(id,title,seeds,reduction) values('9ef05303-146e-405e-bed8-e481f6f085b1','gold',60,0.93);
insert into rank(id,title,seeds,reduction) values('131e912c-f211-4526-b362-ffa44743da9d','platinum',140,0.91);
insert into rank(id,title,seeds,reduction) values('ea83a03e-6326-4020-a721-1d1f0715ef36','diamond',300,0.9);

insert into country(id, country)values('b0dd49e3-d4f7-4bf0-91bd-f70930c02fc0','china');
insert into country(id, country)values('76ff2095-2bd5-455b-9b1a-6d71cfa986b7','taiwan');
insert into country(id, country)values('16ded094-2341-49bd-85ae-78451faac49f','japan');
insert into country(id, country)values('6bed40bc-1f21-4126-9046-5bdb208cd7e4','indien');

insert into tea_type(id, min_age, min_seeds, tea_type) values ('1e12b05a-2915-447b-a8a1-6bbd5864e8cd', 0, 0, 'white');
insert into tea_type(id, min_age, min_seeds, tea_type) values ('4d195cc9-9491-4b62-b1d4-b16c76cc9c8b', 0, 0, 'yellow');
insert into tea_type(id, min_age, min_seeds, tea_type) values ('0b227894-f73d-4e2c-9fed-d9167c88b1db', 0, 140, 'raw puerh');
insert into tea_type(id, min_age, min_seeds, tea_type) values ('d64dc94d-33be-4855-ae43-79194dcea7fe', 18, 0, 'medicinal herbs');
insert into tea_type(id, min_age, min_seeds, tea_type) values ('09b1cf5b-2426-4b31-8a18-c68adfcc9dcb', 0, 0, 'green');
insert into tea_type(id, min_age, min_seeds, tea_type) values ('005956c8-7494-4df8-8e6e-a9492d19c982', 0, 140, 'oolong');
insert into tea_type(id, min_age, min_seeds, tea_type) values ('1d2e4628-91a0-49cd-8dd5-b67267c6ff1c', 0, 0, 'ripened');
insert into tea_type(id, min_age, min_seeds, tea_type) values ('4d731a42-bc28-4f2f-9dc4-78e8498cfc0f', 0, 0, 'scented');
insert into tea_type(id, min_age, min_seeds, tea_type) values ('072667ca-852f-4775-ac2c-049acde27718', 0, 0, 'matcha');
insert into tea_type(id, min_age, min_seeds, tea_type) values ('16ff9d0b-1c35-4b62-a655-19f79e905f09', 0, 0, 'black');
insert into tea_type(id, min_age, min_seeds, tea_type) values ('de07ede6-b210-4707-8c9e-5223e3d66ba6', 0, 0, 'tisanes');
insert into tea_type(id, min_age, min_seeds, tea_type) values ('1085142b-b7f2-4bcd-a44f-e4798744faaf', 18, 0, 'functional blends');

insert into tea(id, harvest_date, price, stock, description, country_id, tea_type_id) values('a47d9683-2aed-42a3-846f-ccea365c7c9d','2021-04-23', 34 , 25, 'white jasmine',                   'b0dd49e3-d4f7-4bf0-91bd-f70930c02fc0','1e12b05a-2915-447b-a8a1-6bbd5864e8cd');
insert into tea(id, harvest_date, price, stock, description, country_id, tea_type_id) values('4b484dad-fab1-4833-99a1-9060e44b7556','2021-04-14', 31 , 25, 'amber mountain',                  'b0dd49e3-d4f7-4bf0-91bd-f70930c02fc0','4d195cc9-9491-4b62-b1d4-b16c76cc9c8b');
insert into tea(id, harvest_date, price, stock, description, country_id, tea_type_id) values('aec9da51-98ff-4079-bc05-9f9805939282','2021-04-01', 125, 25, 'mist pilgrim',                   'b0dd49e3-d4f7-4bf0-91bd-f70930c02fc0','0b227894-f73d-4e2c-9fed-d9167c88b1db');
insert into tea(id, harvest_date, price, stock, description, country_id, tea_type_id) values('ab5acc7d-2826-449c-b5d2-90c60467f653','2021-05-01', 53 , 25, 'ceremonial matcha tea',           '16ded094-2341-49bd-85ae-78451faac49f','09b1cf5b-2426-4b31-8a18-c68adfcc9dcb');
insert into tea(id, harvest_date, price, stock, description, country_id, tea_type_id) values('d37993db-1f5d-4112-b918-267985693245','2021-05-03', 11 , 25, 'dandelion leaf',                  'b0dd49e3-d4f7-4bf0-91bd-f70930c02fc0','d64dc94d-33be-4855-ae43-79194dcea7fe');
insert into tea(id, harvest_date, price, stock, description, country_id, tea_type_id) values('dc852512-f356-4132-ba8a-52edbd68eb5d','2021-04-02', 50 , 25, 'amber gaba oolong',               '76ff2095-2bd5-455b-9b1a-6d71cfa986b7','005956c8-7494-4df8-8e6e-a9492d19c982');
insert into tea(id, harvest_date, price, stock, description, country_id, tea_type_id) values('fac6b968-827b-4561-a7d2-32991230e3db','2021-05-01', 36 , 25, 'mulberry leaf powder',            '16ded094-2341-49bd-85ae-78451faac49f','de07ede6-b210-4707-8c9e-5223e3d66ba6');
insert into tea(id, harvest_date, price, stock, description, country_id, tea_type_id) values('11276c0e-195e-11ed-861d-0242ac120002','2021-04-01', 54 , 25, 'river high',                      'b0dd49e3-d4f7-4bf0-91bd-f70930c02fc0','1085142b-b7f2-4bcd-a44f-e4798744faaf');
insert into tea(id, harvest_date, price, stock, description, country_id, tea_type_id) values('20f4984f-b376-4523-895c-47fe147e71ec','1996-05-01', 45 , 25, 'black yunnan tuo 96',             'b0dd49e3-d4f7-4bf0-91bd-f70930c02fc0','1d2e4628-91a0-49cd-8dd5-b67267c6ff1c');
insert into tea(id, harvest_date, price, stock, description, country_id, tea_type_id) values('d7eb2c65-c74d-47e9-aa0e-7a7be3ce8304','2021-04-01', 42 , 25, 'traditional lapsang blending set','b0dd49e3-d4f7-4bf0-91bd-f70930c02fc0','16ff9d0b-1c35-4b62-a655-19f79e905f09');
insert into tea(id, harvest_date, price, stock, description, country_id, tea_type_id) values('fc0409ba-dc09-411f-add2-709870715be1','2021-04-1',  54 , 25, 'ren shen wulong',                 'b0dd49e3-d4f7-4bf0-91bd-f70930c02fc0','4d731a42-bc28-4f2f-9dc4-78e8498cfc0f');
insert into tea(id, harvest_date, price, stock, description, country_id, tea_type_id) values('9167ba4f-5374-4f08-bae5-489d3d06e53a','2021-02-01', 33 , 25, 'juice journo',                    'b0dd49e3-d4f7-4bf0-91bd-f70930c02fc0','4d731a42-bc28-4f2f-9dc4-78e8498cfc0f');

insert into role(id, name) values('48608063-985b-496d-94c6-1df46bb3ba26','ADMIN');
insert into role(id, name) values('ce7c3462-23f3-4c37-a75b-b945367f59ae','USER');

insert into authority(id, name) values('3648810a-6722-4e70-9667-69409afc699d','USER_SEE');
insert into authority(id, name) values('5fc20c1e-afbc-4e22-804b-85bf0b828b56','USER_MODIFY');
insert into authority(id, name) values('94f30ecd-8f8c-4a15-acdf-08d020496305','PRODUCT_SEE');
insert into authority(id, name) values('94f30eef-8f8c-4a15-acdf-08d020496305','CAN_SEE_STATISTICS');

insert into role_authority(role_id, authority_id) values('48608063-985b-496d-94c6-1df46bb3ba26','3648810a-6722-4e70-9667-69409afc699d');
insert into role_authority(role_id, authority_id) values('48608063-985b-496d-94c6-1df46bb3ba26','5fc20c1e-afbc-4e22-804b-85bf0b828b56');
insert into role_authority(role_id, authority_id) values('48608063-985b-496d-94c6-1df46bb3ba26','94f30ecd-8f8c-4a15-acdf-08d020496305');
insert into role_authority(role_id, authority_id) values('48608063-985b-496d-94c6-1df46bb3ba26','94f30eef-8f8c-4a15-acdf-08d020496305');
insert into role_authority(role_id, authority_id) values('ce7c3462-23f3-4c37-a75b-b945367f59ae','94f30ecd-8f8c-4a15-acdf-08d020496305');
insert into role_authority(role_id, authority_id) values('ce7c3462-23f3-4c37-a75b-b945367f59ae','3648810a-6722-4e70-9667-69409afc699d');