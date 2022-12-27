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

public class ScheduleQueries {
    private static Connection connection;
    //private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addSchedule;
    private static PreparedStatement studentSchedule;
    private static PreparedStatement scheduledStudentCount;
    private static PreparedStatement getAllScheduledStudents;
    private static PreparedStatement getAllWaitlistedStudents;
    private static PreparedStatement dropSchedule;
    private static PreparedStatement dropStudentSchedule;
    private static PreparedStatement updateSchedule;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry){
        connection = DBConnection.getConnection();
        try
        {
            addSchedule = connection.prepareStatement("INSERT INTO APP.SCHEDULE (SEMESTER, STUDENTID, COURSECODE, STATUS, TIMESTAMP) VALUES (?, ?, ?, ?, ?)");
            addSchedule.setString(1, entry.getSemester());
            addSchedule.setString(2, entry.getStudentID());
            addSchedule.setString(3, entry.getCourseCode());
            addSchedule.setString(4, entry.getStatus());
            addSchedule.setTimestamp(5, entry.getTimestamp());
            addSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> schedule = new ArrayList<ScheduleEntry>();
        
        try
        {
            studentSchedule = connection.prepareStatement("SELECT * FROM APP.SCHEDULE WHERE SEMESTER = ? and STUDENTID = ?");
            studentSchedule.setString(1, semester);
            studentSchedule.setString(2, studentID);
            resultSet = studentSchedule.executeQuery();
            
            while(resultSet.next())
            {
                schedule.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return schedule;
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode){
        connection = DBConnection.getConnection();
        int count = 0;
        
        try
        {
            scheduledStudentCount = connection.prepareStatement("SELECT count(STUDENTID) FROM APP.SCHEDULE WHERE SEMESTER = ? and COURSECODE = ?");
            scheduledStudentCount.setString(1, currentSemester);
            scheduledStudentCount.setString(2, courseCode);
            resultSet = scheduledStudentCount.executeQuery();
            
            while(resultSet.next())
            {
                count = resultSet.getInt(1);
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return count;
    }
    
    public static ArrayList<ScheduleEntry> getScheduledStudentsByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> scheduledStudents = new ArrayList<ScheduleEntry>();
        
        try {
            getAllScheduledStudents = connection.prepareStatement("SELECT * FROM APP.SCHEDULE WHERE SEMESTER = ? AND COURSECODE = ? AND STATUS = 'S'");
            getAllScheduledStudents.setString(1, semester);
            getAllScheduledStudents.setString(2, courseCode);
            

            resultSet = getAllScheduledStudents.executeQuery();
            
            while(resultSet.next())
            {
                scheduledStudents.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));
            }
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return scheduledStudents;
    }
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<ScheduleEntry> waitlistedStudents = new ArrayList<ScheduleEntry>();
        
        try {
            getAllWaitlistedStudents = connection.prepareStatement("SELECT * FROM APP.SCHEDULE WHERE SEMESTER = ? AND COURSECODE = ? AND STATUS = 'W' ORDER BY TIMESTAMP");
            getAllWaitlistedStudents.setString(1, semester);
            getAllWaitlistedStudents.setString(2, courseCode);
            

            resultSet = getAllWaitlistedStudents.executeQuery();
            
            while(resultSet.next())
            {
                waitlistedStudents.add(new ScheduleEntry(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getTimestamp(5)));
            }
        }
        
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return waitlistedStudents;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode){
        connection = DBConnection.getConnection();
        try
        {
            dropStudentSchedule = connection.prepareStatement("DELETE FROM APP.SCHEDULE WHERE SEMESTER = ? AND STUDENTID = ? AND COURSECODE = ?");
            dropStudentSchedule.setString(1, semester);
            dropStudentSchedule.setString(2, studentID);
            dropStudentSchedule.setString(3, courseCode);
            dropStudentSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void dropScheduleByCourse(String semester, String courseCode){
        connection = DBConnection.getConnection();
        try
        {
            dropSchedule = connection.prepareStatement("DELETE FROM APP.SCHEDULE WHERE SEMESTER = ? AND COURSECODE = ?");
            dropSchedule.setString(1, semester);
            dropSchedule.setString(2, courseCode);
            dropSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static void updateScheduleEntry(ScheduleEntry entry){
        // changes w to s
        
        connection = DBConnection.getConnection();
        try
        {
            updateSchedule = connection.prepareStatement("UPDATE APP.SCHEDULE SET STATUS = 'S' WHERE SEMESTER = ? AND STUDENTID = ? AND COURSECODE = ? AND TIMESTAMP = ?");
            updateSchedule.setString(1, entry.getSemester());
            updateSchedule.setString(2, entry.getStudentID());
            updateSchedule.setString(3, entry.getCourseCode());
            updateSchedule.setTimestamp(4, entry.getTimestamp());
            updateSchedule.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
}
