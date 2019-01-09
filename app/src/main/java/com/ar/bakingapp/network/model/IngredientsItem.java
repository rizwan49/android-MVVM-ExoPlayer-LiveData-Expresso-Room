package com.ar.bakingapp.network.model;

import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

@Entity
public class IngredientsItem{

	@SerializedName("quantity")
	private float quantity;

	@SerializedName("measure")
	private String measure;

	@SerializedName("ingredient")
	private String ingredient;

	public void setQuantity(float quantity){
		this.quantity = quantity;
	}

	public float getQuantity(){
		return quantity;
	}

	public void setMeasure(String measure){
		this.measure = measure;
	}

	public String getMeasure(){
		return measure;
	}

	public void setIngredient(String ingredient){
		this.ingredient = ingredient;
	}

	public String getIngredient(){
		return ingredient;
	}

	@Override
 	public String toString(){
		return 
			"IngredientsItem{" + 
			"quantity = '" + quantity + '\'' + 
			",measure = '" + measure + '\'' + 
			",ingredient = '" + ingredient + '\'' + 
			"}";
		}


}