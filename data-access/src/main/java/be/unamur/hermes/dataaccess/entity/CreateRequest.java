package be.unamur.hermes.dataaccess.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CreateRequest {
    private Long requestTypeId;
    private String type;
    private Integer status;
    private Long citizen;
    private Long assignee;

    public String getType() {
	return type;
    }

    public Integer getStatus() {
	return status;
    }

    public Long getCitizen() {
	return citizen;
    }

    public Long getAssignee() {
	return assignee;
    }

    public void setType(String type) {
	this.type = type;
    }

    public void setStatus(Integer status) {
	this.status = status;
    }

    public void setCitizen(Long citizen) {
	this.citizen = citizen;
    }

    public void setAssignee(Long assignee) {
	this.assignee = assignee;
    }

    @JsonIgnore
    public Long getRequestTypeId() {
	return requestTypeId;
    }

    public void setRequestTypeId(Long requestTypeId) {
	this.requestTypeId = requestTypeId;
    }

}