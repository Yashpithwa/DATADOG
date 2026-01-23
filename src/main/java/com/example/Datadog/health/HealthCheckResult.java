package com.example.Datadog.health;

public class HealthCheckResult {

    private String status;
    private long responseTime;
    private String error;

    public HealthCheckResult(String status,long responseTime,String error)
    {
        this.status = status;
        this.responseTime=responseTime;
        this.error=error;
    }


    public String getStatus(){
        return status;
    }
    public String getError(){
        return  error;
    }
    public long getResponseTime(){
        return responseTime;
    }
}
