package in.saranshit.service;

import java.util.List;


import in.saranshit.entity.StudentEnquiry;
import in.saranshit.request.DashboardResponse;
import in.saranshit.request.EnquiryForm;
import in.saranshit.request.EnquirySearchCriteria;

public interface EnquiryService {

	public DashboardResponse getDashboardData(Integer userId);

	public List<String> getCourses();

	public List<String> getEnqStatus();

	public boolean saveEnquiry(EnquiryForm enquiryForm);

	public List<StudentEnquiry> getEnquiry();
	
	public List<StudentEnquiry> getFilterData(EnquirySearchCriteria criteria , Integer userId);

	public StudentEnquiry editEnq(Integer stuId);
	
	public boolean updateEnquiry(EnquiryForm enquiryForm);

}
