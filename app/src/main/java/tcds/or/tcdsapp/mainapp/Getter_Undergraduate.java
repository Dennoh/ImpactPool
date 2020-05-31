package tcds.or.tcdsapp.mainapp;

/**
 * Created by DENC on 6/1/2016.
 */
public class Getter_Undergraduate {
    private String id;
    private String Programme;
    private String Code;
    private String AdmReq;
    private String MinInstAdmPoints;
    private String AdmCapacity;
    private String ProgDuration;
    private String university;
    private String region;
    private String sector;

    public Getter_Undergraduate(String id, String programme, String code, String admReq, String minInstAdmPoints, String admCapacity, String progDuration, String university, String region,String sector) {
        this.id = id;
        Programme = programme;
        Code = code;
        AdmReq = admReq;
        MinInstAdmPoints = minInstAdmPoints;
        AdmCapacity = admCapacity;
        ProgDuration = progDuration;
        this.university = university;
        this.region = region;
        this.sector = sector;
    }


    public String getId() {
        return id;
    }

    public String getProgramme() {
        return Programme;
    }

    public String getCode() {
        return Code;
    }

    public String getAdmReq() {
        return AdmReq;
    }

    public String getMinInstAdmPoints() {
        return MinInstAdmPoints;
    }

    public String getAdmCapacity() {
        return AdmCapacity;
    }

    public String getProgDuration() {
        return ProgDuration;
    }

    public String getUniversity() {
        return university;
    }

    public String getRegion() {
        return region;
    }

    public String getSector() {
        return sector;
    }
}
