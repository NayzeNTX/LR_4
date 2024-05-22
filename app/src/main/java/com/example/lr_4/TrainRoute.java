package com.example.lr_4;

public class TrainRoute {
    public int _id;
    public String route;
    public String departureDate;
    public String arrivalDate;
    public TrainRoute(int id, String rt, String depDate, String arrDate){
        _id = id;
        route = rt;
        departureDate = depDate;
        arrivalDate = arrDate;
    }
}
