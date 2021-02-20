package com.thinking.machines.hr.dl.dao;
import  com.thinking.machines.hr.dl.interfaces.dao.*;
import  com.thinking.machines.hr.dl.exceptions.*;
import  com.thinking.machines.enums.*;
import  com.thinking.machines.hr.dl.interfaces.dto.*;
import  com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.math.*;
import java.io.*;
import java.text.*;
public class EmployeeDAO implements EmployeeDAOInterface
{

private static String DATA_FILE = "employee.data";
public void add(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO == null) throw new DAOException("Employee is null");
String name = employeeDTO.getName();
if(name == null) throw new DAOException("Name is null");
name = name.trim();
if(name.length() == 0) throw new DAOException("Length of name is zero");
int designationCode = employeeDTO.getDesignationCode();
if(designationCode <= 0) throw new DAOException("Invalid designation code : "+designationCode);
DesignationDAOInterface designationDAO = new DesignationDAO();
if(!(designationDAO.codeExists(employeeDTO.getDesignationCode())))
{
throw new DAOException("Designation Code : " + employeeDTO.getDesignationCode() + " not exists.");
}

Date dateOfBirth = employeeDTO.getDateOfBirth();
if(dateOfBirth == null) throw new DAOException("date of birth is null");
char gender = employeeDTO.getGender();
if(gender == ' ') throw new DAOException("Gender not set to M/F");
boolean isIndian = employeeDTO.getIsIndian();
BigDecimal basicSalary = employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("basic salary is null");
if(basicSalary.signum() == -1) throw new DAOException("basic salary is (-)ve");
String panNumber = employeeDTO.getPANNumber();
if(panNumber == null) throw new DAOException("Pan number is null");
panNumber = panNumber.trim();
if(panNumber.length() == 0) throw new DAOException("Length of panNumber is zero");
String aadharCardNumber = employeeDTO.getAadharCardNumber();
if(aadharCardNumber == null) throw new DAOException("aadhar Card Number is null");
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0) throw new DAOException("Length of aadhar Card Number is zero");


