package labMP_Book_Store.main.model;

public class Client extends BaseEntity<Long>{
    private String CNP;
    private String lastName;

    private String firstName;
    private int age;


    public Client(Long id, String CNP, String lastName, String firstName, int age) {
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

    public int getAge() {return age;}

    public void setAge(String agge) {
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