import java.awt.*;
import javax.swing.*;
import com.thinking.machines.hr.pl.model.*;
import com.thinking.machines.hr.bl.exceptions.*;
class DesignationModelTestcase extends JFrame
{
private JTable tb;
private JScrollPane jsp;
private DesignationModel designationModel; 
private Container container;
DesignationModelTestcase()
{
designationModel = new DesignationModel();
tb  = new JTable(designationModel);
jsp = new JScrollPane(tb,ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container = getContentPane();
container.setLayout(new FlowLayout());
container.add(jsp);
setLocation(100,100);
setVisible(true);
setSize(500,600);
}
public static void main(String[] aa)
{
DesignationModelTestcase dmtc = new DesignationModelTestcase();
}
}