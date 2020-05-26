package tcds.or.tcdsapp;

/**
 * Created by DENC on 6/1/2016.
 */
public class Getter_OccupationalPathways {
    private String id;
    private String major_occupation;
    private String sub_major_occupation;
    private String minor_occupation;
    private String unit_label;
    private String DescriptionsofUnits;


    public Getter_OccupationalPathways(String id, String major_occupation, String sub_major_occupation, String minor_occupation, String unit_label, String descriptionsofUnits) {
        this.id = id;
        this.major_occupation = major_occupation;
        this.sub_major_occupation = sub_major_occupation;
        this.minor_occupation = minor_occupation;
        this.unit_label = unit_label;
        DescriptionsofUnits = descriptionsofUnits;
    }

    public String getId() {
        return id;
    }

    public String getMajor_occupation() {
        return major_occupation;
    }

    public String getSub_major_occupation() {
        return sub_major_occupation;
    }

    public String getMinor_occupation() {
        return minor_occupation;
    }

    public String getUnit_label() {
        return unit_label;
    }

    public String getDescriptionsofUnits() {
        return DescriptionsofUnits;
    }
}
