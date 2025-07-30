package com.infinite.jsf.provider.daoImpl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import com.infinite.jsf.insurance.model.Subscribe;
import com.infinite.jsf.insurance.model.SubscribedMember;
import com.infinite.jsf.insurance.model.SubscriptionStatus;
import com.infinite.jsf.insurance.model.SubscriptionType;
import com.infinite.jsf.provider.dao.InsuranceDao;
import com.infinite.jsf.provider.model.PatientInsuranceDetails;
import com.infinite.jsf.provider.model.RelatedPatientInsuranceDetails;

public class InsuranceDaoImpl implements InsuranceDao {

    static SessionFactory sessionFactory;
    static {
        sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
    }

    @Override
    public List<PatientInsuranceDetails> showInsuranceOfRecipient(String recipientId) {
        Session session = sessionFactory.openSession();
        session.clear();

        Query query = session.getNamedQuery("PatientInsuranceDetails.findByRecipientId");
        query.setParameter("hId", recipientId);
        List<Object[]> results = query.list();
        List<PatientInsuranceDetails> detailsList = new ArrayList<>();

        for (Object[] row : results) {
            // Enums: ensure DB has UPPERCASE values
            SubscriptionStatus status = SubscriptionStatus.valueOf(((String) row[8]).toUpperCase());

            // Only continue if status is ACTIVE
            if (status != SubscriptionStatus.ACTIVE) {
                continue;
            }

            SubscriptionType type = SubscriptionType.valueOf(((String) row[9]).toUpperCase());

            PatientInsuranceDetails details = new PatientInsuranceDetails();
            details.setSubscribeId((String) row[0]);      // subscribeId
            details.sethId((String) row[1]);              // hId
            details.setPatientName((String) row[2]);      // full name
            details.setCompanyName((String) row[3]);      // company
            details.setPlanName((String) row[4]);         // plan
            details.setEnrollmentDate((Date) row[5]);     // enrollment
            details.setCoverageStartDate((Date) row[6]);  // start date
            details.setCoverageEndDate((Date) row[7]);    // end date
            details.setCoverageStatus(status);
            details.setCoverageType(type);
            details.setCoverageLimit((Double) row[10]);
            details.setRemaining((Double) row[11]);
            details.setClaimed((Double) row[12]);
            details.setLastClaimDate((Date) row[13]);

            // Handle subscribed members for FAMILY type
            if (type == SubscriptionType.FAMILY) {
                Query memberQuery = session.getNamedQuery("SubscribedMember.findBySubscribeId");
                memberQuery.setParameter("subscribeId", details.getSubscribeId());

                List<SubscribedMember> memberRows = memberQuery.list();
                List<SubscribedMember> members = new ArrayList<>();

                for (SubscribedMember member : memberRows) {
                    Subscribe subscribe = new Subscribe();
                    subscribe.setSubscribeId(details.getSubscribeId());
                    member.setSubscribe(subscribe);
                    members.add(member);
                }

                System.out.println("printing members");
                System.out.println(members);
                details.setSubscribedMembers(members);
            } else {
                details.setSubscribedMembers(new ArrayList<>());
            }

            System.out.println("printing details for a single insurance");
            System.out.println(details);
            detailsList.add(details);
        }

        session.close();
        return detailsList;
    }
    public List<RelatedPatientInsuranceDetails> showRelatedInsuranceOfMember(String hId) {
        Session session = sessionFactory.openSession();
        session.clear();

        Query query = session.getNamedQuery("RelatedPatientInsuranceDetails.findByMemberId");
        query.setParameter("hId", hId);

        List<Object[]> results = query.list();
        List<RelatedPatientInsuranceDetails> detailsList = new ArrayList<>();

        for (Object[] row : results) {
            // Extract and normalize ENUM fields
            String statusRaw = (String) row[10];
            String typeRaw = (String) row[11];

            if (statusRaw == null || !statusRaw.equalsIgnoreCase("ACTIVE")) {
                continue; // Skip non-ACTIVE records
            }

            RelatedPatientInsuranceDetails details = new RelatedPatientInsuranceDetails();

            details.setSubscribeId((String) row[0]);          // subscribeId
            details.setMemberHId((String) row[1]);            // memberHId
            details.setMemberName((String) row[2]);           // memberName
            details.setMemberAge((Integer) row[3]);           // age
            details.setMemberGender((String) row[4]);         // gender
            details.setRelationWithProposer((String) row[5]); // relation
            details.setProposerName((String) row[6]);         // proposerName
            details.setEnrollmentDate((Date) row[7]);         // enrollment
            details.setCoverageStartDate((Date) row[8]);      // start
            details.setCoverageEndDate((Date) row[9]);        // end
            details.setCoverageStatus(statusRaw.toUpperCase());
            details.setCoverageType(typeRaw.toUpperCase());
            details.setCoverageLimit((Double) row[12]);
            details.setRemaining((Double) row[13]);
            details.setClaimed((Double) row[14]);
            details.setLastClaimDate((Date) row[15]);
            details.setPlanName((String) row[16]);
            details.setCompanyName((String) row[17]);

            detailsList.add(details);
        }

        session.close();
        return detailsList;
    }



}