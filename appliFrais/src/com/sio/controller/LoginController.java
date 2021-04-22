package com.sio.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

import com.sio.dao.LoginDao;
import com.sio.model.LoginBean;
import com.sio.model.Visiteur;

@WebServlet("/login")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private LoginDao loginDao;
	private Visiteur utilisateur;

	public void init() {
		loginDao = new LoginDao();
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("login/login.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		authenticate(request, response);
	}

	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String profil = request.getParameter("Profile");
		String id = request.getParameter("id");
		String nom = request.getParameter("nom");
		String prenom = request.getParameter("prenom");
		String ville = request.getParameter("prenom");
	
		LoginBean loginbean = new LoginBean(username, password, profil, nom, prenom);
		loginbean.setUsername(username);
		loginbean.setPassword(password);
		loginbean.setProfil(profil);
		//int l_iId = Integer.parseInt(id);
	//	loginBean.setId(l_iId);
	//	HttpSession session = request.getSession();
		//Visiteur visiteur = new Visiteur(id, nom, prenom, ville, profil, "2021-05-02");
//		loginbean.setNom(nom);
//		loginbean.setPrenom(prenom);
//	request.setAttribute("nom", nom);
//	request.setAttribute("prenom", prenom);
//		 session.setAttribute("loginbean", loginbean);
//		 session.setAttribute("nom", nom);
//		 session.setAttribute("prenom", prenom);
//		 System.out.println(session.getAttribute("nom"));
//		 System.out.println(session.getAttribute("prenom"));
//		 System.out.println("nom" + loginbean.getNom());
		 System.out.println("passage dr l'authenticate");
		try {
		//	if (loginDao.validate(loginBean)) {
				if(loginDao.validate(loginbean) == 1){
				RequestDispatcher dispatcher = request.getRequestDispatcher("frais/frais-list.jsp");
				//RequestDispatcher dispatcher = request.getRequestDispatcher("todo/todo-list.jsp");
				
				dispatcher.forward(request, response);
				}
				else if(loginDao.validate(loginbean) == 2){
					RequestDispatcher dispatcher = request.getRequestDispatcher("frais/frais-list.jsp");
					
					dispatcher.forward(request, response);
					
			}
				else if(loginDao.validate(loginbean) == 3){
					RequestDispatcher dispatcher = request.getRequestDispatcher("admin/adminAccueil.jsp");
					
					dispatcher.forward(request, response);
					
			}else {
				
				//HttpSession session = request.getSession();
//				 session.setAttribute("id", id);
//				 session.setAttribute("nom", nom);
//				 session.setAttribute("prenom", prenom);
//			
			     response.sendRedirect("login/login.jsp");
//			     String someMessage = "L'identifiant ou le mot de passe est incorrect";
//					PrintWriter out = response.getWriter();
//					out.print("<html><head>");
//					out.print("<script type=\"text/javascript\">alert(" + someMessage + ");</script>");
//					out.print("</head><body></body></html>");
					}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}
}
