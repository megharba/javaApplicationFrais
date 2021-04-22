package com.sio.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sio.model.FicheFrais;
import com.sio.model.LigneFraisForfait;
import com.sio.model.LigneFraisHorsForfait;
import com.sio.model.LoginBean;
import com.sio.model.Visiteur;
import com.sio.utils.JDBCUtils;


/**
 * This DAO class provides CRUD database operations for the table todos in the
 * database.
 * 
 * @author Ramesh Fadatare
 *
 */

public class SaisieFraisDaoImpl {

	private static final String INSERT_TODOS_SQL = "INSERT INTO todos"
			+ "  (title, username, description, target_date,  is_done) VALUES " + " (?, ?, ?, ?, ?);";
//
//	private static final String SELECT_TODO_BY_ID = "select id,title,username,description,target_date,is_done from todos where id =?";
//	private static final String SELECT_ALL_TODOS = "select * from todos";
//	private static final String DELETE_TODO_BY_ID = "delete from todos where id = ?;";
//	private static final String UPDATE_TODO = "update todos set title = ?, username= ?, description =?, target_date =?, is_done = ? where id = ?;";

	public SaisieFraisDaoImpl() {
	}
	
	
				 
	public static void creeNouvellesLignesFrais( int idVisiteur, String mois) throws SQLException 
	{
		FicheFrais ficheFrais = null;// = new FicheFrais(mois, id, nbJustifs, montantValide, dateModif, idEtat, libEtat, lignesFraisHorsForfait, lignesFraisForfait)
		ficheFrais = SaisieFraisDaoImpl.getUneFicheFrais(idVisiteur, mois);
		System.out.println("ficheFrais"  + ficheFrais);
		if(ficheFrais == null){
					try (Connection connection = JDBCUtils.getConnection();
					PreparedStatement ps = connection.prepareStatement("INSERT INTO fichefrais"
							+"(idutilisateur,mois,nbJustificatifs,montantValide,dateModif,idEtat) VALUES"
							+" (?, ?, ?, ?, ?,?)")){
						ps.setInt(1, idVisiteur);
						ps.setString(2, mois);
						ps.setInt(3,0);
						ps.setInt(4, 0);
						ps.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
						ps.setString(6, "CR");
					ps.execute();
					ps.close();
					System.out.println(ps);
					}
					catch (SQLException exception) {
						JDBCUtils.printSQLException(exception);
					}
		
				
					
				
		}
				
				} 
	
//	public static void creeNouvellesLignesFrais( int idVisiteur, String mois) throws SQLException 
//	{
//		FicheFrais ficheFrais = null;// = new FicheFrais(mois, id, nbJustifs, montantValide, dateModif, idEtat, libEtat, lignesFraisHorsForfait, lignesFraisForfait)
//		ficheFrais = SaisieFraisDaoImpl.getUneFicheFrais(idVisiteur, mois);
//		System.out.println("ficheFrais"  + ficheFrais);
//		if(ficheFrais == null){
//					try (Connection connection = JDBCUtils.getConnection();
//					PreparedStatement ps = connection.prepareStatement("INSERT INTO fichefrais"
//							+"(idutilisateur,mois,nbJustificatifs,montantValide,dateModif,idEtat) VALUES"
//							+" (?, ?, ?, ?, ?,?)")){
//						ps.setInt(1, idVisiteur);
//						ps.setString(2, mois);
//						ps.setInt(3,0);
//						ps.setInt(4, 0);
//						ps.setDate(5, java.sql.Date.valueOf(java.time.LocalDate.now()));
//						ps.setString(6, "CR");
//					ps.execute();
//					ps.close();
//					System.out.println(ps);
//					}
//					catch (SQLException exception) {
//						JDBCUtils.printSQLException(exception);
//					}
//		
//					ArrayList<String> lignesId = new ArrayList<String>();
//					lignesId = getLesIdFrais();
//					
//					for(String id : lignesId){
//						String unIdFrais = id;
//						try (Connection connection = JDBCUtils.getConnection();
//					PreparedStatement ps = connection.prepareStatement("insert into lignefraisforfait"
//							+"(idutilisateur,mois,idFraisForfait,quantite) values"
//							+"(?,?,?,?)")) {
//							ps.setInt(1, idVisiteur);
//							ps.setString(2, mois);
//							ps.setString(3,unIdFrais);
//							ps.setInt(4, 0);
//					ps.execute();
//					ps.close();
//					System.out.println(ps);
//					}
//						catch (SQLException exception) {
//							JDBCUtils.printSQLException(exception);
//						}
//					}
//		}
//				
//				} 
			
