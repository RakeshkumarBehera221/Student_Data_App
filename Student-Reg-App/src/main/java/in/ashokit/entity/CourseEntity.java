package in.ashokit.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;



@Entity
@Table(name="AIT_COURSES")
@Data
public class CourseEntity {
	
	@Id
	private Integer courseId;
	
	private String courseName;

}
