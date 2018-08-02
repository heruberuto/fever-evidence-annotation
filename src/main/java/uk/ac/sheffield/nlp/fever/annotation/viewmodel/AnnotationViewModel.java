package uk.ac.sheffield.nlp.fever.annotation.viewmodel;

import uk.ac.sheffield.nlp.fever.annotation.model.Label;

import java.util.List;

public class AnnotationViewModel {

    private List<Long> partialEvidence;
    private List<Long> individualEvidence;
    private Label label;

    public List<Long> getPartialEvidence() {
        return partialEvidence;
    }

    public void setPartialEvidence(List<Long> partialEvidence) {
        this.partialEvidence = partialEvidence;
    }

    public List<Long> getIndividualEvidence() {
        return individualEvidence;
    }

    public void setIndividualEvidence(List<Long> individualEvidence) {
        this.individualEvidence = individualEvidence;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }
}
