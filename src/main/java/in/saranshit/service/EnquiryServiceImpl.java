package in.saranshit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.saranshit.entity.EnquiryStatus;
import in.saranshit.entity.StudentEnquiry;
import in.saranshit.entity.UserDetails;
import in.saranshit.repo.CoursesRepo;
import in.saranshit.repo.EnquiryStatusRepo;
import in.saranshit.repo.StudentEnquiryRepo;
import in.saranshit.repo.UserDetailsRepo;
import in.saranshit.request.DashboardResponse;
import in.saranshit.request.EnquiryForm;
import in.saranshit.request.EnquirySearchCriteria;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDetailsRepo userRepo;

	@Autowired
	private CoursesRepo courseRepo;

	@Autowired
	private EnquiryStatusRepo enqRepo;

	@Autowired
	private StudentEnquiryRepo stuRepo;

	@Autowired
	private HttpSession session;

	@Override
	public StudentEnquiry editEnq(Integer stuId) {

		Optional<StudentEnquiry> findById = stuRepo.findById(stuId);

		if (findById.isPresent()) {
			StudentEnquiry studentEnquiry = findById.get();
			return studentEnquiry;
		}

		return null;
	}

	@Override
	public List<StudentEnquiry> getFilterData(EnquirySearchCriteria criteria, Integer userId) {
		Optional<UserDetails> findById = userRepo.findById(userId);

		if (findById.isPresent()) {
			UserDetails userDetailsEntity = findById.get();
			List<StudentEnquiry> enquiries = userDetailsEntity.getEnquires();
			// filter logic
			if (null != criteria.getCourse() && "" != criteria.getCourse()) {
				enquiries = enquiries.stream().filter(e -> e.getCourses().equals(criteria.getCourse()))
						.collect(Collectors.toList());
			}

			if (null != criteria.getEnquiryStatus() && "" != criteria.getEnquiryStatus()) {
				enquiries = enquiries.stream().filter(e -> e.getEnquiryStatus().equals(criteria.getEnquiryStatus()))
						.collect(Collectors.toList());
			}
			if (null != criteria.getMode() && "" != criteria.getMode()) {
				enquiries = enquiries.stream().filter(e -> e.getMode().equals(criteria.getMode()))
						.collect(Collectors.toList());
			}

			return enquiries;
		}
		return null;
	}

	@Override
	public List<StudentEnquiry> getEnquiry() {

		Integer userId = (Integer) session.getAttribute("userId");
		Optional<UserDetails> findById = userRepo.findById(userId);
		if (findById.isPresent()) {
			UserDetails userDetails = findById.get();
			List<StudentEnquiry> enquires = userDetails.getEnquires();
			return enquires;
		}

		return null;

	}

	@Override
	public List<String> getCourses() {
		List<String> courses = courseRepo.getCourses();

		return courses;
	}

	@Override
	public List<String> getEnqStatus() {
		List<EnquiryStatus> findAll = enqRepo.findAll();
        List<String> statusList = new ArrayList<>();
		for (EnquiryStatus entity : findAll) {
	    statusList.add(entity.getEnquiryStatus());
		}
		return statusList;
	}

	@Override
	public boolean saveEnquiry(EnquiryForm enquiryForm) {
		StudentEnquiry enqEntity = new StudentEnquiry();
		BeanUtils.copyProperties(enquiryForm, enqEntity);
        Integer UserId = (Integer) session.getAttribute("userId");
        UserDetails userEntity = userRepo.findById(UserId).get();
		enqEntity.setUser(userEntity);
		  stuRepo.save(enqEntity);
		 
		return true;
	}

	@Override
	public boolean updateEnquiry(EnquiryForm enquiryForm) {
		if(null!=session.getAttribute("stuId")) {
			Integer stuId =(Integer)session.getAttribute("stuId");
			StudentEnquiry studentEnquiry = stuRepo.findById(stuId).get();
			BeanUtils.copyProperties(enquiryForm, studentEnquiry);
			session.removeAttribute("stuId"); 
			stuRepo.save(studentEnquiry);
			return true;
		}  
		return false;
	}
	
	@Override
	public DashboardResponse getDashboardData(Integer userId) {

		DashboardResponse response = new DashboardResponse();

		Optional<UserDetails> findById = userRepo.findById(userId);

		if (findById.isPresent()) {
			UserDetails userEntity = findById.get();
			List<StudentEnquiry> enquires = userEntity.getEnquires();
			Integer total = enquires.size();
			Integer enrolled = enquires.stream().filter(e -> e.getEnquiryStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			Integer lost = enquires.stream().filter(e -> e.getEnquiryStatus().equals("Lost"))
					.collect(Collectors.toList()).size();

			response.setTotalEnquiryCnt(total);
			response.setEnrolledCnt(enrolled);
			response.setLostCnt(lost);
		}

		return response;
	}

}
