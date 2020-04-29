package impactpool.org.impactpool;

/**
 * Created by DENC on 6/1/2016.
 */
public class Getter_News {
    private String news_tittle;
    private String news_details;
    private String news_postedOn;
    private String news_updatedOn;
    private String id;

    public Getter_News(String id, String news_tittle, String news_details, String news_postedOn, String news_updatedOn) {
        this.id = id;
        this.news_tittle = news_tittle;
        this.news_details = news_details;
        this.news_postedOn = news_postedOn;
        this.news_updatedOn = news_updatedOn;
    }

    public Getter_News() {

    }

    public String getId() {
        return id;
    }

    public String getNews_tittle() {
        return news_tittle;
    }

    public String getNews_details() {
        return news_details;
    }

    public String getNews_postedOn() {
        return news_postedOn;
    }

    public String getNews_updatedOn() {
        return news_updatedOn;
    }


    public void setNews_tittle(String news_tittle) {
        this.news_tittle = news_tittle;
    }

    public void setNews_details(String news_details) {
        this.news_details = news_details;
    }

    public void setNews_postedOn(String news_postedOn) {
        this.news_postedOn = news_postedOn;
    }

    public void setNews_updatedOn(String news_updatedOn) {
        this.news_updatedOn = news_updatedOn;
    }

    public void setId(String id) {
        this.id = id;
    }
}
