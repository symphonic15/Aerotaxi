package models.airplanes;

import io.jsondb.annotation.Document;
import models.airplanes.services.Catering;

@Document(collection = "silver_airplanes", schemaVersion= "1.0")
public class Silver extends Airplane {
    private Catering catering;

    public Catering getCatering() {
        return catering;
    }

    public void setCatering(Catering catering) {
        this.catering = catering;
    }
}
