package com.lionzxy.firstandroidapp.app.vk.video;

/**
 * Created by Никита on 08.05.2016.
 */
public enum Resolution {
    R240("mp4_240", "240p"),
    R360("mp4_360", "360p"),
    R480("mp4_480", "480p"),
    R720("mp4_720", "720p"),
    R1080("mp4_1080", "1080p");

    String code, name;

    Resolution(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Resolution getResolution(String code) {
        for (Resolution res : Resolution.values())
            if (code.equals(res.getCode()))
                return res;
        return null;
    }
}
