package in.saranshit.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.saranshit.entity.Courses;

@Repository
public interface CoursesRepo extends JpaRepository<Courses,Integer>{

	@Query("select courses from Courses")
	public List<String>  getCourses();
}
