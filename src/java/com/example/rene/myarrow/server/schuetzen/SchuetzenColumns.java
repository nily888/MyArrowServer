package com.example.rene.myarrow.server.schuetzen;

/**
 * Created by nily on 14.12.15.
 */
public interface SchuetzenColumns {

    /**
     * Primaerschluessel.
     * 
     * TEXT
     */
    String ID = "_id";

    /**
     * Global ID
     * 
     * TEXT
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
     * Zeitpunkt der letzten Änderung.
     *
     * LONG
     */
    String ZEITSTEMPEL = "zeitstempel";

}
