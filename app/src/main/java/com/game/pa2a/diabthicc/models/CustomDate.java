package com.game.pa2a.diabthicc.models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CustomDate implements Comparable<CustomDate>, Serializable {
    int year, month, day, hours, minutes;
    Calendar calendar;

    public CustomDate(int year, int month, int day, int hours, int minutes) {
        calendar = new GregorianCalendar(year, month-1, day, hours, minutes);
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

    public boolean dayEqualsTo(CustomDate o){
        return year == o.getYear() && month == o.getMonth() && day == o.getDay();
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
        this.calendar.set(year,month,day);
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
        this.calendar.set(year,month,day);
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

    public void setNextDay(){
        this.day ++;
        switch(month){
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                if(day > 31){
                    day = 1;
                    month ++;
                }
                break;
            case 2:
                if(day > (year % 4 == 0 ? 29 : 28)){
                    day = 1;
                    month ++;
                }
                break;
            default:
                if(day > 30){
                    day = 1;
                    month ++;
                }
                break;
        }
        if(month > 12){
            month = 1;
            year ++;
        }
    }

    public void setPreviousDay(){
        this.day --;
        switch(month){
            case 1:
            case 2:
            case 4:
            case 6:
            case 8:
            case 9:
            case 11:
                if(day < 1){
                    day = 31;
                    month --;
                }
                break;
            case 3:
                if(day < 1){
                    day = (year % 4 == 0 ? 29 : 28);
                    month --;
                }
                break;
            default:
                if(day < 1){
                    day = 30;
                    month --;
                }
                break;
        }
        if(month < 1){
            month = 12;
            year --;
        }
    }
}
