package com.inspirion.stockmarket.entity;

public class AppSettings {
    int id;
    int startHour;
    int startMinutes;
    int endHour;
    int endMinute;
    Integer[] workingDays;

    public int getId() {
        return id;
    }

    public int getStartHour() {
        return startHour;
    }

    public int getStartMinutes() {
        return startMinutes;
    }

    public int getEndHour() {
        return endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public Integer[] getWorkingDays() {
        return workingDays;
    }

    public AppSettings() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public void setStartMinutes(int startMinutes) {
        this.startMinutes = startMinutes;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public void setWorkingDays(Integer[] workingDays) {
        this.workingDays = workingDays;
    }

    public AppSettings(
            int id,
            int startHour,
            int startMinutes,
            int endHour,
            int endMinute,
            Integer[] workingDays) {
        this.id = id;
        this.endHour = endHour;
        this.endMinute = endMinute;
        this.startHour = startHour;
        this.startMinutes = startMinutes;
        this.workingDays = workingDays;
    }
}
