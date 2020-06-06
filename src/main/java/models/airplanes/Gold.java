package models.airplanes;

import io.jsondb.annotation.Document;
import models.airplanes.services.Catering;

@Document(collection = "gold_airplanes", schemaVersion= "1.0")
public class Gold extends Airplane {
    private Catering catering;
    private boolean wifi;

    public Catering getCatering() {
        return catering;
    }

    public void setCatering(Catering catering) {
        this.catering = catering;
    }

    public boolean isWifi() {
        return wifi;
    }

    public void setWifi(boolean wifi) {
        this.wifi = wifi;
    }
}
