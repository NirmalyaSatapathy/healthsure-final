<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core" %>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html" %>

<f:view>
<html>
<head>
    <meta charset="UTF-8">
    <title>Subscribed Family Members</title>
    <style>
        /* Base page styling */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f4f8fc;
            color: #2e3e50;
            margin: 0;
            padding: 20px;
        }

        h2 {
            text-align: center;
            color: #1E88E5;
            font-size: 28px;
            margin-bottom: 20px;
            border-bottom: 2px solid #BBDEFB;
            padding-bottom: 6px;
        }

        /* Table styling */
        .data-table {
            width: 100%;
            border-collapse: collapse;
            background-color: #ffffff;
            box-shadow: 0 2px 8px rgba(0,0,0,0.08);
            margin-top: 10px;
        }
        .data-table th {
            background-color: #1E88E5;
            color: #ffffff;
            font-weight: 600;
            padding: 12px;
            text-align: left;
            border: 1px solid #90CAF9;
        }
        .data-table td {
            border: 1px solid #cfd8dc;
            padding: 10px;
            font-size: 15px;
            text-align: left;
        }
        .data-table tr:nth-child(even) {
            background-color: #e8f5e9;
        }
        .data-table tr:hover {
            background-color: #BBDEFB;
        }

        /* Button base */
        .btn {
            border: none;
            border-radius: 4px;
            padding: 6px 12px;
            font-size: 14px;
            cursor: pointer;
            transition: background-color 0.3s ease;
            margin: 0 4px;
        }
        .btn-primary {
            background-color: #1E88E5;
            color: #ffffff;
        }
        .btn-primary:hover {
            background-color: #1565C0;
        }
        .btn-tertiary {
            background-color: #ffffff;
            color: #1E88E5;
            border: 2px solid #1E88E5;
        }
        .btn-tertiary:hover {
            background-color: #e3f2fd;
        }

        /* Pagination controls */
        .pagination-controls {
            margin-top: 15px;
            text-align: center;
        }

        /* Back button container */
        .back-button {
            margin-top: 20px;
            text-align: center;
        }

        @media (max-width: 600px) {
            h2 {
                font-size: 24px;
            }
            .data-table th,
            .data-table td {
                font-size: 13px;
                padding: 6px;
            }
            .btn {
                width: 100%;
                margin: 6px 0;
            }
        }
    </style>
</head>
<body>
<h2>Subscribed Family Members</h2>

<h:form prependId="false">
    <h:outputText value="Total members: #{insuranceController.subscribedMembers.size()}"
                  style="font-weight: bold; display: block; margin-bottom: 10px;" />

    <h:dataTable value="#{insuranceController.getPaginatedSubscribedMembers()}"
                 var="member"
                 styleClass="data-table">

        <h:column>
            <f:facet name="header">
                <h:commandLink value="Member ID"
                               styleClass="sort-header"
                               action="#{insuranceController.sortBy('members', 'memberId')}" />
            </f:facet>
            <h:outputText value="#{member.memberId}" />
        </h:column>

        <h:column>
            <f:facet name="header">
                <h:commandLink value="Full Name"
                               styleClass="sort-header"
                               action="#{insuranceController.sortBy('members', 'fullName')}" />
            </f:facet>
            <h:outputText value="#{member.fullName}" />
        </h:column>

        <h:column>
            <f:facet name="header">
                <h:commandLink value="Age"
                               styleClass="sort-header"
                               action="#{insuranceController.sortBy('members', 'age')}" />
            </f:facet>
            <h:outputText value="#{member.age}" />
        </h:column>

        <h:column>
            <f:facet name="header">
                <h:commandLink value="Gender"
                               styleClass="sort-header"
                               action="#{insuranceController.sortBy('members', 'gender')}" />
            </f:facet>
            <h:outputText value="#{member.gender}" />
        </h:column>

        <h:column>
            <f:facet name="header">
                <h:commandLink value="Relation"
                               styleClass="sort-header"
                               action="#{insuranceController.sortBy('members', 'relationWithProposer')}" />
            </f:facet>
            <h:outputText value="#{member.relationWithProposer}" />
        </h:column>

        <h:column>
            <f:facet name="header">
                <h:commandLink value="Aadhar No"
                               styleClass="sort-header"
                               action="#{insuranceController.sortBy('members', 'aadharNo')}" />
            </f:facet>
            <h:outputText value="#{member.aadharNo}" />
        </h:column>
    </h:dataTable>

    <!-- Pagination Controls -->
    <div class="pagination-controls">
        <h:commandButton value="First"
                         action="#{insuranceController.setMemberFirst(0)}"
                         disabled="#{insuranceController.memberFirst == 0}"
                         styleClass="btn btn-tertiary" />

        <h:commandButton value="Previous"
                         action="#{insuranceController.previousMemberPage()}"
                         disabled="#{insuranceController.memberFirst == 0}"
                         styleClass="btn btn-tertiary" />

        <h:outputText value="Page #{insuranceController.memberCurrentPage} of #{insuranceController.memberTotalPages}"
                      style="margin: 0 12px; font-weight:bold;" />

        <h:commandButton value="Next"
                         action="#{insuranceController.nextMemberPage()}"
                         disabled="#{!insuranceController.isMemberHasNextPage()}"
                         styleClass="btn btn-tertiary" />

        <h:commandButton value="Last"
                         action="#{insuranceController.setMemberFirst(insuranceController.subscribedMembers.size() - 
                                  (insuranceController.subscribedMembers.size() mod insuranceController.memberPageSize))}"
                         disabled="#{!insuranceController.isMemberHasNextPage()}"
                         styleClass="btn btn-tertiary" />
    </div>

    <!-- Back Button -->
    <div class="back-button">
        <h:commandButton value="Back to Insurance Details"
                         action="showInsuranceDetails"
                         styleClass="btn btn-primary" />
    </div>

</h:form>

</body>
</html>
</f:view>