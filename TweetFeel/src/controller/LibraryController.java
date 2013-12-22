package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.xml.stream.events.Comment;

import constant.KConstant;
import model.KComment;
import model.KWord;
import view.LibraryView;
import vn.hus.nlp.tagger.VietnameseMaxentTagger;
import edu.stanford.nlp.ling.Word;
import edu.stanford.nlp.ling.WordTag;

public class LibraryController {
	LibraryView libraryView;
	private Vector<KComment> comments;
	private Vector<KWord> wordlib;

	public void initLibraryView(LibraryView view) {
//		rebuildWordLib();
		this.libraryView = view;
		view.setVisible(true);
		view.addButtonActionListener(new ButtonActionListener());
		comments = FileReadWriter.readLearnData();
		this.libraryView.setTblLearnData(comments);
		wordlib = FileReadWriter.readWordLib();
		libraryView.setTblWords(wordlib);
	}

	/*
	 * * 1. Np - Proper noun 2. Nc - Classifier 3. Nu - Unit noun 4. N - Common
	 * noun 5. V - Verb //Dong tu 6. A - //Tinh tu 7. P - Pronoun 8. R - Adverb
	 * //Trang tu 9. L - Determiner 10. M - Numeral 11. E - Preposition 12. C -
	 * Subordinating conjunction 13. CC - Coordinating conjunction 14. I -
	 * Interjection 15. T - Auxiliary, modal words 16. Y - Abbreviation 17. Z -
	 * Bound morphemes 18. X - Unknown
	 */
	public Vector<KWord> rebuildWordLib() {
		Vector<KComment> comments;
		Vector<KWord> words = new Vector<>();
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
					addWord(newWord, words, comment.getStatus());
				}

			}

		}

		FileReadWriter.objectToFile(KConstant.WORD_LIB_FILE_NAME, words);
		return words;
	}

	private void addWord(KWord word, Vector<KWord> words, int status) {
		for (KWord kWord : words) {
			if (word.getWord().equals(kWord.getWord())) {
				if (status == 1) {
					kWord.increasePos();
				} else {
					kWord.increasePos();
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
		words.add(word);
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
				libraryView.setTblWords(rebuildWordLib());
			}
		}

	}

	public static void main(String[] args) {
		new LibraryController().initLibraryView(new LibraryView());
		;
	}

}
