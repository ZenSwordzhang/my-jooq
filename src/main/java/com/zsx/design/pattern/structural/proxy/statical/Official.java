package com.zsx.design.pattern.structural.proxy.statical;

public class Official implements ISell {

    @Override
    public String sellTicket() {
        return "Official";
    }
}
