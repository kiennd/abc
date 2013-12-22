package controller;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterConnector {

    private static Twitter twitter ;
    private static TwitterStream twitterStream;
    public static Twitter getTwitterConnection(){
    	if(twitter == null){    		
    		ConfigurationBuilder cb = new ConfigurationBuilder();
    		cb.setDebugEnabled(true)
    		.setOAuthConsumerKey("nwtxXsRyFJzCiNgIGFL4g") 
    		.setOAuthConsumerSecret("oW1Uu6niqZXGtXFy0b8iwKoVXa1GFJDT1RYF8Zgc")
    		.setOAuthAccessToken("2235912205-QNAYsbJm5MzqP91LQbzFBod254k1HB3ggyJb4Y5")
    		.setOAuthAccessTokenSecret("v6CZ8e0GRFWLJg7GgLx1rdhIJ00wgDXJsSeosUXllG93s");
    		twitter = new TwitterFactory(cb.build()).getInstance();
    	}
        return twitter;
    }
    
    public static TwitterStream getTwitterStream(){
    	if(twitterStream == null){
    		ConfigurationBuilder cb = new ConfigurationBuilder();
    		cb.setDebugEnabled(true)
    		.setOAuthConsumerKey("nwtxXsRyFJzCiNgIGFL4g") 
    		.setOAuthConsumerSecret("oW1Uu6niqZXGtXFy0b8iwKoVXa1GFJDT1RYF8Zgc")
    		.setOAuthAccessToken("2235912205-QNAYsbJm5MzqP91LQbzFBod254k1HB3ggyJb4Y5")
    		.setOAuthAccessTokenSecret("v6CZ8e0GRFWLJg7GgLx1rdhIJ00wgDXJsSeosUXllG93s");
    		twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
    	}

		return twitterStream;
    }
    
}
