package model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import javax.persistence.*;

@Entity
@Table(name = "advertisements")
public class Advertisement implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ad_id")
    private int adId;
    
    @Column(name = "company_name")
    private String companyName;
    
    @Column(name = "channel_id")
    private int channelId;
    
    @Column(name = "program_id")
    private int programId;
    
    @Column(name = "cost_paid")
    private double costPaid;
    
    @Column(name = "ad_duration")
    private Time adDuration;
    
    @Column(name = "ad_date")
    private Date adDate;
    
    @Column(name = "payment_status")
    private String paymentStatus;

    public Advertisement() {}

    public Advertisement(String companyName, int channelId, int programId, double costPaid, Time adDuration, Date adDate, String paymentStatus) {
        this.companyName = companyName;
        this.channelId = channelId;
        this.programId = programId;
        this.costPaid = costPaid;
        this.adDuration = adDuration;
        this.adDate = adDate;
        this.paymentStatus = paymentStatus;
    }

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
