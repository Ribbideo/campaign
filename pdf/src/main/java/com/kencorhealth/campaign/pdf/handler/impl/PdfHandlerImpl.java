package com.kencorhealth.campaign.pdf.handler.impl;

import com.kencorhealth.campaign.cdn.CDNUtil;
import com.kencorhealth.campaign.dm.common.CampaignUtil;
import com.kencorhealth.campaign.pdf.signer.SigGen;
import java.io.File;
import com.kencorhealth.campaign.pdf.handler.PdfHandler;
import java.awt.geom.Rectangle2D;

public class PdfHandlerImpl implements PdfHandler {
    @Override
    public String transform(
        String pdfFileId, String campaignId, String signatureFileId)
        throws Exception {
        File pdfFile = CDNUtil.download(pdfFileId);
        
        File signatureFile = CDNUtil.download(campaignId, signatureFileId);
        
        String outputFileId = CampaignUtil.uniqueString() + ".pdf";

        File outFile = CDNUtil.tmpFile(outputFileId);
        
        SigGen c2 = new SigGen();
        c2.setImageFile(signatureFile);
        
        // TODO: Visit these later
        Rectangle2D r = new Rectangle2D.Double(120, 105, 200, 70);
        int pageNumber = 1;
        
        c2.signPDF(pdfFile, outFile, pageNumber, r, null);

        return CDNUtil.upload(campaignId, outFile);
    }
}
