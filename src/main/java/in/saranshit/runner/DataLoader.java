package in.saranshit.runner;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.saranshit.entity.Courses;
import in.saranshit.entity.EnquiryStatus;
import in.saranshit.repo.CoursesRepo;
import in.saranshit.repo.EnquiryStatusRepo;

@Component
public class DataLoader implements ApplicationRunner{

	@Autowired
	private EnquiryStatusRepo EnqRepo;
	
	@Autowired
	private CoursesRepo CourseRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		CourseRepo.deleteAll();
		
		Courses c = new Courses();
		c.setCourses("Java");
		Courses c1 = new Courses();
		c1.setCourses("Python");
		Courses c2 = new Courses();
		c2.setCourses("AWS");
		
		List<Courses> course = Arrays.asList(c,c1,c2);
		CourseRepo.saveAll(course);
		
		
		EnqRepo.deleteAll();
		
		EnquiryStatus e=new EnquiryStatus();
		e.setEnquiryStatus("New");
		EnquiryStatus e1=new EnquiryStatus();
		e1.setEnquiryStatus("Enrolled");
		EnquiryStatus e2=new EnquiryStatus();
		e2.setEnquiryStatus("Lost");
		
		List<EnquiryStatus> status = Arrays.asList(e,e1,e2);
		EnqRepo.saveAll(status);
		
		
	}
}
