package com.example.rene.myarrow.server.rundenziel;

import com.example.rene.myarrow.server.db.MyArrowDB;

import java.sql.*;
        
/**
 * Created by nily on 16.06.16.
 */
public class RundenZielSpeicher {

    /** Markierung für Logging. */
    private static final String TAG = "RundenZielSpeicher";


    /**
     * Verweis auf die MyArrow-Datenbank.
     */
    private final Connection mDb;

    /**
     * Erzeugt einen neuen ZielSpeicher.<br>
     * Dabei wird sichergestellt, dass die zugrundeliegende
     * Datenbank unmittelbar nutzbar ist.
     */
    public RundenZielSpeicher() {
        mDb = new MyArrowDB().getInstance();
    }

    /**
     * Legt ein neues Ziel in der Datenbank an.
     *
     * @param gid
     * @param rundengid
     * @param zielgid
     * @param rundenschuetzengid
     * @param nummer
     * @param eins
     * @param zwei
     * @param drei
     * @param zeitstempel
     * @param dateiname
     * @param anmerkung
     * @param kill
     * @param killkill
     * @param punkte
     */
    public void insertRundenZiel(
            String gid,
            String rundengid,
            String zielgid,
            String rundenschuetzengid,
            int nummer,
            int eins,
            int zwei,
            int drei,
            int kill,
            int killkill,
            int punkte,
            String anmerkung,
            String dateiname,
            long zeitstempel) {
        
        PreparedStatement insertData = null;
        
        try {
            mDb.setAutoCommit(false);
            /**
             * Datensatz einfügen
             */
            System.out.println("System: insertRundenZiel(): Datensatz einfügen");
            insertData = mDb.prepareStatement(RundenZielTbl.STMT_INSERT);
            insertData.setString(1, gid);
            insertData.setString(2, rundengid);
            insertData.setString(3, zielgid);
            insertData.setString(4, rundenschuetzengid);
            insertData.setInt(5, nummer);
            insertData.setInt(6, eins);
            insertData.setInt(7, zwei);
            insertData.setInt(8, drei);
            insertData.setInt(9, kill);
            insertData.setInt(10, killkill);
            insertData.setInt(11, punkte);
            insertData.setString(12, anmerkung);
            insertData.setString(13, dateiname);
            insertData.setLong(14, zeitstempel);
            insertData.executeUpdate();
            mDb.commit();
        } catch (SQLException ex) {
            /**
             * Wenn Fehlercode = 1062; Duplicate, einfach ein Update
             */
            if (ex.getErrorCode()==1062){
                try {
                    System.out.println("System: insertRundenZiel(): Datensatz aktualisieren");
                    insertData = mDb.prepareStatement(RundenZielTbl.STMT_UPDATE);
                    insertData.setString(1, rundengid);
                    insertData.setString(2, zielgid);
                    insertData.setString(3, rundenschuetzengid);
                    insertData.setInt(4, nummer);
                    insertData.setInt(5, eins);
                    insertData.setInt(6, zwei);
                    insertData.setInt(7, drei);
                    insertData.setInt(8, kill);
                    insertData.setInt(9, killkill);
                    insertData.setInt(10, punkte);
                    insertData.setString(11, anmerkung);
                    insertData.setString(12, dateiname);
                    insertData.setLong(13, zeitstempel);
                    insertData.setString(14, gid);
                    insertData.executeUpdate();
                    mDb.commit();
                } catch(SQLException excep) {
                    System.out.println("System: insertRundenZiel(): " + insertData.toString());
                    System.err.println("System: insertRundenZiel(): Error Code    = " + excep.getErrorCode());
                    System.err.println("System: insertRundenZiel(): Error Message = " + excep.getMessage());
                    System.err.print("System: insertRundenZiel(): " + excep);
                }
            } else {
                System.out.println("System: insertRundenZiel(): " + insertData.toString());
                System.err.println("System: insertRundenZiel(): Error Code    = " + ex.getErrorCode());
                System.err.println("System: insertRundenZiel(): Error Message = " + ex.getMessage());
                System.err.println(ex);
                if (mDb != null) {
                    try {
                        System.out.print("System: insertRundenZiel(): Transaction is being rolled back");
                        mDb.rollback();
                    } catch(SQLException excep) {
                        System.err.println("System: insertRundenZiel(): Error Code    = " + excep.getErrorCode());
                        System.err.println("System: insertRundenZiel(): Error Message = " + excep.getMessage());
                        System.err.print("System: insertRundenZiel(): " + excep);
                    }
                }
            }
        } finally {
            if (insertData != null) {
                try {
                    System.out.print("System: insertRundenZiel(): Transaction will be closed");
                    insertData.close();
                } catch(SQLException excep) {
                    System.err.print("System: insertRundenZiel(): " + excep);
                }                
            }
            try {
                System.out.print("System: insertRundenZiel(): AutoCommit() switched on again");
                mDb.setAutoCommit(true);
            } catch(SQLException excep) {
                System.err.print("System: insertRundenZiel(): " + excep);
            }                
        }
    }

