package com.example.systemb.util;

public final class MaskingUtils {
    private MaskingUtils() {}

    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) return nullSafe(email);
        String[] parts = email.split("@", 2);
        String local = parts[0];
        String domain = parts[1];
        if (local.length() <= 2) return repeat('*', local.length()) + "@" + domain;
        return local.charAt(0) + repeat('*', Math.max(1, local.length() - 2)) + local.charAt(local.length() - 1) + "@" + domain;
    }

    public static String maskName(String name) {
        if (name == null || name.isBlank()) return nullSafe(name);
        if (name.length() == 1) return name + "*";
        return name.charAt(0) + repeat('*', name.length() - 1);
    }

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) return nullSafe(phone);
        return repeat('*', Math.max(0, phone.length() - 4)) + phone.substring(phone.length() - 4);
    }

    private static String repeat(char c, int n) { return String.valueOf(c).repeat(Math.max(0, n)); }
    private static String nullSafe(String s) { return s == null ? null : s; }
}

