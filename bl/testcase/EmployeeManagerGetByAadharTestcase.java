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

class EmployeeManagerGetByAadharTestcase
{
public static void main(String[] aa)
{
try
{
EmployeeManagerInterface employeeManager = EmployeeManager.getEmployeeManager();
EmployeeInterface employee = employeeManager.getByAadharNumber(aa[0]);

System.out.println(employee.getName());
DesignationInterface designation = employee.getDesignation();
System.out.println(designation.getCode());
System.out.println(designation.getTitle());

System.out.println(employee.getDateOfBirth());
System.out.println(employee.getGender());
System.out.println(employee.getIsIndian());
System.out.println(employee.getBasicSalary());
System.out.println(employee.getAadharCardNumber());
System.out.println(employee.getPANNumber());


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