package dataClasses;


import java.util.ArrayList;

import activities.uniActivity.UniActivity;

/**
 * Created by matthias rohrmoser on 22.05.2015.
 */
public class University {
    private int id;
    private String name;



    private int addressId;
    private Address address;
    private String website;
    private boolean flag;
    private ArrayList<Course> courses;
    private static ArrayList<University> universities;



    public University(int id, String name, int addressId, String website, boolean flag) {
        this.id = id;
        this.name = name;
        this.addressId = addressId;
        this.website = website;
        this.flag = flag;
    }

    public University() {
    }

    public static ArrayList<University> getUniversities() {
        if(universities == null)
        {
            universities = new ArrayList<>();
            //public Address(int id, String street, int houseNumber, int zip, String region, String country)
            universities.add(new University(1, "Hauptuni", 1,"www.hauptuni.org",false ));
            universities.add(new University(2, "Technikum", 2,"www.techniku-wien.at",false ));
        }
        return universities;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "University{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address=" + addressId +
                ", website='" + website + '\'' +
                ", flag=" + flag +
                '}';
    }
}