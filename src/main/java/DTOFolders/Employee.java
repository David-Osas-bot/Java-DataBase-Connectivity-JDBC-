/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTOFolders;

/**
 *
 * @author DELL
 */
public class Employee {
    private String firstname;
    private String lastname;
    private int age;            
    private String id;

    
    public Employee(){
        
    }
    
    public Employee(String firstname, String lastname, int age, String id){
        this.firstname = firstname;
        this.lastname = lastname;
        this.age = age;
        this.id = id;
    }
    
    public String getFirstname(){
        return this.firstname;
    }
    
    public void setFirstname(String firstname){
        this.firstname = firstname;
    }
    
    public String getLastname(){
        return this.lastname;
    }
    
    public void setLastname(String lastname){
        this.lastname = lastname;
    }
    
    public int getAge(){
        return this.age;
    }
    
    public void setAge(int age){
        this.age = age;
    }
    
    public String getId(){
        return this.id;
    }
    
    public void setId(String id){
        this.id = id;
    }
}