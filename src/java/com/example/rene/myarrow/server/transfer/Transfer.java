package com.example.rene.myarrow.server.transfer;

/**
 * 
 * @author René Düber, 11.06.2016
 * 
 */
public class Transfer implements TransferColumns {
    private int id;
    private String gid;
    private int transfered;
    private String deviceid;
    private String target_table;
    private long zeitstempel;

    public Transfer() { }
  
    public Transfer(final int id, final String gid, final String deviceid, final String table, 
        final int transfered, final long zeitstempel) {
        this.id = id;
        this.gid = gid;
        this.deviceid = deviceid;
        this.target_table = target_table;
        this.transfered = transfered;
        this.zeitstempel = zeitstempel;
    }

    public int getID() {
        return id;
    }
    public void setID(final int id) {
        this.id = id;
    }

    public String getGID() {
        return gid;
    }
    public void setGID(final String gid) {
        this.gid = gid;
    }

    public String getDeviceID() {
        return deviceid;
    }
    public void setDeviceID(final String deviceid) {
        this.deviceid = deviceid;
    }

    public String getTargetTable() {
        return target_table;
    }
    public void setTargetTable(final String target_table) {
        this.target_table = target_table;
    }

    public int getTransfered() {
        return transfered;
    }
    public void setTransfered(final int transfered) {
        this.transfered = transfered;
    }

    public long getZeitstempel() {
        return zeitstempel;
    }
    public void setZeitstempel(final long zeitstempel) {
        this.zeitstempel = zeitstempel;
    }

    @Override
    public String toString() {
        return "Transfer [" 
                + GID + "=" + gid + ", "
                + DEVICEID + "=" + deviceid + ", "
                + TARGET_TABLE + "=" + target_table + ", "
                + TRANSFERED + "=" + String.valueOf(transfered) + ", "
                + ZEITSTEMPEL + "=" + String.valueOf(zeitstempel)
            + "]";
    }
}