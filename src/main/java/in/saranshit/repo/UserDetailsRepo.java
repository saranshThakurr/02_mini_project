package in.saranshit.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.saranshit.entity.UserDetails;

@Repository
public interface UserDetailsRepo extends JpaRepository<UserDetails, Integer>{

	public UserDetails findByEmail(String email);
	public UserDetails findByEmailAndPassword(String email,String password);
}
