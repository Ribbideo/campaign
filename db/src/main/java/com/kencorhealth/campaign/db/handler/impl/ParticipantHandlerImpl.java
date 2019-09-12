package com.kencorhealth.campaign.db.handler.impl;

import com.kencorhealth.campaign.db.handler.CampaignHandler;
import com.kencorhealth.campaign.db.handler.MemberHandler;
import com.kencorhealth.campaign.db.handler.ParticipantHandler;
import com.kencorhealth.campaign.db.handler.ProviderHandler;
import com.kencorhealth.campaign.dm.provider.Member;
import com.kencorhealth.campaign.dm.entity.Participant;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.DbException;
import com.kencorhealth.campaign.dm.exception.ExistsException;
import com.kencorhealth.campaign.dm.exception.InvalidException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.dm.input.ParticipantInput;
import com.kencorhealth.campaign.mongo.handler.impl.MongoHandlerImpl;
import com.mongodb.MongoClient;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParticipantHandlerImpl
    extends MongoHandlerImpl<Participant, ParticipantInput>
    implements ParticipantHandler {
    public ParticipantHandlerImpl(MongoClient mc) {
        super(mc);
    }

    @Override
    public Participant add(ParticipantInput input)
        throws InvalidException, ExistsException,
        DbException, CampaignException {
        Participant retVal = null;
        
        try (MemberHandler mh = new MemberHandlerImpl(mc)) {
            String memberId = input.getMemberId();
            
            Member m = mh.findById(memberId);
            
            if (m == null) {
                String message =
                    "Member with Id '" + memberId + "' not found";
                throw new InvalidException(message);
            }
            
            retVal = doAdd(input);
        } catch (Exception e) {
            throw new DbException(e);
        }
        
        return retVal;
    }

    @Override
    public List<Participant> findByProviderAndCampaign(
        String providerId, String campaignId)
        throws NotFoundException, CampaignException {
        Map<String, Object> filter = new HashMap();
        filter.put(PROVIDER_ID_KEY, providerId);
        filter.put(CAMPAIGN_ID_KEY, campaignId);
        
        return findMany(filter);
    }

    @Override
    public Participant findById(String providerId,
                                String campaignId,
                                String participantId)
        throws NotFoundException, CampaignException {
        Participant retVal = null;
        
        try (ProviderHandler ph = new ProviderHandlerImpl(mc)) {
            ph.findById(providerId);
        } catch (Exception e) {
            throw new CampaignException(e);
        }
        
        try (CampaignHandler ch = new CampaignHandlerImpl(mc)) {
            ch.findById(campaignId);
        } catch (Exception e) {
            throw new CampaignException(e);
        }
        
        Map<String, Object> filter = new HashMap();
        filter.put(PROVIDER_ID_KEY, providerId);
        filter.put(CAMPAIGN_ID_KEY, campaignId);
        
        List<Participant> participants = findMany(filter);
        
        if (!participants.isEmpty()) {
            retVal = participants.get(0);
        } else {
            String message =
                "No campaign found for Id '" + campaignId +
                "' and provider '" + providerId + "'";
            
            throw new NotFoundException(message);
        }
        
        return retVal;
    }
}
