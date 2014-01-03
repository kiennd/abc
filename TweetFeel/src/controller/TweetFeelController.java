package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import model.AbbreviationWord;
import model.KComment;
import model.SearchInfo;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

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
	private Vector<AbbreviationWord> abwLib = FileReadWriter.readAbbreviationLib();
	
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
		
		for (int i = 0; i < abwLib.size(); i++) {
			statusText = statusText.replaceAll("(?i)"+abwLib.get(i).getAbbreviation(), abwLib.get(i).getOriginal());
		}
		
		statusText = statusText.replaceAll("\n", " ");
		statusText = statusText.replaceAll("\t", " ");
		
		if(sf.isTranslated() == true){
			statusText = Translator.toVietnamese(statusText);			
		}
		
		// remove url
		if(sf.isRemoveUrl()){
			statusText = statusText.replaceAll("[a-zA-Z]+:\\/\\/*+[a-zA-Z]+.[a-zA-Z\\?&-_]+"," ");
		}
		
		//remove html tag
		if(sf.isRemoveHtmlTag()){
			statusText = statusText.replaceAll("<[a-zA-Z_@ \"=\\-/']+>"," ");
		}
		
		// remove special character
		if(sf.isRemoveSpecial()){
			statusText = statusText.replaceAll("[!@#$%^&\\*\\(\\)\\+=\\-_`~|}{\\[\\]:\"?/><';]"," ");
		}
		
		comment.setContent(statusText);
		comment.setUser(user);
		
		
		return comment;
	}
	
    public void drawChar(int Pos, int Neg, int Neu){
        DefaultPieDataset data = new DefaultPieDataset(); 
        int sum = Neg + Neu+Pos;
        //float ne = Math.round(se.getNegative()/sum, 2);
       //MathForDummies.round(3.1415926, 2);
       // DefaultPieDataset data = new DefaultPieDataset(); 
       // int sum = tichcuc + tieucuc + khongco;
         DecimalFormat df=new DecimalFormat("0.00"); 
        df.setRoundingMode(RoundingMode.UP); 
        
        float tic = (float)Pos*100/(float)sum;
        float tec = (float)Neg*100/(float)sum;
        float kc = (float)Neu*100/(float)sum;

        data.setValue("Tiêu cực: " + Neg+ " (" + df.format(tec) + "%)", Neg); 
        data.setValue("Tích cực: " + Pos+ " (" + df.format(tic) + "%)", Pos); 
        data.setValue("Trung lập: " + Neu+ " (" + df.format(kc) + "%)", Neu); 
        // create a chart... 
        JFreeChart chart = ChartFactory.createPieChart( 
        "Biểu đồ thống kê", 
        data, 
        true, // legend? 
        true, // tooltips? 
        false // URLs? 
        ); 
        // create and display a frame... 
        
        ChartFrame frame = new ChartFrame("Tweets Feel", chart); 
        frame.pack(); 
        //hien thi bieu do len giua ban hinh
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds((screenSize.width-frame.getWidth())/2, (screenSize.height-frame.getHeight())/2, frame.getWidth(), frame.getHeight());
        
        frame.setVisible(true); 
    }
	
    public void processNewComments(Vector<KComment> newcomments){
		comments = new Vector<>();
		for (KComment comment : newcomments) {
			comment = generateComment(comment.getUser(), comment.getContent(), currentSearchInfo);
			System.out.println(comment.getContent());
			comments.addElement(comment);
		}
		tfv.setTblComment(comments);
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
				
				if(sf.getType() == KConstant.METHOD_VNEXPRESS_URL|| sf.getType() == KConstant.METHOD_NGOISAO_URL){
					Vector<KComment> newcomments = CommentFetcher.vnefetchComments(currentSearchInfo.getKeyword());
					processNewComments(newcomments);
					
				}
				if(sf.getType() == KConstant.METHOD_VNEXPRESS_RSS || sf.getType() == KConstant.METHOD_NGOISAO_RSS){
					Vector<KComment> newcomments = CommentFetcher.vneRssToComments(currentSearchInfo.getKeyword());
					processNewComments(newcomments);
				}
				if(sf.getType() == KConstant.METHOD_ZING_URL){
					Vector<KComment> newcomments = CommentFetcher.zingGetCommentsFromUrl(currentSearchInfo.getKeyword());
					processNewComments(newcomments);
				}
				
				if(sf.getType() == KConstant.METHOD_ZING_RSS){
					Vector<KComment> newcomments = CommentFetcher.zingGetCommentsFromRSS(currentSearchInfo.getKeyword());
					processNewComments(newcomments);
				}
				if(sf.getType() == KConstant.METHOD_HAIVL_URL){
					Vector<KComment> newcomments = CommentFetcher.haivlGetCommentsFromUrl(currentSearchInfo.getKeyword());
					processNewComments(newcomments);
				}
				if(sf.getType() == KConstant.METHOD_RING_URL){
					Vector<KComment> newcomments = CommentFetcher.ringGetCommentsFromUrl(currentSearchInfo.getKeyword());
					processNewComments(newcomments);
				}
				if(sf.getType() == KConstant.METHOD_RING_RSS){
					Vector<KComment> newcomments = CommentFetcher.ringGetCommentsFromRSS(currentSearchInfo.getKeyword());
					processNewComments(newcomments);
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
			
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_CLASSIFY)){
				Vector<KComment> comments = tfv.getCommentData();
				Vector<KComment> classifierResult = new Vector<>();
				for (KComment kComment : comments) {
					Classifier clf = new Classifier();
					int result = clf.classifiSentence(kComment.getContent());
					kComment.setStatus(result);
					classifierResult.add(kComment);
					
				}
				tfv.setTblComment(classifierResult);
			}
			
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_VIEW_CHART)){
				Vector<KComment> comments = tfv.getCommentData();
				int neg=0,pos=0,neu=0;
				for (KComment kComment : comments) {
					if(kComment.getStatus()>=1){
						pos++;
						continue;
					}
					if(kComment.getStatus()<=-1){
						neg++;
						continue;
					}
					if(kComment.getStatus()==0){
						neu++;
						continue;
					}
				}
				drawChar(pos, neg, neu);
			}
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_MANUAL_COMMENT)){
				tfv.addCommentRow();
			}
		}
	}
	
	
	
}
