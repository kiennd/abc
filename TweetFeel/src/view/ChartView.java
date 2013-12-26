package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.math.RoundingMode;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class ChartView extends JDialog {

	private final JPanel contentPanel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			ChartView dialog = new ChartView();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public ChartView() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setLayout(new FlowLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	   //Ham ve bieu do
    public void DrawChar(int Pos, int Neg, int Neu){
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
   

}
