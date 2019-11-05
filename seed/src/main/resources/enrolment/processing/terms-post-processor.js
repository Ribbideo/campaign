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

    var consentForm = helper.download("ConsentFormAnnotated.pdf");

    var data = helper.newMap();

    var provider = proh.findById(authToken.providerId);

    data.put("patientFullName", userFormData.firstName + " " + userFormData.lastName);
    data.put("todaysDate", helper.today());
    data.put("patientSignature", helper.download(context.campaignId, signatureFormData.file));
    data.put("clinicName", provider.name);

    var consentFormUrl = ph.transform(consentForm, context.campaignId, data);

    var memberInput = helper.newMember();

    var phoneNumber = userFormData.phoneNumber;

    var phoneType = twh.getPhoneType("1" + phoneNumber);

    memberInput.providerId = authToken.providerId;
    memberInput.firstName = userFormData.firstName;
    memberInput.lastName = userFormData.lastName;
    memberInput.phoneNumber = phoneNumber;
    memberInput.phoneType = phoneType;
    memberInput.roleName = "PATIENT";
    memberInput.consentFormUrl = consentFormUrl;

    var memberId = mh.add(memberInput);

    var existingPatient = uh.userIfExists(phoneNumber);

    if (existingPatient != null) {
        /* Just add consent doc URL */
        uh.updateConsentFormUrl(existingPatient.id, consentFormUrl);
    } else {
        /* Create patient in RPM */
        var userInput = {
	  templateType: "CHF",
	  firstName: userFormData.firstName,
	  lastName: userFormData.lastName,
	  mobileNumber: phoneNumber,
	  consentFormUrl: consentFormUrl,
	  phoneType: phoneType.stringify(),
	  userType: "PATIENT",
	  role: "patient",
	  userName: phoneNumber,
	  clinicId: provider.clinicId,
	  billingApproverId: authToken.approverId,
	  billingApproverName: authToken.approverName,
	  initialPassword: "123456"
	};

        var userOutput = uh.create(userInput);

        print("User output: " + userOutput);
    }
 
    return true;
}
