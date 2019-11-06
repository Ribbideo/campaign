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
    var rpmh = input["campaign.handler.http.rpm"].rpm;
    var ph = input["campaign.handler.pdf"].pdf;

    var clinicId = authToken.rpm.clinic.id;

    var userFormData = wdh.get(clinicId, context.campaignId, context.containerId, 1);
    var signatureFormData = wdh.get(clinicId, context.campaignId, context.containerId, 2);

    var consentForm = helper.download("ConsentFormAnnotated.pdf");

    var data = helper.newMap();

    data.put("patientFullName", userFormData.firstName + " " + userFormData.lastName);
    data.put("todaysDate", helper.today());
    data.put("patientSignature", helper.download(context.campaignId, signatureFormData.file));
    data.put("clinicName", authToken.rpm.clinic.name);

    var consentFormUrl = ph.transform(consentForm, context.campaignId, data);

    var memberInput = helper.newMember();

    var phoneNumber = userFormData.phoneNumber;

    var phoneType = twh.getPhoneType("1" + phoneNumber);

    memberInput.clinicId = clinicId;
    memberInput.firstName = userFormData.firstName;
    memberInput.lastName = userFormData.lastName;
    memberInput.phoneNumber = phoneNumber;
    memberInput.phoneType = phoneType;
    memberInput.roleName = "PATIENT";
    memberInput.consentFormUrl = consentFormUrl;

    var memberId = mh.add(memberInput);

    var existingPatient = rpmh.userIfExists(phoneNumber);

    if (existingPatient != null) {
        /* Just add consent doc URL */
        rpmh.updateConsentFormUrl(existingPatient.id, consentFormUrl);
    } else {
        /* Create patient in RPM */
        var userInput = {
	  templateType: "CHF",
	  firstName: userFormData.firstName,
	  lastName: userFormData.lastName,
	  mobileNumber: phoneNumber,
	  consentFormUrl: consentFormUrl,
	  userType: "PATIENT",
	  role: "patient",
	  userName: phoneNumber,
	  clinicId: clinicId,
	  billingApproverId: authToken.approverId,
	  billingApproverName: authToken.approverName,
	  initialPassword: "123456",
          phoneNumber: "",
          address:{},
          diagnosis:{}
	};

        var userOutput = rpmh.create(userInput);

        print("User output: " + userOutput);
    }
 
    return true;
}
