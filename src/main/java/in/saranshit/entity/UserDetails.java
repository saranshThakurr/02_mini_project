package in.saranshit.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="User_Details")
public class UserDetails {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer userId;
private String Name;
private String email;
private Long phno;
private String accStatus;
private String password;

@OneToMany(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.EAGER)
private List<StudentEnquiry> enquires;


}
