package com.kencorhealth.campaign.pdf.signer;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main(String[] args) throws Exception {
        File pdfFile = new File("/home/srinivas/Desktop/ConsentForm.pdf");
        
        File outFile = new File("/tmp/out.pdf");

        Map<String, Object> data = new HashMap();
        
        data.put("clinicName", "Kencor");
        data.put("todaysDate", "11-Nov-2019");
        data.put("patientFullName", "Joe Wilken");
        data.put("patientSignature", new File("/home/srinivas/Desktop/KH/screenshots/signature-transparent.png"));
        
        ModifyDoc.modify(pdfFile, outFile, data);
    }
}
