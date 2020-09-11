package com.mexel.frmk.job;

public class JobContext {
	
	public JobContext(Long companyId, Long userId){
		this.companyId = companyId;
		this.userId = userId;
	}
	
	public static final ThreadLocal<JobContext> thredLocal = new ThreadLocal<JobContext>();

	public static void set(JobContext context) {
		thredLocal.set(context);
	}

	public static void clear() {
		thredLocal.remove();
	}

	public static JobContext get() {
		return thredLocal.get();
	}

	private Long companyId;
	private Long userId;

	public Long getCompanyId() {
		return companyId;
	}

	public Long getUserId() {
		return userId;
	}
	
	public static JobContext create(Long companyId,Long userId){
		JobContext context = new JobContext(companyId, userId);
		set(context);
		return get();
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


}
