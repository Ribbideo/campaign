package com.kencorhealth.campaign.pdf.handler;

import com.kencorhealth.campaign.dm.annotations.Exportable;
import java.io.File;
import java.util.Map;

@Exportable
public interface PdfHandler extends PdfBasedHandler, AutoCloseable {
    @Override
    public default String alias() {
        return "pdf";
    }
    
    String transform(File inputPdf,
                     String campaignId,
                     Map<String, Object> data) throws Exception;

    @Override
    default void close() {
        // Empty
    }
}