	/**
 * Retourne tous les id de la table FraisForfait
	 * @return 
 
 * @return un tableau associatif 
	 * @throws SQLException 
*/
	public static ArrayList<String> getLesIdFrais() throws SQLException{
		ArrayList<String> lignes = new ArrayList<String>();
	//ArrayList lignes = new ArrayList();

		Connection conn = JDBCUtils.getConnection();
		
		ResultSet res = null;


		Statement statement;
		statement = conn.createStatement();
		
		String req = "select fraisforfait.id as idfrais from fraisforfait order by fraisforfait.id";
		res = statement.executeQuery(req);

		while(res.next()){
			String id = res.getString("idfrais");
			//Integer id = res.getInt("idfrais");
			lignes.add(id);
					
		}
	
		return lignes;
	}
	


	/**
	 * Retourne toutes les fiches de frais sous forme d'ArrayList d'un Visiteur dont
	 * l'id est pass� en param�tre. 
	 * 
	 * @param id
	 * 		L'id du visiteur.
	 * @return Toutes les fiches de frais sous forme du Visiteur.
	 * @throws SQLException
	 */
	
	public static  ArrayList<FicheFrais> get_fiches_frais_visiteur(int id) throws SQLException 
	{
		
		Connection c = JDBCUtils.getConnection();
		ArrayList<FicheFrais> lesFichesFrais = new ArrayList<FicheFrais>();
		String req = "SELECT fichefrais.*, etat.id as idEt, etat.libelle as libEtat " +
					"FROM fichefrais, utilisateur, etat " +
					"WHERE fichefrais.idutilisateur = utilisateur.id " +
					"AND etat.id = fichefrais.idetat " +
					"AND idutilisateur = '"+id+"' "; 
		ResultSet res = c.createStatement().executeQuery(req);
		
		while(res.next())
		{
			String mois = res.getString("mois");
			int nbJustifs = res.getInt("nbjustificatifs");
			double montantValide = res.getDouble("montantvalide");
			Date dateModif = res.getDate("datemodif");
			String idEtat = res.getString("idEt");
			String libEtat = res.getString("libEtat");			
			lesFichesFrais.add(new FicheFrais(mois,id,nbJustifs,montantValide,dateModif,idEtat,libEtat,get_lignes_frais_hors_forfait(id,mois),get_lignes_frais_forfait(id,mois)));
		
		}
		res.close();
		return lesFichesFrais;
	}
	
	/**
	 * Retourne toutes les lignes frais forfait d'une fiche de frais identifi� par
	 * l'identifiant du visiteur et le mois correspondant � la fiche.
	 * 
	 * @param idVisiteur
	 * 		Identifiant du visiteur � qui la fiche appartient.
	 * @param mois
	 * 		Mois coorespondant � la fiche.
	 * @return
	 * 		Une ArrayList de LigneFraisForfait correspondant � la fiche.
	 *  
	 * @throws SQLException
	 */

