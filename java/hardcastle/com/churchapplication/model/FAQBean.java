package hardcastle.com.churchapplication.model;

public class FAQBean {

    private String faq_Question;
    private String faq_Answer;

    public FAQBean(String faq_Question, String faq_Answer) {

        this.faq_Question = faq_Question;
        this.faq_Answer = faq_Answer;
    }

    public String getFaq_Question() {
        return faq_Question;
    }

    public void setFaq_Question(String faq_Question) {
        this.faq_Question = faq_Question;
    }

    public String getFaq_Answer() {
        return faq_Answer;
    }

    public void setFaq_Answer(String faq_Answer) {
        this.faq_Answer = faq_Answer;
    }
}
