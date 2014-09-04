
-- Sample data set

insert into template(uuid, type, content) values ('a1', 'FREEMARKER', '
''${name}'' frå [[${nationality}]] er ein [[${instrument}]]ist.  Han har spelt med ${collabrators?join(", ")}.

Han var ${occupations?join(", ")}.

')
insert into template(uuid, type, content) values ('a2', 'FREEMARKER', 'Hello ${world}! Copy')

insert into article_set(uuid, template_id, local_template_id) values('b1', 2, 1)

insert into value_override(article_set_id, key, old_value, new_value) values(1, 'instrument', 'saxophone', 'saksofon')
insert into value_override(article_set_id, key, old_value, new_value) values(1, 'occupations', 'musician', 'musikar')

