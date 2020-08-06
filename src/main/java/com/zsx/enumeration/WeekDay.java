package com.zsx.enumeration;

public enum WeekDay {


    SUN(0, "Sunday", "星期日"), MON(1, "Monday", "星期一"),
    TUE(2, "Tuesday", "星期二"), WEN(3, "Wednesday", "星期三"),
    THU(4, "Thursday", "星期四"), FRI(5, "Monday", "星期五"),
    SAT(6, "Saturday", "星期六");

    private Integer index;
    private String enName;
    private String zhName;

    WeekDay(Integer index, String enName, String zhName) {
        this.index = index;
        this.enName = enName;
        this.zhName = zhName;
    }

    public static WeekDay get(int index) {
        for (WeekDay day : WeekDay.values()) {
            if (day.index == index) {
                return day;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "WeekDay{" +
                "index=" + index +
                ", enName='" + enName + '\'' +
                ", zhName='" + zhName + '\'' +
                '}';
    }
}
