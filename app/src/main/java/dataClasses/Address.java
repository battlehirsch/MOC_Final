package dataClasses;

/**
 * Created by Matthias Rohrmoser on 22.05.2015.
 */
public class Address {
    public Address() {
    }

    public Address(int id, String street, int houseNumber, int zip, String region, String country) {
        this.id = id;
        this.street = street;
        this.houseNumber = houseNumber;
        this.zip = zip;
        this.region = region;
        this.country = country;
    }

    private int id;
    private String street;
    private int houseNumber;
    private int zip;
    private String region;
    private String country;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



    @Override
    public String toString() {
        return this.getStreet() + " " + this.getHouseNumber() + ", " + this.getZip() + " " + this.getRegion() + ", " + this.getCountry();

    }


}
