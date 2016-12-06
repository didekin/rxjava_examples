SET FOREIGN_KEY_CHECKS = 0;

# ==============================================
# .......... COMUNIDADES Y PROVINCIAS ..........
# ==============================================

DROP TABLE IF EXISTS municipio;
DROP TABLE IF EXISTS provincia;
DROP TABLE IF EXISTS comunidad_autonoma;

CREATE TABLE comunidad_autonoma (
  ca_id  SMALLINT UNSIGNED NOT NULL,
  nombre VARCHAR(100)      NOT NULL,
  PRIMARY KEY (ca_id)
);

CREATE TABLE municipio
(
  m_id   INTEGER UNSIGNED  NOT NULL AUTO_INCREMENT,
  pr_id  SMALLINT UNSIGNED NOT NULL, -- código de provincia del fichero de carga.
  m_cd   SMALLINT UNSIGNED NOT NULL, -- codigo municipio intra-provincial. No único.
  nombre VARCHAR(100)      NOT NULL,
  PRIMARY KEY (m_id),
  UNIQUE (pr_id, m_cd),
  INDEX id_parent_provincia (pr_id),
  FOREIGN KEY (pr_id) REFERENCES provincia (pr_id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);

CREATE TABLE provincia
(
  pr_id  SMALLINT UNSIGNED NOT NULL, -- código INE de la provincia
  ca_id  SMALLINT UNSIGNED NOT NULL,
  nombre VARCHAR(100)      NOT NULL,
  PRIMARY KEY (pr_id),
  INDEX id_parent_comunidad (ca_id),
  FOREIGN KEY (ca_id) REFERENCES comunidad_autonoma (ca_id)
    ON UPDATE CASCADE
    ON DELETE RESTRICT
);