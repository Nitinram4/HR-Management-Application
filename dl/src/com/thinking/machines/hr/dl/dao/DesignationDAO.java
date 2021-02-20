package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.dao.*;
import com.thinking.machines.hr.dl.interfaces.dto.*;
import java.io.*;
import java.util.*;
public class DesignationDAO implements DesignationDAOInterface
{
private String DATA_FILE = "designation.data";

public void add(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO == null) throw new DAOException("Designation is  null");
String title = designationDTO.getTitle();
if(title == null) throw new DAOException("Designation is  null");
title = title.trim();
if(title.length() == 0) throw new DAOException("length of designation is 0");
try
{
File file = new File(DATA_FILE);
RandomAccessFile RAF = new RandomAccessFile(file,"rw");
int lastGeneratedCode = 0; // initial code value
int recordCount = 0; // initial record value
String lastGeneratedCodeString = "";
String recordCountString = "";
if(RAF.length()==0)
{
lastGeneratedCodeString = "0";
while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString += " ";

recordCountString = "0";
while(recordCountString.length()<10) recordCountString += " ";

RAF.writeBytes(lastGeneratedCodeString);
RAF.writeBytes("\n");
RAF.writeBytes(recordCountString);
RAF.writeBytes("\n");
}
else
{
lastGeneratedCodeString = RAF.readLine().trim(); // isko ab int is convert krenge to trim krna jruri h ni to space bhi convert ho jaega
recordCountString = RAF.readLine().trim();

lastGeneratedCode = Integer.parseInt(lastGeneratedCodeString);
recordCount = Integer.parseInt(recordCountString);
}
int fCode;
String fTitle;


while(RAF.getFilePointer()<RAF.length())
{
fCode = Integer.parseInt(RAF.readLine());
fTitle = RAF.readLine();
if(fTitle.equalsIgnoreCase(title))
{
RAF.close();
throw new DAOException("Designation : " + title + " exists.");
}
}



int code = lastGeneratedCode + 1;
RAF.writeBytes(String.valueOf(code));
RAF.writeBytes("\n");
RAF.writeBytes(title);
RAF.writeBytes("\n");
designationDTO.setCode(code);


lastGeneratedCode++;
recordCount++;
lastGeneratedCodeString = String.valueOf(lastGeneratedCode);
while(lastGeneratedCodeString.length()<10) lastGeneratedCodeString += " ";
recordCountString = String.valueOf(recordCount);
while(recordCountString.length()<10) recordCountString += " ";

RAF.seek(0);
RAF.writeBytes(lastGeneratedCodeString);
RAF.writeBytes("\n");
RAF.writeBytes(recordCountString);
RAF.writeBytes("\n");
RAF.close();
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}//try-catch_ends

}//Add_ends

