package com.kencorhealth.campaign.db.handler;

import com.kencorhealth.campaign.dm.annotations.Exportable;
import com.kencorhealth.campaign.dm.clinic.Member;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.dm.input.MemberInput;
import java.util.List;

@Exportable
public interface MemberHandler
    extends CampaignBasedHandler<Member, MemberInput> {
    @Override
    default String collectionName() {
        return MEMBER_COLLECTION;
    }
    
    List<Member> findByClinic(String clinicId)
        throws NotFoundException, CampaignException;
    Member findByName(
        String clinicId, String lastName, String firstName)
        throws NotFoundException, CampaignException;
    boolean existsByMobileNumber(
        String clinicId, String mobileNumber, String roleName)
        throws CampaignException;
    Member findById(String clinicId, String memberId)
        throws NotFoundException, CampaignException;
}
