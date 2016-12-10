package com.example.rene.myarrow.server.rundenschuetzen;

/**
 * Created by nily on 15.12.15.
 */
public interface RundenSchuetzenColumns {

    /**
     * Primaerschluessel.
     */
    String ID = "_id";

    /**
     * globaler Primaerschluessel.
     */
    String GID = "gid";

    /**
     * Pflichtfeld: Schuetzen-GID
     *
     * TEXT
     */
    String SCHUETZENGID = "schuetzengid";

    /**
     * Pflichtfeld: Runden-GID
     *
     * TEXT
     */
    String RUNDENGID = "rundengid";

    /**
     * Pflichtfeld: Gesamtergebnis
     *
     * INTEGER
     */
    String GESAMTERGEBNIS = "gesamtergebnis";

    /**
     * Zeitpunkt der letzten Änderung.
     *
     * LONG
     */
    String ZEITSTEMPEL = "zeitstempel";

}
