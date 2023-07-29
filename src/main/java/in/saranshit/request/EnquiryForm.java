package in.saranshit.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class EnquiryForm {
	private String name;
	private Long phone;
	private String mode;
	private String courses;
	private String enquiryStatus;
	private LocalDate created;
	
}
