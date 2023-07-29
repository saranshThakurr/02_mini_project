package in.saranshit.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.saranshit.entity.StudentEnquiry;

@Repository
public interface StudentEnquiryRepo extends JpaRepository<StudentEnquiry, Integer>{
	
	 
}
