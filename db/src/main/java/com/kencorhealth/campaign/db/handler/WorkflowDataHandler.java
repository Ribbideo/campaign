package com.kencorhealth.campaign.db.handler;

import com.kencorhealth.campaign.dm.annotations.Exportable;
import com.kencorhealth.campaign.dm.delivery.web.WorkflowData;
import com.kencorhealth.campaign.dm.exception.CampaignException;
import com.kencorhealth.campaign.dm.exception.DbException;
import com.kencorhealth.campaign.dm.exception.NotFoundException;
import com.kencorhealth.campaign.dm.input.WorkflowDataInput;
import java.util.Map;

@Exportable
public interface WorkflowDataHandler
    extends CampaignBasedHandler<WorkflowData, WorkflowDataInput> {
    @Override
    default String collectionName() {
        return WORKFLOW_DATA_COLLECTION;
    }

    WorkflowData createOrGetUnused(String clinicId, String campaignId)
        throws CampaignException, DbException;
    Map<String, Object> get(
        String clinicId,
        String campaignId,
        String containerId,
        String key)
        throws NotFoundException, CampaignException, DbException;
    Map<String, Object> get(
        String clinicId,
        String campaignId,
        String containerId,
        int index)
        throws NotFoundException, CampaignException, DbException;
    void update(String clinicId,
                String campaignId,
                String containerId,
                String key,
                Map<String, Object> value)
        throws NotFoundException, CampaignException, DbException;
}
