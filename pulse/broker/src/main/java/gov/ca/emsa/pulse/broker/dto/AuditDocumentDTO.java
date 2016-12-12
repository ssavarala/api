package gov.ca.emsa.pulse.broker.dto;

import java.util.Date;

import gov.ca.emsa.pulse.broker.entity.AuditDocumentEntity;

public class AuditDocumentDTO {
	
	private Long id;
	
	private Long auditEventId;
	
	private Long participantObjectTypeCodeId;
	
	private ParticipantObjectTypeCodeDTO participantObjectTypeCode;
	
	private Long participantObjectTypeCodeRoleId;
	
	private ParticipantObjectTypeCodeRoleDTO participantObjectTypeCodeRole;
	
	private String participantObjectDataLifecycle;
	
	private String participantObjectIdTypeCode;
	
	private String participantObjectSensitivity;
	
	private String participantObjectId;
	
	private String participantObjectName;
	
	private String participantObjectQuery;
	
	private String participantObjectDetail;
	
	private String participantObjectDetail2;
	
	private Date creationDate;
	
	private Date lastModifiedDate;
	
	public AuditDocumentDTO(){
		
	}
	
	public AuditDocumentDTO(AuditDocumentEntity entity){
		this.auditEventId = entity.getAuditEventId();
		this.participantObjectTypeCodeId = entity.getParticipantObjectTypeCodeId();
		this.participantObjectTypeCodeRoleId = entity.getParticipantObjectTypeCodeRoleId();
		//this.participantObjectTypeCode = new ParticipantObjectTypeCodeDTO(entity.getParticipantObjectTypeCode());
		//this.participantObjectTypeCodeRole = new ParticipantObjectTypeCodeRoleDTO(entity.getParticipantObjectTypeCodeRole());
		this.participantObjectDataLifecycle = entity.getParticipantObjectDataLifecycle();
		this.participantObjectIdTypeCode = entity.getParticipantObjectIdTypeCode();
		this.participantObjectSensitivity = entity.getParticipantObjectSensitivity();
		this.participantObjectId = entity.getParticipantObjectId();
		this.participantObjectName = entity.getParticipantObjectName();
		this.participantObjectQuery = entity.getParticipantObjectQuery();
		this.participantObjectDetail = entity.getParticipantObjectDetail();
		this.participantObjectDetail2 = entity.getParticipantObjectDetail2();
		this.creationDate = entity.getCreationDate();
		this.lastModifiedDate = entity.getLastModifiedDate();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ParticipantObjectTypeCodeDTO getParticipantObjectTypeCode() {
		return participantObjectTypeCode;
	}

	public void setParticipantObjectTypeCode(
			ParticipantObjectTypeCodeDTO participantObjectTypeCode) {
		this.participantObjectTypeCode = participantObjectTypeCode;
	}

	public ParticipantObjectTypeCodeRoleDTO getParticipantObjectTypeCodeRole() {
		return participantObjectTypeCodeRole;
	}

	public void setParticipantObjectTypeCodeRole(
			ParticipantObjectTypeCodeRoleDTO participantObjectTypeCodeRole) {
		this.participantObjectTypeCodeRole = participantObjectTypeCodeRole;
	}

	public String getParticipantObjectDataLifecycle() {
		return participantObjectDataLifecycle;
	}

	public void setParticipantObjectDataLifecycle(
			String participantObjectDataLifecycle) {
		this.participantObjectDataLifecycle = participantObjectDataLifecycle;
	}

	public String getParticipantObjectIdTypeCode() {
		return participantObjectIdTypeCode;
	}

	public void setParticipantObjectIdTypeCode(String participantObjectIdTypeCode) {
		this.participantObjectIdTypeCode = participantObjectIdTypeCode;
	}

	public String getParticipantObjectSensitivity() {
		return participantObjectSensitivity;
	}

	public void setParticipantObjectSensitivity(String participantObjectSensitivity) {
		this.participantObjectSensitivity = participantObjectSensitivity;
	}

	public String getParticipantObjectId() {
		return participantObjectId;
	}

	public void setParticipantObjectId(String participantObjectId) {
		this.participantObjectId = participantObjectId;
	}

	public String getParticipantObjectName() {
		return participantObjectName;
	}

	public void setParticipantObjectName(String participantObjectName) {
		this.participantObjectName = participantObjectName;
	}

	public String getParticipantObjectQuery() {
		return participantObjectQuery;
	}

	public void setParticipantObjectQuery(String participantObjectQuery) {
		this.participantObjectQuery = participantObjectQuery;
	}

	public String getParticipantObjectDetail() {
		return participantObjectDetail;
	}

	public void setParticipantObjectDetail(String participantObjectDetail) {
		this.participantObjectDetail = participantObjectDetail;
	}

	public String getParticipantObjectDetail2() {
		return participantObjectDetail2;
	}

	public void setParticipantObjectDetail2(String participantObjectDetail2) {
		this.participantObjectDetail2 = participantObjectDetail2;
	}

	public Long getAuditEventId() {
		return auditEventId;
	}

	public void setAuditEventId(Long auditEventId) {
		this.auditEventId = auditEventId;
	}
	
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Long getParticipantObjectTypeCodeId() {
		return participantObjectTypeCodeId;
	}

	public void setParticipantObjectTypeCodeId(Long participantObjectTypeCodeId) {
		this.participantObjectTypeCodeId = participantObjectTypeCodeId;
	}

	public Long getParticipantObjectTypeCodeRoleId() {
		return participantObjectTypeCodeRoleId;
	}

	public void setParticipantObjectTypeCodeRoleId(
			Long participantObjectTypeCodeRoleId) {
		this.participantObjectTypeCodeRoleId = participantObjectTypeCodeRoleId;
	}
	
	
	
}
