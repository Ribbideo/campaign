package com.kencorhealth.campaign.http.incoming.handler.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kencorhealth.campaign.dm.provider.Member;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.input.MemberInput;
import com.kencorhealth.campaign.http.base.handler.impl.HttpBasedHandlerImpl;
import com.kencorhealth.campaign.http.incoming.handler.MemberHandler;
import java.util.List;

public class MemberHandlerImpl extends HttpBasedHandlerImpl
    implements MemberHandler {
    @Override
    public String create(MemberInput input) throws CampaignException {
        return
            sendPost(
                null,
                input,
                String.class,
                PROVIDER,
                input.getProviderId(),
                MEMBER
            );
    }

    @Override
    public Member getByName(
        String providerId,
        String lastName,
        String firstName) throws CampaignException {
        return
            sendGet(
                null,
                Member.class,
                PROVIDER,
                providerId,
                MEMBER,
                BY_NAME,
                lastName,
                firstName
            );
    }

    @Override
    public Member getById(String providerId, String memberId)
        throws CampaignException {
        return
            sendGet(
                null,
                Member.class,
                PROVIDER,
                providerId,
                MEMBER,
                memberId
            );
    }

    @Override
    public List<Member> getByProvider(String providerId)
        throws CampaignException {
        return
            sendGet(
                null,
                new TypeReference<List<Member>>() {},
                PROVIDER,
                providerId,
                MEMBER
            );
    }
}
