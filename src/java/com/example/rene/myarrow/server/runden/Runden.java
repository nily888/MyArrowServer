package com.example.rene.myarrow.server.runden;

/**
 * Created by nily on 16.06.16.
 */
public class Runden implements RundenColumns{
    public int id;
    public String gid;
    public String parcourgid;
    public String bogengid;
    public String pfeilgid;
    public long startzeit;
    public String s_startzeit;
    public long endzeit;
    public String wetter;
    public int punktestand;

    public Runden() {}
    
    public Runden(
        final int id,
        final String gid,
        final String parcourgid,
        final String bogengid,
        final String pfeilgid,
        final long startzeit,
        final String s_startzeit,
        final long endzeit,
        final String wetter,
        final int punktestand){
        this.id = id;
        this.gid = gid;
        this.parcourgid = parcourgid;
        this.bogengid = bogengid;
        this.pfeilgid = pfeilgid;
        this.startzeit = startzeit;
        this. s_startzeit = s_startzeit;
        this.endzeit = endzeit;
        this.wetter = wetter;
        this.punktestand = punktestand;
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
    
    public String getParcourGID(){
        return parcourgid;
    }
    public void setParcourGID(final String parcourgid){
            this.parcourgid = parcourgid;
    }
    
    public String getBogenGID(){
        return bogengid;
    }            
    public void setBogenGID(final String bogengid){
        this.bogengid = bogengid;
    }
    
    public String getPfeilGID(){
        return pfeilgid;
    }            
    public void setPfeilGID(final String pfeilgid){
        this.pfeilgid = pfeilgid;
    }

    public long getStartzeit(){
        return startzeit;
    }
    public void setStartzeit(final long startzeit){
        this.startzeit = startzeit;
    }
    
    public String getS_Startzeit(){
        return s_startzeit;
    }
    public void setS_Startzeit(final String s_startzeit){
        this.s_startzeit = s_startzeit;
    }
    
    public long getEndzeit(){
        return endzeit;
    }
    public void setEndzeit(final long endzeit){
        this.endzeit = endzeit;
    }

    public String getWetter(){
        return wetter;
    }
    public void setWetter(final String wetter){
        this.wetter = wetter;
    }

    public int getPunktestand(){
        return punktestand;
    }
    public void setPunktestand(final int punktestand){
        this.punktestand = punktestand;
    }

    public String toString() {
        return "table=runden"
            + "&" + ID + "="    + String.valueOf(getID())
            + "&" + GID + "="   + getGID()
            + "&" + PARCOURGID  + "=" + getParcourGID()
            + "&" + BOGENGID    + "=" + getBogenGID()
            + "&" + PFEILGID    + "=" + getPfeilGID()
            + "&" + STARTZEIT   + "=" + getStartzeit()
            + "&" + S_STARTZEIT + "=" + getS_Startzeit()
            + "&" + ENDZEIT     + "=" + getEndzeit()
            + "&" + WETTER      + "=" + getWetter()
            + "&" + PUNKTESTAND + "=" + getPunktestand();
    }
}
