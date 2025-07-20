package com.infinite.jsf.provider.controller;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.infinite.ejb.provider.model.Doctor;
import com.infinite.ejb.recipient.model.Recipient;
import com.infinite.jsf.insurance.model.SubscribedMember;
import com.infinite.jsf.provider.daoImpl.InsuranceDaoImpl;
import com.infinite.jsf.provider.daoImpl.ProviderDaoImpl;
import com.infinite.jsf.provider.model.PatientInsuranceDetails;

public class InsuranceController {
    private InsuranceDaoImpl insuranceDaoImpl;
    private ProviderDaoImpl providerDao;
    private String doctorId;
    private String healthId;
    private String patientName;
    private String matchType;
    private String sortField;
    private boolean ascending = true;
    private boolean showInsuranceTable;
    private boolean showInsuranceFlag = false;
    private boolean showPatientsFlag = false;
    private List<SubscribedMember> subscribedMembers;
    private List<PatientInsuranceDetails> patientInsuranceList;
    private List<Recipient> associatedPatients;
    private PatientInsuranceDetails selectedItem;
    private String selectedPatientId;
    private String topMessage;
    private boolean cameFromPatientSearch;
    private int insuranceFirst = 0;
    private int insurancePageSize = 3;

    private int patientFirst = 0;
    private int patientPageSize = 3;

    public int getInsuranceFirst() {
		return insuranceFirst;
	}

	public void setInsuranceFirst(int insuranceFirst) {
		this.insuranceFirst = insuranceFirst;
	}

	public int getInsurancePageSize() {
		return insurancePageSize;
	}

	public void setInsurancePageSize(int insurancePageSize) {
		this.insurancePageSize = insurancePageSize;
	}

	public int getPatientFirst() {
		return patientFirst;
	}

	public void setPatientFirst(int patientFirst) {
		this.patientFirst = patientFirst;
	}

	public int getPatientPageSize() {
		return patientPageSize;
	}

	public void setPatientPageSize(int patientPageSize) {
		this.patientPageSize = patientPageSize;
	}

	public int getMemberFirst() {
		return memberFirst;
	}

	public void setMemberFirst(int memberFirst) {
		this.memberFirst = memberFirst;
	}

	public int getMemberPageSize() {
		return memberPageSize;
	}

	public void setMemberPageSize(int memberPageSize) {
		this.memberPageSize = memberPageSize;
	}

	private int memberFirst = 0;
    private int memberPageSize = 3;
    public void nextInsurancePage() {
        if (insuranceFirst + insurancePageSize < getPatientInsuranceFullList().size()) {
            insuranceFirst += insurancePageSize;
        }
    }

    public void previousInsurancePage() {
        if (insuranceFirst - insurancePageSize >= 0) {
            insuranceFirst -= insurancePageSize;
        }
    }

    public boolean isNextInsuranceAvailable() {
        return insuranceFirst + insurancePageSize < getPatientInsuranceFullList().size();
    }

    public boolean isPreviousInsuranceAvailable() {
        return insuranceFirst > 0;
    }
    public void nextPatientPage() {
        if (patientFirst + patientPageSize < getAssociatedPatientsFullList().size()) {
            patientFirst += patientPageSize;
        }
    }

    public void previousPatientPage() {
        if (patientFirst - patientPageSize >= 0) {
            patientFirst -= patientPageSize;
        }
    }

    public boolean isNextPatientAvailable() {
        return patientFirst + patientPageSize < getAssociatedPatientsFullList().size();
    }

    public boolean isPreviousPatientAvailable() {
        return patientFirst > 0;
    }
    public void nextMemberPage() {
        if (memberFirst + memberPageSize < getSubscribedMembersFullList().size()) {
            memberFirst += memberPageSize;
        }
    }

    public void previousMemberPage() {
        if (memberFirst - memberPageSize >= 0) {
            memberFirst -= memberPageSize;
        }
    }

    public boolean isNextMemberAvailable() {
        return memberFirst + memberPageSize < getSubscribedMembersFullList().size();
    }

