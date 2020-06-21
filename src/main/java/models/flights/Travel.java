package models.flights;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "travels", schemaVersion= "1.0")
public class Travel {
    @Id
    private int id;
    private String cityFrom;
    private String cityTo;
    private int distance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(String cityFrom) {
        this.cityFrom = cityFrom;
    }

    public String getCityTo() {
        return cityTo;
    }

    public void setCityTo(String cityTo) {
        this.cityTo = cityTo;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