public void update(DesignationDTOInterface designationDTO) throws DAOException
{
if(designationDTO == null) throw new DAOException("Designation is  null");
int Code = designationDTO.getCode();
if(Code <= 0) throw new DAOException("invald code : " + Code);
String title = designationDTO.getTitle();
if(title == null) throw new DAOException("Designation is  null");
title = title.trim();
if(title.length() == 0) throw new DAOException("length of designation is 0");

try
{
File file = new File(DATA_FILE);
if(file.exists() == false) throw new DAOException("invalid code : " + Code);
RandomAccessFile RAF = new RandomAccessFile(file,"rw");
if(RAF.length() == 0)
{
RAF.close();
throw new DAOException("invalid code : " + Code);
}
int fCode = 0;
String fTitle = "";
RAF.readLine();
RAF.readLine();
boolean found = false;
while(RAF.getFilePointer() < RAF.length())
{
fCode = Integer.parseInt(RAF.readLine());
if(fCode == Code)
{
found = true;
break;
}
RAF.readLine();
}
if(found == false)
{
RAF.close();
throw new DAOException("invalid code : " + Code);
}
RAF.seek(0);
RAF.readLine();
RAF.readLine();
while(RAF.getFilePointer() < RAF.length())
{
fCode = Integer.parseInt(RAF.readLine());
fTitle = RAF.readLine();
if(fCode != Code && title.equalsIgnoreCase(fTitle) == true)
{
RAF.close();
throw new DAOException("Title : " + title + " exists.");
}
}
File tmp = new File("tmp.data");
if(tmp.exists()) tmp.delete();
RandomAccessFile tmpRAF = new RandomAccessFile(tmp,"rw");
RAF.seek(0);
tmpRAF.writeBytes(RAF.readLine());
tmpRAF.writeBytes("\n");
tmpRAF.writeBytes(RAF.readLine());
tmpRAF.writeBytes("\n");
while(RAF.getFilePointer() < RAF.length())
{
fCode = Integer.parseInt(RAF.readLine());
fTitle = RAF.readLine();
if(Code != fCode)
{
tmpRAF.writeBytes(String.valueOf(fCode));
tmpRAF.writeBytes("\n");
tmpRAF.writeBytes(fTitle);
tmpRAF.writeBytes("\n");
}else
{
tmpRAF.writeBytes(String.valueOf(Code));
tmpRAF.writeBytes("\n");
tmpRAF.writeBytes(title);
tmpRAF.writeBytes("\n");
}
}
RAF.seek(0);
tmpRAF.seek(0);
while(tmpRAF.getFilePointer() < tmpRAF.length())
{
RAF.writeBytes(tmpRAF.readLine());
RAF.writeBytes("\n");
}
RAF.setLength(tmpRAF.length());
tmpRAF.setLength(0);
RAF.close();
tmpRAF.close();
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//update_ends

public void delete(int code) throws DAOException
{
int Code = code;
if(Code <= 0) throw new DAOException("invald code : " + Code);
try
{
File file = new File(DATA_FILE);
if(file.exists() == false) throw new DAOException("invalid code : " + Code);
RandomAccessFile RAF = new RandomAccessFile(file,"rw");
if(RAF.length() == 0)
{
RAF.close();
throw new DAOException("invalid code : " + Code);
}
int fCode = 0;
String fTitle = "";
RAF.readLine();
RAF.readLine();
boolean found = false;
while(RAF.getFilePointer() < RAF.length())
{
fCode = Integer.parseInt(RAF.readLine());
fTitle = RAF.readLine();
if(fCode == Code)
{
found = true;
break;
}
}
if(found == false)
{
RAF.close();
throw new DAOException("invalid code : " + Code);
}
if(new EmployeeDAO().isDesignationAlloted(Code)) 
{
RAF.close();
throw new DAOException("Employees exists with designation : " + fTitle);
}
RAF.seek(0);
File tmp = new File("tmp.data");
if(tmp.exists()) tmp.delete();
RandomAccessFile tmpRAF = new RandomAccessFile(tmp,"rw");
 
tmpRAF.writeBytes(RAF.readLine());
tmpRAF.writeBytes("\n");

int recordCount = Integer.parseInt(RAF.readLine().trim());
recordCount--;
String recordCount1 = String.valueOf(recordCount);
while(recordCount1.length() < 10) recordCount1 += " ";

tmpRAF.writeBytes(recordCount1);
tmpRAF.writeBytes("\n");
while(RAF.getFilePointer() < RAF.length())
{
fCode = Integer.parseInt(RAF.readLine());
fTitle = RAF.readLine();
if(Code != fCode)
{
tmpRAF.writeBytes(String.valueOf(fCode));
tmpRAF.writeBytes("\n");
tmpRAF.writeBytes(fTitle);
tmpRAF.writeBytes("\n");
}
}
RAF.seek(0);
tmpRAF.seek(0);
while(tmpRAF.getFilePointer() < tmpRAF.length())
{
RAF.writeBytes(tmpRAF.readLine());
RAF.writeBytes("\n");
}
RAF.setLength(tmpRAF.length());
tmpRAF.setLength(0);
RAF.close();
tmpRAF.close();
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//delete_ends

public Set<DesignationDTOInterface> getall() throws DAOException
{
Set<DesignationDTOInterface> designations = new TreeSet<>();
try
{
File file = new File(DATA_FILE);
if(file.exists() == false) return designations;
RandomAccessFile RAF = new RandomAccessFile(file,"rw");
if(RAF.length() == 0)
{
RAF.close();
return designations;
}
RAF.readLine();
RAF.readLine();
DesignationDTOInterface designationDTO;
while(RAF.getFilePointer() < RAF.length())
{
designationDTO = new DesignationDTO();
designationDTO.setCode(Integer.parseInt(RAF.readLine()));
designationDTO.setTitle(RAF.readLine());
designations.add(designationDTO);
}
RAF.close();
return designations;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//set_ends

public DesignationDTOInterface getByCode(int code) throws DAOException
{
if(code <= 0) throw new DAOException("Invalid code : " + code);
try
{
File file = new File(DATA_FILE);
if(file.exists() == false) throw new DAOException("invalid code : " + code);
RandomAccessFile RAF = new RandomAccessFile(file,"rw");
if(RAF.length() == 0)
{
RAF.close();
throw new DAOException("invalid code : " + code);
}
RAF.readLine();
int recordCount = Integer.parseInt(RAF.readLine().trim());
if(recordCount == 0)
{
RAF.close();
throw new DAOException("invalid code : " + code);
}
int fCode = 0;
String fTitle = "";
while(RAF.getFilePointer() < RAF.length())
{
fCode = Integer.parseInt(RAF.readLine());
fTitle = RAF.readLine();
if(fCode == code)
{
RAF.close();
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
RAF.close();
throw new DAOException("invalid code : " + code);
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//getByCode_ends

public DesignationDTOInterface getByTitle(String title) throws DAOException
{
if(title == null || title.trim().length() == 0) throw new DAOException("invalid title : " + title);
title = title.trim();
try
{
File file = new File(DATA_FILE);
if(file.exists() == false) throw new DAOException("invalid title : " + title);
RandomAccessFile RAF = new RandomAccessFile(file,"rw");
if(RAF.length() == 0)
{
RAF.close();
throw new DAOException("invalid title : " + title);
}

RAF.readLine();
int recordCount = Integer.parseInt(RAF.readLine().trim());
if(recordCount == 0)
{
RAF.close();
throw new DAOException("invalid title : " + title);
}
int fCode = 0;
String fTitle = "";
while(RAF.getFilePointer() < RAF.length())
{
fCode = Integer.parseInt(RAF.readLine());
fTitle = RAF.readLine();
if(title.equalsIgnoreCase(fTitle))
{
RAF.close();
DesignationDTOInterface designationDTO;
designationDTO = new DesignationDTO();
designationDTO.setCode(fCode);
designationDTO.setTitle(fTitle);
return designationDTO;
}
}
RAF.close();
throw new DAOException("invalid title : " + title);
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//getByTitle_ends

public boolean codeExists(int code) throws DAOException
{
if(code <= 0) return false;
try
{
File file = new File(DATA_FILE);
if(file.exists() == false) return false;
RandomAccessFile RAF = new RandomAccessFile(file,"rw");
if(RAF.length() == 0)
{
RAF.close();
return false;
}

RAF.readLine();
int recordCount = Integer.parseInt(RAF.readLine().trim());
if(recordCount == 0)
{
RAF.close();
return false;
}
int fCode = 0;
while(RAF.getFilePointer() < RAF.length())
{
fCode = Integer.parseInt(RAF.readLine());
RAF.readLine();
if(fCode == code)
{
RAF.close();
return true;
}
}
RAF.close();
return false;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//codeExists_ends

public boolean titleExists(String title) throws DAOException
{
if(title == null || title.trim().length() == 0) return false;
title = title.trim();
try
{
File file = new File(DATA_FILE);
if(file.exists() == false) return false;
RandomAccessFile RAF = new RandomAccessFile(file,"rw");
if(RAF.length() == 0)
{
RAF.close();
return false;
}

RAF.readLine();
int recordCount = Integer.parseInt(RAF.readLine().trim());
if(recordCount == 0)
{
RAF.close();
return false;
}
String fTitle ="";
while(RAF.getFilePointer() < RAF.length())
{
Integer.parseInt(RAF.readLine());
fTitle = RAF.readLine();
if(title.equalsIgnoreCase(fTitle))
{
RAF.close();
return true;
}
}
RAF.close();
return false;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//titleExists_ends

public int getCount() throws DAOException
{
try
{
File file = new File(DATA_FILE);
if(file.exists() == false) return 0;
RandomAccessFile RAF = new RandomAccessFile(file,"rw");
if(RAF.length() == 0)
{
RAF.close();
return 0;
}
RAF.readLine();
int count = 0;
count = Integer.parseInt(RAF.readLine().trim());
RAF.close();
return count;
}catch(IOException ioexception)
{
throw new DAOException(ioexception.getMessage());
}
}//getCount_ends

}//dao_ends