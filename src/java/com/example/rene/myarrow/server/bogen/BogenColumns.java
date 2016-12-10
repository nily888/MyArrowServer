package com.example.rene.myarrow.server.bogen;

/**
 * Created by nily on 14.12.15.
 */
public interface BogenColumns {

    /**
     * Primaerschluessel.
     * */
    String ID = "_id";

    /**
     * Globaler Primaerschluessel
     */
    String GID = "gid";

    /**
     * Pflichtfeld: Name
     *
     * TEXT
     */
    String NAME = "name";

    /**
     * Pflichtfeld: Dateiname
     *
     * TEXT
     */
    String DATEINAME = "dateiname";

    /**
     * Pflichtfeld: Dateiname
     *
     * BOOLEAN
     */
    String STANDARD = "standard";

    /**
     * Zeitpunkt der letzten Änderung.
     *
     * LONG
     */
    String ZEITSTEMPEL = "zeitstempel";

}
