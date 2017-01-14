package com.example.rene.myarrow.server.parcour;

import com.example.rene.myarrow.server.MyArrowDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nily on 15.12.15.
 */
public class ParcourSpeicher {

    /** Markierung für Logging. */
    private static final String TAG = "ParcourSpeicher";

    /**
     * Verweis auf die Mobilfunknummern-Datenbank.
     */
    private final Connection mDb;

    /**
     * Erzeugt einen neuen SchuetzenSpeicher.<br>
     * Dabei wird sichergestellt, dass die zugrundeliegende
     * Datenbank unmittelbar nutzbar ist.
     */
    public ParcourSpeicher() {
        mDb = new MyArrowDB().getInstance();
    }

    /**
     * Legt einen neuen Schuetzen in der Datenbank an.
     *
     * @param name
     *          Name des Schuetzen.
     * @param gid
     *          Globale ID
     * @param anzahl_ziele
     * @param strasse
     * @param plz
     * @param ort
     * @param gps_lat_koordinaten
     * @param gps_lon_koordinaten
     * @param anmerkung
     * @param standard
     * @param zeitstempel
     *      Zeitpunkt des Kontakts.
     */
    public void insertParcour(
            String gid,
            String name,
            int anzahl_ziele,
            String strasse,
            String plz,
            String ort,
            String gps_lat_koordinaten,
            String gps_lon_koordinaten,
            String anmerkung,
            int standard,
            long zeitstempel) {
        
        PreparedStatement insertData = null;
        
        try {
            mDb.setAutoCommit(false);
            /**
             * Datensatz einfügen
             */
            System.out.println("System: insertParcour(): Datensatz einfügen");
            insertData = mDb.prepareStatement(ParcourTbl.STMT_INSERT);
            insertData.setString(1, gid);
            insertData.setString(2, name);
            insertData.setInt(3, anzahl_ziele);
            insertData.setString(4, strasse);
            insertData.setString(5, plz);
            insertData.setString(6, ort);
            insertData.setString(7, gps_lat_koordinaten);
            insertData.setString(8, gps_lon_koordinaten);
            insertData.setString(9, anmerkung);
            insertData.setInt(10, standard);
            insertData.setLong(11, zeitstempel);
            insertData.executeUpdate();
            mDb.commit();
        } catch (SQLException ex) {
            /**
             * Wenn Fehlercode = 1062; Duplicate, einfach ein Update
             */
            if (ex.getErrorCode()==1062){
                try {
                    System.out.println("System: insertParcour(): Datensatz aktualisieren");
                    insertData = mDb.prepareStatement(ParcourTbl.STMT_UPDATE);
                    insertData.setString(1, name);
                    insertData.setInt(2, anzahl_ziele);
                    insertData.setString(3, strasse);
                    insertData.setString(4, plz);
                    insertData.setString(5, ort);
                    insertData.setString(6, gps_lat_koordinaten);
                    insertData.setString(7, gps_lon_koordinaten);
                    insertData.setString(8, anmerkung);
                    insertData.setInt(9, standard);
                    insertData.setLong(10, zeitstempel);
                    insertData.setString(11, gid);
                    insertData.executeUpdate();
                    mDb.commit();
                } catch(SQLException excep) {
                    System.out.println("System: insertParcour(): Error Code    = " + excep.getErrorCode());
                    System.out.println("System: insertParcour(): Error Message = " + excep.getMessage());
                    System.err.print(excep);
                }
            } else {
                System.out.println("System: insertParcour(): Error Code    = " + ex.getErrorCode());
                System.out.println("System: insertParcour(): Error Message = " + ex.getMessage());
                System.out.println(ex);
                if (mDb != null) {
                    try {
                        System.err.print("System: insertParcour(): Transaction is being rolled back");
                        mDb.rollback();
                    } catch(SQLException excep) {
                        System.err.print(excep);
                   }
                }
            }
        } finally {
            if (insertData != null) {
                try {
                    System.out.print("System: insertParcour(): Transaction will be closed");
                    insertData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
            try {
                System.out.print("System: insertParcour(): AutoCommit() switched on again");
                mDb.setAutoCommit(true);
            } catch(SQLException excep) {
                System.err.print(excep);
            }                
        }
    }

    /**
     * Speichert einen neuen Schuetzen. Ist dieser bereits in der
     * Datenbank bekannt, wird der vorhandene Datensatz
     * geändert.<br>
     * Ansonsten wird ein neuer Datensatz erzeugt.
     *
     * @param parcour
     */
    public void insertParcour(Parcour parcour) {
        insertParcour(
            parcour.getGID(),
            parcour.getName(),
            parcour.getAnzahlZiele(),
            parcour.getStrasse(),
            parcour.getPLZ(),
            parcour.getOrt(),
            parcour.getGPS_Lat_Koordinaten(),
            parcour.getGPS_Lon_Koordinaten(),
            parcour.getAnmerkung(),
            parcour.getStandard(),
            parcour.getZeitstempel());
    }

     /**
     * Check for duplicate entries based only on the name
     */
    public String[][] checkForDuplicates() {
        PreparedStatement queryData1;
        PreparedStatement queryData2;
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        String[][] checkForDuplicates = null;
        int n=0;
        queryData1 = null;
        queryData2 = null;
        try {
            queryData1 = mDb.prepareStatement(ParcourTbl.STMT_WHERE_GID_NAME_EQUALS);
            rs1 = queryData1.executeQuery();
            
            /**
             * check if something was found, else return null
             */
            if (!rs1.first()) {
                System.err.println("System: checkForDuplicates(): No records found!!");
                return null;
            }
            
            while (!rs1.isAfterLast()) {
                
                /**
                 * Search for the same name
                 */
                queryData2 = mDb.prepareStatement(ParcourTbl.STMT_WHERE_GID_NAME_NAME_EQUALS);
                queryData1.setString(1, rs1.getString(ParcourTbl.NAME));
                rs2 = queryData2.executeQuery();

                /**
                * check if something was found and store it
                */
                if (rs1.first()) {
                    checkForDuplicates[n][1] = rs1.getString(ParcourTbl.GID);
                    checkForDuplicates[n][2] = rs2.getString(ParcourTbl.GID);
                    checkForDuplicates[n][3] = rs2.getString(ParcourTbl.NAME);
                    n++;
                }
                
                /*
                * close recordset 2 and to the next record
                */
                rs2.close();
                queryData2.close();
                rs1.next();

            }

            /**
            * close recordset 2 and to the next record
            */
            rs1.close();
            queryData1.close();
            return checkForDuplicates;
            
        } catch (SQLException ex) {
            System.err.println("System: checkForDuplicates(): " + ex);
            if (mDb != null) {
                try {
                    System.out.print("System: checkForDuplicates(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return null;   
        } finally {
            try {
                System.out.print("System: checkForDuplicates: Alles wird geschlossen");
                if (rs1 != null) rs1.close();
                if (rs2 != null) rs2.close();
                if (queryData1 != null) queryData1.close();
                if (queryData2 != null) queryData2.close();
            } catch(SQLException excep) {
                System.err.print("System: checkForDuplicates(): " + excep);
            }
        }
    }

    /**
     * Schliesst die zugrundeliegende Datenbank.
     * <br>
     * Vor dem naechsten Zugriff muss oeffnen() aufgerufen
     * werden.
     */
    public void schliessen() {
        try {
            mDb.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public Parcour loadParcourDetails(String gid) {
        PreparedStatement queryData;
        ResultSet rs = null;
        queryData = null;
        try {
            queryData = mDb.prepareStatement(ParcourTbl.STMT_WHERE_GID_EQUALS);
            queryData.setString(1, gid);
            rs = queryData.executeQuery();
            rs.last();
            if (rs.getRow() == 1) {
                Parcour loadParcourDetails = new Parcour();
                loadParcourDetails.setID(rs.getInt(ParcourTbl.ID));
                loadParcourDetails.setGID(rs.getString(ParcourTbl.GID));
                loadParcourDetails.setName(rs.getString(ParcourTbl.NAME));
                loadParcourDetails.setAnzahlZiele(rs.getInt(ParcourTbl.ANZAHL_ZIELE));
                loadParcourDetails.setStrasse(rs.getString(ParcourTbl.STRASSE));
                loadParcourDetails.setPLZ(rs.getString(ParcourTbl.PLZ));
                loadParcourDetails.setOrt(rs.getString(ParcourTbl.ORT));
                loadParcourDetails.setGPS_Lat_Koordinaten(rs.getString(ParcourTbl.GPS_LAT_KOORDINATEN));
                loadParcourDetails.setGPS_Lon_Koordinaten(rs.getString(ParcourTbl.GPS_LON_KOORDINATEN));
                loadParcourDetails.setAnmerkung(rs.getString(ParcourTbl.ANMERKUNG));
                loadParcourDetails.setStandard(rs.getInt(ParcourTbl.STANDARD));
                loadParcourDetails.setZeitstempel(rs.getLong(ParcourTbl.ZEITSTEMPEL));
                // loadParcourDetails.setTransfered(rs.getInt(ParcourTbl.TRANSFERED));
                System.out.println("System: loadParcourDetails(): Parcour = " + loadParcourDetails.toString());
                return loadParcourDetails;
            } else {
                System.err.println("System: loadParcourDetails(): Treffer nicht eindeutig = " + rs.getRow());
            }
            
        } catch (SQLException ex) {
            System.err.println("System: loadParcourDetails(): " + ex);
            if (mDb != null) {
                try {
                    System.out.print("System: loadParcourDetails(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return null;
            
        } finally {
            try {
                System.out.print("System: loadParcourDetails(): Alles wird geschlossen");
                if (rs != null) rs.close();
                if (queryData != null) queryData.close();
            } catch(SQLException excep) {
                System.err.print("System: loadParcourDetails(): " + excep);
            }
        }
        return null;
    }
    
}
