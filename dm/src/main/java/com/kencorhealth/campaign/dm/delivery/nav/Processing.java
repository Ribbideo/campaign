package com.kencorhealth.campaign.dm.delivery.nav;

public class Processing {
    private PreProcessor preProcessor;
    private Processor processor;
    private PostProcessor postProcessor;

    public PreProcessor getPreProcessor() {
        return preProcessor;
    }

    public void setPreProcessor(PreProcessor preProcessor) {
        this.preProcessor = preProcessor;
    }

    public Processor getProcessor() {
        return processor;
    }

    public void setProcessor(Processor processor) {
        this.processor = processor;
    }

    public PostProcessor getPostProcessor() {
        return postProcessor;
    }

    public void setPostProcessor(PostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }

    @Override
    public String toString() {
        return
            "Processing{" + "preProcessor=" + preProcessor + ", processor=" +
            processor + ", postProcessor=" + postProcessor + '}';
    }
}
