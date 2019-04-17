package com.bodjo.main.objects;

public class ServerStartupModel {
    private String type;
    private Table table;
    private int wsPort;
    private SSL ssl;

    public ServerStartupModel(String type, Table table, int wsPort, SSL ssl) {
        this.table = table;
        this.wsPort = wsPort;
        this.type = type;
        this.ssl = ssl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public int getWsPort() {
        return wsPort;
    }

    public void setWsPort(int wsPort) {
        this.wsPort = wsPort;
    }

    public SSL getSsl() {
        return ssl;
    }

    public void setSsl(SSL ssl) {
        this.ssl = ssl;
    }
}
