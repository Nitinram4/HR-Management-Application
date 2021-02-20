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

class EmployeeManagerAddTestcase
{
public static void main(String[] aa)
{
try
{
String name=  "ram";
DesignationInterface designation = new Designation();
designation.setCode(1);
designation.setTitle("manager");

Date dateOfBirth = new Date();
boolean isIndian = false;
BigDecimal basicSalary = new BigDecimal("21230");
String panNumber = "123123qweqwe";
String aadharCardNumber = "qweqweqweqwe";

EmployeeInterface employee;
employee = new Employee();
employee.setName(name);
employee.setDesignation(designation);
employee.setDateOfBirth(dateOfBirth);
employee.setGender(GENDER.MALE);
employee.setIsIndian(isIndian);
employee.setBasicSalary(basicSalary);
employee.setPANNumber(panNumber);
employee.setAadharCardNumber(aadharCardNumber);
EmployeeManagerInterface employeeManager = EmployeeManager.getEmployeeManager();
employeeManager.addEmployee(employee);
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