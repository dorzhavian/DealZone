package Models;

public class Address {
    private String street;
    private String houseNum;
    private String city;
    private String state;

    public Address(String street, String houseNum, String city, String state) {
        this.street = street;
        this.houseNum = houseNum;
        this.city = city;
        this.state = state;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(street).append(" ").append(houseNum).append(" , ").append(city).append(" , ").append(state);
        return sb.toString();
    }
}
