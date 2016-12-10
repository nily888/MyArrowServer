package com.example.rene.myarrow.server.rundenziel;

/**
 * Created by nily on 16.06.16.
 */
public class RundenZiel implements RundenZielColumns{
    private int id;
    private String gid;
    private String rundengid;
    private String zielgid;
    private String rundenschuetzengid;
    private int nummer;
    private int eins;
    private int zwei;
    private int drei;
    private int kill;
    private int killkill;
    private int punkte;
    private String anmerkung;
    private String dateiname;
    private long zeitstempel;

    
    public RundenZiel() { }
  
    public RundenZiel(
            final int id,
            final String gid,
            final String rudengid,
            final String zielgid,
            final String rundenschuetzengid,
            final int nummer,
            final int eins,
            final int zwei,
            final int drei,
            final int kill,
            final int killkill,
            final int punkte,
            final String anmerkung,
            final String dateiname,
            final long zeitstempel) {
        this.id = id;
        this.gid = gid;
        this.rundengid = rundengid;
        this.zielgid = zielgid;
        this.rundenschuetzengid = rundenschuetzengid;
        this.nummer = nummer;
        this.eins = eins;
        this.zwei = zwei;
        this.drei = drei;
        this.kill = kill;
        this.killkill = killkill;
        this.punkte = punkte;
        this.anmerkung = anmerkung;
        this.dateiname = dateiname;
        this.zeitstempel = zeitstempel;
    }

    public int getID(){
        return id;
    }
    public void setID(final int id){
        this.id = id;
    }

    public String getGID(){
        return gid;
    }
    public void setGID(final String gid){
        this.gid = gid;
    }
    
    public String getRundenGID(){
        return rundengid;
    }
    public void setRundenGID(final String rundengid){
            this.rundengid = rundengid;
    }

    public String getZielGID(){
        return zielgid;
    }
    public void setZielGID(final String zielgid){
            this.zielgid = zielgid;
    }

        public String getRundenSchuetzenGID(){
        return rundenschuetzengid;
    }
    public void setRundenSchuetzenGID(final String rundenschuetzengid){
            this.rundenschuetzengid = rundenschuetzengid;
    }

    public int getNummer(){
        return nummer;
    }
    public void setNummer(final int nummer){
        this.nummer = nummer;
    }
    
    public int getEins(){
        return eins;
    }
    public void setEins(final int eins){
        this.eins = eins;
    }
    
    public int getZwei(){
        return zwei;
    }
    public void setZwei(final int zwei){
        this.zwei = zwei;
    }
    
    public int getDrei(){
        return drei;
    }
    public void setDrei(final int drei){
        this.drei = drei;
    }
    
    public int getKill(){
        return kill;
    }
    public void setKill(final int kill){
        this.kill = kill;
    }
    
    public int getKillKill(){
        return killkill;
    }
    public void setKillKill(final int killkill){
        this.killkill = killkill;
    }

    public int getPunkte(){
        return punkte;
    }
    public void setPunkte(final int punkte){
        this.punkte = punkte;
    }
        
    public String getAnmerkung(){
        return anmerkung;
    }
    public void setAnmerkung(final String anmerkung){
            this.anmerkung = anmerkung;
    }
    
    public String getDateiname(){
        return dateiname;
    }            
    public void setDateiname(final String dateiname){
        this.dateiname = dateiname;
    }
    
    public long getZeitstempel(){
        return zeitstempel;
    }
    public void setZeitstempel(final long zeitstempel){
        this.zeitstempel = zeitstempel;
    }

    public String toString() {
        String toString = "table=rundenziel" +
            "&" + ID                 + "=" + String.valueOf(getID()) +
            "&" + GID                + "=" + getGID() +
            "&" + RUNDENGID          + "=" + getRundenGID() +
            "&" + ZIELGID            + "=" + getZielGID() +
            "&" + RUNDENSCHUETZENGID + "=" + getRundenSchuetzenGID() +
            "&" + NUMMER             + "=" + String.valueOf(getNummer()) +
            "&" + EINS               + "=" + String.valueOf(getEins()) +
            "&" + ZWEI               + "=" + String.valueOf(getZwei()) +
            "&" + DREI               + "=" + String.valueOf(getDrei()) +
            "&" + KILL               + "=" + String.valueOf(getKill()) +
            "&" + KILLKILL           + "=" + String.valueOf(getKillKill()) +
            "&" + PUNKTE             + "=" + String.valueOf(getPunkte()) +
            "&" + ZEITSTEMPEL        + "=" + String.valueOf(getZeitstempel());
        if (!getAnmerkung().equals("")) toString += "&" + ANMERKUNG + "=" + getAnmerkung();
        if (!getDateiname().equals("")) toString += "&" + DATEINAME + "=" + getDateiname();
        return toString;
    }
}
