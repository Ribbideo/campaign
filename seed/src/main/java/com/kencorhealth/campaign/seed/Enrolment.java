package com.kencorhealth.campaign.seed;

import com.kencorhealth.campaign.dm.common.Script;
import com.kencorhealth.campaign.dm.common.TypeInfo;
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
import com.kencorhealth.campaign.dm.delivery.nav.PostProcessor;
import com.kencorhealth.campaign.dm.delivery.nav.PreProcessor;
import com.kencorhealth.campaign.dm.delivery.nav.Processing;
import com.kencorhealth.campaign.dm.delivery.nav.Processor;
import com.kencorhealth.campaign.dm.delivery.nav.TextInput;
import com.kencorhealth.campaign.dm.delivery.nav.TitleNav;
import com.kencorhealth.campaign.dm.delivery.type.WebMethod;
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

public class Enrolment {
    static void create() throws Exception {
        Campaign c = new Campaign();
        c.setName("Patient enrolment campaign");
        c.setDescription("Patient enrolment campaign");
        c.setTypeInfo(TypeInfo.from(CampaignType.PATIENT_ENROLMENT));
        c.setGoal("Enable self-enrolment of patients");
        
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
        
        ChoiceInput yes = new ChoiceInput();
        yes.setTitle("Yes");
        yes.setFieldName("yes");
        yes.setFieldValue("Yes");
        
        ChoiceInput no = new ChoiceInput();
        no.setTitle("No");
        no.setFieldName("no");
        no.setFieldValue("No");

        havePhone.setChoices(Arrays.asList(yes, no));
        
        FormNav details = new FormNav();
        details.setTitle("Enter details");
        
        Processing processing = new Processing();
        
        PreProcessor preProcessor = new PreProcessor();
        preProcessor.setScript(loadScript("pre-processor"));

        Processor processor = new Processor();
        processor.setScript(loadScript("processor"));
        
        PostProcessor postProcessor = new PostProcessor();
        postProcessor.setScript(loadScript("post-processor"));

        processing.setPreProcessor(preProcessor);
        processing.setProcessor(processor);
        processing.setPostProcessor(postProcessor);
        
        details.setProcessing(processing);
        
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
        
        details.setItems(Arrays.asList(fnn, lnn, mnn));
        details.setButtonText("Submit");
        details.setHint("Press ENTER");
        
        TitleNav thankYou = new TitleNav();
        thankYou.setTitle("Thank you. You should receive an SMS");
        thankYou.setButtonText("Finish");
        thankYou.setHint("Press ENTER");
        
        TitleNav oops = new TitleNav();
        oops.setTitle("Oops, something went wrong");
        oops.setButtonText("Finish");
        oops.setHint("Press ENTER");
        
        details.setSubmitSuccess(thankYou);
        details.setSubmitFailure(oops);
        
        IfCondition havePhoneCondition = new IfCondition();
        havePhoneCondition.setCondition(havePhone.getId() + ".yes == \"Yes\"");
        havePhoneCondition.setNav(details);

        TitleNav noPhone = new TitleNav();
        noPhone.setTitle("Sorry, you need a smartphone");
        noPhone.setButtonText("Finish");
        noPhone.setHint("Press ENTER");

        ElseCondition noPhoneCondition = new ElseCondition();
        noPhoneCondition.setNav(noPhone);
        
        BranchNav havePhoneBranch = new BranchNav();
        havePhoneBranch.setIfCondition(havePhoneCondition);
        havePhoneBranch.setElseCondition(noPhoneCondition);

        List<Nav> nav = new ArrayList();
        nav.add(title);
        nav.add(media);
        nav.add(havePhone);
        nav.add(havePhoneBranch);
        
        webMethod.setNav(nav);
        
        delivery.setWeb(webMethod);
        
        c.setDelivery(delivery);
        
        System.out.println(JsonUtil.asJson(c));
    }
    
    private static Script loadScript(String fileName) throws IOException {
        InputStream is =
            Enrolment.class.getResourceAsStream(
                "/enrolment/processing/" + fileName + ".js"
            );
        String contents = IOUtils.toString(is);
        return Script.ecma(contents);
    }
}
