package controller;

import java.util.List;
import java.util.Vector;

import javax.annotation.PostConstruct;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import constant.KConstant;
import edu.stanford.nlp.ling.WordTag;
import model.KComment;
import model.KWord;

public class Classifier {

	private Vector<KWord> wordlib;

	public Classifier() {
		this.wordlib = FileReadWriter.readDecisionWordLib();
	}

	public int classifiSentence(String sentence) {
		Vector<KWord> wordList;
		wordList = buildWordList(sentence);
		int result =0;
		for (KWord kword : wordList) {
			if(kword.getWord().equalsIgnoreCase("không")){
				continue;
			}
			double posWeight = getPosWeight(kword.getWord());
			if(Double.isNaN(posWeight)){
				continue;
			}
			int posfinalWeight = getFinalWeight(posWeight);
			
			
			int count = countWordInString(sentence, kword.getWord());
			int count2 = countWordInString(sentence, "không "+kword.getWord());
			
			count-= count2;
			
			if(posfinalWeight > 0){
				result += posfinalWeight*count;
				result -= posfinalWeight*count2;
			}else{
				result += posfinalWeight*count;
				result += (posfinalWeight+1)*count2;
			}
			System.out.println(count +" "+ count2+ " "+ kword.getWord()+" "+posfinalWeight);
		}
		
		return result;
	}
	
	// đếm số lần xuất hiện của từ trong str
	public int countWordInString(String str, String word){
//		str = str + " ";
//		word = word+" ";
		int lastIndex = 0;
		int count =0;

		while(lastIndex != -1){

		       lastIndex = str.indexOf(word,lastIndex);

		       if( lastIndex != -1){
		             count ++;
		             lastIndex+=word.length();
		      }
		}
		return count;
	}
	
	// tách từ và lưu vào tập từ của câu cần phân loại
	private Vector<KWord> buildWordList(String sentence) {
		Vector<KWord> words = new Vector<>();
		String str = sentence;
		VietnameseMaxentTagger vnTagger = SingletonData.getVnTagger();
		List<WordTag> list = vnTagger.tagText2(str);
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).tag() + " " + list.get(i).word());
			String tag = list.get(i).tag();
			String word = list.get(i).word();
			if (tag.equalsIgnoreCase("V") || tag.equalsIgnoreCase("A") || tag.equalsIgnoreCase("R")) {
				KWord newWord = new KWord();
				newWord.setWord(word);
				newWord.setType(tag);
				addWord(newWord, words);
			}

		}

		return words;
	}

	private void addWord(KWord word, Vector<KWord> words) {
		for (KWord kWord : words) {
			if (word.getWord().equalsIgnoreCase(kWord.getWord())) {
				kWord.setTotal(kWord.getTotal()+1);
				return;
			}

		}
		word.setTotal(1);
		words.add(word);
	}

	private double getPosWeight(String word) { // weight: trọng số
		KWord currentWord = new KWord();
		boolean available = false;
		for (KWord kWord : wordlib) {
			if (kWord.getWord().equalsIgnoreCase(word)) {
				currentWord = kWord;
				available = true;
				break;
			}
		}
		if (available) {
			double negCount = currentWord.getCountneg();
			double posCount = currentWord.getCountpos();
			return posCount / (posCount + negCount);
		} else {
			return Double.NaN;
		}

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

	public static void main(String[] args) {
		
		System.out.println(new Classifier().classifiSentence(""
				+ "Lại bị treo máy rồi, chán quá"));
	}

}
