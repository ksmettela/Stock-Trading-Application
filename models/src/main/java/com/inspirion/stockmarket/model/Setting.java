package com.inspirion.stockmarket.model;

import com.inspirion.stockmarket.entity.AppSettings;

public class Setting {
    int id;
    int startHour;
    int startMinutes;
    int endHour;
    int endMinute;
    Integer[] workingDays;

    public Setting() {
    }

    public Setting(AppSettings s) {
        this.id = s.getId();
        this.endHour = s.getEndHour();
        this.endMinute = s.getEndMinute();
        this.startHour = s.getStartHour();
        this.startMinutes = s.getStartMinutes();
        this.workingDays = s.getWorkingDays();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinutes() {
        return startMinutes;
    }

    public void setStartMinutes(int startMinutes) {
        this.startMinutes = startMinutes;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public Integer[] getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(Integer[] workingDays) {
        this.workingDays = workingDays;
    }
}
