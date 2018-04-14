package services;

import data.FeedResponse;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import play.libs.ws.WS;
import play.libs.ws.WSRequest;
import play.libs.ws.WSResponse;

import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

public class FeedService {
    public FeedResponse getFeedByQuery(String query){
         FeedResponse feedResponseObject=new FeedResponse();
        try{
            WSRequest feedRequest=WS.url("https://news.google.com/news");
            CompletionStage<WSResponse> responsePromise=feedRequest
                    .setQueryParameter("q",query)
                    .setQueryParameter("output","rss")
                    .setQueryParameter("lang","en")
                    .get();
            Document response=responsePromise.thenApply(WSResponse::asXml).toCompletableFuture().get();
            //response=compleat xml file
            Node item=response.getFirstChild().getFirstChild().getChildNodes().item(10);
            feedResponseObject.title   = item.getChildNodes().item(0).getNodeValue();
            feedResponseObject.pubDate  = item.getChildNodes().item(3).getNodeValue();
            feedResponseObject.description  = item.getChildNodes().item(4).getNodeValue();
        }
        catch(Exception e)
        {

        }
        return feedResponseObject;
    }


}
