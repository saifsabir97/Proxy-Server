import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.ProxyAuthenticator;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

import java.net.InetSocketAddress;
import java.util.HashSet;

public class Main {
    public static void main(String []args) {
        HashSet<String> blockedHosts = new HashSet<>();
        HttpProxyServer server =
                DefaultHttpProxyServer.bootstrap()
                        .withFiltersSource(new HttpFiltersSourceAdapter() {
                            public Filter filterRequest(HttpRequest originalRequest, ChannelHandlerContext ctx) {
                                return new Filter(ctx, originalRequest, blockedHosts);
                            }
                        })
                        .withAddress(new InetSocketAddress("0.0.0.0", 8081))
                        .withProxyAuthenticator(new ProxyAuthenticator() {
                            @Override
                            public boolean authenticate(String s, String s1) {
                                return s.equals("random") && s1.equals("fandom");
                            }

                            @Override
                            public String getRealm() {
                                return null;
                            }
                        })
                        .start();
    }
}
