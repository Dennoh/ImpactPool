package tcds.or.tcdsapp;

public class GetterBanner {

    private String id;
    private String name;
    private String link;

    public GetterBanner(String id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLink() {
        return link;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
