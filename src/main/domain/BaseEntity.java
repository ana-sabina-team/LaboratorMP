package src.main.ro.ntt.catalog.domain;


//ID - generic type, putem trimite orice tip din clasa parinte in functie de ce tip are ID-ul
//Ex: book ID de tip Long
//Ex: client ID de tip String
//etc
public abstract class BaseEntity <ID> {


    //nu poti crea obiecte din ele, no new BaseEntity() possible
    //o clasa de baza, care este extinsa de alte clase parinte
    //stocheaza functionalitea comuna intre alte clase (code reuse)
    public ID id;

    BaseEntity(ID id) {
        this.id=id;
    }

    public ID getId() {
        return id;
    }


    public void setId(ID id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                '}';
    }
}
