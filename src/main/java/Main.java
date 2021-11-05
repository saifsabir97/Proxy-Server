import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

import java.util.HashSet;

public class Main {
    public static void main(String []args) {
        HashSet<String> blockedHosts = new HashSet<>();
        HttpProxyServer server =
                DefaultHttpProxyServer.bootstrap()
                        .withPort(8080)
                        .withFiltersSource(new HttpFiltersSourceAdapter() {
                            public Filter filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
                                return new Filter(ctx, originalRequest, blockedHosts);
                            }
                        })
                        .start();
    }
}