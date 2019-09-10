package com.kencorhealth.campaign.dm.common;

public class Script {
    private String contents;
    private ScriptLanguage language;

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public ScriptLanguage getLanguage() {
        return language;
    }

    public void setLanguage(ScriptLanguage language) {
        this.language = language;
    }
    
    public static Script ecma(String contents) {
        Script retVal = new Script();
        
        retVal.contents = contents;
        retVal.language = ScriptLanguage.ECMASCRIPT;
        
        return retVal;
    }

    @Override
    public String toString() {
        return
            "Script{" + "contents=" + contents + ", language=" + language + '}';
    }
}
