package com.example.rene.myarrow.server.uptime;

import com.example.rene.myarrow.server.uptime.upTimeColumns;

/**
 * Schnittstelle zur Tabelle upTimeTbl
 * Die Klasse liefert
 *
 * SQL-Code zur Erzeugung der Tabelle
 * SQL-Code für alle für MyArrow erforderlichen Statements
 *
 * @author Rene Dueber
 */
public final class upTimeTbl implements upTimeColumns {

    /**
     * Name der Datenbanktabelle.
     */
    public static final String TABLE_NAME = "uptime";

    /**
     * SQL Anweisung zur Schemadefinition.
     */
    public static final String SQL_CREATE =
        "CREATE TABLE " + TABLE_NAME + " (" +
            "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "starttime LONG," +
            "endtime LONG " +
        ");";

    /**
CREATE TABLE `myarrow`.`uptime` (
  `_id` INT NOT NULL AUTO_INCREMENT,
  `starttime` BIGINT NOT NULL,
  `endtime` BIGINT NULL,
  PRIMARY KEY (`_id`),
  UNIQUE INDEX `_id_UNIQUE` (`_id` ASC));
     */

    /**
     * Standard-Sortierreihenfolge fuer die Tabelle.
     *
     * Sortiert wird nach Zeitstempel absteigend.
     */
    public static final String DEFAULT_SORT_ORDER = STARTTIME + " DESC";

    /**
     * SQL Anweisung zur Loeschung der Tabelle.
     */
    public static final String SQL_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME;

    /**
     * SQL Anweisung fuer Erzeugung eines neuen Eintrages.
     */
    public static final String STMT_INSERT = "INSERT INTO "+ TABLE_NAME +
            " (starttime, endtime) " + "VALUES (?, ?)";

    /**
     * SQL-Anweisung zur Loeschung aller Pfeile
     */
    public static final String STMT_SCHUETZE_DELETE = "DELETE " + TABLE_NAME;

    /**
     * Liste aller bekannten Attribute.
     */
    public static final String[] ALL_COLUMNS = new String[] {
                ID,
                STARTTIME,
                ENDTIME
    };

    /**
     * WHERE-Bedingung fuer ID-Anfrage.
     */
    public static final String WHERE_ID_EQUALS = ID + "=?";

    /**
     * Klasse enthaelt nur Konstanten.
     * Daher keine Objekterzeugung vorgesehen.
     */
    private upTimeTbl() {
    }
}
