package com.springmvc.mybatis;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

//Definir obligatoirement comme Controlleur
@Controller
public class PagesController {
	// Injecter SqlSession Mybatis
	@Autowired
	private SqlSession m_SqlSession;

	// Ajout page d'Authentification****************************************
	// Redirige sur cette page au lancement de l'application en utilisant "/"
	// RequestMapping accepte plusieurs valeurs en utilisant "{}"
	@RequestMapping(value = { "/", "/connexion" }, method = RequestMethod.GET)
	public String connexionGET(Model m) {
		m.addAttribute("util", new Utilisateur());
		m.addAttribute("lienSeconnecter", "connexion");
		return "connexion";
	}

	@RequestMapping(value = { "/", "/connexion" }, method = RequestMethod.POST)
	public String connexionPOST(Model m) {

		return "redirect:/espace_personnel";
	}

	// *********************************************************************
	@RequestMapping(value = "/espace_personnel", method = RequestMethod.GET)
	public String espace_perso(Model m) {
		// Fait du mapping Mybatis pour recuperer les donnees depuis la base
		List<Utilisateur> utilisateurs = m_SqlSession.selectList("login-mapper.listerUtilisateurs");
		// on attribut les données recupérées à notre Model
		m.addAttribute("utilisateurs", utilisateurs);
		return "espace_personnel";
	}

	@RequestMapping(value = { "/modifier" }, method = RequestMethod.GET)
	public String recupererUtiliateur(@RequestParam(value = "id", required = false) Integer idUtilisateur, Model m) {
		// On peut aussi vérifier si existe on modifie sinon on en cré un
		// nouveau
		Utilisateur donneesUtil;
		if (idUtilisateur != null) {
			donneesUtil = m_SqlSession.selectOne("login-mapper.recupererUtilisateur", idUtilisateur);
			m.addAttribute("lienSenregistrer", "modifier");
		} else {
			donneesUtil = new Utilisateur();
			m.addAttribute("lienSenregistrer", "ajouter");
		}

		m.addAttribute("util", donneesUtil);
		return "inscription";
	}

	@RequestMapping(value = "/modifier", method = RequestMethod.POST)
	// On peut aussi utiliser l'annotation @ModelAttribute à la place de Model
	// ...
	public String modifierUtilisateur(@ModelAttribute("ed") Utilisateur util) {
		m_SqlSession.update("login-mapper.modifierUtilisateur", util);
		return "redirect:/espace_personnel";
	}

	@RequestMapping(value = "/supprimer/{idUtilisateur}")
	public String supprimerUtilisateur(@PathVariable("idUtilisateur") Integer idUtilisateur) {
		m_SqlSession.delete("login-mapper.supprimerUtilisateur", idUtilisateur);

		return "redirect:/espace_personnel";
	}

	// Inscription *********************************************************
	@RequestMapping(value = "/inscription", method = RequestMethod.GET)
				  // unique à chaque fois, sinon erreur
	public String inscriptionGET(Model m) {
		Utilisateur util = new Utilisateur();
		util.setEmail("contact@email.com");
		m.addAttribute("util", util);
		m.addAttribute("lienSenregistrer", "S'enregistrer");

		return "inscription";
	}

	@RequestMapping(value = "/inscription", method = RequestMethod.POST)
	public String inscriptionPOST(@ModelAttribute("util") Utilisateur u) {
		m_SqlSession.insert("login-mapper.inserrerUtilisateur", u);

		return "redirect:/connexion";
	} // ******************************************************************
}