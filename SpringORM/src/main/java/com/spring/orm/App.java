package com.spring.orm;

import java.io.*;
import java.util.List;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.spring.orm.dao.StudentDao;
import com.spring.orm.entities.Student;


public class App {
	
	static ApplicationContext context = new ClassPathXmlApplicationContext("springormcfg.xml");
	static StudentDao studentDao = context.getBean("studentDao", StudentDao.class);
	
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
    public static void main( String[] args ) {
    	System.out.println("\n*********Welcome To My ORM App*********");
    	
        boolean flag1 = true;
        
        while(flag1) {
        	System.out.println("\n**************************************");
        	System.out.println("Press 1: Add new student");
        	System.out.println("Press 2: Get a student");
        	System.out.println("Press 3: Get all students");
        	System.out.println("Press 4: Update a student");
        	System.out.println("Press 5: Delete a student");
        	System.out.println("Press 6: Exit\n");
        	
        	try {
        	int input = Integer.parseInt(br.readLine());
        	
        	switch (input) {
	        		case 1 : {
	        			int result = createStudent();
	        			System.out.println(result + " Student added successfully\n");
	        			break;
	        		}
	        		
	        		case 2 : {
	        			System.out.println(getStudent() +"\n");
	        			break;
	        		}
	        		
	        		case 3 : {
	        			List<Student> students = getAllStudents();
	        			for(Student s: students) {
	        				System.out.println(s);
	        			}
	        			System.out.println();
	        			break;
	        		}
	        		
	        		case 4 : {
	        			System.out.println("Note: Before updating the student, student should be available first in database");
					System.out.println("ID cannot be changed you can change Name and City only");
					updateStudent();
					break;	
	        		}
	        		
	        		case 5 : {
	        			deleteStudent();
	        			break;
	        		}
	        		
	        		case 6 : { 
	        			flag1 = false; 
	        			break;
	        		}
        		}
        	}
        	catch (Exception e) {
				System.out.println("Sorry, you entered wrong input!\n");
				e.getMessage();
			}
        }
        System.out.println("Thanks for using our ORM App!");
    }
    
    public static int createStudent() throws IOException {
    	System.out.print("Enter the ID of the student => ");
    	int studentId = Integer.parseInt(br.readLine());
    	System.out.print("Enter the Name of the student => ");
    	String studentName = br.readLine();
    	System.out.print("Enter the City of the student => ");
    	String studentCity = br.readLine();
    	
    	Student student = new Student(); 
    	student.setStudentId(studentId);
    	student.setStudentName(studentName);
    	student.setStudentCity(studentCity);
    	
    	return studentDao.create(student);
    }
    
    public static Student getStudent() throws NumberFormatException, IOException {
    	System.out.print("Enter the ID of the student to get its details => ");
    	int studentId = Integer.parseInt(br.readLine());
    
    	Student student = studentDao.read(studentId);
    	
    	return student;
    }
    
    public static List<Student> getAllStudents() {
    	return studentDao.readAll();
    }
    
    public static void updateStudent() throws NumberFormatException, IOException {
    	System.out.print("Enter the ID of the student which you want to update => ");
    	int studentId = Integer.parseInt(br.readLine());
    	String studentName = "";
    	String studentCity ="";
    	boolean[] checker = {false, false, false};
    	
    	boolean flag2 = true;
    	while(flag2) {
	    	System.out.println("Press 1: Change only Name");
	    	System.out.println("Press 2: Change only City");
	    	System.out.println("Press 3: Change both Name and City");
	    	System.out.println("Press 4: Don't update/ Save & exit");
	    	
	    	try {
		    	int noc = Integer.parseInt(br.readLine());
		    	
		    	switch(noc) {
		    		case 1: {
					System.out.print("Enter the Name of the student => ");
					studentName = br.readLine();
					System.out.println("Name of the student is updated successfully, now you can exit\n");
					System.out.println("If you want to update again, then you can update or else if you don't want you can save & exit by Pressing 4\n");
					checker[0] = true;
					break;
		    		}
		    		
		    		case 2: {
					System.out.print("Enter the City of the student => ");
					studentCity = br.readLine();
					System.out.println("City of the student is updated successfully, now you can exit\n");
					System.out.println("If you want to update again, then you can update or else if you don't want you can save & exit by Pressing 4\n");
					checker[1] = true;
					break;
		    		}
		    		
		    		case 3: {
		    			System.out.print("Enter the Name of the student => ");
		    	    		studentName = br.readLine();
		    			System.out.print("Enter the City of the student => ");
					studentCity = br.readLine();
					System.out.println("Name & City of the student is updated successfully!");
					System.out.println("If you want to update again, then you can update or else if you don't want you can save & exit by Pressing 4\n");
					checker[2] = true;
					break;
		    		}
		    		
		    		case 4: {
		    			flag2 = false;
		    			break;
		    		}
		    	}
	    	} catch (Exception e) {
				System.out.println("Sorry, you entered wrong input!\n");
			}
    	}
    	
    	studentDao.update(checker, studentId, studentName, studentCity);

    	System.out.println("Student updated successfully\n");
    }
    
    public static void deleteStudent() throws NumberFormatException, IOException {
    	System.out.print("Enter the ID of the student => ");
    	int studentId = Integer.parseInt(br.readLine());
    	
    	studentDao.delete(studentId);

    	System.out.println("Student deleted successfully\n");
    }
}
