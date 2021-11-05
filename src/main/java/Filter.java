import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.littleshoot.proxy.HttpFiltersAdapter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;

public class Filter extends HttpFiltersAdapter {

    HashSet<String> blockedHosts;

    public Filter(ChannelHandlerContext ctx, HttpRequest originalRequest, HashSet<String> blockedHosts) {
        super(originalRequest, ctx);
        this.blockedHosts = blockedHosts;
    }

    @Override
    public HttpResponse clientToProxyRequest(HttpObject httpObject) {
        // TODO: implement your proxy filtering here
        if(httpObject instanceof DefaultHttpRequest){
            try{
                DefaultHttpRequest currentRequest = (DefaultHttpRequest)httpObject;
                URI uri = new URI("http://" + currentRequest.getUri());
                String domain = uri.getHost();
                domain =  domain.startsWith("www.") || domain.startsWith("http") ? domain.substring(4) : domain;
                System.out.println("Request received for domain: " + domain);
                if (this.blockedHosts.contains(domain)){
                    return new DefaultFullHttpResponse(HttpVersion.HTTP_1_0, HttpResponseStatus.INTERNAL_SERVER_ERROR);
                }
            } catch (URISyntaxException e) {
                System.out.println("Unable to parse URI: " + e.getMessage());
            }
        }
        return null;
    }

    @Override
    public HttpObject serverToProxyResponse(HttpObject httpObject) {
        // TODO: implement your reverse-proxy filtering here
        return httpObject;
    }
}
