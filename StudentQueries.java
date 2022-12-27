/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Alfonsus Rahardjo
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentQueries {
    private static Connection connection;
    //private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addStudent;
    private static PreparedStatement getAllStudents;
    private static PreparedStatement getStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
        
    
    public static void addStudent(StudentEntry student){
   
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("INSERT INTO APP.STUDENT (STUDENTID, FIRSTNAME, LASTNAME) VALUES (?, ?, ?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<StudentEntry> getAllStudents(){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> allStudents = new ArrayList<StudentEntry>();
        
        try {
            getAllStudents = connection.prepareStatement("SELECT * FROM APP.STUDENT ORDER BY STUDENTID");

            resultSet = getAllStudents.executeQuery();
            
            while(resultSet.next())
            {
                allStudents.add(new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3)));
            }
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return allStudents;
    }
    
    public static StudentEntry getStudent(String studentID){
        connection = DBConnection.getConnection();
        String firstName = "";
        String lastName = "";
        
        try {
            getStudent = connection.prepareStatement("SELECT * FROM APP.STUDENT WHERE STUDENTID = ?");
            getStudent.setString(1, studentID);

            resultSet = getStudent.executeQuery();
            
            while(resultSet.next()){
                //entry = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
                firstName = resultSet.getString(2);
                lastName = resultSet.getString(3);
            }
            //entry = new StudentEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3));
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return new StudentEntry(studentID, firstName, lastName);
    }
    
    
    public static void dropStudent(String studentID){
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("DELETE FROM APP.STUDENT WHERE STUDENTID = ?");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
