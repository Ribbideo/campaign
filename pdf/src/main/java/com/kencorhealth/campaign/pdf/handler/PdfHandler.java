package com.kencorhealth.campaign.pdf.handler;

import com.kencorhealth.campaign.dm.annotations.Exportable;

@Exportable
public interface PdfHandler extends PdfBasedHandler, AutoCloseable {
    @Override
    public default String alias() {
        return "pdf";
    }
    
    String transform(
        String pdfFileId, String campaignId, String signatureFileId)
        throws Exception;

    @Override
    default void close() {
        // Empty
    }
}