    public boolean isPreviousMemberAvailable() {
        return memberFirst > 0;
    }
    public List<PatientInsuranceDetails> getPaginatedInsuranceList() {
        if (patientInsuranceList == null) return Collections.emptyList();
        int toIndex = Math.min(insuranceFirst + insurancePageSize, patientInsuranceList.size());
        return patientInsuranceList.subList(insuranceFirst, toIndex);
    }

    private List<PatientInsuranceDetails> getPatientInsuranceFullList() {
        return patientInsuranceList == null ? Collections.emptyList() : patientInsuranceList;
    }
    public List<Recipient> getPaginatedAssociatedPatients() {
        if (associatedPatients == null) return Collections.emptyList();
        int toIndex = Math.min(patientFirst + patientPageSize, associatedPatients.size());
        return associatedPatients.subList(patientFirst, toIndex);
    }

    private List<Recipient> getAssociatedPatientsFullList() {
        return associatedPatients == null ? Collections.emptyList() : associatedPatients;
    }
    public List<SubscribedMember> getPaginatedSubscribedMembers() {
        if (subscribedMembers == null || subscribedMembers.isEmpty()) {
            return Collections.emptyList();  // Avoid NPE or division by 0
        }

        int toIndex = Math.min(memberFirst + memberPageSize, subscribedMembers.size());
        return subscribedMembers.subList(memberFirst, toIndex);
    }


    private List<SubscribedMember> getSubscribedMembersFullList() {
        return subscribedMembers;
    }
    public boolean isPatientHasNextPage() {
        return patientFirst + patientPageSize < (associatedPatients != null ? associatedPatients.size() : 0);
    }
    public boolean isInsuranceHasNextPage() {
        return insuranceFirst + insurancePageSize < (patientInsuranceList != null ? patientInsuranceList.size() : 0);
    }
    public boolean isMemberHasNextPage() {
        return subscribedMembers != null && memberFirst + memberPageSize < subscribedMembers.size();
    }




    public boolean isPatientHasPrevPage() {
        return patientFirst > 0;
    }

    private void resetPagination() {
        insuranceFirst = 0;
        patientFirst = 0;
        memberFirst = 0;
    }
    public int getAssociatedPatientsTotalPages() {
        int size = associatedPatients != null ? associatedPatients.size() : 0;
        return (int) Math.ceil((double) size / patientPageSize);
    }

    public int getAssociatedPatientsCurrentPage() {
        return (patientFirst / patientPageSize) + 1;
    }
    public int getInsuranceTotalPages() {
        int size = patientInsuranceList != null ? patientInsuranceList.size() : 0;
        return (int) Math.ceil((double) size / insurancePageSize);
    }

    public int getInsuranceCurrentPage() {
        return (insuranceFirst / insurancePageSize) + 1;
    }
    public int getMemberCurrentPage() {
        return (memberFirst / memberPageSize) + 1;
    }
    public int getMemberTotalPages() {
        int size = subscribedMembers != null ? subscribedMembers.size() : 0;
        return (int) Math.ceil((double) size / memberPageSize);
    }


    // Getters and Setters
    public InsuranceDaoImpl getInsuranceDaoImpl() {
        return insuranceDaoImpl;
    }

    public void setInsuranceDaoImpl(InsuranceDaoImpl insuranceDaoImpl) {
        this.insuranceDaoImpl = insuranceDaoImpl;
    }

    public ProviderDaoImpl getProviderDao() {
        return providerDao;
    }

