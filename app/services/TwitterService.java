package services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import Model.TweetModel;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterService {
	
	private static Twitter getInstance() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
	    cb.setDebugEnabled(true).setOAuthConsumerKey("uvZYPifCuMDmDLhGzryVaH9sA")
	    						.setOAuthConsumerSecret("G01gHXhUerHAevCjhR0U1iOlm5VNmxC5cGRnldvHscVFcMfkvQ")
	    						.setOAuthAccessToken("969353476450017280-r7gPo5QAT8svSDiCBoB0jSKu1f3Oa8P")
	    						.setOAuthAccessTokenSecret("TmapsealCXor82pige4FwRZ16tqKollMbBb6AieVcKVrJ");
	    TwitterFactory tf = new TwitterFactory(cb.build());
	    Twitter twitterInstance = tf.getInstance();
	    return twitterInstance;
	}
	
	public static CompletableFuture<List<TweetModel>> getTweets(String keywords) {
	
		CompletableFuture <List<TweetModel>> futureTweets = new CompletableFuture <> ();
		
		List<TweetModel> tweetList = new ArrayList();
		
		Twitter twitter = getInstance();
		Query query = new Query(keywords);
		QueryResult result;
		
		try {
			result = twitter.search(query);
			List<Status> tweets = result.getTweets();
			
			tweets.forEach(tweet -> {
				TweetModel tm = new TweetModel();
				tm.setText(tweet.getText());
				tm.setAuthor(tweet.getUser().getName());
				//tm.setGeolocation(tweet.getGeoLocation().toString());
				tweetList.add(tm);
				//tm.setHashtags(tweet.getHashtagEntities());
				//tm.setSentiment(tweet.get);
			});
			
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		
		futureTweets.complete(tweetList);
		return futureTweets;
	}
}