package tesda.tcsdi.simplepos.model;

public class Supplier {
    private int id;
    private String name;
    private String phoneNumber;
    private String email;
    private String address;

    public int getId() {
        return id;
    }

    public Supplier setId(int id) {
        this.id = id;
        return this;
    }
    
    public String getName() {
        return name;
    }

    public Supplier setName(String name) {
        this.name = name;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Supplier setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Supplier setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Supplier setAddress(String address) {
        this.address = address;
        return this;
    }
}
