package website.nickb.nettycoder.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClients;
import org.jetbrains.annotations.NotNull;
import website.nickb.nettycoder.client.util.Constants;
import website.nickb.nettycoder.common.requests.NCRequestType;

import java.net.IDN;
import java.net.URI;
import java.net.URISyntaxException;

public class NCClient
{
    public static final String PROTOCOL = "http";

    private final HttpClient client;
    private final ObjectMapper objMapper;

    private final RequestBuilder requestObj;

    public NCClient(@NotNull String host, int port) throws URISyntaxException
    {
        this.client = HttpClients.createDefault();
        this.objMapper = new ObjectMapper();

        // Make sure address conforms to RFC 2396
        String hostAscii = IDN.toASCII(host);

        // Get URI from ASCII address and port
        URI uri = new URI(null, null, hostAscii, port, "", null, null);

        this.requestObj = RequestBuilder.post(host);
        requestObj.setUri(uri);
        requestObj.addHeader("User-Agent", Constants.USER_AGENT);
    }

    public void sendRequest(NCRequestType type)
    {
    }
}
