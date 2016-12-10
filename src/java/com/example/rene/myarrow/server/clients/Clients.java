package com.example.rene.myarrow.server.clients;

/**
 * 
 * @author René Düber, 2016
 * 
 */
public class Clients implements ClientsColumns {
    private int id;
    private String deviceid;
    private String name;
    private long zeitstempel;

    public Clients() { }
  
    public Clients(final int id, final String deviceid, final String name, 
        final long zeitstempel) {
        this.id = id;
        this.deviceid = deviceid;
        this.name = name;
        this.zeitstempel = zeitstempel;
    }

    public int getID() {
        return id;
    }

    public void setID(final int id) {
        this.id = id;
    }

    public String getDeviceID() {
        return deviceid;
    }

    public void setDeviceID(final String deviceid) {
        this.deviceid = deviceid;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public long getZeitstempel() {
        return zeitstempel;
    }

    public void setZeitstempel(final long zeitstempel) {
        this.zeitstempel = zeitstempel;
    }

    @Override
    public String toString() {
        return "Clients [" 
                + ID + "=" + String.valueOf(id) + ", "
                + DEVICEID + "=" + String.valueOf(deviceid) + ", "
                + NAME + "=" + name + ", "
                + ZEITSTEMPEL + "=" + String.valueOf(zeitstempel)
            + "]";
    }
}