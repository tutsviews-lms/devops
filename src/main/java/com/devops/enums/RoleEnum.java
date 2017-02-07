package com.devops.enums;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */
public enum RoleEnum {

    BASIC(1,"ROLE_BASIC"),
    PRO(2,"ROLE_PRO"),
    ADMIN(3,"ROLE_ADMIN");

    private final int id;
    private final String roleName;

    RoleEnum(int id, String name) {
        this.id = id;
        this.roleName = name;
    }

    public int getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }
}
