package gov.ca.emsa.pulse.common.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QueryLocationMap {
	private Long id;
	private Long queryId;
	private Location location;
	private QueryLocationStatus status;
	private Date startDate;
	private Date endDate;
	private List<PatientRecord> results;
	
	public QueryLocationMap() {
		results = new ArrayList<PatientRecord>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getQueryId() {
		return queryId;
	}

	public void setQueryId(Long queryId) {
		this.queryId = queryId;
	}

	public QueryLocationStatus getStatus() {
		return status;
	}

	public void setStatus(QueryLocationStatus status) {
		this.status = status;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public List<PatientRecord> getResults() {
		return results;
	}

	public void setResults(List<PatientRecord> results) {
		this.results = results;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}