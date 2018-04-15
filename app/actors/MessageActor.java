package actors;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import com.fasterxml.jackson.databind.ObjectMapper;
import data.FeedResponse;
import data.Message;
import data.NewsAgentResponse;
import javassist.expr.Instanceof;
import services.FeedService;
import services.NewsAgentService;

import java.util.UUID;

public class MessageActor extends UntypedActor {
    //self reference the actor
    //props
    //objects of feed service
    //object of news Agent Service
    //define another actor reference

    public static Props props(ActorRef out) {
        return Props.create(MessageActor.class, out);//out is self reference
    }

    private final ActorRef out;

    public MessageActor(ActorRef out) {
        this.out = out;
    }

    private FeedService feedService = new FeedService();
    private NewsAgentService newsAgentService = new NewsAgentService();
    public FeedResponse feedResponse = new FeedResponse();

    @Override
    public void onReceive(Object message) throws Throwable {

        //send back response
        //object mapper to convert xml to json
        ObjectMapper objectMapper = new ObjectMapper();
        //instanceof check for type of variables
        NewsAgentResponse newsAgentResponse=new NewsAgentResponse();
        if (message instanceof String) {
            Message messageObject = new Message();
            messageObject.text=(String)message;
            messageObject.sender= Message.Sender.USER;
            out.tell(objectMapper.writeValueAsString(messageObject), self());
            //self is reference convert to json
            newsAgentResponse=newsAgentService.getNewsAgentResponse(messageObject.text,UUID.randomUUID());//keyword
            feedResponse=feedService.getFeedByQuery(newsAgentResponse.query);//actual news
            messageObject.text = (feedResponse.title == null) ? "No results found" : "Showing results for: " + newsAgentResponse.query;
            messageObject.feedResponse=feedResponse;
            messageObject.sender=Message.Sender.BOT;
        }
    }

}