<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<f:view>
<html>
<head>
  <meta charset="UTF-8">
  <title>Booked Appointments</title>
  <style>
    /* Base page and typography */
    body {
      font-family: 'Segoe UI', Arial, sans-serif;
      background-color: #f4f8fc;
      color: #2e3e50;
      margin: 0;
      padding: 20px;
    }

    h2 {
      color: #1E88E5;
      font-size: 28px;
      margin-bottom: 20px;
      border-bottom: 2px solid #BBDEFB;
      padding-bottom: 6px;
    }

    /* Form styling */
    .form-group {
      margin-bottom: 15px;
    }

    .form-group label {
      display: inline-block;
      width: 140px;
      font-weight: bold;
      color: #2E7D32;
    }

    .form-control {
      padding: 8px;
      width: 250px;
      font-size: 14px;
      border: 1px solid #90CAF9;
      border-radius: 4px;
      background-color: #ffffff;
      transition: border-color 0.3s ease;
    }

    .form-control:focus {
      outline: none;
      border-color: #1E88E5;
      box-shadow: 0 0 4px rgba(30, 136, 229, 0.3);
    }

    .error {
      display: block;
      color: #f44336;
      font-size: 14px;
      font-weight: 600;
      margin-top: 6px;
    }

    /* Button base */
    .btn {
      border: none;
      border-radius: 4px;
      padding: 8px 14px;
      font-size: 14px;
      cursor: pointer;
      transition: background-color 0.3s ease;
      margin-right: 10px;
    }

    /* Primary (Search) */
    .btn-primary {
      background-color: #1E88E5;
      color: #ffffff;
    }
    .btn-primary:hover {
      background-color: #1565C0;
    }

    /* Secondary (Reset) */
    .btn-secondary {
      background-color: #43A047;
      color: #ffffff;
    }
    .btn-secondary:hover {
      background-color: #2E7D32;
    }

    /* Tertiary (Dashboard) */
    .btn-tertiary {
      background-color: #ffffff;
      color: #1E88E5;
      border: 2px solid #1E88E5;
    }
    .btn-tertiary:hover {
      background-color: #e3f2fd;
    }

    /* Data table */
    .data-table {
      width: 100%;
      margin-top: 30px;
      border-collapse: collapse;
      box-shadow: 0 2px 8px rgba(0,0,0,0.08);
    }

    .data-table th {
      background-color: #1E88E5;
      color: #ffffff;
      font-weight: 600;
      padding: 10px;
      text-align: center;
      border: 1px solid #90CAF9;
    }

    .data-table td {
      background-color: #ffffff;
      color: #2e3e50;
      padding: 10px;
      text-align: center;
      border: 1px solid #cfd8dc;
      font-size: 15px;
    }

    .data-table tr:nth-child(even) {
      background-color: #e8f5e9;
    }

    .data-table tr:hover {
      background-color: #BBDEFB;
    }

    /* Pagination grid */
    .pagination-group {
      margin-top: 20px;
    }

    /* Responsive tweaks */
    @media (max-width: 600px) {
      h2 {
        font-size: 24px;
      }
      .form-control {
        width: 100%;
      }
      .data-table th,
      .data-table td {
        padding: 6px;
        font-size: 13px;
      }
    }
  </style>
</head>

