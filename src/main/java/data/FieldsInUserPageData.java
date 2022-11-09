package data;

import lombok.Getter;

@Getter
public enum FieldsInUserPageData {
    Name("Имя", "fname"),
    NameLatin("Имя на латинском", "fname_latin"),
    LastName("Фамилия", "lname"),
    LastNameLatin("Фамилия на латинском", "lname_latin"),
    BlogName("Имя блога", "blog_name"),
    BirthDate("Дата рождения", "date_of_birth");

    private String name;
    private String selector;

    FieldsInUserPageData(String name, String selector) {
        this.name = name;
        this.selector = selector;
    }
}
