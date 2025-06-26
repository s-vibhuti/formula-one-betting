package org.sportybet.formulaonebetting.services.factory;

import org.sportybet.formulaonebetting.services.external.OpenF1Service;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SessionClientFactory {

    private final Map<String, OpenF1Service> clientMap;

    public SessionClientFactory(List<OpenF1Service> clients) {
        this.clientMap = new HashMap<>();
        for (OpenF1Service client : clients) {
            this.clientMap.put(client.getVendorKey(), client); // each client provides its vendor key
        }
    }

    public OpenF1Service getClient(String vendorKey) {
        if (!clientMap.containsKey(vendorKey)) {
            throw new IllegalArgumentException("No client found for vendor: " + vendorKey);
        }
        return clientMap.get(vendorKey);
    }
}