package com.sio.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sio.dao.LoginDao;
import com.sio.dao.SaisieFraisDaoImpl;
import com.sio.dao.VisiteurDaoImpl;
import com.sio.model.FicheFrais;
import com.sio.model.LigneFraisForfait;
import com.sio.model.LigneFraisHorsForfait;
import com.sio.model.LoginBean;
import com.sio.model.Visiteur;


@WebServlet("/")
public class TodoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private SaisieFraisDaoImpl saisieDAO;
	//private LoginBean loginBean = new LoginBean();
	
	 LocalDate today = LocalDate.now();
	 int month = today.getMonthValue();
	 String mois = Integer.toString(month);
	 int year = today.getYear();
	 String now = Integer.toString(month)+"-"+Integer.toString(year);
	
	public void init() {
	
		saisieDAO = new SaisieFraisDaoImpl();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getServletPath();

		try {
			switch (action) {
			case "/new":
				showNewForm(request, response);
				break;
			case "/insert":
				insertTodo(request, response);
				break;
			case "/delete":
				deleteTodo(request, response);
				break;
			case "/edit":
				showEditForm(request, response);
				break;
			case "/deletehf":
				deleteFraisHorsFrais(request, response);
				break;
			case "/edithf":
				showEditFormHF(request, response);
				break;
			case "/update":
				updateTodo(request, response);
				break;
			case "/list":
				listTodo(request, response);
				break;
			case "/listHF":
				listTodoHF(request, response);
				break;
			case "/listAdmin":
				listAdmin(request, response);
				break;
			case "/newFraisHF":
				newFraisHF(request, response);
				break;
			case "/newUser":
				AddNew(request, response);
				break;
			case "/newFormUser":
				showNewFormUser(request, response);
				break;
			case "/listComptable":
				listComptable(request, response);
				break;
			default:
				RequestDispatcher dispatcher = request.getRequestDispatcher("login/login.jsp");
				dispatcher.forward(request, response);
				break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void listTodo(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		Integer i_id =LoginBean.getId();
		List<FicheFrais> listFrais = SaisieFraisDaoImpl.get_fiches_frais_visiteur(i_id);
		request.setAttribute("listFrais", listFrais);
		RequestDispatcher dispatcher = request.getRequestDispatcher("frais/frais-list.jsp");
		dispatcher.forward(request, response);
		

	}
	
	
	private void listComptable(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		Integer i_id =LoginBean.getId();
		List<FicheFrais> listFichefrais = SaisieFraisDaoImpl.get_fiches_frais_visiteur(i_id);
		request.setAttribute("listFrais", listFichefrais);
		RequestDispatcher dispatcher = request.getRequestDispatcher("comptable/listComptable.jsp");
		dispatcher.forward(request, response);
	}
	
	private void listAdmin(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		List<Visiteur> listVisiteur = SaisieFraisDaoImpl.get_all_visiteurs();
		request.setAttribute("listVisiteur", listVisiteur);
	
		RequestDispatcher dispatcher = request.getRequestDispatcher("admin/adminAccueil.jsp");
		dispatcher.forward(request, response);
	}

	
	private void listTodoHF(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		Integer i_id =LoginBean.getId();
		List<LigneFraisHorsForfait> listFraisHF = SaisieFraisDaoImpl.get_lignes_frais_hors_forfait(i_id, now);
		request.setAttribute("listFraisHF", listFraisHF);
		RequestDispatcher dispatcher = request.getRequestDispatcher("frais/fraisHorsForfait.jsp");
		dispatcher.forward(request, response);
	
	}
	
	private void showNewFormUser(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
	
		RequestDispatcher dispatcher = request.getRequestDispatcher("admin/adminNewUser.jsp");
		dispatcher.forward(request, response);
	}
	
	private void AddNew(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException {
		String cp = request.getParameter("codepostaleU");
	int cpInt =Integer.parseInt(cp) ;
		//SaisieFraisDaoImpl.addNewVisiteur(request.getParameter(" request.getParameter("dateNaissance"),  cp, request.getParameter("villeU"), request.getParameter("dateEmbauche"));		
	VisiteurDaoImpl.addNewVisiteur(request.getParameter("nomU"), request.getParameter("prenomU"),  request.getParameter("profileU"), request.getParameter("loginU"), request.getParameter("mdpU"), cpInt, request.getParameter("villeU"),request.getParameter("adresseu"), request.getParameter("dateEmbauche"));
		RequestDispatcher dispatcher = request.getRequestDispatcher("admin/adminAccueil.jsp");
		dispatcher.forward(request, response);
	}

	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {
		int id = LoginBean.getId();
		System.out.println("id:"+id);
		SaisieFraisDaoImpl.creeNouvellesLignesFrais(id, now);
		RequestDispatcher dispatcher = request.getRequestDispatcher("frais/saisieFrais.jsp");
		dispatcher.forward(request, response);
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
		int id = LoginBean.getId();
		String mois = request.getParameter("id");
		//ArrayList<FicheFrais> fichefrais = SaisieFraisDaoImpl.get_fiches_frais_visiteur(id);
		 FicheFrais ficheFrais = SaisieFraisDaoImpl.getUneFicheFrais(id, mois);
		RequestDispatcher dispatcher = request.getRequestDispatcher("frais/saisieFrais.jsp");
		request.setAttribute("ficheFrais", ficheFrais);
		dispatcher.forward(request, response);

	}
	
	private void showEditFormHF(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, ServletException, IOException {
	
		String idLigneHF = request.getParameter("id");
		System.out.println("idLigneHF dans showEditForm: " + idLigneHF);
		int id = Integer.parseInt(idLigneHF);
		System.out.println("id dans showEditForm: " + id);
		 LigneFraisHorsForfait ligneFraisHorsForfait = SaisieFraisDaoImpl.getUneLigneHF(id);
			System.out.println("ligneFraisHF dans showEditForm: " + ligneFraisHorsForfait);
		 request.setAttribute("ligneFraisHorsForfait", ligneFraisHorsForfait);
		 RequestDispatcher dispatcher = request.getRequestDispatcher("frais/fraisHorsForfait.jsp");
			
		dispatcher.forward(request, response);
		
		
	

	}

	private void insertTodo(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ServletException {
		
		int quatite= 0;
		String unIdFraisForfait = "";
		String libelle = " ";
		String libelleHF = "";
		int montant = 0;
		String dateHF = "";
		String dateFicheFrais=request.getParameter("targetDate");
		Integer i_id =LoginBean.getId();
		ArrayList<LigneFraisForfait> listeLigneFraisForfait = new ArrayList <LigneFraisForfait>();
		ArrayList<LigneFraisHorsForfait> listeLigneFraisHorsForfait = new ArrayList <LigneFraisHorsForfait>();
	    
		System.out.println("request.getParameter('enregistrer') "+request.getParameter("enregistrer") );
		System.out.println("request.getParameter('enregistrerHF') "+request.getParameter("enregistrerHF") );
		
		if (request.getParameter("enregistrer") != null) {
	    	if( request.getParameter("ETP") != null || request.getParameter("ETP") != ""){
				 quatite = Integer.parseInt(request.getParameter("ETP"));
				 unIdFraisForfait = "ETP";
				 libelle = "Etape";
				 
					LigneFraisForfait lff = new LigneFraisForfait(unIdFraisForfait, libelle, quatite, 0);
					listeLigneFraisForfait.add(lff);
					
			}
			if( request.getParameter("NUI") != null || request.getParameter("NUI") !=  ""){
				 quatite = Integer.parseInt(request.getParameter("NUI"));
				 unIdFraisForfait = "NUI";
				 libelle = "Nuité";
				
					LigneFraisForfait lff = new LigneFraisForfait(unIdFraisForfait, libelle, quatite, 0);
					listeLigneFraisForfait.add(lff);
			}
			if( request.getParameter("REP") != null || request.getParameter("REP") != ""){
				 quatite = Integer.parseInt(request.getParameter("REP"));
				 unIdFraisForfait = "REP";
				 libelle = "Repas midi";
				 
				 LigneFraisForfait lff = new LigneFraisForfait(unIdFraisForfait, libelle, quatite, 0);
					listeLigneFraisForfait.add(lff);
			}
			if( request.getParameter("KM") != null || request.getParameter("KM") != ""){
				 quatite = Integer.parseInt(request.getParameter("KM"));
				 unIdFraisForfait = "KM";
				 libelle = "kilometre";
				 
				 LigneFraisForfait lff = new LigneFraisForfait(unIdFraisForfait, libelle, quatite, 0);
					listeLigneFraisForfait.add(lff);
			}
			//A voir, créer une fiche frais à maintenant puis lui mettre à jour la date si elle est changé ?
			//Pour éviter un problème / Donc update la fiche frais en premier pour update les date des autres frais
			FicheFrais ficheFrais;
			ficheFrais = SaisieFraisDaoImpl.getUneFicheFrais(i_id, now);

			ficheFrais.setLignesFraisForfait(listeLigneFraisForfait);
			SaisieFraisDaoImpl.validerFraisForfait(ficheFrais);
			
			response.sendRedirect("list");
	    } 

	}
	
	
	private void newFraisHF(HttpServletRequest request, HttpServletResponse response) 
			throws SQLException, IOException, ParseException {
		Integer i_id =LoginBean.getId();
		ArrayList<LigneFraisHorsForfait> listeLigneFraisHorsForfait = new ArrayList <LigneFraisHorsForfait>();
	    
		if( request.getParameter("libelle") != null && request.getParameter("montant") != null ){
		double	montant =Double.parseDouble(request.getParameter("montant"));
	
		String dateHF = request.getParameter("dateHF");
//		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//		java.util.Date date = formatter.parse(dateHF);
		String libelleHF = request.getParameter("libelle");
		
			
				LigneFraisHorsForfait lfhr = new LigneFraisHorsForfait(dateHF, montant, libelleHF);
				listeLigneFraisHorsForfait.add(lfhr);
				FicheFrais ficheFrais;
				ficheFrais = SaisieFraisDaoImpl.getUneFicheFrais(i_id, now);
				ficheFrais.setLignesFraisHorsForfait(listeLigneFraisHorsForfait);
				
				SaisieFraisDaoImpl.validerLigneFraisHorsForfait(ficheFrais);
				response.sendRedirect("listHF");
		
		}
		
		else {
			System.out.println("Libellé ou montant manquant");
		}
	}


	private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		String title = request.getParameter("title");
		String username = request.getParameter("username");
		String description = request.getParameter("description");
		//DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-mm-dd");
		LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"));
		
		boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
		//Todo updateTodo = new Todo(id, title, username, description, targetDate, isDone);
		
		//todoDAO.updateTodo(updateTodo);
		
		response.sendRedirect("list");
	}

	private void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = LoginBean.getId();
		String mois = request.getParameter("id");
		System.out.println(id +" id et mois " + mois);
	SaisieFraisDaoImpl.DeleteUneFicheFrais(id, mois);
		response.sendRedirect("list");
	}
	
	private void deleteFraisHorsFrais(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		
		String id = request.getParameter("id");
	SaisieFraisDaoImpl.deleteFraisHF(id);
		response.sendRedirect("listHF");
	}
}
