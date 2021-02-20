import com.thinking.machines.hr.bl.interfaces.pojo.*;
import com.thinking.machines.hr.bl.interfaces.managers.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.enums.*;
import java.io.*;
import java.util.*;
import java.math.*;
import java.text.*;

class EmployeeManagerRemoveTestcase
{
public static void main(String[] aa)
{
try
{
EmployeeManagerInterface employeeManager = EmployeeManager.getEmployeeManager();
employeeManager.removeEmployee(aa[0]);
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