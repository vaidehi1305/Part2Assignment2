package hello;

public class Classification {

    private long id;
    private String content;
    private String Amount_Requested;
    private String Employment_Length;
    private String Debt_To_Income_Ratio;
    private String Fico_Score;
    private String zip_code;

    public String getAmount_Requested() {
        return Amount_Requested;
    }

    public void setAmount_Requested(String amount_Requested) {
        Amount_Requested = amount_Requested;
    }

    public String getEmployment_Length() {
        return Employment_Length;
    }

    public void setEmployment_Length(String employment_Length) {
        Employment_Length = employment_Length;
    }

    public String getDebt_To_Income_Ratio() {
        return Debt_To_Income_Ratio;
    }

    public void setDebt_To_Income_Ratio(String debt_To_Income_Ratio) {
        Debt_To_Income_Ratio = debt_To_Income_Ratio;
    }

    public String getFico_Score() {
        return Fico_Score;
    }

    public void setFico_Score(String fico_Score) {
        Fico_Score = fico_Score;
    }

    public String getZip_code() {
        return zip_code;
    }

    public void setZip_code(String zip_code) {
        this.zip_code = zip_code;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
