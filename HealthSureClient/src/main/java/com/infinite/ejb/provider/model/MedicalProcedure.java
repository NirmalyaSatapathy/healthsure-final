package com.infinite.ejb.provider.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.infinite.ejb.insurance.model.Subscribe;
import com.infinite.ejb.recipient.model.Recipient;
import com.infinite.jsf.provider.model.ProcedureDailyLog;
import com.infinite.ejb.provider.model.Appointment;
import com.infinite.ejb.provider.model.Doctor;
import com.infinite.ejb.provider.model.Provider;

public class MedicalProcedure implements Serializable {

    private String procedureId;
    private Appointment appointment;
    private Recipient recipient;
    private Provider provider;
    private Doctor doctor;

    private Date scheduledDate;      // NEW: for procedures planned ahead
    private Date procedureDate;      // actual date
    private Date fromDate;           // long-term start
    private Date toDate;             // long-term end

    private String diagnosis;
    private String recommendations;
    private ProcedureType type;
    public ProcedureType getType() {
		return type;
	}

	public void setType(ProcedureType type) {
		this.type = type;
	}

	private ProcedureStatus procedureStatus;  // NEW: 'scheduled', 'in_progress', 'completed', 'cancelled'
    private Date createdAt;

    private Set<Claims> claims;
    private Set<Prescription> prescriptions;
    private Set<ProcedureDailyLog> logs;
    public Set<ProcedureDailyLog> getLogs() {
		return logs;
	}

	public void setLogs(Set<ProcedureDailyLog> logs) {
		this.logs = logs;
	}

	public MedicalProcedure() {
        this.appointment = new Appointment();
        this.recipient = new Recipient();
        this.provider = new Provider();
        this.doctor = new Doctor();
    }

    // Getters and Setters
    public String getProcedureId() {
        return procedureId;
    }

    public void setProcedureId(String procedureId) {
        this.procedureId = procedureId;
    }

    public Appointment getAppointment() {
        return appointment;
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public Recipient getRecipient() {
        return recipient;
    }

    public void setRecipient(Recipient recipient) {
        this.recipient = recipient;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(Date scheduledDate) {
        this.scheduledDate = scheduledDate;
    }

    public Date getProcedureDate() {
        return procedureDate;
    }

    public void setProcedureDate(Date procedureDate) {
        this.procedureDate = procedureDate;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getRecommendations() {
        return recommendations;
    }

    public void setRecommendations(String recommendations) {
        this.recommendations = recommendations;
    }

  

    public ProcedureStatus getProcedureStatus() {
		return procedureStatus;
	}

	public void setProcedureStatus(ProcedureStatus procedureStatus) {
		this.procedureStatus = procedureStatus;
	}

	public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Set<Claims> getClaims() {
        return claims;
    }

    public void setClaims(Set<Claims> claims) {
        this.claims = claims;
    }

    public Set<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(Set<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public String toString() {
        return "MedicalProcedure [" +
                "procedureId=" + procedureId +
                ", appointmentId=" + (appointment != null ? appointment.getAppointmentId() : null) +
                ", recipientId=" + (recipient != null ? recipient.gethId() : null) +
                ", providerId=" + (provider != null ? provider.getProviderId() : null) +
                ", doctorId=" + (doctor != null ? doctor.getDoctorId() : null) +
                ", scheduledDate=" + scheduledDate +
                ", procedureDate=" + procedureDate +
                ", fromDate=" + fromDate +
                ", toDate=" + toDate +
                ", diagnosis=" + diagnosis +
                ", recommendations=" + recommendations +
                ", procedureStatus=" + procedureStatus +
                ", createdAt=" + createdAt +
                ", claimsCount=" + (claims != null ? claims.size() : 0) +
                ", prescriptionsCount=" + (prescriptions != null ? prescriptions.size() : 0) +
                ", logsCount=" + (logs != null ? logs.size() : 0) +
                "]";
    }
}
