package com.kencorhealth.campaign.pdf.signer;

import com.kencorhealth.campaign.dm.common.CampaignUtil;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationMarkup;

public final class ModifyDoc
{
    public static void modify(File inputFile,
                              File outputFile,
                              Map<String, Object> data) throws IOException
    {
        try (PDDocument doc = PDDocument.load(inputFile))
        {
            PDDocument newDoc = new PDDocument();
            PDPageTree pages = doc.getPages();
            
            for (int i = 0; i < pages.getCount(); i++)
            {
                PDPage page = doc.getPage(i);
                PDPage newPage = new PDPage(page.getCOSObject());
                
                List<PDAnnotation> annotations = page.getAnnotations();
                List<PDAnnotation> newAnnotations = new ArrayList();
                
                for (PDAnnotation annot: annotations) {
                    PDAnnotation pda = new PDAnnotationMarkup();
                    pda.setRectangle(annot.getRectangle());

                    String key = parseAnnotation(annot);
                    
                    if (CampaignUtil.valid(key)) {
                        Object value = data.get(key);

                        PDPageContentStream contentStream =
                            new PDPageContentStream(
                                doc,
                                page,
                                PDPageContentStream.AppendMode.APPEND,
                                true
                            );

                        PDRectangle r = annot.getRectangle();
                        
                        if (value instanceof String) {
                            //Begin the Content stream 
                            contentStream.beginText(); 

                            //Setting the font to the Content stream  
                            contentStream.setFont(PDType1Font.COURIER, 12);
                            
                            //Setting the position for the line 
                            contentStream.newLineAtOffset(
                                r.getLowerLeftX(),
                                r.getLowerLeftY()
                            );
                            
                            String text = (String) value;
                            
                            //Adding text in the form of string 
                            contentStream.showText(text);      

                            //Ending the content stream
                            contentStream.endText();
                        } else if (value instanceof File) {
                            File file = (File) value;
                            PDImageXObject pdImage =
                                PDImageXObject.createFromFile(
                                    file.getAbsolutePath(),
                                    doc
                                );

                            contentStream.drawImage(
                                pdImage,
                                r.getLowerLeftX(),
                                r.getLowerLeftY(),
                                r.getWidth(),
                                r.getHeight()
                            );
                        }

                        //Closing the content stream
                        contentStream.close();

                        newAnnotations.add(pda);
                    }
                }
                
                newPage.setAnnotations(newAnnotations);
                newDoc.addPage(newPage);
            }
            
            newDoc.save(outputFile);
        }
    }
    
    private static String parseAnnotation(PDAnnotation annotation) {
        String retVal = null;

        String contents = annotation.getContents();

        if (CampaignUtil.valid(contents)) {
            int leftIndex = contents.indexOf("{");
            int rightIndex = contents.indexOf("}");

            if (leftIndex > 0 && rightIndex > 0) {
                String str = contents.substring(leftIndex+1, rightIndex);
                retVal = str.split(":")[0];
            }
        }

        return retVal;
    }
}