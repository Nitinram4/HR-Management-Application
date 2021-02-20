import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
class DesignationManagerCodeExistsTestcase
{
public static void main(String[] aa)
{
try
{
DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();

System.out.println("Code " + aa[0] +" Exists : " + designationManager.designationCodeExists(Integer.parseInt(aa[0])));
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