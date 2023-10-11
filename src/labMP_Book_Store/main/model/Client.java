package labMP_Book_Store.model;

public class Client extends BaseEntity<Long> {

    private String CNP;
    private String lastName;

    private String firstName;
    private int age;


    public Client(Long id, String CNP, String lastName, String firstName, int age) {
        super(id);
        this.CNP = CNP;
        this.name = name;
        this.group = group;
    }
}
