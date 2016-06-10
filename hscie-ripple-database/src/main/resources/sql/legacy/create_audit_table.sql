DROP TABLE IF EXISTS poc_legacy.audit;

CREATE TABLE poc_legacy.audit (
  id              		BIGINT          NOT NULL    AUTO_INCREMENT,
  requester_username    VARCHAR(100)    NOT NULL,
  target_nhs_number     INT             NOT NULL,
  target_resource       VARCHAR(500)    NOT NULL,
  request_date_time     DATETIME        NOT NULL,
  PRIMARY KEY   (id)
);