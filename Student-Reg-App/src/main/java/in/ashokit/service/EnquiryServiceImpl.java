package in.ashokit.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.binding.DashboardResponse;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.binding.EnquirySearchCriteria;
import in.ashokit.entity.CourseEntity;
import in.ashokit.entity.EnqStatusEntity;
import in.ashokit.entity.StudentEnqEntity;
import in.ashokit.entity.UserDtlsEntity;
import in.ashokit.repo.CourseRepo;
import in.ashokit.repo.EnqStatusRepo;
import in.ashokit.repo.StudentEnqRepo;
import in.ashokit.repo.UserDtlsRepo;

@Service
public class EnquiryServiceImpl implements EnquiryService {

	@Autowired
	private UserDtlsRepo userDtlsRepo;

	@Autowired
	private CourseRepo courseRepo;

	@Autowired
	private EnqStatusRepo statusRepo;

	@Autowired
	private StudentEnqRepo enqRepo;

	@Autowired
	private HttpSession session;

	@Override
	public DashboardResponse getDashboardData(Integer userId) {

		DashboardResponse response = new DashboardResponse();

		Optional<UserDtlsEntity> findById = userDtlsRepo.findById(userId);

		if (findById.isPresent()) {

			UserDtlsEntity userEntity = findById.get();

			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();
			Integer totalCnt = enquiries.size();

			Integer enrolledCnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("Enrolled"))
					.collect(Collectors.toList()).size();

			Integer lostCnt = enquiries.stream().filter(e -> e.getEnqStatus().equals("Lost"))
					.collect(Collectors.toList()).size();

			response.setTotalEnquriesCnt(totalCnt);
			response.setEnrolledCnt(enrolledCnt);
			response.setLostCnt(lostCnt);

		}

		return response;
	}

	@Override
	public List<String> getCourses() {
		// List<CourseEntity> findAll = courseRepo.findAll();//here all the course
		// entity we will get
		List<CourseEntity> findAll = courseRepo.findAll();

		List<String> names = new ArrayList<>();
		for (CourseEntity entity : findAll) {
			names.add(entity.getCourseName());
		}
		return names;
	}

	@Override
	public List<String> getEnqStatuses() {
		List<EnqStatusEntity> findAll = statusRepo.findAll();

		List<String> statusList = new ArrayList<>();
		for (EnqStatusEntity entity : findAll) {
			statusList.add(entity.getStatusName());
		}
		return statusList;
	}

	@Override
	public boolean saveEnquriry(EnquiryForm form) {
		StudentEnqEntity enqEntity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, enqEntity);

		Integer userId = (Integer) session.getAttribute("userId");
		UserDtlsEntity userEntity = userDtlsRepo.findById(userId).get();
		enqEntity.setUser(userEntity);

		enqRepo.save(enqEntity);

		return true;
	}

	@Override
	public List<StudentEnqEntity> getEnquries() {
		
	Integer userId	=(Integer)session.getAttribute("userId");
		
	Optional<UserDtlsEntity> findById=userDtlsRepo.findById(userId)	;
		
	if (findById.isPresent()) {
		UserDtlsEntity userDtlsEntity = findById.get();
		
		List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
		 
		return enquiries;
		
	}
		
		return null;
	}

	@Override
	public List<StudentEnqEntity> getFilteredEnqs(EnquirySearchCriteria criteria, Integer userId) {
		
		Optional<UserDtlsEntity> findById=userDtlsRepo.findById(userId)	;
		
		if (findById.isPresent()) {
			UserDtlsEntity userDtlsEntity = findById.get();
			
			List<StudentEnqEntity> enquiries = userDtlsEntity.getEnquiries();
			 
			//filter logic 
			

			if (null!=criteria.getCourseName() & !"".equals(criteria.getCourseName())) {
				
				enquiries = enquiries.stream()
						.filter(e ->e.getCourseName().equals(criteria.getCourseName()))
						.collect(Collectors.toList());
				
			}
			
			
			
			
			if (null!=criteria.getCourseName() & !"".equals(criteria.getEnqStatus())) {
				
				enquiries = enquiries.stream()
						.filter(e ->e.getEnqStatus().equals(criteria.getEnqStatus()))
						.collect(Collectors.toList());
				
			}
			

			if (null!=criteria.getClassMode() & !"".equals(criteria.getClassMode())) {
				
				enquiries = enquiries.stream()
						.filter(e ->e.getClassMode().equals(criteria.getClassMode()))
						.collect(Collectors.toList());
				
			}
			
			
			return enquiries;
		
		}
		return null;
	}

}
