package controller;

import entity.City;
import view.CityView;

public class CityController {
    private City city;
    private CityView cityView;

    public CityController(CityView cityView) {

        this.cityView = cityView;
    }
}