try
{
File file = new File(DATA_FILE);
RandomAccessFile raf = new RandomAccessFile(file,"rw");
int recordCount = 0; // initial record value
String lastGeneratedEmployeeIdString = "";
String recordCountString = "";
if(raf.length() == 0)
{
lastGeneratedEmployeeIdString = "A" + 100000000;
while(lastGeneratedEmployeeIdString.length() < 11) lastGeneratedEmployeeIdString += " ";

recordCountString = "0";
while(recordCountString.length() < 10) recordCountString += " ";

raf.writeBytes(lastGeneratedEmployeeIdString + "\n");
raf.writeBytes(recordCountString + "\n");
}
else
{
lastGeneratedEmployeeIdString = raf.readLine().trim();
recordCountString = raf.readLine().trim();

recordCount = Integer.parseInt(recordCountString);
}
boolean panNumberExists = false,aadharCardNumberExists=false;
while(raf.getFilePointer() < raf.length())
{
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();
raf.readLine();
String fPANNumber = raf.readLine();
String fAadharCardNumber  = raf.readLine();
if(panNumberExists == false && fPANNumber.equalsIgnoreCase(panNumber))
{
panNumberExists = true;
}

if(aadharCardNumberExists == false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberExists = true;
}

if(panNumberExists == true && aadharCardNumberExists == true) break;
}
if(panNumberExists && aadharCardNumberExists)
{
raf.close();
throw new DAOException("Pan number ("+panNumber+") and aadhar card number("+aadharCardNumber+") exists.");
}
if(panNumberExists)
{
raf.close();
throw new DAOException("Pan number ("+panNumber+") exists.");
}
if(aadharCardNumberExists)
{
raf.close();
throw new DAOException("aadhar Card Number("+aadharCardNumber+") exists.");
}

int tmp = Integer.parseInt(lastGeneratedEmployeeIdString.substring(1).trim()) + 1;
lastGeneratedEmployeeIdString = "A" + tmp;
while(lastGeneratedEmployeeIdString.length() < 11) lastGeneratedEmployeeIdString += " ";
raf.writeBytes(lastGeneratedEmployeeIdString);
raf.writeBytes("\n");
raf.writeBytes(employeeDTO.getDesignationCode() + "\n");

employeeDTO.setEmployeeId(lastGeneratedEmployeeIdString);

raf.writeBytes(employeeDTO.getName() + "\n");

SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
raf.writeBytes(sdf.format(employeeDTO.getDateOfBirth()) + "\n");
raf.writeBytes(employeeDTO.getGender() + "\n");
raf.writeBytes(employeeDTO.getIsIndian() + "\n");
raf.writeBytes(employeeDTO.getBasicSalary() + "\n");

raf.writeBytes(employeeDTO.getPANNumber() + "\n");

raf.writeBytes(employeeDTO.getAadharCardNumber() + "\n");

raf.seek(0);
raf.writeBytes(lastGeneratedEmployeeIdString + "\n");
recordCountString = String.valueOf(recordCount + 1);
while(recordCountString.length() < 10) recordCountString += " ";
raf.writeBytes(String.valueOf(recordCountString) + "\n");
raf.close();
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//Add_ends

//_____________________________________________________________________________________________________________
public void update(EmployeeDTOInterface employeeDTO) throws DAOException
{
if(employeeDTO == null) throw new DAOException("Employee is null");
String employeeId = employeeDTO.getEmployeeId(); 
if(employeeId==null) throw new DAOException("EmployeeId is null");
employeeId = employeeId.trim();
if(employeeId.length()==0) throw new DAOException("Length employeeId is zero");
String name = employeeDTO.getName();
if(name == null) throw new DAOException("Name is null");
name = name.trim();
if(name.length() == 0) throw new DAOException("Length of name is zero");
int designationCode = employeeDTO.getDesignationCode();
if(designationCode <= 0) throw new DAOException("Invalid designation code : "+designationCode);
DesignationDAOInterface designationDAO = new DesignationDAO();
if(!(designationDAO.codeExists(employeeDTO.getDesignationCode())))
{
throw new DAOException("Designation Code : " + employeeDTO.getDesignationCode() + " not exists.");
}

Date dateOfBirth = employeeDTO.getDateOfBirth();

if(dateOfBirth == null) throw new DAOException("date of birth is null");
char gender = employeeDTO.getGender();
if(gender == ' ') throw new DAOException("Gender not set to M/F");
boolean isIndian = employeeDTO.getIsIndian();
BigDecimal basicSalary = employeeDTO.getBasicSalary();
if(basicSalary==null) throw new DAOException("basic salary is null");
if(basicSalary.signum() == -1) throw new DAOException("basic salary is (-)ve");
String panNumber = employeeDTO.getPANNumber();
if(panNumber == null) throw new DAOException("Pan number is null");
panNumber = panNumber.trim();
if(panNumber.length() == 0) throw new DAOException("Length of panNumber is zero");
String aadharCardNumber = employeeDTO.getAadharCardNumber();
if(aadharCardNumber == null) throw new DAOException("aadhar Card Number is null");
aadharCardNumber = aadharCardNumber.trim();
if(aadharCardNumber.length() == 0) throw new DAOException("Length of aadhar Card Number is zero");

try
{
File file = new File(DATA_FILE);
if(file.exists() == false) throw new DAOException("2 invalid id : " + employeeId);
RandomAccessFile raf = new RandomAccessFile(file,"rw");
if(raf.length() == 0)
{
raf.close();
throw new DAOException("Invalid Id : " + employeeId);
}

raf.readLine();
raf.readLine();
boolean panNumberFound = false,aadharCardNumberFound=false;
boolean employeeIdFound = false;
String panNumberFoundAgainstEmployeeId = "";
String aadharCardNumberFoundAgainstEmployeeId = "";
while(raf.getFilePointer() < raf.length())
{
String fEmployeeId = raf.readLine().trim();
for(int i=0;i<6;i++)raf.readLine();
String fPANNumber = raf.readLine();
String fAadharCardNumber  = raf.readLine();
if(employeeIdFound==false&&fEmployeeId.equalsIgnoreCase(employeeId))
{
employeeIdFound=true;
}

if(panNumberFound == false && fPANNumber.equalsIgnoreCase(panNumber))
{
panNumberFound = true;
panNumberFoundAgainstEmployeeId = employeeId; 
}

if(aadharCardNumberFound == false && fAadharCardNumber.equalsIgnoreCase(aadharCardNumber))
{
aadharCardNumberFound = true;
aadharCardNumberFoundAgainstEmployeeId = employeeId; 
}

if(employeeIdFound && panNumberFound && aadharCardNumberFound) break;
}

if(employeeIdFound == false)
{
raf.close();
throw new DAOException("Invalid employee Id : "+employeeId);
}

boolean panNumberExists = false;
if(panNumberFound && panNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
panNumberExists = true;
}

boolean aadharCardNumberExists = false;
if(aadharCardNumberFound && aadharCardNumberFoundAgainstEmployeeId.equalsIgnoreCase(employeeId)==false)
{
aadharCardNumberExists = true;
}

if(panNumberExists && aadharCardNumberExists)
{
raf.close();
throw new DAOException("Pan number ("+panNumber+") and aadhar card number("+aadharCardNumber+") exists.");
}
if(panNumberExists)
{
raf.close();
throw new DAOException("Pan number ("+panNumber+") exists.");
}
if(aadharCardNumberExists)
{
raf.close();
throw new DAOException("aadhar Card Number("+aadharCardNumber+") exists.");
}

raf.seek(0);
File tmp = new File("tmp.data");
if(tmp.exists()) tmp.delete();
RandomAccessFile tmpRaf = new RandomAccessFile(tmp,"rw");
tmpRaf.writeBytes(raf.readLine() + "\n");
tmpRaf.writeBytes(raf.readLine() + "\n");
while(raf.getFilePointer() < raf.length())
{
String femployeeId = raf.readLine().trim();
if(!employeeId.equalsIgnoreCase(femployeeId))
{
  

tmpRaf.writeBytes(femployeeId + "\n");
tmpRaf.writeBytes(raf.readLine() + "\n");
tmpRaf.writeBytes(raf.readLine() + "\n");
tmpRaf.writeBytes(raf.readLine() + "\n");
tmpRaf.writeBytes(raf.readLine() + "\n");
tmpRaf.writeBytes(raf.readLine() + "\n");
tmpRaf.writeBytes(raf.readLine() + "\n");
tmpRaf.writeBytes(raf.readLine() + "\n");
tmpRaf.writeBytes(raf.readLine() + "\n");
}
else
{

for(int i=0;i<8;i++) raf.readLine();
tmpRaf.writeBytes(employeeDTO.getEmployeeId() + "\n" );
tmpRaf.writeBytes(employeeDTO.getDesignationCode() + "\n" );
tmpRaf.writeBytes(employeeDTO.getName() + "\n" );

SimpleDateFormat sdf = new SimpleDateFormat();
tmpRaf.writeBytes(sdf.format(employeeDTO.getDateOfBirth()) + "\n" );

tmpRaf.writeBytes(employeeDTO.getGender() + "\n" );

tmpRaf.writeBytes(Boolean.toString(employeeDTO.getIsIndian()) + "\n" );


tmpRaf.writeBytes(employeeDTO.getBasicSalary().toString() + "\n" );

tmpRaf.writeBytes(employeeDTO.getPANNumber() + "\n" );
tmpRaf.writeBytes(employeeDTO.getAadharCardNumber() + "\n" );

}
}

raf.seek(0);
tmpRaf.seek(0);
while(tmpRaf.getFilePointer() < tmpRaf.length())
{
raf.writeBytes(tmpRaf.readLine() + "\n");
}
raf.setLength(tmpRaf.length());
tmpRaf.setLength(0);
raf.close();
tmpRaf.close();

}catch(IOException ioexception)
{
System.out.println("EmployeeDAO update Catch");
throw new DAOException(ioexception.getMessage());
}
}//update ends here

//_____________________________________________________________________________________________________________
public void delete(String employeeId) throws DAOException
{
String employeeID = employeeId.trim();
if(employeeID == "") throw new DAOException("1 invald code : " + employeeId);

try
{
File file = new File(DATA_FILE);
if(file.exists() == false) throw new DAOException("invalid id : " + employeeId);
RandomAccessFile raf = new RandomAccessFile(file,"rw");
if(raf.length() == 0)
{
raf.close();
throw new DAOException("Invalid Id : " + employeeId);
}
raf.readLine();
int recordCount = Integer.parseInt(raf.readLine().trim());
String fEmployeeId;
int x;
boolean isExists = false;
long foundAt = 0;
while(raf.getFilePointer() < raf.length())
{
foundAt = raf.getFilePointer();
fEmployeeId = raf.readLine().trim();
for(int i=0;i<8;i++) raf.readLine();
if(employeeID.equalsIgnoreCase(fEmployeeId))
{
isExists = true;
break;
}
}
 
if(isExists == false)
{
raf.close();
throw new DAOException("Invlaid Employee Code");
}
File tmp = new File("tmp.data");
if(tmp.exists()) tmp.delete();
RandomAccessFile tmpRaf = new RandomAccessFile(tmp,"rw");
while(raf.getFilePointer() < raf.length())
{
tmpRaf.writeBytes(raf.readLine() + "\n");
}
raf.seek(foundAt); 
tmpRaf.seek(0);
while(tmpRaf.getFilePointer() < tmpRaf.length())
{
raf.writeBytes(tmpRaf.readLine() + "\n");
}
raf.setLength(raf.getFilePointer());
recordCount--;
String recordCountString = String.format("%-10d",recordCount); 
raf.seek(0);
raf.readLine();
raf.writeBytes(recordCountString);
raf.close();
tmpRaf.setLength(0);
tmpRaf.close();
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//delete_ends

public Set<EmployeeDTOInterface> getall() throws DAOException
{
Set<EmployeeDTOInterface> designations = new TreeSet<>();
try
{
File file = new File(DATA_FILE);
if(file.exists() == false) return designations;
RandomAccessFile raf = new RandomAccessFile(file,"rw");
if(raf.length() == 0)
{
raf.close();
return designations;
}
raf.readLine();
raf.readLine();
EmployeeDTOInterface employeeDTO ;
char fGender;
while(raf.getFilePointer() < raf.length())
{
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId(raf.readLine());
employeeDTO.setDesignationCode(Integer.parseInt(raf.readLine()));
employeeDTO.setName(raf.readLine());
Date date=new SimpleDateFormat("dd/MM/yyyy").parse(raf.readLine());  
employeeDTO.setDateOfBirth(date);

fGender = raf.readLine().charAt(0);
if(fGender == 'M')
{ 
employeeDTO.setGender(GENDER.MALE);
}
else
{
employeeDTO.setGender(GENDER.FEMALE);
}
employeeDTO.setIsIndian(Boolean.parseBoolean(raf.readLine()));
BigDecimal basicSalary =new BigDecimal(raf.readLine());
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(raf.readLine());
employeeDTO.setAadharCardNumber(raf.readLine());
designations.add(employeeDTO);
}
raf.close();
return designations;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
catch(ParseException pexception)
{
throw new DAOException(pexception.getMessage());
}
}//getAll_ends

public Set<EmployeeDTOInterface> getByDesignationCode(int designationCode) throws DAOException
{
Set<EmployeeDTOInterface> designations = new TreeSet<>();
try
{
File file = new File(DATA_FILE);
if(file.exists() == false) return designations;
RandomAccessFile raf = new RandomAccessFile(file,"rw");
if(raf.length() == 0)
{
raf.close();
return designations;
}
raf.readLine();
raf.readLine();
EmployeeDTOInterface employeeDTO ;
while(raf.getFilePointer() < raf.length())
{
String fEmployeeId = raf.readLine();
int fDesignationCode = Integer.parseInt(raf.readLine());
String fName = raf.readLine();
Date fdate=new SimpleDateFormat("dd/MM/yyyy").parse(raf.readLine());
char fGender = raf.readLine().charAt(0);
boolean fIsIndian = Boolean.parseBoolean(raf.readLine());
BigDecimal fBasicSalary =new BigDecimal(raf.readLine());
String fPANNumber = raf.readLine();
String fAadharCardNumber = raf.readLine();
if(designationCode == fDesignationCode)
{
employeeDTO = new EmployeeDTO();
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setName(fName);
employeeDTO.setDateOfBirth(fdate);
if(fGender == 'M')
employeeDTO.setGender(GENDER.MALE);
else
employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
designations.add(employeeDTO);
}
}
raf.close();
return designations;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
catch(ParseException pexception)
{
throw new DAOException(pexception.getMessage());
}
}//getByDesignationCode_ends

public EmployeeDTOInterface getByEmployeeId(String employeeId) throws DAOException
{
if(employeeId.trim().length() == 0) throw new DAOException("length cant be 0");
try
{
EmployeeDTOInterface employeeDTO = new EmployeeDTO();

File file = new File(DATA_FILE);
RandomAccessFile raf = new RandomAccessFile(file,"rw");
if(raf.length() == 0)
{
raf.close();
throw new DAOException("file cant be opened");
}
raf.readLine();
raf.readLine();
char fGender;
while(raf.getFilePointer() < raf.length())
{
String fEmployeeId = raf.readLine().trim();
if(employeeId.equalsIgnoreCase(fEmployeeId))
{
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setDesignationCode(Integer.parseInt(raf.readLine()));
employeeDTO.setName(raf.readLine());
Date date=new SimpleDateFormat("dd/MM/yyyy").parse(raf.readLine());  
employeeDTO.setDateOfBirth(date);
fGender = raf.readLine().charAt(0);
if(fGender == 'M')
employeeDTO.setGender(GENDER.MALE);
else
employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(Boolean.parseBoolean(raf.readLine()));
BigDecimal basicSalary =new BigDecimal(raf.readLine());
employeeDTO.setBasicSalary(basicSalary);
employeeDTO.setPANNumber(raf.readLine());
employeeDTO.setAadharCardNumber(raf.readLine());


raf.close();
return employeeDTO;
}
for(int i=0;i<8;i++) raf.readLine();
}
throw new DAOException("Employee ID not found");

}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
catch(ParseException pe)
{
throw new DAOException(pe.getMessage());
}

}//getByEmployeeId_ends

public EmployeeDTOInterface getByPANNumber(String panNumber) throws DAOException
{
if(panNumber.trim().length() == 0) throw new DAOException("length cant be 0");
try
{
EmployeeDTOInterface employeeDTO = new EmployeeDTO();

File file = new File(DATA_FILE);
RandomAccessFile raf = new RandomAccessFile(file,"rw");
if(raf.length() == 0)
{
raf.close();
throw new DAOException("file cant be opened");
}
raf.readLine();
raf.readLine();
while(raf.getFilePointer() < raf.length())
{
String fEmployeeId = raf.readLine();
int fDesignationCode = Integer.parseInt(raf.readLine());
String fName = raf.readLine();
Date fdate=new SimpleDateFormat("dd/MM/yyyy").parse(raf.readLine());
char fGender = raf.readLine().charAt(0);
boolean fIsIndian = Boolean.parseBoolean(raf.readLine());
BigDecimal fBasicSalary =new BigDecimal(raf.readLine());
String fPANNumber = raf.readLine();
String fAadharCardNumber = raf.readLine();
if(panNumber.trim().equalsIgnoreCase(fPANNumber))
{
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setName(fName);
employeeDTO.setDateOfBirth(fdate);
employeeDTO.setGender((fGender == 'M')? GENDER.MALE:GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
raf.close();
return employeeDTO;
}
}
raf.close();
throw new DAOException("ID not found");
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
catch(ParseException pe)
{
throw new DAOException(pe.getMessage());
}
}//getByPANnumber_ends

public EmployeeDTOInterface getByAadharNumber(String aadharNumber) throws DAOException
{
if(aadharNumber.trim().length() == 0) throw new DAOException("length cant be 0");
try
{
EmployeeDTOInterface employeeDTO = new EmployeeDTO();
File file = new File(DATA_FILE);
RandomAccessFile raf = new RandomAccessFile(file,"rw");
if(raf.length() == 0)
{
raf.close();
throw new DAOException("file cant be opened");
}
raf.readLine();
raf.readLine();
while(raf.getFilePointer() < raf.length())
{
String fEmployeeId = raf.readLine();
int fDesignationCode = Integer.parseInt(raf.readLine());
String fName = raf.readLine();
Date fdate=new SimpleDateFormat("dd/MM/yyyy").parse(raf.readLine());
char fGender = raf.readLine().charAt(0);
boolean fIsIndian = Boolean.parseBoolean(raf.readLine());
BigDecimal fBasicSalary =new BigDecimal(raf.readLine());
String fPANNumber = raf.readLine();
String fAadharCardNumber = raf.readLine();
if(aadharNumber.trim().equalsIgnoreCase(fAadharCardNumber))
{
employeeDTO.setEmployeeId(fEmployeeId);
employeeDTO.setDesignationCode(fDesignationCode);
employeeDTO.setName(fName);
employeeDTO.setDateOfBirth(fdate);
if(fGender == 'M')
employeeDTO.setGender(GENDER.MALE);
else
employeeDTO.setGender(GENDER.FEMALE);
employeeDTO.setIsIndian(fIsIndian);
employeeDTO.setBasicSalary(fBasicSalary);
employeeDTO.setPANNumber(fPANNumber);
employeeDTO.setAadharCardNumber(fAadharCardNumber);
raf.close();
return employeeDTO;
}
}
raf.close();
throw new DAOException("ID not found");
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
catch(ParseException pe)
{
throw new DAOException(pe.getMessage());
}
}//getByAadharNumber_ends

public boolean employeeIdExists(String employeeId) throws DAOException
{
if(employeeId.trim().length() == 0) throw new DAOException("Employee Id cant be of 0 length");
try
{
File file = new File(DATA_FILE);
RandomAccessFile raf = new RandomAccessFile(file,"rw");
if(raf.length() == 0)
{
raf.close();
return false; //false likhna h
}
raf.readLine();
raf.readLine();
while(raf.getFilePointer() < raf.length())
{
//String employeeID1 = raf.readLine();
if(employeeId.equalsIgnoreCase(raf.readLine().trim()))
{
raf.close();
return true;
}
for(int x=0;x<8;x++) raf.readLine();
}
raf.close();
return false;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//EmployeeIDExists_ends

public boolean panNumberExists(String panNumber) throws DAOException
{
if(panNumber == null) throw new DAOException("Aadhar Number is null");
if(panNumber.trim().length() == 0) throw new DAOException("Aadhar Number cant be of 0 length");
try
{
File file = new File(DATA_FILE);
RandomAccessFile raf = new RandomAccessFile(file,"rw");
if(raf.length() == 0)
{
raf.close();
throw new DAOException("file is empty");
}
raf.readLine();
raf.readLine();
while(raf.getFilePointer() < raf.length())
{
for(int x=0;x<7;x++) raf.readLine();
String PANNumber = raf.readLine();
if(panNumber.equalsIgnoreCase(PANNumber))
{
raf.close();
return true;
}
raf.readLine();
}
raf.close();
return false;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//panNumberExists_ends

public boolean aadharNumberExists(String aadharNumber) throws DAOException
{
if(aadharNumber == null) throw new DAOException("Aadhar Number is null");
if(aadharNumber.trim().length() == 0) throw new DAOException("Aadhar Number cant be of 0 length");
try
{
File file = new File(DATA_FILE);
RandomAccessFile raf = new RandomAccessFile(file,"rw");
if(raf.length() == 0)
{
raf.close();
throw new DAOException("file is empty");
}
raf.readLine();
raf.readLine();
while(raf.getFilePointer() < raf.length())
{
for(int x=0;x<8;x++) raf.readLine();
String AadharCardNumber = raf.readLine();
if(aadharNumber.equalsIgnoreCase(AadharCardNumber))
{
raf.close();
return true;
}
}
raf.close();
return false;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//aadharNumberExists_ends

public int getCount() throws DAOException
{
try
{
File file = new File(DATA_FILE);
RandomAccessFile raf = new RandomAccessFile(file,"rw");
if(raf.length() == 0)
{
raf.close();
return 0;
}
raf.readLine();
raf.readLine();
int count = 0;
while(raf.getFilePointer() < raf.length())
{
for(int x=0;x<9;x++) raf.readLine();
count++;
}
raf.close();
return count;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//getCount_ends

public int getCountByDesignation(int designationCode) throws DAOException
{
try
{
File file = new File(DATA_FILE);
RandomAccessFile raf = new RandomAccessFile(file,"rw");
if(raf.length() == 0)
{
raf.close();
return 0;
}
raf.readLine();
raf.readLine();
int count = 0;
while(raf.getFilePointer() < raf.length())
{
raf.readLine();
if(designationCode == Integer.parseInt(raf.readLine()))
count++;
for(int x=0;x<7;x++) raf.readLine();
}
raf.close();
return count;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//getByDesignationCode_ends

public boolean isDesignationAlloted(int designationCode) throws DAOException
{
try
{
File file = new File(DATA_FILE);
RandomAccessFile raf = new RandomAccessFile(file,"rw");
if(raf.length() == 0)
{
raf.close();
return false;
}
raf.readLine();
raf.readLine();
while(raf.getFilePointer() < raf.length())
{
raf.readLine();
if(designationCode == Integer.parseInt(raf.readLine()))
return true;
for(int x=0;x<7;x++) raf.readLine();
}
raf.close();
return false;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//isDesignationAlloted

}