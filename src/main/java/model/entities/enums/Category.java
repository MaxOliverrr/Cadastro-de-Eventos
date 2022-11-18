package model.entities.enums;

import lombok.Getter;

@Getter
public enum Category {

    CULTURAL("Cultural"),
    ESPORTIVO("Esportivo"),
    RELIGIOSO("Religioso"),
    OUTRO("Outro");

    private String name;

    Category(String name) {
        this.name = name;
    }

}
