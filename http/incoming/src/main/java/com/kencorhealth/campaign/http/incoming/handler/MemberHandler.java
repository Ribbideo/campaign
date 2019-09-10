package com.kencorhealth.campaign.http.incoming.handler;

import com.kencorhealth.campaign.dm.provider.Member;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.input.MemberInput;
import java.util.List;

public interface MemberHandler extends CampaignBasedHandler {
    String create(MemberInput input) throws CampaignException;
    Member getByName(
        String providerId,
        String lastName,
        String firstName) throws CampaignException;
    Member getById(String providerId, String memberId) throws CampaignException;
    List<Member> getByProvider(String providerId) throws CampaignException;
}
