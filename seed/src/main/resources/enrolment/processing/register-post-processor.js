// Enrolment form post-processing script
function execute(input) {
    print("Form processing");

    var scriptInput = input["campaign.script"];

    var nav = scriptInput.nav;
    var context = scriptInput.context;
    var formData = scriptInput.formData;

    var memberHandler = input["campaign.handler.db"].member;

    var memberInput = scriptInput.newMember();

    memberInput.providerId = context.providerId;
    memberInput.firstName = formData.firstName,
    memberInput.lastName = formData.lastName,
    memberInput.mobileNumber = formData.mobileNumber,
    memberInput.roleName = "PATIENT";

    var memberId = memberHandler.add(memberInput);

    print("Member Id: " + memberId);

    return true;
}
