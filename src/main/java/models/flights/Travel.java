package models.flights;

import io.jsondb.annotation.Document;
import io.jsondb.annotation.Id;

@Document(collection = "travels", schemaVersion= "1.0")
public class Travel {
    @Id
    private int id;
    private City cityFrom;
    private City cityTo;
    private int distance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public City getCityFrom() {
        return cityFrom;
    }

    public void setCityFrom(City cityFrom) {
        this.cityFrom = cityFrom;
    }

    public City getCityTo() {
        return cityTo;
    }

    public void setCityTo(City cityTo) {
        this.cityTo = cityTo;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }
}
