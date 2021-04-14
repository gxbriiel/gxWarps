package com.gxbriiel.gxwarps.utils;

public class Heads {

    private static final String prefix = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";

    public static final String questionhead = "YmFkYzA0OGE3Y2U3OGY3ZGFkNzJhMDdkYTI3ZDg1YzA5MTY4ODFlNTUyMmVlZWQxZTNkYWYyMTdhMzhjMWEifX19";
    public static final String redhead = "YzQ3MjM3NDM3ZWVmNjM5NDQxYjkyYjIxN2VmZGM4YTcyNTE0YTk1NjdjNmI2YjgxYjU1M2Y0ZWY0YWQxY2FlIn19fQ==";
    public static final String greenhead = "ZDI3Y2E0NmY2YTliYjg5YTI0ZmNhZjRjYzBhY2Y1ZTgyODVhNjZkYjc1MjEzNzhlZDI5MDlhZTQ0OTY5N2YifX19";

    public static String getHeadId(String head) {
        return prefix + head;
    }

}
