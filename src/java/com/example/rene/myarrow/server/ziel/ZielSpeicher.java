package com.example.rene.myarrow.server.ziel;

import com.example.rene.myarrow.server.MyArrowDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by nily on 11.06.16.
 */
public class ZielSpeicher {

    /**
     * Markierung für Logging.
     */
    private static final String TAG = "ZielSpeicher";

    /**
     * Verweis auf die MyArrow-Datenbank.
     */
    private final Connection mDb;

    /**
     * Erzeugt einen neuen ZielSpeicher.<br>
     * Dabei wird sichergestellt, dass die zugrundeliegende
     * Datenbank unmittelbar nutzbar ist.
     */
    public ZielSpeicher() {
        mDb = new MyArrowDB().getInstance();
    }

    /**
     * Legt ein neues Ziel in der Datenbank an.
     *
     * @param name
     *          Name des Ziels.
     * @param parcourgid
     * @param nummer
     * @param gid
     *          Globale ID
     * @param gps_lat_koordinaten
     * @param gps_lon_koordinaten
     * @param dateiname
     *          Dateiname für das Bild
     * @param zeitstempel
     *      Zeitpunkt des Kontakts.
     */
    public Boolean insertZiel(
            String gid,
            String parcourgid,
            int nummer,
            String name,
            String gps_lat_koordinaten,
            String gps_lon_koordinaten,
            String dateiname,
            long zeitstempel) {
        
        Boolean insertZiel = false;
        PreparedStatement insertData = null;
        
        try {
            mDb.setAutoCommit(false);
            /**
             * Datensatz einfügen
             */
            System.out.println("System: insertZiel(): Datensatz einfügen");
            insertData = mDb.prepareStatement(ZielTbl.STMT_INSERT);
            insertData.setString(1, gid);
            insertData.setString(2, parcourgid);
            insertData.setInt(3, nummer);
            insertData.setString(4, name);
            insertData.setString(5, gps_lat_koordinaten);
            insertData.setString(6, gps_lon_koordinaten);            
            insertData.setString(7, dateiname);
            insertData.setLong(8, zeitstempel);
            insertData.executeUpdate();
            mDb.commit();
            insertZiel = true;
        } catch (SQLException ex) {
            /**
             * Wenn Fehlercode = 1062; Duplicate, einfach ein Update
             */
            if (ex.getErrorCode()==1062){
                try {
                    System.out.println("System: insertZiel(): Datensatz aktualisieren");
                    insertData = mDb.prepareStatement(ZielTbl.STMT_UPDATE);
                    insertData.setString(1, parcourgid);
                    insertData.setInt(2, nummer);
                    insertData.setString(3, name);
                    insertData.setString(4, gps_lat_koordinaten);
                    insertData.setString(5, gps_lon_koordinaten);            
                    insertData.setString(6, dateiname);
                    insertData.setLong(7, zeitstempel);
                    insertData.setString(8, gid);
                    insertData.executeUpdate();
                    mDb.commit();
                    insertZiel = true;
                } catch(SQLException excep) {
                    System.out.println("System: insertZiel(): " + insertData.toString());
                    System.err.println("System: insertZiel(): Error Code    = " + excep.getErrorCode());
                    System.err.println("System: insertZiel(): Error Message = " + excep.getMessage());
                    System.err.print("System: insertZiel(): " + excep);
                }
            } else {
                System.out.println("System: insertZiel(): " + insertData.toString());
                System.err.println("System: insertZiel(): Error Code    = " + ex.getErrorCode());
                System.err.println("System: insertZiel(): Error Message = " + ex.getMessage());
                System.err.println("System: insertZiel(): " + ex);
                if (mDb != null) {
                    try {
                        System.err.print("System: insertZiel(): Transaction is being rolled back");
                        mDb.rollback();
                    } catch(SQLException excep) {
                        System.err.println("System: insertZiel(): Error Code    = " + excep.getErrorCode());
                        System.err.println("System: insertZiel(): Error Message = " + excep.getMessage());
                        System.err.print("System: insertZiel(): " + excep);
                    }
                }
            }
        } finally {
            if (insertData != null) {
                try {
                    System.out.print("System: insertZiel(): Transaction will be closed");
                    insertData.close();
                } catch(SQLException excep) {
                    System.err.print("System: insertZiel(): " + excep);
                }                
            }
            try {
                System.out.print("System: insertZiel(): AutoCommit() switched on again");
                mDb.setAutoCommit(true);
            } catch(SQLException excep) {
                System.err.print("System: insertZiel(): " + excep);
            }                
        }
        return insertZiel;
    }

