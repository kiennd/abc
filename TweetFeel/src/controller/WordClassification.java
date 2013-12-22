package controller;

import java.util.List;
import java.util.Vector;

import model.KWord;
import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.WordTag;

public class WordClassification {
	
	public Vector<KWord> classifyWord(String str){
		Vector<KWord> result = new Vector<>();
		
        VietnameseMaxentTagger vnTagger = new VietnameseMaxentTagger();
        List<WordTag> list = vnTagger.tagText2(str);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).tag() + " " + list.get(i).word());
        }
		
		return result;
	}
	
}
