package fr.eservices.drive.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "perishable")
@Inheritance(strategy = InheritanceType.JOINED)
public class Perishable extends Article {
    @Temporal(TemporalType.DATE)
    private Date bestBefore;
    private String lot;

    public Date getBestBefore() {
        return bestBefore;
    }

    public void setBestBefore(Date bestBefore) {
        this.bestBefore = bestBefore;
    }

    public String getLot() {
        return lot;
    }

    public void setLot(String lot) {
        this.lot = lot;
    }
}
