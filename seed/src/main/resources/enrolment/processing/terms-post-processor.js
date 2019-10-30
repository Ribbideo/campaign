// Terms post processor
function execute(input) {
    print("Terms post processor");

    var scriptInput = input["campaign.script"];

    var nav = scriptInput.nav;
    var context = scriptInput.context;
    var authToken = context.authToken;

    var mh = input["campaign.handler.db"].member;
    var wdh = input["campaign.handler.db"].workflowData;
    var uh = input["campaign.handler.http.rpm"].user;
    var ph = input["campaign.handler.pdf"].pdf;

    var userFormData = wdh.get(authToken.providerId, context.campaignId, context.containerId, 1);
    var signatureFormData = wdh.get(authToken.providerId, context.campaignId, context.containerId, 2);

    var consentDocUrl = ph.transform("ConsentForm.pdf", context.campaignId, signatureFormData.file);

    var memberInput = scriptInput.newMember();

    memberInput.providerId = authToken.providerId;
    memberInput.firstName = userFormData.firstName;
    memberInput.lastName = userFormData.lastName;
    memberInput.mobileNumber = userFormData.mobileNumber;
    memberInput.roleName = "PATIENT";
    memberInput.consentDocUrl = consentDocUrl;

    var memberId = mh.add(memberInput);

    var userInput = {
        templateType: "CHF",
        firstName: userFormData.firstName,
        lastName: userFormData.lastName,
        mobileNumber: userFormData.mobileNumber,
        consentDocUrl: consentDocUrl,
        phoneType: "mobile",
        userType: "PATIENT",
        role: "patient",
        userName: userFormData.mobileNumber,
        clinicId: authToken.providerId,
        billingApproverId: authToken.approverId,
        billingApproverName: authToken.approverName,
        initialPassword: "123456"
    };

    var userOutput = uh.create(userInput);

    print("User output: " + userOutput);
 
    return true;
}