    /**
     * Speichert ein neues Ziel. Ist dieser bereits in der
     * Datenbank bekannt, wird der vorhandene Datensatz
     * geändert.<br>
     * Ansonsten wird ein neuer Datensatz erzeugt.
     *
     * @param ziel zu speichernder Schuetze.
     */
    public Boolean  insertZiel(Ziel ziel) {
        return insertZiel(
                ziel.getGID(),
                ziel.getParcourGID(),
                ziel.getNummer(),
                ziel.getName(),
                ziel.getGPS_Lat_Koordinaten(),
                ziel.getGPS_Lon_Koordinaten(),
                ziel.getDateiname(),
                ziel.getZeitstempel());
    }

    public Ziel loadZielDetails(String gid) {
        PreparedStatement queryData;
        ResultSet rs = null;
        queryData = null;
        try {
            queryData = mDb.prepareStatement(ZielTbl.STMT_WHERE_GID_EQUALS);
            queryData.setString(1, gid);
            rs = queryData.executeQuery();
            rs.last();
            if (rs.getRow() == 1) {
                Ziel loadZielDetails = new Ziel();
                loadZielDetails.setID(rs.getInt(ZielTbl.ID));
                loadZielDetails.setGID(rs.getString(ZielTbl.GID));
                loadZielDetails.setParcourGID(rs.getString(ZielTbl.PARCOURGID));
                loadZielDetails.setNummer(rs.getInt(ZielTbl.NUMMER));
                loadZielDetails.setName(rs.getString(ZielTbl.NAME));
                loadZielDetails.setGPS_Lat_Koordinaten(rs.getString(ZielTbl.GPS_LAT_KOORDINATEN));
                loadZielDetails.setGPS_Lon_Koordinaten(rs.getString(ZielTbl.GPS_LON_KOORDINATEN));
                loadZielDetails.setDateiname(rs.getString(ZielTbl.DATEINAME));
                loadZielDetails.setZeitstempel(rs.getLong(ZielTbl.ZEITSTEMPEL));
                // loadZielDetails.setTransfered(rs.getInt(ZielTbl.TRANSFERED));
                return loadZielDetails;
            } else {
                System.err.println("System: loadZielDetails(): Treffer nicht eindeutig = " + rs.getRow());
            }
            
        } catch (SQLException ex) {
            System.err.println("System: loadZielDetails(): " + ex);
            if (mDb != null) {
                try {
                    System.out.print("System: loadZielDetails(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return null;
            
        } finally {
            try {
                System.out.print("System: loadZielDetails(): Alles wird geschlossen");
                if (rs != null) rs.close();
                if (queryData != null) queryData.close();
            } catch(SQLException excep) {
                System.err.print("System: loadZielDetails(): " + excep);
            }
        }
        return null;
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
            queryData1 = mDb.prepareStatement(ZielTbl.STMT_WHERE_GID_NAME_EQUALS);
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
                queryData2 = mDb.prepareStatement(ZielTbl.STMT_WHERE_GID_NAME_NAME_EQUALS);
                queryData1.setString(1, rs1.getString(ZielTbl.NAME));
                rs2 = queryData2.executeQuery();

                /**
                * check if something was found and store it
                */
                if (rs1.first()) {
                    checkForDuplicates[n][1] = rs1.getString(ZielTbl.GID);
                    checkForDuplicates[n][2] = rs2.getString(ZielTbl.GID);
                    checkForDuplicates[n][3] = rs2.getString(ZielTbl.NAME);
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
            System.err.println(ex);
        }
    }
}
