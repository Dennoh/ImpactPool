package impactpool.org.impactpool;

/**
 * Created by DENC on 6/1/2016.
 */
public class Getter_LearningInst {
    private String id;
    private String OfferingInstitution;
    private String District;
    private String Region;
    private String Sector;
    private String Details;

    public Getter_LearningInst(String id, String offeringInstitution, String district, String region, String sector, String details) {
        this.id = id;
        OfferingInstitution = offeringInstitution;
        District = district;
        Region = region;
        Sector = sector;
        Details = details;
    }

    public String getId() {
        return id;
    }

    public String getOfferingInstitution() {
        return OfferingInstitution;
    }

    public String getDistrict() {
        return District;
    }

    public String getRegion() {
        return Region;
    }

    public String getSector() {
        return Sector;
    }

    public String getDetails() {
        return Details;
    }
}
