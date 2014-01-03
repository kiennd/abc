package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;

import model.AbbreviationWord;
import model.EffectWord;
import model.KComment;
import model.KWord;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.general.DefaultPieDataset;

import view.LibraryView;
import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import constant.KConstant;
import edu.stanford.nlp.ling.WordTag;

public class LibraryController {
	LibraryView libraryView;
	private Vector<KComment> comments;
	private Vector<KWord> unlabelWordLib;
	private Vector<KWord> decisionWordLib;
	private Vector<KWord> nondecisionWordLib;
	
	private String currentRadioSelected;

	public void initLibraryView(LibraryView view) {
//		rebuildWordLib();
		this.libraryView = view;
		view.setVisible(true);
		view.addButtonActionListener(new ButtonActionListener());
		comments = FileReadWriter.readLearnData();
		this.libraryView.setTblLearnData(comments);
		unlabelWordLib = FileReadWriter.readUnlabelWordLib();
		decisionWordLib = FileReadWriter.readDecisionWordLib();
		nondecisionWordLib = FileReadWriter.readNonDecisionWordLib();
		
		
		libraryView.setTblWords(unlabelWordLib);
		libraryView.addRadioButtonActionListener(new RadioButtonActionListener());
		currentRadioSelected = KConstant.UNLABEL;
		libraryView.setUnlabelSelected();
		libraryView.setTblAbbreviation(FileReadWriter.readAbbreviationLib());
		libraryView.setEffectWorData(FileReadWriter.readEffectWordLib());
	}
	
	public void initChart(Vector<KWord> unlabelWordLib ,Vector<KWord> decisionWordLib ,Vector<KWord> nondecisionWordLib){
		int word1=0,word_1=0,word2=0,word_2=0;
		for (KWord kWord : nondecisionWordLib) {
			double weight = (double)kWord.getCountpos()/(double)(kWord.getCountpos()+kWord.getCountneg());
			if(getFinalWeight(weight)==1){
				word1++;
				continue;
			}
			if(getFinalWeight(weight)==-1){
				word_1++;
				continue;
			}
			if(getFinalWeight(weight)==2){
				word2++;
				continue;
			}
			if(getFinalWeight(weight)==-1){
				word_1++;
				continue;
			}
		}
		for (KWord kWord : unlabelWordLib) {
			double weight = (double)kWord.getCountpos()/(double)(kWord.getCountpos()+kWord.getCountneg());
			if(getFinalWeight(weight)==1){
				word1++;
				continue;
			}
			if(getFinalWeight(weight)==-1){
				word_1++;
				continue;
			}
			if(getFinalWeight(weight)==2){
				word2++;
				continue;
			}
			if(getFinalWeight(weight)==-1){
				word_1++;
				continue;
			}
		}
		for (KWord kWord : decisionWordLib) {
			double weight = (double)kWord.getCountpos()/(double)(kWord.getCountpos()+kWord.getCountneg());
			if(getFinalWeight(weight)==1){
				word1++;
				continue;
			}
			if(getFinalWeight(weight)==-1){
				word_1++;
				continue;
			}
			if(getFinalWeight(weight)==2){
				word2++;
				continue;
			}
			if(getFinalWeight(weight)==-1){
				word_1++;
				continue;
			}
		}
		drawChar(word1, word_1, word2, word_2, 656, 177);
		
	}

	private int getFinalWeight(double weight) {

		// nếu trọng số positive>=0.75 thì là 2, >=0.5 thì là 1, >=0.25
		// là -1 còn <0.25 là -2
		if (weight >= 0.75) {
			return 2;
		}
		if (weight >= 0.5) {
			return 1;
		}
		if (weight >= 0.25) {
			return -1;
		}
		return -2;
	}
	/*
	 * * 1. Np - Proper noun 2. Nc - Classifier 3. Nu - Unit noun 4. N - Common
	 * noun 5. V - Verb //Dong tu 6. A - //Tinh tu 7. P - Pronoun 8. R - Adverb
	 * //Trang tu 9. L - Determiner 10. M - Numeral 11. E - Preposition 12. C -
	 * Subordinating conjunction 13. CC - Coordinating conjunction 14. I -
	 * Interjection 15. T - Auxiliary, modal words 16. Y - Abbreviation 17. Z -
	 * Bound morphemes 18. X - Unknown
	 */
	public void rebuildWordLib() {
		resetCount(unlabelWordLib);
		resetCount(decisionWordLib);
		resetCount(nondecisionWordLib);
		Vector<KComment> comments;
		comments = FileReadWriter.readLearnData();
		for (KComment comment : comments) {
			String str = comment.getContent();
			VietnameseMaxentTagger vnTagger = new VietnameseMaxentTagger();
			List<WordTag> list = vnTagger.tagText2(str);
			for (int i = 0; i < list.size(); i++) {
				System.out
						.println(list.get(i).tag() + " " + list.get(i).word());
				String tag = list.get(i).tag();
				String word = list.get(i).word();
				if (tag.equals("V") || tag.equals("A") || tag.equals("R")) {
					KWord newWord = new KWord();
					newWord.setWord(word);
					newWord.setType(tag);
					boolean isExisted = false;
					isExisted = checkExist(newWord, decisionWordLib, comment.getStatus());
					if(!isExisted){
						isExisted = checkExist(newWord, nondecisionWordLib, comment.getStatus());
					}
					if(!isExisted){
						 addNewWord(newWord, unlabelWordLib, comment.getStatus());
					}
				}

			}

		}
		saveData();

	}
	
