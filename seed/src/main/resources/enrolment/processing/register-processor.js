/* Register processor */
function execute(input) {
    print("Register processor");

    var scriptInput = input["campaign.script"];

    var nav = scriptInput.nav;
    var context = scriptInput.context;
    var formData = scriptInput.formData;

    var memberHandler = input["campaign.handler.db"].member;

    var memberExists = memberHandler.existsByMobileNumber(context.providerId, formData.mobileNumber, "PATIENT");

    if (memberExists) {
        retVal = nav.failureNav;
        retVal.title = "Looks like you have already registered";
        retVal.mustAbort(true);
    } else {
        retVal = nav.successNav;
    }

    return retVal;
}
