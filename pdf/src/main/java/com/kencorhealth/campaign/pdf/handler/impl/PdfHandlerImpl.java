package com.kencorhealth.campaign.pdf.handler.impl;

import com.kencorhealth.campaign.cdn.CDNUtil;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import java.io.File;
import com.kencorhealth.campaign.pdf.handler.PdfHandler;
import com.kencorhealth.campaign.pdf.signer.ModifyDoc;
import java.util.Map;

public class PdfHandlerImpl implements PdfHandler {
    @Override
    public String transform(
        String pdfFileId,
        String campaignId,
        Map<String, Object> data) throws Exception {
        File pdfFile = CDNUtil.download(pdfFileId);
        
        String outputFileId = CampaignUtil.uniqueString() + ".pdf";

        File outFile = CDNUtil.tmpFile(outputFileId);
        
        ModifyDoc.modify(pdfFile, outFile, data);

        return CDNUtil.upload(campaignId, outFile);
    }
}
