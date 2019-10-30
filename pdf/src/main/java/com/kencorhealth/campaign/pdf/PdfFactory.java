package com.kencorhealth.campaign.pdf;

import com.kencorhealth.campaign.cdn.CDNUtil;
import com.kencorhealth.campaign.cdn.S3Config;
import com.kencorhealth.campaign.pdf.handler.impl.PdfHandlerImpl;
import com.kencorhealth.campaign.pdf.handler.PdfHandler;

public class PdfFactory {
    public static void init(S3Config config) {
        CDNUtil.init(config);
    }
    
    public static PdfHandler get() {
        return new PdfHandlerImpl();
    }
}