	public static ArrayList<LigneFraisForfait> get_lignes_frais_forfait(int idVisiteur, String mois) throws SQLException
	{
		ArrayList<LigneFraisForfait> listeLigneFraisForfait = new ArrayList <LigneFraisForfait>();	
	
		Connection conn = JDBCUtils.getConnection();
		Statement statement = conn.createStatement();
		String req = "SELECT * FROM lignefraisforfait, fraisforfait " +
				"WHERE idutilisateur = '"+idVisiteur+"' "+
				"AND mois = '"+mois+"'" +
				"AND fraisforfait.id = idfraisforfait";
		ResultSet res = statement.executeQuery(req);
		
		while(res.next()) {
			LigneFraisForfait ligne = new LigneFraisForfait(res.getString("idfraisforfait"), res.getString("libelle"), res.getInt("quantite"), res.getDouble("montant"));
			listeLigneFraisForfait.add(ligne);
		}

		return listeLigneFraisForfait;
	}
	
	
	
	public static ArrayList<Visiteur> get_all_visiteurs() throws SQLException
	{
		ArrayList<Visiteur> listeVisiteurs = new ArrayList <Visiteur>();	
		
		Connection conn = JDBCUtils.getConnection();
		Statement statement = conn.createStatement();
		String req = "SELECT * FROM utilisateur";
		ResultSet res = statement.executeQuery(req);
		
		while(res.next()) {
			
		Visiteur visiteur = new Visiteur(res.getInt("id"), res.getString("nom"), res.getString("prenom"), res.getString("ville"),res.getString("adresse"),res.getString("cp"), res.getString("profile"), res.getDate("date_embauche"));
			listeVisiteurs.add(visiteur);
		}

		return listeVisiteurs;
	}
	
	/**
	 * Retourne toutes les lignes frais hors forfait d'une fiche de frais identifi� par
	 * l'identifiant du visiteur et le mois correspondant � la fiche.
	 * 
	 * @param idVisiteur
	 * 		Identifiant du visiteur � qui la fiche appartient.
	 * @param mois
	 * 		Mois coorespondant � la fiche.
	 * @return
	 * 		Une ArrayList de LigneFraisHorsForfait "attach�" � la fiche.
	 *  
	 * @throws SQLException
	 */
	
