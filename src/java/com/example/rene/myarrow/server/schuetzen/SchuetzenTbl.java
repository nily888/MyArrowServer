package com.example.rene.myarrow.server.schuetzen;

/**
 * Schnittstelle zur Tabelle SchuetzenTbl
 * Die Klasse liefert
 *
 * SQL-Code zur Erzeugung der Tabelle
 * SQL-Code für alle für MyArrow erforderlichen Statements
 *
 * @author Rene Dueber
 */
public final class SchuetzenTbl implements SchuetzenColumns {

    /**
     * Name der Datenbanktabelle.
     */
    public static final String TABLE_NAME = "schuetzen";

    /**
     * SQL Anweisung zur Schemadefinition.
     */
    public static final String SQL_CREATE =
        "CREATE TABLE " + TABLE_NAME + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "gid TEXT NOT NULL," +
            "name TEXT NOT NULL," +
            "dateiname TEXT," +
            "zeitstempel LONG " +
        ");";

    /**
CREATE TABLE `myarrow`.`schuetzen` (
  `_id` INT ZEROFILL NOT NULL,
  `gid` VARCHAR(50) NULL,
  `name` VARCHAR(50) NULL,
  `dateiname` VARCHAR(256) NULL,
  `zeitstempel` BIGINT NULL,
  PRIMARY KEY (`_id`),
  UNIQUE INDEX `_id_UNIQUE` (`_id` ASC));
    */
    
    /**
     * Standard-Sortierreihenfolge fuer die Tabelle.
     *
     * Sortiert wird nach Zeitstempel absteigend.
     */
    public static final String DEFAULT_SORT_ORDER = NAME + " DESC";

    /**
     * SQL Anweisung zur Loeschung der Tabelle.
     */
    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * SQL Anweisung fuer Erzeugung eines neuen Eintrages.
     */
    public static final String STMT_INSERT = "INSERT INTO "+ TABLE_NAME +
            " (gid, name, dateiname, zeitstempel) " + "VALUES (?, ?, ?, ?)";

    /**
     * SQL Anweisung fuer Erzeugung eines neuen Eintrages.
     */
    public static final String STMT_UPDATE = "UPDATE "+ TABLE_NAME + " SET " +
            " name=?, dateiname=?, zeitstempel=? WHERE GID=?";

    /**
     * SQL-Anweisung zur Loeschung aller Schuetzen
     */
    public static final String STMT_SCHUETZE_DELETE = "DELETE " + TABLE_NAME;

    /**
     * Liste aller bekannten Attribute.
     */
    public static final String[] ALL_COLUMNS = new String[] {
                ID,
                GID,
                NAME,
                DATEINAME,
                ZEITSTEMPEL
    };

    /**
     * WHERE-Bedingung fuer ID-Anfrage.
     */
    public static final String STMT_WHERE_GID_EQUALS = 
            "select " +
                ID + ", " +
                GID + ", " +
                NAME + ", " +
                DATEINAME + ", " +
                ZEITSTEMPEL + " " +
            "from " + TABLE_NAME + " " +
            "where " + GID + "=?";

    /**
     * WHERE-Bedingung fuer Pfeil-Anfrage.
     */
    public static final String WHERE_SCHUETZE_EQUALS = NAME + "=?";

    /**
    * Statement to get all records (ID, GID, Name)
    */
    public static final String STMT_WHERE_GID_NAME_EQUALS  =
            "select " + ID   + ", " + GID + ", " + NAME + " " +
            "from " + TABLE_NAME;
    
    /**
     * Statement to get all records (ID, GID, Name) where the name is equal
     */
    public static final String STMT_WHERE_GID_NAME_NAME_EQUALS  =
            "select " + ID   + ", " + GID + ", " + NAME + " " +
            "from " + TABLE_NAME + " " +
            "where " + NAME + "=?";

    /**
     * Klasse enthaelt nur Konstanten.
     * Daher keine Objekterzeugung vorgesehen.
     */
    private SchuetzenTbl() {
    }
}
