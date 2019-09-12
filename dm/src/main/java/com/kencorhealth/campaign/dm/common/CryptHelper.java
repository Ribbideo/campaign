package com.kencorhealth.campaign.dm.common;

public class CryptHelper {
    public static String saltFor(Identified identified) {
        return
            identified.getId() +
            "KH" +
            new StringBuilder(
                String.valueOf(identified.getCreateTime())
            ).reverse().toString();
    }
}