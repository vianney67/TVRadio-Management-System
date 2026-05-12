package model;
import java.io.Serializable;
public class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    
    // Plain DTO for client-side usage
    private int id;
    
    private String firstName;
    
    private String lastName;
    
    private String email;
    private String phone;
    private String role; 
    
    private User user;

    public Employee() {}

    public Employee(int id, String firstName, String lastName, String email, String phone, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
