package website.nickb.nettycoder.server.responses;

public class NCResponse<T>
{
    private T response;
    private NCResponseCode responseCode;

    public NCResponse()
    {
        this(null);
    }

    public NCResponse(T response)
    {
        this(response, NCResponseCode.OK);
    }

    public NCResponse(T response, NCResponseCode responseCode)
    {
        this.response = response;
        this.responseCode = responseCode;
    }
}
