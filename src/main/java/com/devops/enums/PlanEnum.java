package com.devops.enums;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */
public enum PlanEnum {

    BASIC(1,"Basic"),
    PRO(2,"Pro");

    private final int id;
    private final String namePlan;


    PlanEnum(int id, String namePlan) {
        this.id = id;
        this.namePlan = namePlan;
    }

    public int getId() {
        return id;
    }

    public String getNamePlan() {
        return namePlan;
    }
}
