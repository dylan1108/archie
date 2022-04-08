package com.yilami.archie.gray.plan;

/**
 * @author Weihua
 * @since 1.0.0
 */
public class ClientRouteRule {

    private String name;
    private String clientCondition;
    private String serverCondition;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientCondition() {
        return clientCondition;
    }

    public void setClientCondition(String clientCondition) {
        this.clientCondition = clientCondition;
    }

    public String getServerCondition() {
        return serverCondition;
    }

    public void setServerCondition(String serverCondition) {
        this.serverCondition = serverCondition;
    }
}
