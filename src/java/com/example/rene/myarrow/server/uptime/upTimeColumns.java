package com.example.rene.myarrow.server.uptime;

/**
 * Created by René Düber on 01.06.16.
 */
public interface upTimeColumns {

    /**
     * Primaerschluessel.
     * 
     * TEXT
     */
    String ID = "_id";

    /**
     * Startzeitpunkt
     * 
     * LONG
     */
    String STARTTIME = "starttime";

    /**
     * Endzeiteitpunkt
     *
     * LONG
     */
    String ENDTIME = "endtime";

}
