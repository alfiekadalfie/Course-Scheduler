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

public class CourseQueries {
    private static Connection connection;
    //private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addCourse;
    private static PreparedStatement getAllCourses;
    private static PreparedStatement getAllCourseCodes;
    private static PreparedStatement getAllSeats;
    private static PreparedStatement dropCourse;
    private static ResultSet resultSet;
    
    public static ArrayList<CourseEntry> getAllCourses(String semester){
        connection = DBConnection.getConnection();
        ArrayList<CourseEntry> allCourses = new ArrayList<CourseEntry>();
        try
        {
            getAllCourses = connection.prepareStatement("SELECT * FROM APP.COURSE WHERE SEMESTER = ?");
            getAllCourses.setString(1, semester);
            resultSet = getAllCourses.executeQuery();
            
            while(resultSet.next())
            {
                allCourses.add(new CourseEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return allCourses;
    }
    
    public static void addCourse(CourseEntry course){
        
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("INSERT INTO APP.COURSE (SEMESTER, COURSECODE, DESCRIPTION, SEATS) VALUES (?, ?, ?, ?)");
            addCourse.setString(1, course.getSemester());
            addCourse.setString(2, course.getCourseCode());
            addCourse.setString(3, course.getDescription());
            addCourse.setInt(4, course.getSeats());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    // might need to incorporate semester parameter
    public static ArrayList<String> getAllCourseCodes(String semester){
        connection = DBConnection.getConnection();
        ArrayList<String> allCourseCodes = new ArrayList<String>();
        
        try
        {
            getAllCourseCodes = connection.prepareStatement("SELECT COURSECODE FROM APP.COURSE WHERE SEMESTER = ?");
            getAllCourseCodes.setString(1, semester);
            resultSet = getAllCourseCodes.executeQuery();
            
            while(resultSet.next())
            {
                allCourseCodes.add(resultSet.getString(1));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return allCourseCodes;
    }
    
    public static int getCourseSeats(String semester, String courseCode){
        connection = DBConnection.getConnection();
        int courseSeats = 0;
        
        try
        {
            getAllSeats = connection.prepareStatement("SELECT SEATS FROM APP.COURSE WHERE SEMESTER = ? AND COURSECODE = ?");
            getAllSeats.setString(1, semester);
            getAllSeats.setString(2, courseCode);
            resultSet = getAllSeats.executeQuery();
            
            while(resultSet.next())
            {
                courseSeats = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courseSeats;
    }
    
    public static void dropCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try
        {
            dropCourse = connection.prepareStatement("DELETE FROM APP.COURSE WHERE SEMESTER = ? AND COURSECODE = ?");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, courseCode);
            dropCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
