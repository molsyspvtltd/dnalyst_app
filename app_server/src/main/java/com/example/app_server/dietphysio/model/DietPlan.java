package com.example.app_server.dietphysio.model;

import com.example.app_server.dietphysio.model.DietChart;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "diet_plans")
public class DietPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "chart_number", referencedColumnName = "chartNumber", nullable = false)
    @JsonBackReference
    private DietChart dietChart;

    private String day;

    @Enumerated(EnumType.STRING)
    private com.example.app_server.dietphysio.model.MealType mealTime;

    private String menu;

    private String recipe;

    private String caloriesProtein;

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public DietChart getDietChart() {
        return dietChart;
    }

    public void setDietChart(DietChart dietChart) {
        this.dietChart = dietChart;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public com.example.app_server.dietphysio.model.MealType getMealTime() {
        return mealTime;
    }

    public void setMealTime(com.example.app_server.dietphysio.model.MealType mealTime) {
        this.mealTime = mealTime;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getCaloriesProtein() {
        return caloriesProtein;
    }

    public void setCaloriesProtein(String caloriesProtein) {
        this.caloriesProtein = caloriesProtein;
    }
}
