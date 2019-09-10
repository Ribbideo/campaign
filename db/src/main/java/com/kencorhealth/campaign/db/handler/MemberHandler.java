package com.kencorhealth.campaign.db.handler;

import com.kencorhealth.campaign.dm.annotations.Exportable;
import com.kencorhealth.campaign.dm.provider.Member;
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
    
    List<Member> findByProvider(String providerId)
        throws NotFoundException, CampaignException;
    Member findByName(
        String providerId, String lastName, String firstName)
        throws NotFoundException, CampaignException;
    Member findByMobileNumber(
        String providerId, String mobileNumber)
        throws NotFoundException, CampaignException;
    Member findById(String providerId, String memberId)
        throws NotFoundException, CampaignException;
}
