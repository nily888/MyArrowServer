package com.example.rene.myarrow.server.uptime;

import com.example.rene.myarrow.server.db.MyArrowDB;

import java.util.Date;
import java.sql.*;

/**
 * Created by René Düber on 26.05.16.
 */
public class upTimeSpeicher {

    /**
     * Markierung für Logging.
     */
    private static final String TAG = "upTimeSpeicher";

    /**
     * Verweis auf die MyArrow-Datenbank.
     */
    private Connection mDb;

    /**
     * Erzeugt einen neuen upTimeSpeicher.<br>
     * Dabei wird sichergestellt, dass die zugrundeliegende
     * Datenbank unmittelbar nutzbar ist.
     */
    public upTimeSpeicher() {
        mDb = new MyArrowDB().getInstance();
    }

    /**
     * Legt einen neuen Eitrag in der Datenbank an.
     *
     * @param starttime
     *          Zeit, wenn das Servlet gestartet wurde.
     * @throws SQLException
     *           falls Speichern nicht möglich ist.
     */
    public int insertStartTime() {
        int row_id;
        PreparedStatement insertData = null;
        System.out.print("System: insertStartTime(): started");
        try {
            System.out.print("System: AutoCommit() switched off");
            mDb.setAutoCommit(false);
            insertData = mDb.prepareStatement(upTimeTbl.STMT_INSERT);
            insertData.setLong(1, System.currentTimeMillis());
            insertData.setLong(2, 0);
            insertData.executeUpdate();
            mDb.commit();
            row_id = 0;
        } catch (SQLException ex) {
            System.out.println(ex);
            if (mDb != null) {
                try {
                    System.err.print("System: Transaction is being rolled back");
                    mDb.rollback();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }
            }
            row_id = -1;
        } finally {
            if (insertData != null) {
                try {
                    insertData.close();
                } catch(SQLException excep) {
                    System.err.print(excep);
                }                
            }
            try {
                System.out.print("System: AutoCommit() switched on again");
                mDb.setAutoCommit(true);
            } catch(SQLException excep) {
                System.err.print(excep);
            }                
        }
        System.out.print("System: insertStartTime(): ended");
        return row_id;
    }

    public void updateEndTime(int id) {
        
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
}
