package com.example.rene.myarrow.server.managegid;

import com.example.rene.myarrow.server.clients.*;

/**
 * 
 * @author René Düber, 2016
 * 
 */
public class ManageGID implements ManageGIDColumns {
    private int id;
    private String tablename;
    private String servergid;
    private String mobilgid;
    private String deviceid;

    public ManageGID() { }
  
    public ManageGID(final int id, final String tablename, final String servergid,
            final String mobilgid) {
        this.id = id;
        this.tablename = tablename;
        this.servergid = servergid;
        this.mobilgid = mobilgid;
    }

    public int getID() {
        return id;
    }

    public void setID(final int id) {
        this.id = id;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(final String tablename) {
        this.tablename = tablename;
    }

    public String getServerGID() {
        return servergid;
    }

    public void setServerGID(final String servergid) {
        this.servergid = servergid;
    }

    public String getMobilGID() {
        return mobilgid;
    }

    public void setMobilGID(final String mobilgid) {
        this.mobilgid = mobilgid;
    }

    public String getDeviceID() {
        return deviceid;
    }

    public void setDeviceID(final String deviceid) {
        this.deviceid = deviceid;
    }
    
    @Override
    public String toString() {
        return "ManageGID [" 
                + ID + "=" + String.valueOf(id) + ", "
                + TABLENAME + "=" + tablename + ", "
                + SERVERGID + "=" + servergid + ", "
                + MOBILGID + "=" + mobilgid + ", "
                + DEVICEID + "=" + deviceid
            + "]";
    }
}