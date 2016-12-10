package com.example.rene.myarrow.server.schuetzen;

/**
 * 
 * @author René Düber, 2016
 * 
 */
public class Schuetzen implements SchuetzenColumns {
    private int id;
    private String gid;
    private String name;
    private String dateiname;
    private long zeitstempel;

    public Schuetzen() { }
  
    public Schuetzen(final int id, final String gid, final String name, 
        final String dateiname, final long zeitstempel) {
        this.id = id;
        this.gid = gid;
        this.name = name;
        this.dateiname = dateiname;
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

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDateiname() {
        return dateiname;
    }

    public void setDateiname(final String dateiname) {
        this.dateiname = dateiname;
    }

    public long getZeitstempel() {
        return zeitstempel;
    }

    public void setZeitstempel(final long zeitstempel) {
        this.zeitstempel = zeitstempel;
    }

    @Override
    public String toString() {
        String toString = "table=schuetzen" +
                "&" + ID          + "=" + String.valueOf(getID()) +
                "&" + GID         + "=" + getGID() + 
                "&" + NAME        + "=" + getName() + 
                "&" + ZEITSTEMPEL + "=" + String.valueOf(getZeitstempel());
        if (getDateiname().equals("")) toString = toString + "&" + DATEINAME + "=" + getDateiname();
        return toString;
    }
}