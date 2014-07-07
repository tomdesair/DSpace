alter table metadatavalue rename item_id to resource_id;
UPDATE metadatavalue SET resource_id = NULL;
alter table metadatavalue add column resource_type_id integer;
UPDATE metadatavalue SET resource_type_id = 2;

-- ---------
-- community
-- ---------

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
community_id AS resource_id,
4 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'description' and qualifier is null) AS metadata_field_id,
introductory_text AS text_value,
null AS text_lang,
0 AS place
FROM community where not introductory_text is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
community_id AS resource_id,
4 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'description' and qualifier = 'abstract') AS metadata_field_id,
short_description AS text_value,
null AS text_lang,
0 AS place
FROM community where not short_description is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
community_id AS resource_id,
4 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'description' and qualifier = 'tableofcontents') AS metadata_field_id,
side_bar_text AS text_value,
null AS text_lang,
0 AS place
FROM community where not side_bar_text is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
community_id AS resource_id,
4 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'rights' and qualifier is null) AS metadata_field_id,
copyright_text AS text_value,
null AS text_lang,
0 AS place
FROM community where not copyright_text is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
community_id AS resource_id,
4 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'title' and qualifier is null) AS metadata_field_id,
name AS text_value,
null AS text_lang,
0 AS place
FROM community where not name is null;

alter table community drop column introductory_text, drop column short_description, drop column side_bar_text, drop column copyright_text, drop column name;


-- ----------
-- collection
-- ----------



INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
collection_id AS resource_id,
3 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'description' and qualifier is null) AS metadata_field_id,
introductory_text AS text_value,
null AS text_lang,
0 AS place
FROM collection where not introductory_text is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
collection_id AS resource_id,
3 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'description' and qualifier = 'abstract') AS metadata_field_id,
short_description AS text_value,
null AS text_lang,
0 AS place
FROM collection where not short_description is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
collection_id AS resource_id,
3 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'description' and qualifier = 'tableofcontents') AS metadata_field_id,
side_bar_text AS text_value,
null AS text_lang,
0 AS place
FROM collection where not side_bar_text is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
collection_id AS resource_id,
3 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'rights' and qualifier is null) AS metadata_field_id,
copyright_text AS text_value,
null AS text_lang,
0 AS place
FROM collection where not copyright_text is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
collection_id AS resource_id,
3 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'title' and qualifier is null) AS metadata_field_id,
name AS text_value,
null AS text_lang,
0 AS place
FROM collection where not name is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
collection_id AS resource_id,
3 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'provenance' and qualifier is null) AS metadata_field_id,
provenance_description AS text_value,
null AS text_lang,
0 AS place
FROM collection where not provenance_description is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
collection_id AS resource_id,
3 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'rights' and qualifier = 'license') AS metadata_field_id,
license AS text_value,
null AS text_lang,
0 AS place
FROM collection where not license is null;

alter table collection drop column introductory_text, drop column short_description, drop column copyright_text, drop column side_bar_text, drop column name, drop column license, drop column provenance_description;


-- ---------
-- bundle
-- ---------

alter table metadatavalue drop constraint metadatavalue_item_id_fkey;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
bundle_id AS resource_id,
1 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'title' and qualifier is null) AS metadata_field_id,
name AS text_value,
null AS text_lang,
0 AS place
FROM bundle where not name is null;

alter table bundle drop column name;



-- ---------
-- bitstream
-- ---------


INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
bitstream_id AS resource_id,
0 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'title' and qualifier is null) AS metadata_field_id,
name AS text_value,
null AS text_lang,
0 AS place
FROM bitstream where not name is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
bitstream_id AS resource_id,
0 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'description' and qualifier is null) AS metadata_field_id,
description AS text_value,
null AS text_lang,
0 AS place
FROM bitstream where not description is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
bitstream_id AS resource_id,
0 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'format' and qualifier is null) AS metadata_field_id,
user_format_description AS text_value,
null AS text_lang,
0 AS place
FROM bitstream where not user_format_description is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
bitstream_id AS resource_id,
0 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'source' and qualifier is null) AS metadata_field_id,
source AS text_value,
null AS text_lang,
0 AS place
FROM bitstream where not source is null;

alter table bitstream drop column name, drop column description, drop column user_format_description, drop column source;


-- ---------
-- epersongroup
-- ---------

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
eperson_group_id AS resource_id,
6 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='dc') and element = 'title' and qualifier is null) AS metadata_field_id,
name AS text_value,
null AS text_lang,
0 AS place
FROM epersongroup where not name is null;

alter table epersongroup drop column name;



-- ---------
-- eperson
-- ---------



INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
eperson_id AS resource_id,
7 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='eperson') and element = 'email' and qualifier is null) AS metadata_field_id,
email AS text_value,
null AS text_lang,
0 AS place
FROM eperson where not email is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
eperson_id AS resource_id,
7 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='eperson') and element = 'firstname' and qualifier is null) AS metadata_field_id,
firstname AS text_value,
null AS text_lang,
0 AS place
FROM eperson where not firstname is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
eperson_id AS resource_id,
7 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='eperson') and element = 'lastname' and qualifier is null) AS metadata_field_id,
lastname AS text_value,
null AS text_lang,
0 AS place
FROM eperson where not lastname is null;

INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
eperson_id AS resource_id,
7 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='eperson') and element = 'phone' and qualifier is null) AS metadata_field_id,
phone AS text_value,
null AS text_lang,
0 AS place
FROM eperson where not phone is null;


INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
eperson_id AS resource_id,
7 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='eperson') and element = 'netid' and qualifier is null) AS metadata_field_id,
netid AS text_value,
null AS text_lang,
0 AS place
FROM eperson where not netid is null;


INSERT INTO metadatavalue (resource_id, resource_type_id, metadata_field_id, text_value, text_lang, place)
SELECT
eperson_id AS resource_id,
7 AS resource_type_id,
(select metadata_field_id from metadatafieldregistry where metadata_schema_id=(select metadata_schema_id from metadataschemaregistry where short_id='eperson') and element = 'language' and qualifier is null) AS metadata_field_id,
language AS text_value,
null AS text_lang,
0 AS place
FROM eperson where not language is null;


alter table eperson drop column email, drop column firstname, drop column lastname, drop column phone, drop column netid, drop column language;