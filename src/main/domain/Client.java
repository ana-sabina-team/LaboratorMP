package main.domain;


public class Client extends BaseEntity<Long> {
    private static Long id;
    private String CNP;
    private String lastName;
    private String firstName;
    private double age;

    public Client() {
        super(id);
    }
    public Client(Long id,String CNP, String lastName, String firstName, int age) {
        super(id);
        this.CNP = CNP;
        this.lastName = lastName;
        this.firstName = firstName;
        this.age = age;
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
                "CNP='" + CNP + '\'' +
                ", Last name='" + lastName + '\'' +
                ", First name=" + firstName + ", Age=" + age +
                '}';
    }

}