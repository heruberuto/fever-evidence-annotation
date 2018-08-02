package uk.ac.sheffield.nlp.fever.annotation.viewmodel;

public class AnnotatorCountViewModel {
    private Long count;

    public void setCount(long count) {
        this.count = count;
    }

    public AnnotatorCountViewModel(long annotationsCount) {
        count = annotationsCount;
    }

    public Long getCount(){
        return count;
    }
}
