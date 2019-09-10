package com.kencorhealth.campaign.dm.delivery.nav;

import java.util.List;

public class ChoiceNav extends Nav {
    private List<ChoiceInput> choices;
    private ChoiceType choiceType;
    private String title;
    private String buttonText;
    private String hint;

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public ChoiceType getChoiceType() {
        return choiceType;
    }

    public void setChoiceType(ChoiceType choiceType) {
        this.choiceType = choiceType;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ChoiceInput> getChoices() {
        return choices;
    }

    public void setChoices(List<ChoiceInput> choices) {
        this.choices = choices;
    }

    @Override
    public String toString() {
        return
            "ChoiceNav{" + "choices=" + choices + ", title=" +
            title + ", choiceType=" + choiceType +
            ", buttonText=" + buttonText + ", hint=" + hint +
            "}, " + super.toString();
    }
}
