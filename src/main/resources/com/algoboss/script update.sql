ALTER TABLE dev_prototype_component_property ADD COLUMN prototype_component_behaviors_id bigint;
  
  
  ALTER TABLE dev_report_requirement ADD COLUMN report_datasource character varying(255);
ALTER TABLE dev_report_requirement ADD COLUMN report_file character varying(255);


-- Table: dev_prototype_component_behaviors

-- DROP TABLE dev_prototype_component_behaviors;

CREATE TABLE dev_prototype_component_behaviors
(
  prototype_component_behaviors_id bigint NOT NULL,
  prototype_component_name character varying(255),
  prototype_component_type character varying(255),
  parent_id bigint,
  CONSTRAINT dev_prototype_component_behaviors_pkey PRIMARY KEY (prototype_component_behaviors_id),
  CONSTRAINT fk_dev_prototype_component_behaviors_parent_id FOREIGN KEY (parent_id)
      REFERENCES dev_prototype_component_property (prototype_component_property_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE dev_prototype_component_behaviors
  OWNER TO postgres;
  
  

CREATE SEQUENCE sequence_dev_prototype_component_behaviors
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 78
  CACHE 1;
ALTER TABLE sequence_dev_prototype_component_behaviors
  OWNER TO postgres;
  
  --not (property_value = 'null' or property_value is null or property_value = 'false'  or property_value = '0')
  
  
	--19/07/2014
  ALTER TABLE dev_entity_class   ADD COLUMN canonical_class_name character varying(255);
  
  ALTER TABLE adm_service ADD COLUMN description character varying(1000);
  
	--28/07/2014
  ALTER TABLE dev_requirement ADD COLUMN requirement_style character varying(1000);
	--11/08/2014
  ALTER TABLE dev_report_field_container
  ADD COLUMN requirement_id bigint;
ALTER TABLE dev_report_field_container
  ADD CONSTRAINT fk_dev_requirement FOREIGN KEY (requirement_id) REFERENCES dev_requirement (requirement_id) ON UPDATE NO ACTION ON DELETE NO ACTION;
--22/09/2014
  ALTER TABLE dev_entity_object
  ADD COLUMN user_id bigint;
ALTER TABLE dev_entity_object
  ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES sec_user (user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;

    ALTER TABLE dev_entity_class
  ADD COLUMN user_id bigint;
ALTER TABLE dev_entity_class
  ADD CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES sec_user (user_id) ON UPDATE NO ACTION ON DELETE NO ACTION;
