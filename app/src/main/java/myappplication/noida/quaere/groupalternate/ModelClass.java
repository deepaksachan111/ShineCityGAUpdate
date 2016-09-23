package myappplication.noida.quaere.groupalternate;

/**
 * Created by intex on 9/7/2015.
 */
public class ModelClass {

    //data for placement Details
    String memberName = "";
    String mobileNo = "";
    String loginId = "";
    String joiningDate = "";
    String pdrecordno = "";

    //data for payout list
    String payoutMember = "";
    String netAmount = "";
    String grossAmount = "";
    String closingDate = "";
    String payotlistNo = "";



    String payoutNo = "";
    //payout vs business data
    String pbPayountNo = "";
    String pbNetAmount = "";
    String pbGrossAmout = "";
    String pbProcessingFee = "";
    String pbClosingDate = "";
    String pbSelfIncm = "";
    String pbTeamIncm = "";
    String pbTds = "";

    //Data for Cancel Booking
    String cbCustomerName = "";
    String cbSiteName = "";
    String cbPlotNo = "";
    String cbBookingId = "";
    String cbBookingDate = "";
    String cbCancelDate = "";
    String cbSerialNo = "";

    //Data for Cash Pay Status
    String cashAmount = "";
    String cashChequeNo = "";
    String cashChequeDate = "";
    String cashPartyName = "";
    String cashReceiptNo = "";
    String cashStatus = "";


    String chequeAmount = "";
    String chequeChequeNo = "";
    String chequeChequeDate = "";
    String chequePartyName = "";
    String chequeReceiptNo = "";
    String chequeStatus = "";

    String plotBookingSiteName;
    String plotBookingPlotNo;
    String plotBookingHolderId;
    String plotBookingAmount;
    String plotBookingStatus;
    String plotBookingBalanceAmount;
    String plotBookingHolderName;
    String plotBookingMobile;

    String birthdayName;
    String birthdayDob;
    String birthdayLoginId;
    String birthdaymemType;

    //Due Emi
    String dueEmiCustomerName;
    String dueEmiCustomerId;
    String dueEmidueAmount;
    String dueEmiDueDate;
    String dueEmiPaidAmount;
    String dueEmiPlotNo;
    String dueEmiPlotArea;
    String dueEmiPlotAmount;

    //Due emi customer list

    String emiClcontactNo;
    String emiClLastPaidDate;
    String emiCllastPaidEMIDueDate;
    String emiClplotArea;
    String emiClplotBookingDate;
    String emiClplotHolderName;
    String emiClplotNo;
    String emiClplotRate;
    String emiClplotStatus;
    String emiClplotType;
    String emiClsiteName;


    // Payout Deduction Details
    String payoutDdPaymentAmount;
    String payoutDdChequeBank;
    String payoutDdChequeDate;
    String payoutDdChequeNo;
    String payoutDdMemberID;
    String payoutDdMemberName;
    String payoutDdPaymentDate;
    String payoutDdPaymentMode;
    String payoutDdPaymentRemark;

    //My Epin
    String epinCreatedDate;
    String epinDesignation;
    String epinEpinNo;
    String epinMamberID;
    String epinMemberName;
    String epinRegisteredTo;

    //Register Activity
    String branchName;
    String branchId;
    String designationName;
    String designationId;
    String stateName;
    String stateId;
    String countryName;
    String cityName;
    String cityId;
    String genderName;
    String genderValue;
    String joiningLegName;
    String countryId;
    String joiningLegValue;
    String siteName, siteId;

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getJoiningLegValue() {
        return joiningLegValue;
    }

    public void setJoiningLegValue(String joiningLegValue) {
        this.joiningLegValue = joiningLegValue;
    }

    public String getJoiningLegName() {
        return joiningLegName;
    }

    public void setJoiningLegName(String joiningLegName) {
        this.joiningLegName = joiningLegName;
    }




    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getGenderValue() {
        return genderValue;
    }

