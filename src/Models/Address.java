package Models;

public class Address {
    private final String street;
    private final String houseNum;
    private final String city;
    private final String state;

    public Address(String street, String houseNum, String city, String state) {
        this.street = street;
        this.houseNum = houseNum;
        this.city = city;
        this.state = state;
    }

    @Override
    public String toString() {
        return street + " " + houseNum + " , " + city + " , " + state;
    }
}
