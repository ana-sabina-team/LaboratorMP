package main.domain;

public class BaseEntity<ID> {
    private ID id;

    BaseEntity(ID id) {
        this.id = id;
    }

    public BaseEntity() {
    }

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "main.domain.BaseEntity{" +
                "id=" + id +
                '}';
    }
}

