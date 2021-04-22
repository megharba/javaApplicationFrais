package com.sio.model;

import java.awt.Color;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import com.sio.dao.SaisieFraisDaoImpl;
import com.sio.utils.JDBCUtils;

public class FicheFrais //implements Comparable
{
	private String mois;
	private int idVisiteur;
	private int nbJustificatifs;
	private double montantValide;
	private Date dateModif ;
	private String idEtat;
	private String libelleEtat;
	private Date date;


	private ArrayList<FicheFrais> ficheFrais;
	private ArrayList<LigneFraisForfait> lignesFraisForfait;
	private ArrayList<LigneFraisHorsForfait> lignesFraisHorsForfait;
	
	
	/**
	 * Constructeur de FicheFrais.
	 * 
	 * @param mois
	 * 		Mois correspondant � la fiche.
	 * @param id
	 * 		Id du visiteur dont la fiche appartient.
	 * @param nbJustifs
	 * 		Nombre de justificatifs.
	 * @param montantValide
	 * 		Ca je ne sais pas trop ce que c'est.
	 * @param dateModif
	 * 		Date de derni�re modification de la fiche.
	 * @param idEtat
	 * 		Identifiant de l'�tat de la fiche.
	 * @param libEtat
	 * 		Libell� de l'�tat de la fiche.
	 * @param lignesFraisHorsForfait
	 * 		ArrayList de LigneFraisForfait associ�es � la fiche.
	 * @param lignesFraisForfait
	 * 		ArrayList de LigneFraisHorforfait associ�es � la fiche.
	 */
	public FicheFrais(String mois, int id, int nbJustifs,
			double montantValide, Date dateModif, String idEtat,
			String libEtat,
			ArrayList<LigneFraisHorsForfait> lignesFraisHorsForfait,
			ArrayList<LigneFraisForfait> lignesFraisForfait) 
	{
		this.mois = mois;
		this.idVisiteur = id;
		this.nbJustificatifs = nbJustifs;
		this.montantValide = montantValide;
		this.dateModif = dateModif;
		this.idEtat = idEtat;
		this.libelleEtat = libEtat;
		
		this.lignesFraisForfait = lignesFraisForfait;
		this.lignesFraisHorsForfait = lignesFraisHorsForfait;
	}




	//getters et setters
	
	
	public String getIdEtat() 
	{
		return idEtat;
	}
	
	public ArrayList<FicheFrais> getFicheFrais() {
		return ficheFrais;
	}




	public void setFicheFrais(ArrayList<FicheFrais> ficheFrais) {
		this.ficheFrais = ficheFrais;
	}




	public void setLignesFraisForfait(ArrayList<LigneFraisForfait> lignesFraisForfait) {
		this.lignesFraisForfait = lignesFraisForfait;
	}




	public void setLignesFraisHorsForfait(ArrayList<LigneFraisHorsForfait> lignesFraisHorsForfait) {
		this.lignesFraisHorsForfait = lignesFraisHorsForfait;
	}




	public void setLibelleEtat(String libelleEtat) 
	{
		this.libelleEtat = libelleEtat;
	}
	
	public String getLibelleEtat() {
		return libelleEtat;
	}

	public void setIdEtat(String idEtat) {
		this.idEtat = idEtat;
	}
	
	public String getMois() {
		return mois;
	}

	public void setMois(String mois) {
		this.mois = mois;
	}

	public int getIdVisiteur() {
		return idVisiteur;
	}

	public void setIdVisiteur(int idVisiteur) {
		this.idVisiteur = idVisiteur;
	}

	public int getNbJustificatifs() {
		return nbJustificatifs;
	}

	public void setNbJustificatifs(int nbJustificatifs) {
		this.nbJustificatifs = nbJustificatifs;
	}

	public double getMontantValide() {
		return montantValide;
	}

	public void setMontantValide(double montantValide) {
		this.montantValide = montantValide;
	}

	public Date getDateModif() {
		return dateModif;
	}

	public void setDateModif(Date dateModif) {
		this.dateModif = dateModif;
	}
	
	public ArrayList<LigneFraisForfait> getLignesFraisForfait() {
		return lignesFraisForfait;
	}

	public ArrayList<LigneFraisHorsForfait> getLignesFraisHorsForfait() {
		return lignesFraisHorsForfait;
	}
	
//      _____________________________________
	

	
	
	public void valider() throws SQLException
	{
		//SaisieFraisDaoImpl.validerFicheFrais(this);
		idEtat = "VA";
		libelleEtat = "Valid�e et mise en paiement";
	}


//	public String toString()
//	{
//		return "Fiche de : "+formatMois();		
//	}
//
//	private String formatMois()
//	{
//		String annee = mois.substring(0,4);
//		String libMois = "";
//		
//		int numMois = Integer.parseInt(mois.substring(4,6));
//		switch(numMois)
//		{
//		case(1):
//			libMois = "Janvier";
//			break;
//		case(2):
//			libMois = "F�vrier";
//			break;
//		case(3):
//			libMois = "Mars";
//			break;
//		case(4):
//			libMois = "Avril";
//			break;
//		case(5):
//			libMois = "Mai";
//			break;
//		case(6):
//			libMois = "Juin";
//			break;
//		case(7):
//			libMois = "Juillet";
//			break;
//		case(8):
//			libMois = "Ao�t";
//			break;
//		case(9):
//			libMois = "Septembre";
//			break;
//		case(10):
//			libMois = "Octobre";
//			break;
//		case(11):
//			libMois = "Novembre";
//			break;
//		case(12):
//			libMois = "D�cembre";
//			break;
//		}
//		
//		return libMois + " " + annee; 
//	}
	
	/**
	 * Retourne une couleur en fonction de l'�tat de la fiche.
	 * @return
	 */
	public Color getColor()
	{
		if(idEtat.equals("CL"))
		{
			return Color.DARK_GRAY; //gris
		}
		else if(idEtat.equals("CR"))
		{
			return Color.orange;  //orange
		}
		else if(idEtat.equals("RB"))
		{
			return new Color(0,100,0); //Vert
		}
		else if(idEtat.equals("VA"))
		{
			return Color.blue; //bleu
		}
		else
		{
			return Color.black;  //noir
		}
		
	}

//	@Override
//	public int compareTo(Object o) 
//	{
//		boolean comparer;
//		int annee1 = Integer.parseInt(mois.substring(0,4));
//		int annee2 = Integer.parseInt(((FicheFrais)o).mois.substring(0,4));
//		
//		if(annee1 > annee2)
//		{
//			return -1;
//		}
//		else if(annee1 < annee2)
//		{
//			return 1;
//		}
//		
//		int numMois1 = Integer.parseInt(mois.substring(4,6));
//		int numMois2 = Integer.parseInt(((FicheFrais)o).mois.substring(4,6));
//		
//		if(numMois1 > numMois2)
//		{
//			return -1;
//		}
//		else if(numMois1 < numMois2)
//		{
//			return 1;
//		}
//		
//		return 0;
//	}
}
