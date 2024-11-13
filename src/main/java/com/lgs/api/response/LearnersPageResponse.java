package com.lgs.api.response;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class LearnersPageResponse {
	private int page;
	private int pageSize;
	private String searchBy;
	private String searchTerm;
	private String sortColumn;
	private int sortDirection;
	private Date startDate;
	private Date endDate;
	private List<LearnersResponse> learners;

	// Constructor
	public LearnersPageResponse(int page, int pageSize, String searchBy, String searchTerm, String sortColumn,
			int sortDirection, Date startDate, Date endDate, List<LearnersResponse> learners) {
		this.page = page;
		this.pageSize = pageSize;
		this.searchBy = searchBy;
		this.searchTerm = searchTerm;
		this.sortColumn = sortColumn;
		this.sortDirection = sortDirection;
		this.startDate = startDate;
		this.endDate = endDate;
		this.learners = learners;
	}

}
