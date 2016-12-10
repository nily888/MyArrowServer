package com.example.rene.myarrow.server.runden;

import com.example.rene.myarrow.server.MyArrowDB;
import java.sql.*;

/**
 * Created by nily on 15.12.15.
 */
public class RundenSpeicher {

    /** Markierung für Logging. */
    private static final String TAG = "RundenSpeicher";

        /**
     * Verweis auf die MyArrow-Datenbank.
     */
    private final Connection mDb;

    /**
     * Erzeugt einen neuen ZielSpeicher.<br>
     * Dabei wird sichergestellt, dass die zugrundeliegende
     * Datenbank unmittelbar nutzbar ist.
     */
    public RundenSpeicher() {
        mDb = new MyArrowDB().getInstance();
    }

    /**
     * Legt ein neues Bogen in der Datenbank an.
     *
     */
    public void insertRunden(
            String gid,
            String parcourgid,
            String bogengid,
            String pfeilgid,
            long startzeit,
            String s_startzeit,
            long endzeit,
            String wetter) {
        
        PreparedStatement insertData = null;
        
        try {
            mDb.setAutoCommit(false);
            /**
             * Datensatz einfügen
             */
            System.out.println("System: insertRunden(): Datensatz einfügen");
            insertData = mDb.prepareStatement(RundenTbl.STMT_INSERT);
            insertData.setString(1, gid);
            insertData.setString(2, parcourgid);
            insertData.setString(3, bogengid);
            insertData.setString(4, pfeilgid);
            insertData.setLong(5, startzeit);
            insertData.setString(6, s_startzeit);
            insertData.setLong(7, endzeit);
            insertData.setString(8, wetter); 
            insertData.executeUpdate();
            mDb.commit();
        } catch (SQLException ex) {
            /**
             * Wenn Fehlercode = 1062; Duplicate, einfach ein Update
             */
            if (ex.getErrorCode()==1062){
                try {
                    System.out.println("System: insertRunden(): Datensatz aktualisieren");
                    insertData = mDb.prepareStatement(RundenTbl.STMT_UPDATE);
                    insertData.setString(1, parcourgid);
                    insertData.setString(2, bogengid);
                    insertData.setString(3, pfeilgid);
                    insertData.setLong(4, startzeit);
                    insertData.setString(5, s_startzeit);
                    insertData.setLong(6, endzeit);
                    insertData.setString(7, wetter);
                    insertData.setString(8, gid);
                    insertData.executeUpdate();
                    mDb.commit();
                } catch(SQLException excep) {
                    System.out.println("System: insertRunden(): Error Code    = " + excep.getErrorCode());
                    System.out.println("System: insertRunden(): Error Message = " + excep.getMessage());
                    System.err.print(excep);
                }
            } else {
                System.out.println("System: insertRunden(): Error Code    = " + ex.getErrorCode());
                System.out.println("System: insertRunden(): Error Message = " + ex.getMessage());
                System.err.println(ex);
                if (mDb != null) {
                    try {
                        System.err.print("System: insertRunden(): Transaction is being rolled back");
                        mDb.rollback();
                    } catch(SQLException excep) {
                        System.out.println("System: insertRunden(): Error Code    = " + excep.getErrorCode());
                        System.out.println("System: insertRunden(): Error Message = " + excep.getMessage());
                        System.err.print(excep);
                    }
                }
            }
        } finally {
            if (insertData != null) {
                try {
                    System.out.print("System: insertRunden(): Transaction will be closed");
                    insertData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
            try {
                System.out.print("System: insertRunden(): AutoCommit() switched on again");
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
    public void insertRunden(Runden runden) {
        insertRunden(
            runden.getGID(),
            runden.getParcourGID(),
            runden.getBogenGID(),
            runden.getPfeilGID(),
            runden.getStartzeit(),
            runden.getS_Startzeit(),
            runden.getEndzeit(),
            runden.getWetter());
    }

    public Runden loadRundenDetails(String gid) {
        PreparedStatement queryData;
        ResultSet rs = null;
        queryData = null;
        try {
            queryData = mDb.prepareStatement(RundenTbl.STMT_WHERE_GID_EQUALS);
            queryData.setString(1, gid);
            rs = queryData.executeQuery();
            rs.last();
            if (rs.getRow() == 1) {
                Runden loadRundenDetails = new Runden();
                loadRundenDetails.setID(rs.getInt(RundenTbl.ID));
                loadRundenDetails.setGID(rs.getString(RundenTbl.GID));
                loadRundenDetails.setParcourGID(rs.getString(RundenTbl.PARCOURGID));
                loadRundenDetails.setBogenGID(rs.getString(RundenTbl.BOGENGID));
                loadRundenDetails.setPfeilGID(rs.getString(RundenTbl.PFEILGID));
                loadRundenDetails.setStartzeit(rs.getLong(RundenTbl.STARTZEIT));
                loadRundenDetails.setS_Startzeit(rs.getString(RundenTbl.S_STARTZEIT));
                loadRundenDetails.setEndzeit(rs.getLong(RundenTbl.ENDZEIT));
                loadRundenDetails.setWetter(rs.getString(RundenTbl.WETTER));
                // loadRundenDetails.setTransfered(rs.getInt(RundenTbl.TRANSFERED));
                return loadRundenDetails;
            } else {
                System.err.println("System: loadRundenDetails(): Treffer nicht eindeutig = " + rs.getRow());
            }
            
        } catch (SQLException ex) {
            System.err.println("System: loadRundenDetails(): " + ex);
            if (mDb != null) {
                try {
                    System.out.print("System: loadRundenDetails(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return null;
            
        } finally {
            try {
                System.out.print("System: loadRundenDetails(): Alles wird geschlossen");
                if (rs != null) rs.close();
                if (queryData != null) queryData.close();
            } catch(SQLException excep) {
                System.err.print("System: loadRundenDetails(): " + excep);
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