    public void setGenderValue(String genderValue) {
        this.genderValue = genderValue;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }


    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }


    public String getDesignationId() {
        return designationId;
    }

    public void setDesignationId(String designationId) {
        this.designationId = designationId;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }
    public String getPayoutNo() {
        return payoutNo;
    }

    public void setPayoutNo(String payoutNo) {
        this.payoutNo = payoutNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchId() {
        return branchId;
    }

    public void setBranchId(String branchId) {
        this.branchId = branchId;
    }

    public String getEpinUsedDate() {
        return epinUsedDate;
    }

    public void setEpinUsedDate(String epinUsedDate) {
        this.epinUsedDate = epinUsedDate;
    }

    public String getEpinRegisteredTo() {
        return epinRegisteredTo;
    }

    public void setEpinRegisteredTo(String epinRegisteredTo) {
        this.epinRegisteredTo = epinRegisteredTo;
    }

    public String getEpinMemberName() {
        return epinMemberName;
    }

    public void setEpinMemberName(String epinMemberName) {
        this.epinMemberName = epinMemberName;
    }

    public String getEpinMamberID() {
        return epinMamberID;
    }

    public void setEpinMamberID(String epinMamberID) {
        this.epinMamberID = epinMamberID;
    }

    public String getEpinEpinNo() {
        return epinEpinNo;
    }

    public void setEpinEpinNo(String epinEpinNo) {
        this.epinEpinNo = epinEpinNo;
    }

    public String getEpinDesignation() {
        return epinDesignation;
    }

    public void setEpinDesignation(String epinDesignation) {
        this.epinDesignation = epinDesignation;
    }

    public String getEpinCreatedDate() {
        return epinCreatedDate;
    }

    public void setEpinCreatedDate(String epinCreatedDate) {
        this.epinCreatedDate = epinCreatedDate;
    }

    String epinUsedDate;

    public String getPayoutDdTotalAdvance() {
        return payoutDdTotalAdvance;
    }

    public void setPayoutDdTotalAdvance(String payoutDdTotalAdvance) {
        this.payoutDdTotalAdvance = payoutDdTotalAdvance;
    }

    public String getPayoutDdPaymentAmount() {
        return payoutDdPaymentAmount;
    }

    public void setPayoutDdPaymentAmount(String payoutDdPaymentAmount) {
        this.payoutDdPaymentAmount = payoutDdPaymentAmount;
    }

    public String getPayoutDdChequeBank() {
        return payoutDdChequeBank;
    }

    public void setPayoutDdChequeBank(String payoutDdChequeBank) {
        this.payoutDdChequeBank = payoutDdChequeBank;
    }

    public String getPayoutDdChequeDate() {
        return payoutDdChequeDate;
    }

    public void setPayoutDdChequeDate(String payoutDdChequeDate) {
        this.payoutDdChequeDate = payoutDdChequeDate;
    }

    public String getPayoutDdChequeNo() {
        return payoutDdChequeNo;
    }

    public void setPayoutDdChequeNo(String payoutDdChequeNo) {
        this.payoutDdChequeNo = payoutDdChequeNo;
    }

    public String getPayoutDdMemberID() {
        return payoutDdMemberID;
    }

    public void setPayoutDdMemberID(String payoutDdMemberID) {
        this.payoutDdMemberID = payoutDdMemberID;
    }

    public String getPayoutDdMemberName() {
        return payoutDdMemberName;
    }

    public void setPayoutDdMemberName(String payoutDdMemberName) {
        this.payoutDdMemberName = payoutDdMemberName;
    }

    public String getPayoutDdPaymentDate() {
        return payoutDdPaymentDate;
    }

    public void setPayoutDdPaymentDate(String payoutDdPaymentDate) {
        this.payoutDdPaymentDate = payoutDdPaymentDate;
    }

    public String getPayoutDdPaymentMode() {
        return payoutDdPaymentMode;
    }

    public void setPayoutDdPaymentMode(String payoutDdPaymentMode) {
        this.payoutDdPaymentMode = payoutDdPaymentMode;
    }

    public String getPayoutDdPaymentRemark() {
        return payoutDdPaymentRemark;
    }

    public void setPayoutDdPaymentRemark(String payoutDdPaymentRemark) {
        this.payoutDdPaymentRemark = payoutDdPaymentRemark;
    }

    String payoutDdTotalAdvance;


    public String getEmiCltotalPaidAmount() {
        return emiCltotalPaidAmount;
    }

    public void setEmiCltotalPaidAmount(String emiCltotalPaidAmount) {
        this.emiCltotalPaidAmount = emiCltotalPaidAmount;
    }

    public String getEmiClcontactNo() {
        return emiClcontactNo;
    }

    public void setEmiClcontactNo(String emiClcontactNo) {
        this.emiClcontactNo = emiClcontactNo;
    }

    public String getEmiClLastPaidDate() {
        return emiClLastPaidDate;
    }

    public void setEmiClLastPaidDate(String emiClLastPaidDate) {
        this.emiClLastPaidDate = emiClLastPaidDate;
    }

    public String getEmiCllastPaidEMIDueDate() {
        return emiCllastPaidEMIDueDate;
    }

    public void setEmiCllastPaidEMIDueDate(String emiCllastPaidEMIDueDate) {
        this.emiCllastPaidEMIDueDate = emiCllastPaidEMIDueDate;
    }

    public String getEmiClplotArea() {
        return emiClplotArea;
    }

    public void setEmiClplotArea(String emiClplotArea) {
        this.emiClplotArea = emiClplotArea;
    }

    public String getEmiClplotBookingDate() {
        return emiClplotBookingDate;
    }

    public void setEmiClplotBookingDate(String emiClplotBookingDate) {
        this.emiClplotBookingDate = emiClplotBookingDate;
    }

    public String getEmiClplotHolderName() {
        return emiClplotHolderName;
    }

    public void setEmiClplotHolderName(String emiClplotHolderName) {
        this.emiClplotHolderName = emiClplotHolderName;
    }

    public String getEmiClplotNo() {
        return emiClplotNo;
    }

    public void setEmiClplotNo(String emiClplotNo) {
        this.emiClplotNo = emiClplotNo;
    }

    public String getEmiClplotRate() {
        return emiClplotRate;
    }

    public void setEmiClplotRate(String emiClplotRate) {
        this.emiClplotRate = emiClplotRate;
    }

    public String getEmiClplotStatus() {
        return emiClplotStatus;
    }

    public void setEmiClplotStatus(String emiClplotStatus) {
        this.emiClplotStatus = emiClplotStatus;
    }

    public String getEmiClplotType() {
        return emiClplotType;
    }

    public void setEmiClplotType(String emiClplotType) {
        this.emiClplotType = emiClplotType;
    }

    public String getEmiClsiteName() {
        return emiClsiteName;
    }

    public void setEmiClsiteName(String emiClsiteName) {
        this.emiClsiteName = emiClsiteName;
    }

    String emiCltotalPaidAmount;

    public String getDueEmiProjectName() {
        return dueEmiProjectName;
    }

    public void setDueEmiProjectName(String dueEmiProjectName) {
        this.dueEmiProjectName = dueEmiProjectName;
    }

    public String getDueEmiPlotAmount() {
        return dueEmiPlotAmount;
    }

    public void setDueEmiPlotAmount(String dueEmiPlotAmount) {
        this.dueEmiPlotAmount = dueEmiPlotAmount;
    }

    public String getDueEmiPlotArea() {
        return dueEmiPlotArea;
    }

    public void setDueEmiPlotArea(String dueEmiPlotArea) {
        this.dueEmiPlotArea = dueEmiPlotArea;
    }

    public String getDueEmiPlotNo() {
        return dueEmiPlotNo;
    }

    public void setDueEmiPlotNo(String dueEmiPlotNo) {
        this.dueEmiPlotNo = dueEmiPlotNo;
    }

    public String getDueEmiPaidAmount() {
        return dueEmiPaidAmount;
    }

    public void setDueEmiPaidAmount(String dueEmiPaidAmount) {
        this.dueEmiPaidAmount = dueEmiPaidAmount;
    }

    public String getDueEmiDueDate() {
        return dueEmiDueDate;
    }

    public void setDueEmiDueDate(String dueEmiDueDate) {
        this.dueEmiDueDate = dueEmiDueDate;
    }

    public String getDueEmidueAmount() {
        return dueEmidueAmount;
    }

    public void setDueEmidueAmount(String dueEmidueAmount) {
        this.dueEmidueAmount = dueEmidueAmount;
    }

    public String getDueEmiCustomerId() {
        return dueEmiCustomerId;
    }

    public void setDueEmiCustomerId(String dueEmiCustomerId) {
        this.dueEmiCustomerId = dueEmiCustomerId;
    }

    public String getDueEmiCustomerName() {
        return dueEmiCustomerName;
    }

    public void setDueEmiCustomerName(String dueEmiCustomerName) {
        this.dueEmiCustomerName = dueEmiCustomerName;
    }

    String dueEmiProjectName;

    public String getBirthdayName() {
        return birthdayName;
    }

    public void setBirthdayName(String birthdayName) {
        this.birthdayName = birthdayName;
    }

    public String getBirthdayMobile() {
        return birthdayMobile;
    }

    public void setBirthdayMobile(String birthdayMobile) {
        this.birthdayMobile = birthdayMobile;
    }

    public String getBirthdaymemType() {
        return birthdaymemType;
    }

    public void setBirthdaymemType(String birthdaymemType) {
        this.birthdaymemType = birthdaymemType;
    }

    public String getBirthdayLoginId() {
        return birthdayLoginId;
    }

    public void setBirthdayLoginId(String birthdayLoginId) {
        this.birthdayLoginId = birthdayLoginId;
    }

    public String getBirthdayDob() {
        return birthdayDob;
    }

    public void setBirthdayDob(String birthdayDob) {
        this.birthdayDob = birthdayDob;
    }

    String birthdayMobile;

    public String getPlotBookingAmount() {
        return plotBookingAmount;
    }

    public void setPlotBookingAmount(String plotBookingAmount) {
        this.plotBookingAmount = plotBookingAmount;
    }

    public String getPlotBookingBalanceAmount() {
        return plotBookingBalanceAmount;
    }

    public void setPlotBookingBalanceAmount(String plotBookingBalanceAmount) {
        this.plotBookingBalanceAmount = plotBookingBalanceAmount;
    }

    public String getPlotBookingHolderId() {
        return plotBookingHolderId;
    }

    public void setPlotBookingHolderId(String plotBookingHolderId) {
        this.plotBookingHolderId = plotBookingHolderId;
    }

    public String getPlotBookingHolderName() {
        return plotBookingHolderName;
    }

    public void setPlotBookingHolderName(String plotBookingHolderName) {
        this.plotBookingHolderName = plotBookingHolderName;
    }

    public String getPlotBookingMobile() {
        return plotBookingMobile;
    }

    public void setPlotBookingMobile(String plotBookingMobile) {
        this.plotBookingMobile = plotBookingMobile;
    }

    public String getPlotBookingPlotNo() {
        return plotBookingPlotNo;
    }

    public void setPlotBookingPlotNo(String plotBookingPlotNo) {
        this.plotBookingPlotNo = plotBookingPlotNo;
    }

    public String getPlotBookingSiteName() {
        return plotBookingSiteName;
    }

    public void setPlotBookingSiteName(String plotBookingSiteName) {
        this.plotBookingSiteName = plotBookingSiteName;
    }

    public String getPlotBookingStatus() {
        return plotBookingStatus;
    }

    public void setPlotBookingStatus(String plotBookingStatus) {
        this.plotBookingStatus = plotBookingStatus;
    }


    public String getChequeChequeDate() {
        return chequeChequeDate;
    }

    public void setChequeChequeDate(String chequeChequeDate) {
        this.chequeChequeDate = chequeChequeDate;
    }

    public String getChequeAmount() {
        return chequeAmount;
    }

    public void setChequeAmount(String chequeAmount) {
        this.chequeAmount = chequeAmount;
    }

    public String getChequeChequeNo() {
        return chequeChequeNo;
    }

    public void setChequeChequeNo(String chequeChequeNo) {
        this.chequeChequeNo = chequeChequeNo;
    }

    public String getChequePartyName() {
        return chequePartyName;
    }

    public void setChequePartyName(String chequePartyName) {
        this.chequePartyName = chequePartyName;
    }

    public String getChequeReceiptNo() {
        return chequeReceiptNo;
    }

    public void setChequeReceiptNo(String chequeReceiptNo) {
        this.chequeReceiptNo = chequeReceiptNo;
    }

    public String getChequeStatus() {
        return chequeStatus;
    }

    public void setChequeStatus(String chequeStatus) {
        this.chequeStatus = chequeStatus;
    }


    public String getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(String cashAmount) {
        this.cashAmount = cashAmount;
    }

    public String getCashChequeNo() {
        return cashChequeNo;
    }

    public void setCashChequeNo(String cashChequeNo) {
        this.cashChequeNo = cashChequeNo;
    }

    public String getCashChequeDate() {
        return cashChequeDate;
    }

    public void setCashChequeDate(String cashChequeDate) {
        this.cashChequeDate = cashChequeDate;
    }

    public String getCashPartyName() {
        return cashPartyName;
    }

    public void setCashPartyName(String cashPartyName) {
        this.cashPartyName = cashPartyName;
    }

    public String getCashReceiptNo() {
        return cashReceiptNo;
    }

    public void setCashReceiptNo(String cashReceiptNo) {
        this.cashReceiptNo = cashReceiptNo;
    }

    public String getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(String cashStatus) {
        this.cashStatus = cashStatus;
    }


    public String getCbSerialNo() {
        return cbSerialNo;
    }

    public void setCbSerialNo(String cbSerialNo) {
        this.cbSerialNo = cbSerialNo;
    }


    public String getCbCustomerName() {
        return cbCustomerName;
    }

    public void setCbCustomerName(String cbCustomerName) {
        this.cbCustomerName = cbCustomerName;
    }

    public String getCbSiteName() {
        return cbSiteName;
    }

    public void setCbSiteName(String cbSiteName) {
        this.cbSiteName = cbSiteName;
    }

    public String getCbPlotNo() {
        return cbPlotNo;
    }

    public void setCbPlotNo(String cbPlotNo) {
        this.cbPlotNo = cbPlotNo;
    }

    public String getCbBookingId() {
        return cbBookingId;
    }

    public void setCbBookingId(String cbBookingId) {
        this.cbBookingId = cbBookingId;
    }

    public String getCbBookingDate() {
        return cbBookingDate;
    }

    public void setCbBookingDate(String cbBookingDate) {
        this.cbBookingDate = cbBookingDate;
    }

    public String getCbCancelDate() {
        return cbCancelDate;
    }

    public void setCbCancelDate(String cbCancelDate) {
        this.cbCancelDate = cbCancelDate;
    }


    public String getPbPayountNo() {
        return pbPayountNo;
    }

    public void setPbPayountNo(String pbPayountNo) {
        this.pbPayountNo = pbPayountNo;
    }

    public String getPbNetAmount() {
        return pbNetAmount;
    }

    public void setPbNetAmount(String pbNetAmount) {
        this.pbNetAmount = pbNetAmount;
    }

    public String getPbGrossAmout() {
        return pbGrossAmout;
    }

    public void setPbGrossAmout(String pbGrossAmout) {
        this.pbGrossAmout = pbGrossAmout;
    }

    public String getPbClosingDate() {
        return pbClosingDate;
    }

    public void setPbClosingDate(String pbClosingDate) {
        this.pbClosingDate = pbClosingDate;
    }

    public String getPbProcessingFee() {
        return pbProcessingFee;
    }

    public void setPbProcessingFee(String pbProcessingFee) {
        this.pbProcessingFee = pbProcessingFee;
    }

    public String getPbSelfIncm() {
        return pbSelfIncm;
    }

    public void setPbSelfIncm(String pbSelfIncm) {
        this.pbSelfIncm = pbSelfIncm;
    }

    public String getPbTeamIncm() {
        return pbTeamIncm;
    }

    public void setPbTeamIncm(String pbTeamIncm) {
        this.pbTeamIncm = pbTeamIncm;
    }

    public String getPbTds() {
        return pbTds;
    }

    public void setPbTds(String pbTds) {
        this.pbTds = pbTds;
    }


    public String getPayotlistNo() {
        return payotlistNo;
    }

    public void setPayotlistNo(String payotlistNo) {
        this.payotlistNo = payotlistNo;
    }


    public String getPayoutMember() {
        return payoutMember;
    }

    public void setPayoutMember(String payoutMember) {
        this.payoutMember = payoutMember;
    }

    public String getNetAmount() {
        return netAmount;
    }

    public void setNetAmount(String netAmount) {
        this.netAmount = netAmount;
    }

    public String getGrossAmount() {
        return grossAmount;
    }

    public void setGrossAmount(String grossAmount) {
        this.grossAmount = grossAmount;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }


    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getPdrecordno() {
        return pdrecordno;
    }

    public void setPdrecordno(String pdrecordno) {
        this.pdrecordno = pdrecordno;
    }
}
