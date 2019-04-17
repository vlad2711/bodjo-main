package com.bodjo.main.objects;

public class SSL {
    private String cert = "/home/vkram/data/bodjo/ssl/cert.pem";
    private String key = "/home/vkram/data/bodjo/ssl/privkey.pem";

    public SSL(String cert, String key) {
        this.cert = cert;
        this.key = key;
    }

    public String getCert() {
        return cert;
    }

    public String getKey() {
        return key;
    }
}
