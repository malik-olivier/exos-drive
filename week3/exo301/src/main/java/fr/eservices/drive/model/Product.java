package fr.eservices.drive.model;


import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "product")
public class Product extends Article {

}
