--<ScriptOptions statementTerminator=";"/>

--todo 2015/07/29
ALTER TABLE adm_contract
  ADD COLUMN private_key character varying;
ALTER TABLE adm_contract
  ADD COLUMN system_schema character varying;
--todo 2015/07/31  
ALTER TABLE adm_contract
  ADD COLUMN system_context_path character varying;  
  