	public static ArrayList<LigneFraisHorsForfait> get_lignes_frais_hors_forfait(int idVisiteur, String mois) throws SQLException
	{
		Connection conn = JDBCUtils.getConnection();
		ArrayList <LigneFraisHorsForfait> listeHorsForfait = new ArrayList <LigneFraisHorsForfait>();
		ResultSet res = null;


		Statement statement = conn.createStatement();
		String req = "SELECT * FROM lignefraishorsforfait " +
				"WHERE  idutilisateur = '"+idVisiteur+"'" +
				"AND mois = '" +mois+"' " +
				"order by id";
		res = statement.executeQuery(req);

		while(res.next()){
			LigneFraisHorsForfait unHorsForfait = new LigneFraisHorsForfait(res.getInt("id"), res.getString("date"), res.getDouble("montant"), res.getString("libelle"));
			listeHorsForfait.add(unHorsForfait);				
		}
		
		res.close();
		statement.close();

		
		return listeHorsForfait;
	}
	
	
	public static  FicheFrais getUneFicheFrais(int idVisiteur, String mois) throws SQLException
	{
		Connection conn = JDBCUtils.getConnection();
		ArrayList<LigneFraisForfait> listeLigneFraisForfait = get_lignes_frais_forfait(idVisiteur, mois);	
		ArrayList<LigneFraisHorsForfait> listeLigneFraisHorsForfait = get_lignes_frais_hors_forfait(idVisiteur,mois);
	
		ResultSet res = null;
		FicheFrais fichefrais = null;

		Statement statement = conn.createStatement();
		String req = "SELECT * FROM fichefrais " +
				"WHERE  idutilisateur = '"+idVisiteur+"'" +
				"AND mois = '" +mois+"' "; 
		res = statement.executeQuery(req);

		while(res.next()){
			 fichefrais = new FicheFrais(res.getString("mois"), res.getInt("idutilisateur"), res.getInt("nbjustificatifs") ,res.getDouble("montantvalide"),  res.getDate("datemodif"), res.getString("idetat") , "", listeLigneFraisHorsForfait,listeLigneFraisForfait );
					
		}
		
		res.close();
		statement.close();

		
		return fichefrais;
	}
	
	
	public static  LigneFraisHorsForfait getUneLigneHF(int idLigneHF) throws SQLException
	{
		Connection conn = JDBCUtils.getConnection();
Date date ;
Double montant =0.0;
String libelle = "";
		ResultSet res = null;
		//LigneFraisHorsForfait ligneFraisHf = null;

		Statement statement = conn.createStatement();
		String req = " SELECT date, libelle, montant FROM lignefraishorsforfait where id ='" +idLigneHF+"'";
			
		res = statement.executeQuery(req);
		LigneFraisHorsForfait ligneFraisHf =  new LigneFraisHorsForfait(idLigneHF, "", 0.0, "");  
		
		if (res.next())
		{
			ligneFraisHf.setMontant(res.getDouble("montant"));//("montant"));
//			date = res.getDate("date");
			montant = res.getDouble("montant");
			ligneFraisHf.setDate(res.getDate("date").toString());
			ligneFraisHf.setLibelle(res.getString("libelle"));
			ligneFraisHf.setId(idLigneHF);
			 
			System.out.println("res.getString('date') " + res.getDate("date"));
			System.out.println("res.getDouble(montant) " + res.getDouble("montant"));
			System.out.println("res.getString(libelle) " + res.getString("libelle"));
//			String	date =res.getString("date");
//			Double	montant=res.getDouble("montant");
//			String	libelle= res.getString("libelle");
//			ligneFraisHf = new LigneFraisHorsForfait(idLigneHF, date, montant, libelle);	
			 
		}
//		ligneFraisHf = new LigneFraisHorsForfait(idLigneHF, date, montant, libelle);	
		
//		System.out.println("ligneFraisHf " + ligneFraisHf);
		System.out.println("res.getString('date') " + ligneFraisHf.getDate() );
		System.out.println("res.getDouble(montant) " + ligneFraisHf.getMontant());
		System.out.println("res.getString(libelle) " + ligneFraisHf.getLibelle());
		
		
		res.close();
		statement.close();

		
		return ligneFraisHf;
	}
	
	
	public static void   DeleteUneFicheFrais(int idVisiteur, String mois) throws SQLException
	{
		
		Connection conn = JDBCUtils.getConnection();
	
			
			Statement statement = conn.createStatement();
			String req2 = "DELETE  FROM lignefraisforfait " +
					"WHERE  idutilisateur = '"+idVisiteur+"'" +
					"AND mois = '" +mois+"' ";
			statement.executeUpdate(req2);
			statement.close();
	
		
		conn = JDBCUtils.getConnection();
		statement = conn.createStatement();
		String req = "DELETE  FROM fichefrais " +
				"WHERE  idutilisateur = '"+idVisiteur+"'" +
				"AND mois = '" +mois+"' ";
		statement.executeUpdate(req);
		statement.close();

		
	conn = JDBCUtils.getConnection();

		statement = conn.createStatement();
		String req3 = "DELETE  FROM lignefraishorsforfait " +
				"WHERE  idutilisateur = '"+idVisiteur+"'" +
				"AND mois = '" +mois+"' "; 
		 statement.executeUpdate(req3);
		statement.close();
	}
	
	
	public static void   deleteFraisHF(String id) throws SQLException
	{
		
		Connection conn = JDBCUtils.getConnection();
		Statement statement = conn.createStatement();
			
		statement = conn.createStatement();
		String req = "DELETE  FROM lignefraishorsforfait " +
				"WHERE  id = '"+id+"'"; 
		 statement.executeUpdate(req);
		statement.close();
	}
	

	
	/**
	 * Valide un fiche de frais en prenant compte des modifications apport�es.
	 * 
	 * @param ficheFrais 
	 * 		La fiche Frais � valider.
	 * @throws SQLException 
	 */
	

