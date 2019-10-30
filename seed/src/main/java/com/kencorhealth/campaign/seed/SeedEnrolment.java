package com.kencorhealth.campaign.seed;

import com.kencorhealth.campaign.dm.common.Script;
import com.kencorhealth.campaign.dm.common.TypeInfo;
import com.kencorhealth.campaign.dm.delivery.PdfDisplay;
import com.kencorhealth.campaign.dm.delivery.nav.BranchNav;
import com.kencorhealth.campaign.dm.delivery.nav.FormNav;
import com.kencorhealth.campaign.dm.delivery.nav.IfCondition;
import com.kencorhealth.campaign.dm.delivery.nav.MediaNav;
import com.kencorhealth.campaign.dm.delivery.nav.MediaType;
import com.kencorhealth.campaign.dm.delivery.nav.Nav;
import com.kencorhealth.campaign.dm.delivery.nav.ChoiceInput;
import com.kencorhealth.campaign.dm.delivery.nav.ChoiceNav;
import com.kencorhealth.campaign.dm.delivery.nav.ChoiceType;
import com.kencorhealth.campaign.dm.delivery.nav.ElseCondition;
import com.kencorhealth.campaign.dm.delivery.nav.FileInput;
import com.kencorhealth.campaign.dm.delivery.nav.PostProcessor;
import com.kencorhealth.campaign.dm.delivery.nav.PreProcessor;
import com.kencorhealth.campaign.dm.delivery.nav.Processing;
import com.kencorhealth.campaign.dm.delivery.nav.Processor;
import com.kencorhealth.campaign.dm.delivery.nav.TextInput;
import com.kencorhealth.campaign.dm.delivery.nav.TitleNav;
import com.kencorhealth.campaign.dm.delivery.web.WebMethod;
import com.kencorhealth.campaign.dm.entity.Campaign;
import com.kencorhealth.campaign.dm.entity.CampaignType;
import com.kencorhealth.campaign.dm.entity.Delivery;
import com.kencorhealth.campaign.json.JsonUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.io.IOUtils;

