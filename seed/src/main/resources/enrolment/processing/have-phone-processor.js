/* Have phone processor */
function execute(input) {
    print("Have phone processor");

    var retVal = null;

    var scriptInput = input["campaign.script"];

    var formData = scriptInput.formData;
    var nav = scriptInput.nav;

    var branchNav = nav.successNav;

    var ifCondition = branchNav.ifCondition;

    var conditionMatched = eval(ifCondition.condition);

    if (conditionMatched) {
        retVal = ifCondition.nav;
    } else {
        var elseIfCondition = branchNav.elseIfCondition;

        if (elseIfCondition != null) {
            for (var c in elseIfCondition) {
                conditionMatched = eval(c.condition);

                if (conditionMatched) {
                    retVal = c.nav;
                    break;
                }
            }
        }
    }

    if (!conditionMatched && branchNav.elseCondition != null) {
        conditionMatched = eval(branchNav.elseCondition.condition);

        if (conditionMatched) {
            retVal = branchNav.elseCondition.nav;
        }
    }

    if (retVal == null) {
        retVal = branchNav.failureNav;
    }

    return retVal;
}
