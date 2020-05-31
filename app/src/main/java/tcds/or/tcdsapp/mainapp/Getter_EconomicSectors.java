package tcds.or.tcdsapp.mainapp;

/**
 * Created by DENC on 6/1/2016.
 */
public class Getter_EconomicSectors {
    private String id;
    private String mainsector;
    private String submainsector;
    private String subsector;
    private String activitygroup;
    private String activityclass;

    public Getter_EconomicSectors(String id, String mainsector, String submainsector, String subsector, String activitygroup, String activityclass) {
        this.id = id;
        this.mainsector = mainsector;
        this.submainsector = submainsector;
        this.subsector = subsector;
        this.activitygroup = activitygroup;
        this.activityclass = activityclass;
    }

    public String getId() {
        return id;
    }

    public String getMainsector() {
        return mainsector;
    }

    public String getSubmainsector() {
        return submainsector;
    }

    public String getSubsector() {
        return subsector;
    }

    public String getActivitygroup() {
        return activitygroup;
    }

    public String getActivityclass() {
        return activityclass;
    }
}
