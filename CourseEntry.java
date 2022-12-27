/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Alfonsus Rahardjo
 */
public class CourseEntry {
    private String semester;
    private String courseCode;
    private String description;
    private int seats;
    
    public CourseEntry(String semester, String courseCode, String description, int seats) {
        this.semester = semester;
        this.courseCode = courseCode;
        this.description = description;
        this.seats = seats;
    }
    
    public String getSemester(){
        return this.semester;
    }
    
    public String getCourseCode(){
        return this.courseCode;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public int getSeats(){
        return this.seats;
    }
        
}
