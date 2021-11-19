package website.nickb.nettycoder.server.responses;

import com.fasterxml.jackson.annotation.JsonValue;

public enum NCResponseCode
{
    OK(0),
    EXCEPTION(-100),
    MAINTENANCE(-101),
    DB_CONNECTION_ERROR(-102),
    HTTP_ERROR(-103),
    TIMEOUT(-104),
    TOKEN_EXPIRED(-110),
    AUTH_INFO_DOES_NOT_EXIST(-111),
    AUTH_FAIL(-112),
    AUTH_ALREADY_USED(-113),
    INVALID_TOKEN(-114),
    UPDATE_CLIENT(-120);

    private final int responseCode;

    NCResponseCode(int responseCode)
    {
        this.responseCode = responseCode;
    }

    @JsonValue
    public int getResponseCode()
    {
        return responseCode;
    }
}
