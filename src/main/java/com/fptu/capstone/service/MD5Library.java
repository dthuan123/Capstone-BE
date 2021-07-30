package com.fptu.capstone.service;

import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Service
public class MD5Library {
    public String md5(String password){
        String result = "";
        String saltkey = "$%&$#@^akdmrKDLDGK115";
        String newpassword = password + saltkey;
        MessageDigest digest;
        try{
            digest = MessageDigest.getInstance("MD5");
            digest.update(newpassword.getBytes());
            BigInteger bigInteger = new BigInteger(1, digest.digest());
            result = bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }
}