	public static void validerLigneFraisHorsForfait(FicheFrais ficheFrais) throws SQLException 
	{
		Connection c = JDBCUtils.getConnection();

		
		//Mise � jour des lignes hors forfaits
		for(LigneFraisHorsForfait hf : ficheFrais.getLignesFraisHorsForfait())
		{
			String req = "INSERT INTO lignefraishorsforfait "
					+"(idutilisateur, montant , libelle, date, mois ) values" 
					+"(?,?,?,?,?)";
			
			PreparedStatement ps = c.prepareStatement(req);
			
			ps.setInt(1, ficheFrais.getIdVisiteur());
			ps.setDouble(2, hf.getMontant());
			ps.setString(3, hf.getLibelle());
			ps.setString(4, hf.getDate());
			ps.setString(5, ficheFrais.getMois());
			
			System.out.println(ps);
			
			ps.execute();
			ps.close();
		}
	
	}
	
	
	
	public static void validerFraisForfait(FicheFrais ficheFrais) throws SQLException 
	{
		Connection c = JDBCUtils.getConnection();
		
//		ArrayList<String> lignesId = new ArrayList<String>();
//		lignesId = getLesIdFrais();
//		for(String id : lignesId){
			//String unIdFrais = id;
		for(LigneFraisForfait l : ficheFrais.getLignesFraisForfait())
		{
			try (Connection connection = JDBCUtils.getConnection();
		PreparedStatement ps = connection.prepareStatement("insert into lignefraisforfait"
				+"(idutilisateur,mois,idFraisForfait,quantite) values"
				+"(?,?,?,?)")) {
				ps.setInt(1, ficheFrais.getIdVisiteur());
				ps.setString(2,ficheFrais.getMois());
				ps.setString(3, l.getIdFraisForfait());
				ps.setInt(4, l.getQuantite());
		ps.execute();
		ps.close();
		System.out.println(ps);
		}
			catch (SQLException exception) {
				JDBCUtils.printSQLException(exception);
			}
		}
//		//Mise � jour des lignes frais forfait.
//		for(LigneFraisForfait l : ficheFrais.getLignesFraisForfait())
//		{
//			String req = "UPDATE lignefraisforfait " +
//					"SET quantite = ? " +
//					"WHERE idfraisforfait = ? " +
//					"AND mois = ? " +
//					"AND idutilisateur = ?";
//			PreparedStatement ps = c.prepareStatement(req);
//			
//			ps.setInt(1, l.getQuantite());
//			ps.setString(2, l.getIdFraisForfait());
//			ps.setString(3,ficheFrais.getMois());
//			ps.setInt(4, ficheFrais.getIdVisiteur());
//			ps.execute();
//			ps.close();
//		}
		
		
		//Mise � jour des lignes hors forfaits
		for(LigneFraisHorsForfait hf : ficheFrais.getLignesFraisHorsForfait())
		{
			String req = "INSERT INTO lignefraishorsforfait "
					+"(idutilisateur, montant , libelle, date, mois ) values" 
					+"(?,?,?,?,?)";
			
			PreparedStatement ps = c.prepareStatement(req);
			
			ps.setInt(1, ficheFrais.getIdVisiteur());
			ps.setDouble(2, hf.getMontant());
			ps.setString(3, hf.getLibelle());
			ps.setString(4,  hf.getDate());
			ps.setString(5, ficheFrais.getMois());
			
			System.out.println(ps);
			
			ps.execute();
			ps.close();
		}
		System.out.println("ficheFrais.getIdVisiteur()" + ficheFrais.getIdVisiteur());
		System.out.println("ficheFrais.getMois()" + ficheFrais.getMois());
		//Mise � jour de la Fiche de Frais		
		String req = "UPDATE fichefrais " +
				"SET  idetat = 'CL' " +
				"WHERE mois = ? " +
				"AND idutilisateur = ? ";
		PreparedStatement ps = c.prepareStatement(req);
		
//		ps.setInt(1, ficheFrais.getNbJustificatifs());
//		ps.setDouble(2, ficheFrais.getMontantValide());
		ps.setString(1, ficheFrais.getMois());
		ps.setInt(2, ficheFrais.getIdVisiteur());
		ps.execute();
		ps.close();		
	}
	


}


