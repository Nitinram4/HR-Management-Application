import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
class DesignationManagerAddTestcase
{
public static void main(String[] aa)
{
DesignationInterface designation = new Designation();
designation.setTitle(aa[0]);
try
{
DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();
designationManager.addDesignation(designation);
System.out.println("added l,");
}catch(BLException blException)
{
if(blException.hasGenericException()) System.out.println(blException.getGenericException());
List<String> properties  = blException.getProperties();
for(String propertie:properties)
{
System.out.println(blException.getException(propertie));
}
}//catch
}//main
}