	private void addNewWord(KWord word, Vector<KWord> words, int status){
		for (KWord kWord : words) {
			if (word.getWord().equalsIgnoreCase(kWord.getWord())) {
				if (status == 1) {
					kWord.increasePos();
				} else {
					kWord.increaseNeg();;
				}
				return;
			}

		}
		if (status == 1) {
			word.setCountpos(1);
			word.setCountneg(0);
		} else {
			word.setCountpos(0);
			word.setCountneg(1);
		}
		word.setLabel(KConstant.UNLABEL);
		words.add(word);
	}
	private boolean checkExist(KWord word, Vector<KWord> words, int status) {
		for (KWord kWord : words) {
			if (word.getWord().equalsIgnoreCase(kWord.getWord())) {
				
				if (status == 1) {
					kWord.increasePos();
				} else {
					kWord.increaseNeg();;
				}
				return true;
			}

		}
		return false;
	}
	
	private void resetCount(Vector<KWord> words){
		for (KWord kWord : words) {
			kWord.setCountneg(0);
			kWord.setCountpos(0);
		}
	}
	
	public class RadioButtonActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand());
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_UNLABEL_SELECTED)){
				currentRadioSelected = KConstant.UNLABEL;
			}
			
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_DECISION_SELECTED)){
				currentRadioSelected = KConstant.DECISION_LABEL;
				
					
			}
			
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_NON_DECISION_SELECTED)){
				currentRadioSelected = KConstant.NON_DECISION_LABEL;				
			}
			reloadTblWord(currentRadioSelected);
			
			
			
		}
		
	}

	public class ButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(e.getActionCommand());
			if (e.getActionCommand().equals(
					KConstant.ACTION_COMMAND_SAVE_LEARN_DATA)) {
				FileReadWriter.objectToFile(KConstant.LEARN_DATA_FILE_NAME,
						libraryView.getLearnData());
				System.out.println("save done");
			}

			if (e.getActionCommand().equals(
					KConstant.ACTION_COMMAND_IMPORT_LEARN_DATA_FROM_TXT)) {
				JFileChooser filechooser = new JFileChooser();
				int option = filechooser.showOpenDialog(null);
				if (option == JFileChooser.APPROVE_OPTION) {
					File f = filechooser.getSelectedFile();
					System.out.println(f.getAbsolutePath());
					Vector<KComment> comments = FileReadWriter.textToLearnData(f.getAbsolutePath());
					libraryView.setTblLearnData(comments);
				}
			}
			
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_REBUILD_WORDLIB)){
				rebuildWordLib();
			}
			
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_SEARCH_LIB)){
				String keyword = libraryView.getKeyword().toLowerCase();
				
				Vector<KWord> wordsResult = new Vector<>();
				Vector<KComment> commentResult = new Vector<>();
				for (KWord kWord : unlabelWordLib) {
					if(kWord.getWord().toLowerCase().contains(keyword)){
						wordsResult.add(kWord);
					}
				}
				libraryView.setTblWords(wordsResult);
				
				for (KComment comment : comments) {
					if(comment.getContent().toLowerCase().contains(keyword)){
						commentResult.add(comment);
					}
				}
				libraryView.setTblLearnData(commentResult);
			}
			
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_SAVE_UNLABEL_WORD)){
				Vector<KWord> wordsChanged = libraryView.getWordChanged(currentRadioSelected);
				
				for (KWord kWord : wordsChanged) {
					Vector<KWord> newLib = getWordLib(kWord.getLabel());
					Vector<KWord> oldLib = getWordLib(currentRadioSelected); 
					
					changeLabel(oldLib, newLib, kWord);
				}		
				saveData();
			}
			
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_ADD_ABBREVIATION)){
				libraryView.addRowAbbrevation();
			}
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_SAVE_ABBREVIATION)){
				Vector<AbbreviationWord> abws = libraryView.getAbbrevationData();
				FileReadWriter.objectToFile(KConstant.ABBREVIATION_LIB_FILE_NAME, abws);
			}
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_REMOVE_ABBREVIATION)){
				libraryView.removeAbbreviationRow();
			}

			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_VIEW_CHART)){
				initChart(unlabelWordLib, decisionWordLib, nondecisionWordLib);
			}

			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_ADD_EFFECTWORD)){
				libraryView.addEffectRow();
			}

			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_REMOVE_EFFECTWORD)){
				libraryView.removeEffectRow();
			}
			if(e.getActionCommand().equals(KConstant.ACTION_COMMAND_SAVE_EFFECTWORD)){
				Vector<EffectWord> words = libraryView.getEffectWordData();
				FileReadWriter.objectToFile(KConstant.EFFECTWORD_LIB_FILE_NAME, words);
			}
			
		}

	}
	
	public void saveData(){
		FileReadWriter.objectToFile(KConstant.UNLABEL_WORD_LIB_FILE_NAME, unlabelWordLib);
		FileReadWriter.objectToFile(KConstant.DECISION_WORD_LIB_FILE_NAME, decisionWordLib);
		FileReadWriter.objectToFile(KConstant.NONDECISION_WORD_LIB_FILE_NAME, nondecisionWordLib);
		reloadTblWord(currentRadioSelected);
	}
	
	public void reloadTblWord(String label){
		if(label.equals(KConstant.UNLABEL)){
			libraryView.setTblWords(unlabelWordLib);
			return;
		}
		if(label.equals(KConstant.DECISION_LABEL)){
			libraryView.setTblWords(decisionWordLib);
			return;
		}
		if(label.equals(KConstant.NON_DECISION_LABEL)){
			libraryView.setTblWords(nondecisionWordLib);
			return;
		}
	}
	
	public Vector<KWord> getWordLib(String label){
		if(label.equals(KConstant.UNLABEL)){
			return unlabelWordLib;
		}
		if(label.equals(KConstant.DECISION_LABEL)){
			return  decisionWordLib;
		}
		if(label.equals(KConstant.NON_DECISION_LABEL)){
			return  nondecisionWordLib;
		}
		
		return new Vector<>();
	}
	
	public void changeLabel(Vector<KWord> oldLib, Vector<KWord> newLib, KWord wordChanged ){
		for (KWord kWord : oldLib) {
			if(kWord.getWord().equalsIgnoreCase(wordChanged.getWord())){
				oldLib.removeElement(kWord);
				break;
			}
		}
		newLib.addElement(wordChanged);
	}
	
	
    public void drawChar(int word1, int word_1,int word2,int word_2, int width,int height){
        DefaultPieDataset data = new DefaultPieDataset(); 
        int sum = word_1 +word1+word2+word_2;
        //float ne = Math.round(se.getNegative()/sum, 2);
       //MathForDummies.round(3.1415926, 2);
       // DefaultPieDataset data = new DefaultPieDataset(); 
       // int sum = tichcuc + tieucuc + khongco;
         DecimalFormat df=new DecimalFormat("0.00"); 
        df.setRoundingMode(RoundingMode.UP); 
        
        float t1 = (float)word1*100/(float)sum;
        float t_1 = (float)word_1*100/(float)sum;
        float t2 = (float)word2*100/(float)sum;
        float t_2 = (float)word_2*100/(float)sum;

        
        data.setValue("Weight = -1: " + word_1+ " (" + df.format(t_1) + "%)", word_1); 
        data.setValue("Weight = 1: " + word1+ " (" + df.format(t1) + "%)", word1);
        data.setValue("Weight = 2: " + word1+ " (" + df.format(t2) + "%)", word2);
        data.setValue("Weight = -2: " + word1+ " (" + df.format(t_2) + "%)", word_2);
        
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

	public static void main(String[] args) {
		new LibraryController().initLibraryView(new LibraryView());
		;
	}
}