    public void setProviderDao(ProviderDaoImpl providerDao) {
        this.providerDao = providerDao;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getHealthId() {
        return healthId;
    }

    public void setHealthId(String healthId) {
        this.healthId = healthId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getMatchType() {
        return matchType;
    }

    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public boolean isShowInsuranceTable() {
        return showInsuranceTable;
    }

    public void setShowInsuranceTable(boolean showInsuranceTable) {
        this.showInsuranceTable = showInsuranceTable;
    }

    public boolean isShowInsuranceFlag() {
        return showInsuranceFlag;
    }

    public void setShowInsuranceFlag(boolean showInsuranceFlag) {
        this.showInsuranceFlag = showInsuranceFlag;
    }

    public boolean isShowPatientsFlag() {
        return showPatientsFlag;
    }

    public void setShowPatientsFlag(boolean showPatientsFlag) {
        this.showPatientsFlag = showPatientsFlag;
    }

    public List<SubscribedMember> getSubscribedMembers() {
        return subscribedMembers;
    }

    public void setSubscribedMembers(List<SubscribedMember> subscribedMembers) {
        this.subscribedMembers = subscribedMembers;
    }

    public List<PatientInsuranceDetails> getPatientInsuranceList() {
        return patientInsuranceList;
    }

    public void setPatientInsuranceList(List<PatientInsuranceDetails> patientInsuranceList) {
        this.patientInsuranceList = patientInsuranceList;
    }

    public List<Recipient> getAssociatedPatients() {
        return associatedPatients;
    }

    public void setAssociatedPatients(List<Recipient> associatedPatients) {
        this.associatedPatients = associatedPatients;
    }

    public PatientInsuranceDetails getSelectedItem() {
        return selectedItem;
    }

    public void setSelectedItem(PatientInsuranceDetails selectedItem) {
        this.selectedItem = selectedItem;
    }

    public String getSelectedPatientId() {
        return selectedPatientId;
    }

    public void setSelectedPatientId(String selectedPatientId) {
        this.selectedPatientId = selectedPatientId;
    }

    public String getTopMessage() {
        return topMessage;
    }

    public void setTopMessage(String topMessage) {
        this.topMessage = topMessage;
    }

    public boolean isCameFromPatientSearch() {
        return cameFromPatientSearch;
    }

    public void setCameFromPatientSearch(boolean cameFromPatientSearch) {
        this.cameFromPatientSearch = cameFromPatientSearch;
    }

    // Business Methods
    public String handleSearch() {
        resetPagination();
        cameFromPatientSearch = true;
        insuranceDaoImpl = new InsuranceDaoImpl();
        providerDao = new ProviderDaoImpl();
        FacesContext context = FacesContext.getCurrentInstance();

        topMessage = null;
        patientInsuranceList = null;
        associatedPatients = null;
        showPatientsFlag = false;
        showInsuranceFlag = false;

        if (doctorId == null || doctorId.trim().isEmpty()) {
            FacesContext.getCurrentInstance().addMessage("doctorId",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter doctor id DOCXXX", null));
            return null;
        }

        if (!doctorId.matches("^[Dd][Oo][Cc]\\d{3}$")) {
            FacesContext.getCurrentInstance().addMessage("doctorId",
                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correct doctor id format DOCXXX", null));
            return null;
        }
        Doctor doctor = providerDao.searchDoctorById(doctorId);
        if (doctor == null) {
            context.addMessage("doctorId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Doctor with ID " + doctorId + " does not exist.", null));
            return null;
        }
        	if (matchType != null) {
        	    if (patientName == null || patientName.trim().isEmpty()) {
        	        FacesContext.getCurrentInstance().addMessage("matchType",
        	            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Enter patient name to search", null));
        	        return null;
        	    }
        	}
        if (healthId != null && !healthId.trim().isEmpty()) {
            cameFromPatientSearch = false;

            if (!healthId.matches("^[Hh]\\d{3}$")) {
                FacesContext.getCurrentInstance().addMessage("recipientId",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Correct Patient id format HXXX", null));
                return null;
            }
            Recipient recipient = providerDao.searchRecipientByHealthId(healthId);
            if (recipient == null) {
                context.addMessage("recipientId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Patient with Health ID " + healthId + " does not exist.", null));
                return null;
            }

            if (patientName != null && !patientName.trim().isEmpty()) {
                String cleaned = patientName.replaceAll("\\s+", "");
                if (cleaned.length() < 2) {
                    context.addMessage("patientName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Please enter at least 2 characters in the patient name.", null));
                    return null;
                }
                if (!patientName.matches("^[a-zA-Z0-9\\s]+$")) {
                    context.addMessage("patientName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Patient name can only contain letters, digits, and spaces.", null));
                    return null;
                }

                String fullName = (recipient.getFirstName() + recipient.getLastName()).toLowerCase().replaceAll("\\s+", "");
                String inputName = cleaned.toLowerCase();

                boolean match;
                if (matchType == null || matchType.trim().isEmpty()) {
                    match = fullName.equals(inputName);
                } else {
                    switch (matchType.toLowerCase()) {
                        case "startswith":
                            match = fullName.startsWith(inputName);
                            break;
                        case "contains":
                            match = fullName.contains(inputName);
                            break;
                        default:
                            match = false;
                    }
                }

                if (!match) {
                    context.addMessage("recipientId", new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Patient with ID " + healthId + " does not have a name that " +
                            (matchType != null ? matchType : "matches exactly") +
                            " '" + patientName + "'.", null));
                    return null;
                }
            }

            if (!providerDao.isDoctorPatientAssociatedByAppointment(doctorId, healthId)) {
                context.addMessage("recipientId", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Access denied: The doctor is not associated with this patient via an appointment.", null));
                return null;
            }

            patientInsuranceList = insuranceDaoImpl.showInsuranceOfRecipient(healthId);
            if (patientInsuranceList == null || patientInsuranceList.isEmpty()) {
                context.addMessage("recipientId", new FacesMessage(FacesMessage.SEVERITY_WARN,
                        "No insurance found for patient ID: " + healthId, null));
            } else {
                showInsuranceFlag = true;
            }

        } else if (patientName != null && !patientName.trim().isEmpty()) {
            String cleaned = patientName.replaceAll("\\s+", "");
            if (cleaned.length() < 2) {
                context.addMessage("patientName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Please enter at least 2 characters in the patient name.", null));
                return null;
            }
            if (!patientName.matches("^[a-zA-Z0-9\\s]+$")) {
                context.addMessage("patientName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                        "Patient name can only contain letters, digits, and spaces.", null));
                return null;
            }

            if (matchType == null || matchType.trim().isEmpty()) {
                associatedPatients = providerDao.searchPatientsByExactName(doctorId, patientName);
                if (associatedPatients == null || associatedPatients.isEmpty()) {
                    context.addMessage("patientName", new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "No exact match found for patient name '" + patientName + "' under Doctor ID '" + doctorId + "'. Please select a match type for partial search.", null));
                    return null;
                }
            } else {
                associatedPatients = providerDao.searchPatientsByName(doctorId, patientName, matchType);
                if (associatedPatients == null || associatedPatients.isEmpty()) {
                    String readableMatch = matchType.equalsIgnoreCase("startswith") ? "start with" : "contain";
                    context.addMessage("patientName", new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "No patients found under Doctor ID " + doctorId +
                            " whose name " + readableMatch + " '" + patientName + "'", null));
                    return null;
                }
            }

            showPatientsFlag = true;

        } else {
            associatedPatients = providerDao.getPatientListByDoctorId(doctorId);
            if (associatedPatients == null || associatedPatients.isEmpty()) {
                context.addMessage("doctorId", new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "No patients found for Doctor ID: " + doctorId, null));
            } else {
                showPatientsFlag = true;
            }
        }
        matchType=null;
        return null;
    }

    public String showInsuranceForPatient(String hId) {
        System.out.println("view members called from nested table for hid " + hId);
        patientInsuranceList = insuranceDaoImpl.showInsuranceOfRecipient(hId);
        
        if (patientInsuranceList == null || patientInsuranceList.isEmpty()) {
            topMessage = "No insurance found for patient ID: " + hId;
            showInsuranceFlag = false;
            showPatientsFlag = true;
        } else {
            topMessage = null;
            showInsuranceFlag = true;
            showPatientsFlag = false;
        }
        this.insuranceFirst=0;
        return null;
    }

    public String redirect(PatientInsuranceDetails insurance) {
        this.subscribedMembers = insurance.getSubscribedMembers();
        this.memberFirst = 0;

        if (this.subscribedMembers == null || this.subscribedMembers.isEmpty()) {
            this.topMessage = "No members added to this family plan.";
            return null;
        } else {
            this.topMessage = null; // Clear any previous message
        }

        return "viewMembers?faces-redirect=true&ts=" + System.currentTimeMillis();
    }


    public void sortBy(String listType, String field) {
        if (field.equals(sortField)) {
            ascending = !ascending;
        } else {
            sortField = field;
            ascending = true;
        }

        switch (listType) {
            case "insurance":
                insuranceFirst = 0;
                sortInsuranceList();
                break;
            case "members":
                memberFirst = 0;
                sortSubscribedMembers();
                break;
            case "patients":
                patientFirst = 0;
                sortAssociatedPatients();
                break;
        }
    }
    private void sortAssociatedPatients() {
        if (associatedPatients == null || sortField == null) return;

        Collections.sort(associatedPatients, (p1, p2) -> {
            try {
                Field f = p1.getClass().getDeclaredField(sortField);
                f.setAccessible(true);
                Comparable v1 = (Comparable) f.get(p1);
                Comparable v2 = (Comparable) f.get(p2);
                return ascending ? v1.compareTo(v2) : v2.compareTo(v1);
            } catch (Exception e) {
                return 0;
            }
        });
    }


    private void sortSubscribedMembers() {
        if (subscribedMembers == null || sortField == null) return;

        Collections.sort(subscribedMembers, (m1, m2) -> {
            try {
                Field f = m1.getClass().getDeclaredField(sortField);
                f.setAccessible(true);
                Comparable v1 = (Comparable) f.get(m1);
                Comparable v2 = (Comparable) f.get(m2);
                return ascending ? v1.compareTo(v2) : v2.compareTo(v1);
            } catch (Exception e) {
                return 0;
            }
        });
    }

    private void sortInsuranceList() {
        if (patientInsuranceList == null || sortField == null) return;

        Collections.sort(patientInsuranceList, (i1, i2) -> {
            try {
                Field f = i1.getClass().getDeclaredField(sortField);
                f.setAccessible(true);
                Object v1 = f.get(i1);
                Object v2 = f.get(i2);

                if (v1 == null || v2 == null) return 0;

                if (v1 instanceof Date && v2 instanceof Date) {
                    return ascending ? ((Date) v1).compareTo((Date) v2) : ((Date) v2).compareTo((Date) v1);
                } else if (v1 instanceof Comparable && v2 instanceof Comparable) {
                    return ascending ? ((Comparable) v1).compareTo(v2) : ((Comparable) v2).compareTo(v1);
                } else {
                    return 0;
                }
            } catch (Exception e) {
                return 0;
            }
        });
    }

    public String backToPatients() {
        patientInsuranceList = null;
        showInsuranceFlag = false;
        showPatientsFlag = true;
        topMessage = null;
        return null;
    }
    public String pullTopMessage() {
        String msg = this.topMessage;
        this.topMessage = null;
        return msg;
    }
    public String resetPage() {
        // Clear input fields
        this.doctorId = null;
        this.healthId = null;
        this.patientName = null;
        this.matchType = null;

        // Clear results and flags
        this.patientInsuranceList = null;
        this.associatedPatients = null;
        this.subscribedMembers = null;
        this.showPatientsFlag = false;
        this.showInsuranceFlag = false;
        this.cameFromPatientSearch = false;
        this.topMessage = null;

        // Reset pagination
        this.insuranceFirst = 0;
        this.patientFirst = 0;
        this.memberFirst = 0;

        // Clear sorting
        this.sortField = null;
        this.ascending = true;

        // Clear selected info
        this.selectedItem = null;
        this.selectedPatientId = null;
        // Redirect to same page (force reload)
        FacesContext.getCurrentInstance().getViewRoot().getChildren().clear();
        return "insuranceDetails?faces-redirect=true";
    }
    public String goToDashboard()
    {
        this.doctorId = null;
        this.healthId = null;
        this.patientName = null;
        this.matchType = null;

        // Clear results and flags
        this.patientInsuranceList = null;
        this.associatedPatients = null;
        this.subscribedMembers = null;
        this.showPatientsFlag = false;
        this.showInsuranceFlag = false;
        this.cameFromPatientSearch = false;
        this.topMessage = null;

        // Reset pagination
        this.insuranceFirst = 0;
        this.patientFirst = 0;
        this.memberFirst = 0;

        // Clear sorting
        this.sortField = null;
        this.ascending = true;

        // Clear selected info
        this.selectedItem = null;
        this.selectedPatientId = null;
        // Redirect to same page (force reload)
        FacesContext.getCurrentInstance().getViewRoot().getChildren().clear();
        return "ProviderDashboard?faces-redirect=true";
    }



}