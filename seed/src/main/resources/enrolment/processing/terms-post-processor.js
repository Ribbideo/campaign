// Enrolment form post-processing script
function execute(input) {
    print("Form processing");

    var scriptInput = input["campaign.script"];

    var nav = scriptInput.nav;
    var context = scriptInput.context;
    var authToken = context.authToken;

    var mh = input["campaign.handler.db"].member;
    var wdh = input["campaign.handler.db"].workflowData;
    var uh = input["campaign.handler.http.rpm"].user;

    var formData = wdh.get(authToken.providerId, context.campaignId, context.containerId, 0).data;

print("Form data: " + formData);

    var memberInput = scriptInput.newMember();

    memberInput.providerId = authToken.providerId;
    memberInput.firstName = formData.firstName;
    memberInput.lastName = formData.lastName;
    memberInput.mobileNumber = formData.mobileNumber;
    memberInput.roleName = "PATIENT";

    var memberId = mh.add(memberInput);

    print("Member Id: " + memberId);

    var userInput = {
        templateType: "CHF",
        firstName: formData.firstName,
        lastName: formData.lastName,
        mobileNumber: formData.mobileNumber,
        phoneType: "mobile",
        userType: "PATIENT",
        role: "patient",
        userName: formData.mobileNumber,
        clinicId: authToken.providerId,
        billingApproverId: authToken.approverId,
        billingApproverName: authToken.approverName,
        initialPassword: "123456"
    };

    var userOutput = uh.create(userInput);

    print("User output: " + userOutput);
 
    return true;
}
