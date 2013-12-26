package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.Vector;

import javax.xml.stream.events.Comment;

import constant.KConstant;
import model.AbbreviationWord;
import model.KComment;
import model.KWord;

public class FileReadWriter {
	public static void stringTofile(String content, String fileName,boolean isAppend){
		try {
			File f = new File(fileName);

			if(!f.exists()){
				f.createNewFile();
			}
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(fileName, isAppend)));
		    out.println(content);
		    out.close();
		} catch (IOException e) {
		    //oh noes!
		}
	}
	
	public static String fileToString(String fileName) throws IOException{
		 BufferedReader br = new BufferedReader(new FileReader(fileName));
		 String result = "";
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append('\n');
	            line = br.readLine();
	        }
	        result = sb.toString();
	    } finally {
	        br.close();
	    }
	    return result;
	}
	
	public static Object fileToObject(String filename) {
        Object o = new Object();
        try {
            FileInputStream fi = new FileInputStream(filename);
            ObjectInputStream oi = new ObjectInputStream(fi);
            o = oi.readObject();
            oi.close();
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
        return o;
    }

    public static Boolean objectToFile(String filename, Object o) {
        try {
            FileOutputStream fo = new FileOutputStream(filename, false);
            ObjectOutputStream oo = new ObjectOutputStream(fo);
            oo.writeObject(o);
            oo.close();

        } catch (Exception ex) {
            return false;
        }
        return true;
    }
    
    public static Vector<KComment> readLearnData(){
    	Vector<KComment> comments = (Vector<KComment>) fileToObject(KConstant.LEARN_DATA_FILE_NAME);
    	if(comments!=null){
    		return comments;
    	}
    	return new Vector<>();
    }
    
    public static Vector<KWord> readUnlabelWordLib(){
    	Vector<KWord> words = (Vector<KWord>) fileToObject(KConstant.UNLABEL_WORD_LIB_FILE_NAME);
    	if(words!=null){
    		return words;
    	}
    	return new Vector<>();
    }
    public static Vector<AbbreviationWord> readAbbreviationLib(){
    	Vector<AbbreviationWord> words = (Vector<AbbreviationWord>) fileToObject(KConstant.ABBREVIATION_LIB_FILE_NAME);
    	if(words!=null){
    		return words;
    	}
    	return new Vector<>();
    }
    
    
    public static Vector<KWord> readDecisionWordLib(){
    	Vector<KWord> words = (Vector<KWord>) fileToObject(KConstant.DECISION_WORD_LIB_FILE_NAME);
    	if(words!=null){
    		return words;
    	}
    	return new Vector<>();
    }
    
    public static Vector<KWord> readNonDecisionWordLib(){
    	Vector<KWord> words = (Vector<KWord>) fileToObject(KConstant.NONDECISION_WORD_LIB_FILE_NAME);
    	if(words!=null){
    		return words;
    	}
    	return new Vector<>();
    }

   
    
    public static Vector<KComment> textToLearnData(String fileName){
    	Vector<KComment> comments = new Vector<>();
    	String[] stringData;
		try {
			stringData = fileToString(fileName).split("\n");
			for (String string : stringData) {
				if(string.length()>0){
					String[] commentString = string.split("\t");
					KComment comment = new KComment();
					comment.setStatus(Integer.parseInt(commentString[0]));
					comment.setContent(commentString[1]);
					comments.addElement(comment);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    	return comments;
    }
    
//    public static void main(String[] args) {
//		try {
//			readLearnData();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
