package com.tribe.workshop.appium.enums;

public enum Month {
    JAN(0, 31),
    FEB(1, 28),
    MAR(2, 31),
    APR(3, 30),
    MAY(4, 31),
    JUN(5, 30),
    JUL(6, 31),
    AUG(7, 31),
    SEP(8, 30),
    OCT(9, 31),
    NOV(10, 30),
    DEC(11, 31);

    private final int monthIndex;
    private final int numberOfDays;

    Month(int index, int daysInAMonth) {
        this.monthIndex = index;
        this.numberOfDays = daysInAMonth;
    }

    public int getMonthIndex() {
        return monthIndex;
    }

    public int getNumberOfDays(int year) {
        if (getMonthIndex() == 1) {
            if (isLeapYear(year)) {
                return 29;
            } else {
                return numberOfDays;
            }
        } else {
            return numberOfDays;
        }
    }

    private Boolean isLeapYear(int year) {
        boolean leap = false;

        if (year % 4 == 0) {
            if (year % 100 == 0) {
                // year is divisible by 400, hence the year is a leap year
                if (year % 400 == 0)
                    leap = true;
                else
                    leap = false;
            } else
                leap = true;
        } else
            leap = false;

        if (leap)
            System.out.println(year + " is a leap year.");
        else
            System.out.println(year + " is not a leap year.");
        return leap;
    }
}
