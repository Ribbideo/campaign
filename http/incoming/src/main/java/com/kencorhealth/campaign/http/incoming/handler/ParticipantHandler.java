package com.kencorhealth.campaign.http.incoming.handler;

import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.input.ParticipantInput;
import com.kencorhealth.campaign.dm.output.ParticipantDetail;
import java.util.List;

public interface ParticipantHandler extends CampaignBasedHandler {
    String create(ParticipantInput input) throws CampaignException;
    ParticipantDetail getById(
        String clinicId,
        String campaignId,
        String participantId) throws CampaignException;
    List<ParticipantDetail> getByCampaign(String clinicId, String campaignId)
        throws CampaignException;
}
