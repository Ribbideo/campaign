package com.kencorhealth.campaign.dm.common;

public interface Crypter {
    String encrypt(String plain, SaltProvider sp);
    String decrypt(String encrypted, SaltProvider sp);
}
