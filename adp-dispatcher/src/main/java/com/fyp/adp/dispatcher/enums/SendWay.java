package com.fyp.adp.dispatcher.enums;

public enum SendWay {
    EMAIL("email"),
    BOT("bot");

    private String name;

    SendWay(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SendWay getWay(String way) {
        for (SendWay value : SendWay.values()) {
            if (value.getName().equals(way)) {
                return value;
            }
        }
        return null;
    }
}