<body>
  <h:form prependId="false">
    <h2>Search Booked Appointments</h2>

    <div class="form-group">
      <label for="doctorId"><span style="color: red;">*</span>Doctor ID:</label>
      <h:inputText id="doctorId"
                   value="#{procedureController.doctorId}"
                   styleClass="form-control" />
      <h:message for="doctorId" styleClass="error" />
    </div>

    <div class="form-group">
      <label for="appointmentId">Appointment ID (optional):</label>
      <h:inputText id="appointmentId"
                   value="#{procedureController.appointmentId}"
                   styleClass="form-control" />
      <h:message for="appointmentId" styleClass="error" />
    </div>

    <div class="form-group">
      <h:commandButton value="Search"
                       action="#{procedureController.fetchBookedAppointments()}"
                       styleClass="btn btn-primary" />

      <h:commandButton value="Reset"
                       action="#{procedureController.reset()}"
                       immediate="true"
                       styleClass="btn btn-secondary" />

      <h:commandButton value="Go to Dashboard"
                       action="#{procedureController.goToDashboard3()}"
                       styleClass="btn btn-tertiary" />
    </div>

    <h:panelGroup rendered="#{not empty procedureController.bookedAppointments}">
      <h:outputText value="Total: #{procedureController.allBookedAppointments.size()} booked appointments"
                    style="font-weight: bold; display: block; margin-top: 20px;" />

      <h:dataTable value="#{procedureController.bookedAppointments}"
                   var="appt"
                   styleClass="data-table">

        <!-- Appointment ID -->
        <h:column>
          <f:facet name="header">
            <h:commandLink action="#{procedureController.sortByBooked('appointmentId')}">
              Appointment ID
              <h:outputText styleClass="arrow"
                            value="#{procedureController.sortField eq 'appointmentId'
                                    ? (procedureController.sortAscending ? '↑' : '↓')
                                    : ''}" />
            </h:commandLink>
          </f:facet>
          <h:outputText value="#{appt.appointmentId}" />
        </h:column>

        <!-- Provider ID -->
        <h:column>
          <f:facet name="header">
            <h:commandLink action="#{procedureController.sortByBooked('providerId')}">
              Provider ID
              <h:outputText styleClass="arrow"
                            value="#{procedureController.sortField eq 'providerId'
                                    ? (procedureController.sortAscending ? '↑' : '↓')
                                    : ''}" />
            </h:commandLink>
          </f:facet>
          <h:outputText value="#{appt.provider.providerId}" />
        </h:column>

        <!-- Doctor ID -->
        <h:column>
          <f:facet name="header">
            <h:commandLink action="#{procedureController.sortByBooked('doctorId')}">
              Doctor ID
              <h:outputText styleClass="arrow"
                            value="#{procedureController.sortField eq 'doctorId'
                                    ? (procedureController.sortAscending ? '↑' : '↓')
                                    : ''}" />
            </h:commandLink>
          </f:facet>
          <h:outputText value="#{appt.doctor.doctorId}" />
        </h:column>

        <!-- Doctor Name -->
        <h:column>
          <f:facet name="header">
            <h:commandLink action="#{procedureController.sortByBooked('doctorName')}">
              Doctor Name
              <h:outputText styleClass="arrow"
                            value="#{procedureController.sortField eq 'doctorName'
                                    ? (procedureController.sortAscending ? '↑' : '↓')
                                    : ''}" />
            </h:commandLink>
          </f:facet>
          <h:outputText value="#{appt.doctor.doctorName}" />
        </h:column>

        <!-- Recipient ID -->
        <h:column>
          <f:facet name="header">
            <h:commandLink action="#{procedureController.sortByBooked('recipientId')}">
              Recipient ID
              <h:outputText styleClass="arrow"
                            value="#{procedureController.sortField eq 'recipientId'
                                    ? (procedureController.sortAscending ? '↑' : '↓')
                                    : ''}" />
            </h:commandLink>
          </f:facet>
          <h:outputText value="#{appt.recipient.hId}" />
        </h:column>

        <!-- User Name -->
        <h:column>
          <f:facet name="header">
            <h:commandLink action="#{procedureController.sortByBooked('userName')}">
              User Name
              <h:outputText styleClass="arrow"
                            value="#{procedureController.sortField eq 'userName'
                                    ? (procedureController.sortAscending ? '↑' : '↓')
                                    : ''}" />
            </h:commandLink>
          </f:facet>
          <h:outputText value="#{appt.recipient.userName}" />
        </h:column>

        <!-- Booked At -->
        <h:column>
          <f:facet name="header">
            <h:commandLink action="#{procedureController.sortByBooked('bookedAt')}">
              Booked At
              <h:outputText styleClass="arrow"
                            value="#{procedureController.sortField eq 'bookedAt'
                                    ? (procedureController.sortAscending ? '↑' : '↓')
                                    : ''}" />
            </h:commandLink>
          </f:facet>
          <h:outputText value="#{appt.bookedAt}">
            <f:convertDateTime pattern="yyyy-MM-dd HH:mm" />
          </h:outputText>
        </h:column>

        <!-- Status -->
        <h:column>
          <f:facet name="header">
            <h:commandLink action="#{procedureController.sortByBooked('status')}">
              Status
              <h:outputText styleClass="arrow"
                            value="#{procedureController.sortField eq 'status'
                                    ? (procedureController.sortAscending ? '↑' : '↓')
                                    : ''}" />
            </h:commandLink>
          </f:facet>
          <h:outputText value="#{appt.status}" />
        </h:column>

        <!-- Action -->
        <h:column>
          <f:facet name="header">
            <h:outputText value="Action" />
          </f:facet>
          <h:commandButton value="Add Procedure"
                           action="#{procedureController.selectedAppointment(appt)}"
                           styleClass="btn btn-primary" />
        </h:column>
      </h:dataTable>

      <h:panelGrid columns="5" cellpadding="10" styleClass="pagination-group">
        <h:commandButton value="First"
                         action="#{procedureController.goToFirstPage()}"
                         disabled="#{!procedureController.hasPreviousPage()}"
                         styleClass="btn btn-tertiary" />

        <h:commandButton value="Previous"
                         action="#{procedureController.previousPage()}"
                         disabled="#{!procedureController.hasPreviousPage()}"
                         styleClass="btn btn-tertiary" />

        <h:outputText value="Page #{procedureController.currentPage} of #{procedureController.totalPages}"
                      style="font-weight: bold; align-self: center;" />

        <h:commandButton value="Next"
                         action="#{procedureController.nextPage()}"
                         disabled="#{!procedureController.hasNextPage()}"
                         styleClass="btn btn-tertiary" />

        <h:commandButton value="Last"
                         action="#{procedureController.goToLastPage()}"
                         disabled="#{!procedureController.hasNextPage()}"
                         styleClass="btn btn-tertiary" />
      </h:panelGrid>
    </h:panelGroup>
  </h:form>
</body>
</html>
</f:view>