package main.domain;

public class Client extends BaseEntity<Long> {
    private Long id;
    private String CNP;
    private String lastName;
    private String firstName;
    private double age;

    public Client() {
        super();
    }
    public Client(Long id,String CNP, String lastName, String firstName, double age) {
        super(id);
        this.id = id;
        this.CNP = CNP;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
    }
    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }
    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public double getAge() {return age;}

    public void setAge(double age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Client{" +
                "ID='" + id + '\'' +
                "CNP='" + CNP + '\'' +
                ", Last name='" + lastName + '\'' +
                ", First name=" + firstName + ", Age=" + age +
                '}';
    }

}