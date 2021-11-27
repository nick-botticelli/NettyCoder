package website.nickb.nettycoder.client;

import org.jetbrains.annotations.NotNull;
import picocli.CommandLine;
import website.nickb.nettycoder.client.util.Constants;
import website.nickb.nettycoder.common.requests.NCRequestType;

import java.net.URISyntaxException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "nettycoder-client", version = Constants.CLIENT_VERSION, mixinStandardHelpOptions = true)
public final class NettyCoderClient implements Callable<Integer>
{
    @CommandLine.Option(names = {"-p", "--port"}, description = "port (if not default)")
    int port;

    @CommandLine.Parameters(paramLabel = "host", description = "host name or IP address of NettyCoder server")
    String host;

    @CommandLine.Parameters(paramLabel = "task", description = "type of request task to send")
    NCRequestType task;

    @Override
    public Integer call()
    {
        try
        {
            new NCClient(host, port);
        }
        catch (URISyntaxException ignored)
        {
            System.err.println("Invalid host or port specified! The host must be a hostname (domain) or IP, and the "
                    + "port must be between 1-65535 inclusive.");
            return 2;
        }

        return 0;
    }

    public static void main(@NotNull String[] args)
    {
        int exitCode = new CommandLine(new NettyCoderClient()).setCaseInsensitiveEnumValuesAllowed(true).execute(args);
        System.exit(exitCode);
    }
}
