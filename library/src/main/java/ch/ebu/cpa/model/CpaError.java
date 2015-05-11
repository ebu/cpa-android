package ch.ebu.cpa.model;

/**
 * Created by npietri on 29.04.15.
 */
public class CpaError {

    public enum Kind {
        NETWORK_ERROR,
        INVALID_REQUEST,
        INVALID_CLIENT,
        SLOW_DOWN,
        AUTH_PENDING,
        CANCELED,
        USER_DENIED,
        EXPIRED,
        UNKNOWN
    }

    private Kind error;

    public CpaError(Kind error){
        this.error = error;
    }

    public Kind getError() {
        return error;
    }
}
