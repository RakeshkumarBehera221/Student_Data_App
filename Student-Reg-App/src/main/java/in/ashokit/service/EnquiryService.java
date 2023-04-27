package in.ashokit.service;

import java.util.List;

import in.ashokit.binding.DashboardResponse;
import in.ashokit.binding.EnquiryForm;
import in.ashokit.binding.EnquirySearchCriteria;

public interface EnquiryService {
	
	public List<String> getCourseName();
	
	public List<String> getEnqstatus();
	
	public DashboardResponse geDashboardResponse(Integer userId);
	
    public String addEnquiry(EnquiryForm form); 	 

    public List<EnquiryForm> getEnquries(Integer userId, EnquirySearchCriteria criteria);
    
    public EnquiryForm getEnquiry(Integer enqId);
}
  