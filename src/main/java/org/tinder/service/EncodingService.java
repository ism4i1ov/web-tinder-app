package org.tinder.service;

import java.util.Base64;

public class EncodingService {
    public String encoding(String line) {
        byte[] encode = Base64.getEncoder().encode(line.getBytes());
        return new String(encode);
    }

    public String decoding(String line) {
        byte[] decode = Base64.getDecoder().decode(line);
        return new String(decode);
    }

}
