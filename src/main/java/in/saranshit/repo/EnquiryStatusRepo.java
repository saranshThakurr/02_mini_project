package in.saranshit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.saranshit.entity.EnquiryStatus;

@Repository
public interface EnquiryStatusRepo extends JpaRepository<EnquiryStatus, Integer>{
 
	
	public List<EnquiryStatus> findAll();

}
