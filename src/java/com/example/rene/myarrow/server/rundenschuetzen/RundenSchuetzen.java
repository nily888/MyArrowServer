package com.example.rene.myarrow.server.rundenschuetzen;

/**
 * 
 * @author René Düber, 2016
 * 
 */
public class RundenSchuetzen implements RundenSchuetzenColumns {
    private int id;
    private String gid;
    private String schuetzengid;
    private String rundengid;
    private int  gesamtergebnis;
    private long zeitstempel;
    
    public RundenSchuetzen() { }
  
    public RundenSchuetzen(final int id, final String gid, final String schuetzengid, final String rundengid, 
        final int gesamtergebnis, final long zeitstempel) {
        this.id = id;
        this.gid = gid;
        this.schuetzengid = schuetzengid;
        this.rundengid = rundengid;
        this.gesamtergebnis = gesamtergebnis;
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

    public String getSchuetzenGID() {
        return schuetzengid;
    }
    public void setSchuetzenGID(final String schuetzengid) {
        this.schuetzengid = schuetzengid;
    }

    public String getRundenGID() {
        return rundengid;
    }
    public void setRundenGID(final String rundengid) {
        this.rundengid = rundengid;
    }

    public int getGesamtErgebnis() {
        return gesamtergebnis;
    }
    public void setGesamtErgebnis(final int gesamtergebnis) {
        this.gesamtergebnis = gesamtergebnis;
    }

    public long getZeitstempel() {
        return zeitstempel;
    }
    public void setZeitstempel(final long zeitstempel) {
        this.zeitstempel = zeitstempel;
    }

    @Override
    public String toString() {
        return "table=rundenschuetzen" +
            "&" + ID             + "=" + String.valueOf(getID()) +
            "&" + GID            + "=" + getGID() +
            "&" + RUNDENGID      + "=" + getRundenGID() +
            "&" + GESAMTERGEBNIS + "=" + String.valueOf(getGesamtErgebnis()) +
            "&" + SCHUETZENGID   + "=" + getSchuetzenGID() +
            "&" + ZEITSTEMPEL    + "=" + String.valueOf(getZeitstempel());
    }
}