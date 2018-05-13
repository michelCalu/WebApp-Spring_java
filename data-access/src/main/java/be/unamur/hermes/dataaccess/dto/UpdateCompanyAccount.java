package be.unamur.hermes.dataaccess.dto;

public class UpdateCompanyAccount {

    private String companyNb;
    private String companyStatus;


    public UpdateCompanyAccount() {
    }

    public UpdateCompanyAccount(String companyNb, String companyStatus) {
        this.companyNb = companyNb;
        this.companyStatus = companyStatus;
    }

    public String getCompanyNb() {
        return companyNb;
    }

    public String getCompanyStatus() {
        return companyStatus;
    }

    public void setCompanyNb(String companyNb) {
        this.companyNb = companyNb;
    }

    public void setCompanyStatus(String companyStatus) {
        this.companyStatus = companyStatus;
    }
}
