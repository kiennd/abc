package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import model.KComment;
import model.KWord;
import model.SearchInfo;
import twitter4j.FilterQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import view.LibraryView;
import view.TweetFeelView;
import constant.KConstant;

public class TweetFeelController {
	
	private TweetFeelView tfv;
	private SearchInfo currentSearchInfo;
	private Vector<KComment> comments = new Vector<>();
	
	public TweetFeelController() {
		
	}
	
	public void initTweetFieldView(TweetFeelView view){
		this.tfv = view;
		view.setVisible(true);
		this.tfv.addButtonAction(new TweetFieldButtonAction());
	}
	
	
	public static void main(String[] args) throws Exception {
		TweetFeelView tfv = new TweetFeelView();
		
		TweetFeelController e = new TweetFeelController();
		e.initTweetFieldView(tfv);
		System.out.println("done");
	}

	public void getSearchResult(String queryString, String lang,
			int count){
		
		Twitter twitter = TwitterConnector.getTwitterConnection();
		try {
			Query query = new Query(queryString);
			if (count <= 0) {
				count = 20;
			}
			if (lang.length() == 0) {
				lang = "vi";
			}
			query.count(count);
			query.setLang(lang);

			QueryResult result;
			System.out.println(query.getQuery());
			int co = 0;

			do {

				result = twitter.search(query);
				List<Status> tweets = result.getTweets();
				for (Status tweet : tweets) {
					co++;
					System.out.println(co);
					loadStatusDone(tweet);
					if (co == count) {
						return;
					}
				}
			} while ((query = result.nextQuery()) != null);
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
		}
	}

	public void streamFilter(String keyword, String lang) {
		TwitterStream twitterStream = TwitterConnector.getTwitterStream();
		StatusListener listener = new StatusListener() {
			@Override
			public void onException(Exception arg0) {
			}

			@Override
			public void onDeletionNotice(StatusDeletionNotice arg0) {
			}

			@Override
			public void onScrubGeo(long arg0, long arg1) {
			}

			@Override
			public void onStatus(Status status) {
				loadStatusDone(status);
			}

			@Override
			public void onTrackLimitationNotice(int arg0) {
			}

			@Override
			public void onStallWarning(StallWarning arg0) {
			}

		};
		FilterQuery fq = new FilterQuery();

		String keywords[] = keyword.split(" ");
		String languages[] = { lang };
		fq.language(languages);
		
		fq.track(keywords);
		twitterStream.addListener(listener);
		twitterStream.filter(fq);
		
	}

	public void loadStatusDone(Status status) {
		KComment comment = generateComment(status.getUser().getName(), status.getText(), currentSearchInfo);
		
		tfv.addRowTblStatus(comment);
		comments.addElement(comment);
		System.out.println(status.getText());
	}
	
	public KComment generateComment(String user,String content, SearchInfo sf){
		KComment comment = new KComment();
		String statusText = content;
		statusText = statusText.replaceAll("\n", " ");
		statusText = statusText.replaceAll("\t", " ");
		
		if(sf.isTranslated() == true){
			statusText = Translator.toVietnamese(statusText);			
		}
		
		// remove url
		if(sf.isRemoveUrl()){
			statusText = statusText.replaceAll("[a-zA-Z]+:\\/\\/*+[a-zA-Z]+.[a-zA-Z\\?&-_]+","");
		}
		
		//remove html tag
		if(sf.isRemoveHtmlTag()){
			statusText = statusText.replaceAll("<[a-zA-Z \"=\\-/']+>","");
		}
		
		// remove special character
		if(sf.isRemoveSpecial()){
			statusText = statusText.replaceAll("[!@#$%^&\\*\\(\\)\\+=\\-_`~|}{\\[\\]:\"?/><';]","");
		}
		
		comment.setContent(statusText);
		comment.setUser(user);
		return comment;
	}
	
	
	/*
	 * Action definition
	 * 
	 */
	
	class TweetFieldButtonAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand());
			
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_SEARCH)){
				SearchInfo sf = tfv.getSearchInfo();
				currentSearchInfo = sf;
				if(sf.getType() == KConstant.METHOD_SEARCH_API){
					tfv.removeAllRowTblStatus();
					comments = new Vector<KComment>();
					getSearchResult(sf.getKeyword(), sf.getLanguage(), sf.getCount());
					
				}
				
				if(sf.getType() == KConstant.METHOD_STREAM_API){
					tfv.removeAllRowTblStatus();
					comments = new Vector<KComment>();
					streamFilter(sf.getKeyword(), sf.getLanguage());
				}
				
				if(sf.getType() == KConstant.METHOD_VNEXPRESS_URL){
					Vector<KComment> newcomments = CommentFetcher.fetchComments(currentSearchInfo.getKeyword());
					comments = new Vector<>();
					for (KComment comment : newcomments) {
						Vector<KComment> subComments = new Vector<>();
						for (KComment subComment : comment.getSubComment()) {
							subComments.addElement(generateComment("-"+subComment.getUser(), subComment.getContent(), currentSearchInfo));
						}
						comment = generateComment(comment.getUser(), comment.getContent(), currentSearchInfo);
						System.out.println(comment.getContent());
						comment.setSubComment(subComments);
						
						comments.addElement(comment);
					}
					tfv.setTblStatus(comments);
					
				}
				if(sf.getType() == KConstant.METHOD_VNEXPRESS_RSS){
					Vector<KComment> newcomments = CommentFetcher.rssToComments(currentSearchInfo.getKeyword());
					comments = new Vector<>();
					for (KComment comment : newcomments) {
						comment = generateComment(comment.getUser(), comment.getContent(), currentSearchInfo);
						System.out.println(comment.getContent());
						comments.addElement(comment);
					}
					tfv.setTblStatus(comments);
					
				}
				
			}
			if(e.getActionCommand().equalsIgnoreCase(KConstant.ACTION_COMMAND_ADD_LEARN_DATA)){
				Vector<KComment> newData = tfv.getLearnData();
				Vector<KComment> comments = FileReadWriter.readLearnData();
				comments.addAll(newData);
				FileReadWriter.objectToFile(KConstant.LEARN_DATA_FILE_NAME, comments);
			}
			
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_VIEW_LIB)){
				new LibraryController().initLibraryView(new LibraryView());
			}
			
		}
	}
	

}
