package view;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import constant.KConstant;
import model.KComment;
import model.KWord;

public class LibraryView extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable tblLearnData;
	private JTable tblWords;
	private JTable tblAbbreviation;
	private DefaultTableModel learnDataModel;
	private DefaultTableModel wordsModel;
	
	private JButton btnSaveLearn,btnImportFromtxt,btnRebuildData;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LibraryView frame = new LibraryView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LibraryView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 631, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(64, 76, 343, 28);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(417, 77, 117, 29);
		contentPane.add(btnSearch);
		
		JLabel lblLibrary = new JLabel("Library");
		lblLibrary.setFont(new Font("Lucida Grande", Font.PLAIN, 22));
		lblLibrary.setBounds(43, 22, 92, 27);
		contentPane.add(lblLibrary);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(38, 122, 560, 278);
		contentPane.add(tabbedPane);
		
		JPanel learnDataPanel = new JPanel();
		tabbedPane.addTab("Learn Data", null, learnDataPanel, null);
		learnDataPanel.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(16, 6, 499, 173);
		learnDataPanel.add(scrollPane);
		
		Vector<String>learnDataColumnNames = new Vector<>();
		learnDataColumnNames.add("id");
		learnDataColumnNames.add("Comment");
		learnDataColumnNames.add("Status");
		this.learnDataModel = new DefaultTableModel(learnDataColumnNames, 0);
		tblLearnData = new JTable();
		tblLearnData.setModel(learnDataModel);
		tblLearnData.getColumnModel().getColumn(0).setMaxWidth(60);
		tblLearnData.getColumnModel().getColumn(2).setMaxWidth(80);
		
		
		scrollPane.setViewportView(tblLearnData);
		
		btnSaveLearn = new JButton("Save");
		btnSaveLearn.setBounds(16, 191, 68, 29);
		learnDataPanel.add(btnSaveLearn);
		
		btnImportFromtxt = new JButton("Import from *.txt");
		btnImportFromtxt.setBounds(92, 191, 141, 29);
		learnDataPanel.add(btnImportFromtxt);
		
		JPanel wordLibPanel = new JPanel();
		tabbedPane.addTab("Word Lib", null, wordLibPanel, null);
		wordLibPanel.setLayout(null);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(18, 6, 503, 183);
		wordLibPanel.add(scrollPane_2);
		
		Vector<String> tblWordColumnNames = new Vector<>();
		tblWordColumnNames.add("id");
		tblWordColumnNames.add("Word");
		tblWordColumnNames.add("Type");
		tblWordColumnNames.add("Pos count");
		tblWordColumnNames.add("Neg count");
		
		wordsModel = new DefaultTableModel(tblWordColumnNames, 0);
		
		tblWords = new JTable();
		tblWords.setModel(wordsModel);
		tblWords.getColumnModel().getColumn(0).setMaxWidth(60);
		tblWords.getColumnModel().getColumn(2).setMaxWidth(80);
		tblWords.getColumnModel().getColumn(3).setMaxWidth(80);
		tblWords.getColumnModel().getColumn(4).setMaxWidth(80);
		
		scrollPane_2.setViewportView(tblWords);
		
		 btnRebuildData = new JButton("Rebuild data");
		btnRebuildData.setBounds(17, 197, 117, 29);
		wordLibPanel.add(btnRebuildData);
		
		JPanel abbreviationPanel = new JPanel();
		tabbedPane.addTab("Abbreviation lib", null, abbreviationPanel, null);
		abbreviationPanel.setLayout(null);
		
		tblAbbreviation = new JTable();
		tblAbbreviation.setBounds(18, 6, 502, 171);
		abbreviationPanel.add(tblAbbreviation);
		
		JButton btnSave_1 = new JButton("Save");
		btnSave_1.setBounds(18, 189, 75, 29);
		abbreviationPanel.add(btnSave_1);
		
		JPanel statisticsPanel = new JPanel();
		tabbedPane.addTab("Statistics", null, statisticsPanel, null);
	}
	
	public void setTblLearnData(Vector<KComment> kstatuses){
		while(learnDataModel.getRowCount()>0){
			learnDataModel.removeRow(0);
		}
		int i = 1;
		for (KComment kStatus : kstatuses) {
			Vector<String> rowElements = new Vector<>();
			rowElements.addElement(i+"");
			rowElements.addElement(kStatus.getContent());
			rowElements.addElement(kStatus.getStatus()+"");
			learnDataModel.addRow(rowElements);
			i++;
		}
	}
	
	public void setTblWords(Vector<KWord> words){
		while(wordsModel.getRowCount()>0){
			wordsModel.removeRow(0);
		}
		for (KWord word : words) {
			Vector<String> rowElements = new Vector<>();
			rowElements.addElement(wordsModel.getRowCount()+1+"");
			rowElements.addElement(word.getWord());
			rowElements.addElement(word.getType());
			rowElements.addElement(word.getCountpos()+"");
			rowElements.addElement(word.getCountneg()+"");
			wordsModel.addRow(rowElements);
		}
	}
	
	
	
	public void addButtonActionListener(ActionListener act){
		this.btnSaveLearn.setActionCommand(KConstant.ACTION_COMMAND_SAVE_LEARN_DATA);
		this.btnSaveLearn.addActionListener(act);
		
		this.btnImportFromtxt.setActionCommand(KConstant.ACTION_COMMAND_IMPORT_LEARN_DATA_FROM_TXT);
		this.btnImportFromtxt.addActionListener(act);
		
		this.btnRebuildData.setActionCommand(KConstant.ACTION_COMMAND_REBUILD_WORDLIB);
		this.btnRebuildData.addActionListener(act);
		
	}
	
	public Vector<KComment> getLearnData(){
		Vector<KComment> comments = new Vector<>();
		for (int i = 0; i < learnDataModel.getRowCount(); i++) {
			String classifistatus = (String) learnDataModel.getValueAt(i, 2);
			if(classifistatus==null){
				continue;
			}
			if(classifistatus.equals("1")||classifistatus.equals("0")){
				String comment = (String) learnDataModel.getValueAt(i, 1);
				KComment newComment = new KComment();
				newComment.setContent(comment);
				newComment.setStatus(Integer.parseInt(classifistatus));
				comments.addElement(newComment);
			}
		}
		return comments;
	}
	
}
