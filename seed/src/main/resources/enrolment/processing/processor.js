// Enrolment processing script
function execute(input) {
    var userHandler = input.campaign.handler.http.rpm.user;
    
    print("User handler: " + userHandler);
    
    var formData = input.campaign.ui;
    
    print("Form data: " + formData);

    return true;
}