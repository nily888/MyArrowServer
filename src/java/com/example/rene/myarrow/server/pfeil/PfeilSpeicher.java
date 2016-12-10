package com.example.rene.myarrow.server.pfeil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.rene.myarrow.server.MyArrowDB;
import java.sql.ResultSet;

/**
 * Created by nily on 15.12.15.
 */
public class PfeilSpeicher {

    /** Markierung für Logging. */
    private static final String TAG = "PfeilSpeicher";

    /**
     * Verweis auf die MyArrow-Datenbank.
     */
    private final Connection mDb;

    /**
     * Erzeugt einen neuen ZielSpeicher.<br>
     * Dabei wird sichergestellt, dass die zugrundeliegende
     * Datenbank unmittelbar nutzbar ist.
     */
    public PfeilSpeicher() {
        mDb = new MyArrowDB().getInstance();
    }

    /**
     * Legt ein neues Bogen in der Datenbank an.
     *
     */
    public void insertPfeil(
            String gid,
            String name,
            String dateiname,
            int standard,
            long zeitstempel) {
        
        PreparedStatement insertData = null;
        
        try {
            mDb.setAutoCommit(false);
            /**
             * Datensatz einfügen
             */
            System.out.println("System: insertZiel(): Datensatz einfügen");
            insertData = mDb.prepareStatement(PfeilTbl.STMT_INSERT);
            System.out.println("System: insertZiel(): STMT_INSERT = " + insertData.toString());
            insertData.setString(1, gid);
            insertData.setString(2, name);
            insertData.setString(3, dateiname);
            insertData.setInt(4, standard);
            insertData.setLong(5, zeitstempel);
            insertData.executeUpdate();
            mDb.commit();
        } catch (SQLException ex) {
            /**
             * Wenn Fehlercode = 1062; Duplicate, einfach ein Update
             */
            if (ex.getErrorCode()==1062){
                try {
                    System.out.println("System: insertZiel(): Datensatz aktualisieren");
                    insertData = mDb.prepareStatement(PfeilTbl.STMT_UPDATE);
                    insertData.setString(1, name);
                    insertData.setString(2, dateiname);
                    insertData.setInt(3, standard);
                    insertData.setLong(4, zeitstempel);
                    insertData.setString(5, gid);
                    insertData.executeUpdate();
                    mDb.commit();
                } catch(SQLException excep) {
                    System.out.println("System: insertZiel(): Error Code    = " + excep.getErrorCode());
                    System.out.println("System: insertZiel(): Error Message = " + excep.getMessage());
                    System.err.print(excep);
                }
            } else {
                System.out.println("System: insertZiel(): Error Code    = " + ex.getErrorCode());
                System.out.println("System: insertZiel(): Error Message = " + ex.getMessage());
                System.err.println(ex);
                if (mDb != null) {
                    try {
                        System.err.print("System: insertZiel(): Transaction is being rolled back");
                        mDb.rollback();
                    } catch(SQLException excep) {
                        System.out.println("System: insertZiel(): Error Code    = " + excep.getErrorCode());
                        System.out.println("System: insertZiel(): Error Message = " + excep.getMessage());
                        System.err.print(excep);
                    }
                }
            }
        } finally {
            if (insertData != null) {
                try {
                    System.out.print("System: insertZiel(): Transaction will be closed");
                    insertData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
            try {
                System.out.print("System: insertZiel(): AutoCommit() switched on again");
                mDb.setAutoCommit(true);
            } catch(SQLException excep) {
                System.err.print(excep);
            }                
        }
    }

    /**
     * Speichert ein neues Ziel. Ist dieser bereits in der
     * Datenbank bekannt, wird der vorhandene Datensatz
     * geändert.<br>
     * Ansonsten wird ein neuer Datensatz erzeugt.
     *
     * @param pfeil zu speichernder Schuetze.
     */
    public void insertPfeil(Pfeil pfeil) {
        insertPfeil(
                pfeil.getGID(),
                pfeil.getName(),
                pfeil.getDateiname(),
                pfeil.getStandard(),
                pfeil.getZeitstempel());
    }

    public Pfeil loadPfeilDetails(String gid) {
        PreparedStatement queryData;
        ResultSet rs = null;
        queryData = null;
        try {
            queryData = mDb.prepareStatement(PfeilTbl.STMT_WHERE_GID_EQUALS);
            queryData.setString(1, gid);
            rs = queryData.executeQuery();
            rs.last();
            if (rs.getRow() == 1) {
                Pfeil loadPfeilDetails = new Pfeil();
                loadPfeilDetails.setID(rs.getInt(PfeilTbl.ID));
                loadPfeilDetails.setGID(rs.getString(PfeilTbl.GID));
                loadPfeilDetails.setName(rs.getString(PfeilTbl.NAME));
                loadPfeilDetails.setDateiname(rs.getString(PfeilTbl.DATEINAME));
                loadPfeilDetails.setStandard(rs.getInt(PfeilTbl.STANDARD));
                loadPfeilDetails.setZeitstempel(rs.getLong(PfeilTbl.ZEITSTEMPEL));
                // loadPfeilDetails.setTransfered(rs.getInt(PfeilTbl.TRANSFERED));
                return loadPfeilDetails;
            } else {
                System.err.println("System: loadPfeilDetails(): Treffer nicht eindeutig = " + rs.getRow());
            }
            
        } catch (SQLException ex) {
            System.err.println("System: loadPfeilDetails(): " + ex);
            if (mDb != null) {
                try {
                    System.out.print("System: loadPfeilDetails(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return null;
            
        } finally {
            try {
                System.out.print("System: loadPfeilDetails(): Alles wird geschlossen");
                if (rs != null) rs.close();
                if (queryData != null) queryData.close();
            } catch(SQLException excep) {
                System.err.print("System: loadPfeilDetails(): " + excep);
            }
        }
        return null;
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
