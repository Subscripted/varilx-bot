package de.subscripted.backend;

public class TimeStampMaker {

    public static int getTime(int time) {
        long current = System.currentTimeMillis() / 1000;
        current += time;
        return (int) current;
    }
}
