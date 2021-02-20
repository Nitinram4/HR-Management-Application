import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import java.util.*;
class DesignationManagerGetAllTestcase
{
public static void main(String[] aa)
{
try
{
Set<DesignationInterface> d;
DesignationManagerInterface designationManager = DesignationManager.getDesignationManager();
d = designationManager.getDesignations();
d.forEach((designation)->{
System.out.println("Code : " + designation.getCode());
System.out.println("Title : "+ designation.getTitle());
});
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