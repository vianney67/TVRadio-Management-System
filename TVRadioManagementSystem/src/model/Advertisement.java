/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author admin
 */
public class Advertisement {

    private int adId;
    private String companyName;
    private int channelId;
    private int programId;
    private double costPaid;
    private Time adDuration;
    private Date adDate;
    private String paymentStatus;

    // ✅ Empty constructor
    public Advertisement() {}

    // ✅ Constructor for ADDING advertisement
    public Advertisement(String companyName, int channelId, int programId,
                         double costPaid, Time adDuration,
                         Date adDate, String paymentStatus) {

        this.companyName = companyName;
        this.channelId = channelId;
        this.programId = programId;
        this.costPaid = costPaid;
        this.adDuration = adDuration;
        this.adDate = adDate;
        this.paymentStatus = paymentStatus;
    }

    // ✅ Constructor for UPDATING advertisement
    public Advertisement(int adId, String companyName, int channelId, int programId,
                         double costPaid, Time adDuration,
                         Date adDate, String paymentStatus) {

        this.adId = adId;
        this.companyName = companyName;
        this.channelId = channelId;
        this.programId = programId;
        this.costPaid = costPaid;
        this.adDuration = adDuration;
        this.adDate = adDate;
        this.paymentStatus = paymentStatus;
    }

    // ✅ Getters & Setters
    public int getAdId() { return adId; }
    public void setAdId(int adId) { this.adId = adId; }

    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    public int getChannelId() { return channelId; }
    public void setChannelId(int channelId) { this.channelId = channelId; }

    public int getProgramId() { return programId; }
    public void setProgramId(int programId) { this.programId = programId; }

    public double getCostPaid() { return costPaid; }
    public void setCostPaid(double costPaid) { this.costPaid = costPaid; }

    public Time getAdDuration() { return adDuration; }
    public void setAdDuration(Time adDuration) { this.adDuration = adDuration; }

    public Date getAdDate() { return adDate; }
    public void setAdDate(Date adDate) { this.adDate = adDate; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
   
}
