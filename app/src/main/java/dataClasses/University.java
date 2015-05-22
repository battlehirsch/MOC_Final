package dataClasses;


import java.util.ArrayList;

import activities.uniActivity.UniActivity;

/**
 * Created by matthias rohrmoser on 22.05.2015.
 */
public class University {
    private int id;
    private String name;
    private Address address;
    private String website;
    private boolean flag;
    private ArrayList<Course> courses;
    private static ArrayList<University> universities;



    public University(int id, String name, Address address, String website, boolean flag) {
        this.id = id;
        this.name = name;
        this.address = address;
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
            universities.add(new University(1, "Hauptuni", new Address(1,"Karlsplatz", 1,1010, "Wien", "Österreich" ),"www.hauptuni.org",false ));
            universities.add(new University(2, "Technikum", new Address(1,"Höchstädplatz", 6,1200, "Wien", "Österreich" ),"www.techniku-wien.at",false ));
        }
        return universities;
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
                ", address=" + address +
                ", website='" + website + '\'' +
                ", flag=" + flag +
                '}';
    }
}