public class SeedEnrolment {
    static void create() throws Exception {
        Campaign c = new Campaign();
        c.setName("Patient enrolment campaign");
        c.setDescription("Patient enrolment campaign");
        c.setTypeInfo(TypeInfo.from(CampaignType.PATIENT_ENROLMENT));
        c.setGoal("This will enable self-enrolment of patients");
        
        Delivery delivery = new Delivery();
        
        WebMethod webMethod = new WebMethod();
        
        TitleNav title = new TitleNav();
        title.setTitle("Welcome to the campaign");
        title.setButtonText("Next");
        title.setHint("Press ENTER");
        
        MediaNav media = new MediaNav();
        media.setTitle("Here is a video");
        media.setMediaType(MediaType.VIDEO);
        media.setUrl("https://www.youtube.com/watch?v=LEUwgnBkFyU");
        media.setButtonText("Next");
        media.setHint("Press ENTER");
        
        ChoiceNav havePhone = new ChoiceNav();
        havePhone.setTitle("Do you have a smartphone?");
        havePhone.setChoiceType(ChoiceType.SINGLE);
        havePhone.setButtonText("Next");
        havePhone.setHint("Press ENTER");
        
        Processing phoneProcessing = new Processing();
        
        Processor hpp = new Processor();
        hpp.setScript(loadScript("have-phone-processor"));
        
        phoneProcessing.setProcessor(hpp);
        
        havePhone.setProcessing(phoneProcessing);
        
        ChoiceInput yes = new ChoiceInput();
        yes.setTitle("Yes");
        yes.setFieldName("yes");
        yes.setFieldValue("Yes");
        
        ChoiceInput no = new ChoiceInput();
        no.setTitle("No");
        no.setFieldName("no");
        no.setFieldValue("No");

        havePhone.setChoices(Arrays.asList(yes, no));
        
        FormNav register = new FormNav();
        register.setTitle("Enter details");
        
        Processing processing = new Processing();
        
        PreProcessor preProcessor = new PreProcessor();
        preProcessor.setScript(loadScript("register-pre-processor"));

        Processor processor = new Processor();
        processor.setScript(loadScript("register-processor"));
        
        PostProcessor postProcessor = new PostProcessor();
        postProcessor.setScript(loadScript("register-post-processor"));

        processing.setPreProcessor(preProcessor);
        processing.setProcessor(processor);
        processing.setPostProcessor(postProcessor);
        
        register.setProcessing(processing);
        
        TextInput fnn = new TextInput();
        fnn.setFieldName("firstName");
        fnn.setTitle("First Name");
        fnn.setHint("Enter first name");
        
        TextInput lnn = new TextInput();
        lnn.setFieldName("lastName");
        lnn.setTitle("Last Name");
        lnn.setHint("Enter last name");
        
        TextInput mnn = new TextInput();
        mnn.setFieldName("mobileNumber");
        mnn.setTitle("Mobile Number");
        mnn.setHint("Enter mobile number");
        
        register.setItems(Arrays.asList(fnn, lnn, mnn));
        register.setButtonText("Submit");
        register.setHint("Press ENTER");
        
        FormNav terms = new FormNav();
        terms.setTitle("Terms");
        
        Processing termsProcessing = new Processing();
        
        PreProcessor termsPreProcessor = new PreProcessor();
        termsPreProcessor.setScript(loadScript("terms-pre-processor"));

        Processor termsProcessor = new Processor();
        termsProcessor.setScript(loadScript("terms-processor"));
        
        PostProcessor termsPostProcessor = new PostProcessor();
        termsPostProcessor.setScript(loadScript("terms-post-processor"));

        termsProcessing.setPreProcessor(termsPreProcessor);
        termsProcessing.setProcessor(termsProcessor);
        termsProcessing.setPostProcessor(termsPostProcessor);
        
        terms.setProcessing(termsProcessing);
        
        PdfDisplay pd = new PdfDisplay();
        pd.setUrl("https://test.kencorhealth.com:6060/api/file/ConsentForm.pdf");
        pd.setTitle("Consent Form");

        FileInput fi = new FileInput();
        fi.setFieldName("signature");
        fi.setHint("Signature");
        
        terms.setItems(Arrays.asList(pd, fi));
        terms.setButtonText("Submit");
        terms.setHint("Press ENTER");        
        
        TitleNav thankYou = new TitleNav();
        thankYou.setTitle("Thank you. You should receive an SMS");
        thankYou.setButtonText("Finish");
        thankYou.setHint("Press ENTER");
        
        TitleNav registerOops = new TitleNav();
        registerOops.setTitle("Oops, something went wrong");
        registerOops.setButtonText("Finish");
        registerOops.setHint("Press ENTER");
        
        TitleNav termsOops = new TitleNav();
        termsOops.setTitle("Oops, something went wrong");
        termsOops.setButtonText("Finish");
        termsOops.setHint("Press ENTER");
        
        terms.setSuccessNav(thankYou);
        terms.setFailureNav(termsOops);
        
        register.setSuccessNav(terms);
        register.setFailureNav(registerOops);
        
        IfCondition ifHavePhoneCondition = new IfCondition();
        ifHavePhoneCondition.setCondition("formData.yes == \"Yes\"");
        ifHavePhoneCondition.setNav(register);

        TitleNav noPhone = new TitleNav();
        noPhone.setTitle("Sorry, you need a smartphone");
        noPhone.setButtonText("Finish");
        noPhone.setHint("Press ENTER");

        ElseCondition elseNoPhoneCondition = new ElseCondition();
        elseNoPhoneCondition.setNav(noPhone);
        
        BranchNav havePhoneBranch = new BranchNav();
        havePhoneBranch.setIfCondition(ifHavePhoneCondition);
        havePhoneBranch.setElseCondition(elseNoPhoneCondition);
        
        TitleNav noConditionMatched = new TitleNav();
        noConditionMatched.setTitle("Oops. No condition matched. Check your script");
        noConditionMatched.setButtonText("Finish");
        noConditionMatched.setHint("Press ENTER");
        
        havePhoneBranch.setFailureNav(noConditionMatched);
        
        TitleNav havePhoneOops = new TitleNav();
        havePhoneOops.setTitle("Oops, something went wrong");
        havePhoneOops.setButtonText("Finish");
        havePhoneOops.setHint("Press ENTER");
        
        havePhone.setSuccessNav(havePhoneBranch);
        havePhone.setFailureNav(havePhoneOops);

        List<Nav> nav = new ArrayList();
        nav.add(title);
        nav.add(media);
        nav.add(havePhone);
        
        webMethod.setNav(nav);
        
        delivery.setWeb(webMethod);
        
        c.setDelivery(delivery);
        
        System.out.println(JsonUtil.asJson(c));
    }
    
    private static Script loadScript(String fileName) throws IOException {
        InputStream is =
            SeedEnrolment.class.getResourceAsStream(
                "/enrolment/processing/" + fileName + ".js"
            );
        String contents = IOUtils.toString(is);
        
        return Script.ecma(contents);
    }
}
