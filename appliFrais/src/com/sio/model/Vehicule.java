package com.sio.model;

import java.sql.SQLException;


public class Vehicule {
String nom;
int id;
String matricule;



public Vehicule(int id, String nom, String matricule) throws SQLException
{
	this.id = id;
	this.nom = nom; 
	this.matricule = matricule; 
	
	
}



public String getNom() {
	return nom;
}



public void setNom(String nom) {
	this.nom = nom;
}



public int getId() {
	return id;
}



public void setId(int id) {
	this.id = id;
}



public String getMatricule() {
	return matricule;
}



public void setMatricule(String matricule) {
	this.matricule = matricule;
}



}
