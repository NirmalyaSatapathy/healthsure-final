<%@ taglib uri="http://java.sun.com/jsf/html" prefix="h" %>
<%@ taglib uri="http://java.sun.com/jsf/core" prefix="f" %>

<f:view>
<html>
<head>
    <title>Add Prescribed Medicine</title>

    <!-- Prevent HTML caching -->
    <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
    <meta http-equiv="Pragma" content="no-cache" />
    <meta http-equiv="Expires" content="0" />

    <script type="text/javascript">
        window.addEventListener('pageshow', function(event) {
            var navEntries = performance.getEntriesByType?.("navigation");
            var navType = navEntries?.length ? navEntries[0].type : "";
            if (event.persisted || navType === "back_forward") {
                document.querySelectorAll('.error').forEach(el => el.textContent = '');
            }
        });
    </script>

    <style>
        html, body {
            margin: 0;
            padding: 0;
            height: 100%;
            font-family: 'Segoe UI', sans-serif;
            background-color: #f4f8fb;
            overflow: hidden;
        }

        .page-wrapper {
            display: flex;
            flex-direction: column;
            height: 100vh;
        }

        .main-content {
            flex-grow: 1;
            padding: 10px;
            overflow-y: auto;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            margin-top: 90px;
        }

        .form-container {
            max-width: 600px;
            width: 100%;
            padding: 15px;
            background-color: #ffffff;
            border-radius: 10px;
            box-shadow: 0 0 10px #ccc;
        }

        .form-title {
            text-align: center;
            font-size: 22px;
            margin-bottom: 12px;
            color: #2a3f54;
        }

        .form-grid {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 8px;
        }

        .form-group {
            display: flex;
            flex-direction: column;
        }

        .form-group.full-width {
            grid-column: 1 / -1;
        }

        label {
            font-weight: bold;
            margin-bottom: 4px;
            font-size: 15px;
        }

        .form-control {
            width: 100%;
            padding: 6px;
            border-radius: 4px;
            border: 1px solid #ccc;
            box-sizing: border-box;
            font-size: 15px;
        }

        .error {
            display: block;
            color: #f44336 !important;
            font-size: 14px;
            font-weight: 600;
            margin-top: 3px;
        }

        .button-group {
            display: flex;
            justify-content: center;
            gap: 10px;
            margin-top: 10px;
        }

        .btn-add, .btn-reset, .action-button {
            padding: 8px 14px;
            font-size: 14px;
            border: none;
            border-radius: 5px;
            color: white;
            cursor: pointer;
        }

        .btn-add {
            background-color: #28a745;
        }

        .btn-add:hover {
            background-color: #218838;
        }

        .btn-reset {
            background-color: #007bff;
        }

        .btn-reset:hover {
            background-color: #0056b3;
        }

        .action-button {
            background-color: #6c757d;
        }

        .action-button:hover {
            background-color: #5a6268;
        }
    </style>
</head>
<body>
<div class="page-wrapper">
    <jsp:include page="/navbar/NavProvider.jsp" />

    <div class="main-content">
        <div class="form-container">
            <div class="form-title">Add Prescribed Medicine</div>

            <h:form prependId="false">
                <h:messages globalOnly="true" styleClass="error" />

                <div class="form-grid">
                    <div class="form-group">
                        <label for="prescribedId">Prescribed ID</label>
                        <h:inputText id="prescribedId" value="#{procedureController.prescribedMedicine.prescribedId}" readonly="true" styleClass="form-control" />
                        <h:message for="prescribedId" styleClass="error" />
                    </div>

                    <div class="form-group">
                        <label for="prescriptionId">Prescription ID</label>
                        <h:inputText id="prescriptionId" value="#{procedureController.prescription.prescriptionId}" readonly="true" styleClass="form-control" />
                        <h:message for="prescriptionId" styleClass="error" />
                    </div>

                    <div class="form-group">
                        <label for="medicineName">Medicine Name <span style="color:red">*</span></label>
                        <h:inputText id="medicineName" value="#{procedureController.prescribedMedicine.medicineName}" styleClass="form-control" />
                        <h:message for="medicineName" styleClass="error" />
                    </div>

                    <div class="form-group">
                        <label for="type">Medicine Type <span style="color:red">*</span></label>
                        <h:selectOneMenu id="type" value="#{procedureController.prescribedMedicine.type}" styleClass="form-control">
                            <f:selectItem itemLabel="-- Select Type --" itemValue="" />
                            <f:selectItem itemLabel="Tablet" itemValue="TABLET" />
                            <f:selectItem itemLabel="Syrup" itemValue="SYRUP" />
                            <f:selectItem itemLabel="Injection" itemValue="INJECTION" />
                            <f:selectItem itemLabel="Drop" itemValue="DROP" />
                        </h:selectOneMenu>
                        <h:message for="type" styleClass="error" />
                    </div>

                    <div class="form-group">
                        <label for="dosage">Dosage <span style="color:red">*</span></label>
                        <h:inputText id="dosage" value="#{procedureController.prescribedMedicine.dosage}" styleClass="form-control" />
                        <h:message for="dosage" styleClass="error" />
                    </div>

                    <div class="form-group">
                        <label for="duration">Duration (days) <span style="color:red">*</span></label>
                        <h:inputText id="duration" value="#{procedureController.prescribedMedicine.duration}" styleClass="form-control" />
                        <h:message for="duration" styleClass="error" />
                    </div>

                    <div class="form-group">
                        <label for="startDate">Start Date <span style="color:red">*</span></label>
                        <h:inputText id="startDate" value="#{procedureController.prescribedMedicine.startDate}" styleClass="form-control">
                            <f:convertDateTime pattern="yyyy-MM-dd" />
                        </h:inputText>
                        <h:message for="startDate" styleClass="error" />
                        <script>
                            const calendarInput = document.querySelector("#startDate");
                            if (calendarInput) {
                                calendarInput.setAttribute("type", "date");
                            }
                        </script>
                    </div>

                    <div class="form-group">
                        <label for="endDate">End Date <span style="color:red">*</span></label>
                        <h:inputText id="endDate" value="#{procedureController.prescribedMedicine.endDate}" styleClass="form-control">
                            <f:convertDateTime pattern="yyyy-MM-dd" />
                        </h:inputText>
                        <h:message for="endDate" styleClass="error" />
                        <script>
                            const calendarInput1 = document.querySelector("#endDate");
                            if (calendarInput1) {
                                calendarInput1.setAttribute("type", "date");
                            }
                        </script>
                    </div>

                    <div class="form-group full-width">
                        <label for="notes">Notes</label>
                        <h:inputTextarea id="notes" value="#{procedureController.prescribedMedicine.notes}" styleClass="form-control" />
                        <h:message for="notes" styleClass="error" />
                    </div>
                </div>

                <div class="button-group">
                    <h:commandButton value="Add Medicine" action="#{procedureController.addPresribedMedicinesController(procedureController.prescribedMedicine)}" styleClass="btn-add" />
                    <h:commandButton value="Reset Form" action="#{procedureController.createNewPrescribedMedicine()}" immediate="true" styleClass="btn-reset" />
                </div>

                <div class="button-group">
                    <h:commandButton value="Edit Prescription" action="AddPrescription?faces-redirect=true" styleClass="action-button" />
                    <h:commandButton value="Edit Procedure" action="#{procedureController.gotoProcedureForm()}"styleClass="action-button" />
          </div>
         
        </h:form>
         </div>
    </div>
    </div>
</body>
</html>
</f:view>