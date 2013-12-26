package controller;

import java.util.Vector;

import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import model.KComment;

public class SingletonData {
	private static VietnameseMaxentTagger vnTagger;
	public static VietnameseMaxentTagger getVnTagger(){
		if(vnTagger == null){
			vnTagger = new VietnameseMaxentTagger();
		}
		return vnTagger;
	}
	
}