    /**
     * Speichert ein neues Ziel. Ist dieser bereits in der
     * Datenbank bekannt, wird der vorhandene Datensatz
     * geändert.<br>
     * Ansonsten wird ein neuer Datensatz erzeugt.
     *
     * @param rundenziel
     */
    public void insertRundenZiel(RundenZiel rundenziel) {
        insertRundenZiel(
            rundenziel.getGID(),
            rundenziel.getRundenGID(),
            rundenziel.getZielGID(),
            rundenziel.getRundenSchuetzenGID(),
            rundenziel.getNummer(),
            rundenziel.getEins(),
            rundenziel.getZwei(),
            rundenziel.getDrei(),
            rundenziel.getKill(),
            rundenziel.getKillKill(),
            rundenziel.getPunkte(),
            rundenziel.getAnmerkung(),
            rundenziel.getDateiname(),
            rundenziel.getZeitstempel());
    }

    
    public RundenZiel loadRundenZielDetails(String gid) {
        PreparedStatement queryData;
        ResultSet rs = null;
        queryData = null;
        try {
            queryData = mDb.prepareStatement(RundenZielTbl.STMT_WHERE_GID_EQUALS);
            queryData.setString(1, gid);
            rs = queryData.executeQuery();
            rs.last();
            if (rs.getRow() == 1) {
                RundenZiel loadRundenZielDetails = new RundenZiel();
                loadRundenZielDetails.setID(rs.getInt(RundenZielTbl.ID));
                loadRundenZielDetails.setGID(rs.getString(RundenZielTbl.GID));
                loadRundenZielDetails.setRundenGID(rs.getString(RundenZielTbl.RUNDENGID));
                loadRundenZielDetails.setZielGID(rs.getString(RundenZielTbl.ZIELGID));
                loadRundenZielDetails.setRundenSchuetzenGID(rs.getString(RundenZielTbl.RUNDENSCHUETZENGID));
                loadRundenZielDetails.setNummer(rs.getInt(RundenZielTbl.NUMMER));
                loadRundenZielDetails.setEins(rs.getInt(RundenZielTbl.EINS));
                loadRundenZielDetails.setZwei(rs.getInt(RundenZielTbl.ZWEI));
                loadRundenZielDetails.setDrei(rs.getInt(RundenZielTbl.DREI));
                loadRundenZielDetails.setKill(rs.getInt(RundenZielTbl.KILL));
                loadRundenZielDetails.setKillKill(rs.getInt(RundenZielTbl.KILLKILL));
                loadRundenZielDetails.setPunkte(rs.getInt(RundenZielTbl.PUNKTE));
                loadRundenZielDetails.setAnmerkung(rs.getString(RundenZielTbl.ANMERKUNG));
                loadRundenZielDetails.setDateiname(rs.getString(RundenZielTbl.DATEINAME));
                loadRundenZielDetails.setZeitstempel(rs.getLong(RundenZielTbl.ZEITSTEMPEL));
                // loadRundenZielDetails.setTransfered(rs.getInt(RundenZielTbl.TRANSFERED));
                return loadRundenZielDetails;
            } else {
                System.err.println("System: loadRundenZielDetails(): Treffer nicht eindeutig = " + rs.getRow());
            }
            
        } catch (SQLException ex) {
            System.err.println("System: loadRundenZielDetails(): " + ex);
            if (mDb != null) {
                try {
                    System.out.print("System: loadRundenZielDetails(): Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            return null;
            
        } finally {
            try {
                System.out.print("System: loadRundenZielDetails(): Alles wird geschlossen");
                if (rs != null) rs.close();
                if (queryData != null) queryData.close();
            } catch(SQLException excep) {
                System.err.print("System: loadRundenZielDetails(): " + excep);
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
