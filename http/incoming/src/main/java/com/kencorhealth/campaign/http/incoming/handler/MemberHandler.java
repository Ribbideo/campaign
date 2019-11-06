package com.kencorhealth.campaign.http.incoming.handler;

import com.kencorhealth.campaign.dm.clinic.Member;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.input.MemberInput;
import java.util.List;

public interface MemberHandler extends CampaignBasedHandler {
    String create(MemberInput input) throws CampaignException;
    Member getByName(
        String clinicId,
        String lastName,
        String firstName) throws CampaignException;
    Member getById(String clinic, String memberId) throws CampaignException;
    List<Member> getByClinic(String clinicId) throws CampaignException;
}
