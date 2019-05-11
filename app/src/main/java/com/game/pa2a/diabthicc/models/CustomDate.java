package com.game.pa2a.diabthicc.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CustomDate implements Comparable<CustomDate>, Serializable {
    int year, month, day, hours, minutes;
    Calendar calendar;

    public CustomDate(int year, int month, int day, int hours, int minutes) {
        calendar = new GregorianCalendar(year, month, day, hours, minutes);
        this.year = year;
        this.month = month;
        this.day = day;
        this.hours = hours;
        this.minutes = minutes;
    }

    public CustomDate(){
        calendar =  new GregorianCalendar();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
    }

    public CustomDate(GregorianCalendar time){
        calendar = time;
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH)+1;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hours = calendar.get(Calendar.HOUR_OF_DAY);
        minutes = calendar.get(Calendar.MINUTE);
    }

    public static CustomDate build(long time) {
        GregorianCalendar date = new GregorianCalendar();
        date.setTime(new Date(time));
        return new CustomDate(date);
    }

    public String dayFormat(){
        return ""+(day < 10 ? "0"+day : day) + "/" + (month < 10 ? "0"+month : month) + "/" + year;
    }

    public String hourFormat(){
        return ""+(hours < 10 ? "0"+hours : hours) + ":" + (minutes < 10 ? "0"+minutes : minutes);
    }

    public boolean isAnteriorAs(CustomDate cd){
        return compareTo(cd) < 0;
    }

    public boolean isToday(){
        CustomDate today = new CustomDate();
        return day == today.day && month == today.month && year == today.year;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public long getTime() {
        return calendar.getTime().getTime();
    }

    @Override
    public int compareTo(CustomDate o) {
        if(year == o.year){
            if(month == o.month){
                if(day == o.day) {
                    if (hours == o.hours) {
                        return minutes - o.minutes;
                    }else {
                        return hours - o.hours;
                    }
                }else{
                    return day - o.day;
                }
            }else{
                return month - o.month;
            }
        }else{
            return year - o.year;
        }
    }

    public long timeSpentInDays(CustomDate o) {
        long diff = this.getTime() - o.getTime();
        long diffDays = diff / (24 * 60 * 60 * 1000);
        return diffDays;

    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }
}
