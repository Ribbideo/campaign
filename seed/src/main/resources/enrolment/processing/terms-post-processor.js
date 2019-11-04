/* Terms post processor */
function execute(input) {
    print("Terms post processor");

    var scriptInput = input["campaign.script"];

    var nav = scriptInput.nav;
    var context = scriptInput.context;
    var authToken = context.authToken;

    var helper = input["campaign.helper"];

    var mh = input["campaign.handler.db"].member;
    var wdh = input["campaign.handler.db"].workflowData;
    var twh = input["campaign.handler.twilio"].twilio;
    var proh = input["campaign.handler.db"].provider;
    var uh = input["campaign.handler.http.rpm"].user;
    var ph = input["campaign.handler.pdf"].pdf;

    var userFormData = wdh.get(authToken.providerId, context.campaignId, context.containerId, 1);
    var signatureFormData = wdh.get(authToken.providerId, context.campaignId, context.containerId, 2);

    var consentForm = helper.download("ConsentForm.pdf");

    var data = helper.newMap();

    data.put("patientFullName", userFormData.firstName + " " + userFormData.lastName);
    data.put("todaysDate", helper.today());
    data.put("patientSignature", helper.download(context.campaignId, signatureFormData.file));
    data.put("clinicName", proh.findById(authToken.providerId).name);

    var consentDocUrl = ph.transform("ConsentForm.pdf", context.campaignId, data);

    var memberInput = helper.newMember();

    var phoneNumber = userFormData.phoneNumber;

    var phoneType = twh.getPhoneType("1" + phoneNumber);

    memberInput.providerId = authToken.providerId;
    memberInput.firstName = userFormData.firstName;
    memberInput.lastName = userFormData.lastName;
    memberInput.phoneNumber = phoneNumber;
    memberInput.phoneType = phoneType;
    memberInput.roleName = "PATIENT";
    memberInput.consentDocUrl = consentDocUrl;

    var memberId = mh.add(memberInput);

    var userInput = {
        templateType: "CHF",
        firstName: userFormData.firstName,
        lastName: userFormData.lastName,
        mobileNumber: phoneNumber,
        consentDocUrl: consentDocUrl,
        phoneType: phoneType.stringify(),
        userType: "PATIENT",
        role: "patient",
        userName: phoneNumber,
        clinicId: authToken.providerId,
        billingApproverId: authToken.approverId,
        billingApproverName: authToken.approverName,
        initialPassword: "123456"
    };

    var userOutput = uh.create(userInput);

    print("User output: " + userOutput);
 
    return true;
}
