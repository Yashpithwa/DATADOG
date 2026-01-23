package com.example.Datadog.service;


import com.example.Datadog.user.User;
import jakarta.persistence.*;

@Entity
@Table(name="service")
public class MointoredService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String serviceName;

    private String baseUrl;

    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "user_id")   // ✅ correct
    private User user;


    public Long getId(){return id;}

    public String getServiceName(){return serviceName;}
    public void setServiceName(String serviceName){this.serviceName=serviceName;}


    public String getBaseUrl(){return baseUrl;}
    public void setBaseUrl(String baseUrl){this.baseUrl=baseUrl;}

    public boolean isActive(){return active;}
    public void setActive(boolean active){this.active=active;}

    public User getUser(){return user;}
    public void setUser(User user){this.user = user;}

}
