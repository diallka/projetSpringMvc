package me.samuelsimon.springmvc_tutorial.simplemvc;

import java.util.List;

import javax.annotation.Generated;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PagesController {
	// Injecter SqlSession Mybatis
	@Autowired
	private SqlSession m_SqlSession;

	//Redirection run 
	//@RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
	// RequestMapping value accepts a list, use that if a controller needs multiple request map
	@RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
	
	public String list(Model p_Model) {
		// Call MyBatis mapper to get data from DB
		List<MyData> t_QueryResult = m_SqlSession.selectList("data-mapper.selectAllPeople");

		// Add retrieved data to our model
		p_Model.addAttribute("personData", t_QueryResult);

		return "list";
	}

	@RequestMapping(value = {"/form", "/edit"}, method = RequestMethod.GET)
	public String form(@RequestParam(value="id", required=false) Integer p_PersonId, Model p_Model) {		
		// Check whether this is an add or edit request 
		MyData t_PersonData;
		if (p_PersonId != null) {
			t_PersonData = m_SqlSession.selectOne("data-mapper.selectOnePerson", p_PersonId);
			p_Model.addAttribute("linkSubmit", "edit");
		}
		else {
			t_PersonData = new MyData();
			p_Model.addAttribute("linkSubmit", "add");
		}

		p_Model.addAttribute("personData", t_PersonData);
		return "form";
	}

	@RequestMapping(value = "/add", method=RequestMethod.POST)
	// Retrieve POST data by adding @ModelAttribute parameter
	public String add(@ModelAttribute("SpringWeb") MyData p_PersonData, Model p_Model) {
		m_SqlSession.insert("data-mapper.insertPerson", p_PersonData);

		return "redirect:/";
	}

	@RequestMapping(value = "/edit", method=RequestMethod.POST)		
	public String edit(@ModelAttribute("SpringWeb") MyData p_PersonData, Model p_Model) {
		m_SqlSession.update("data-mapper.updatePerson", p_PersonData);

		return "redirect:/";
	}

	@RequestMapping(value = "/delete/{personId}")
	public String delete(@PathVariable("personId") Integer p_PersonId) {
		m_SqlSession.delete("data-mapper.deletePerson", p_PersonId);

		return "redirect:/";
	}
	
	//Ajout page d'Authentification****************************************
	@RequestMapping(value="/connexion", method = RequestMethod.GET)
	public String connexionGET (Model m){
		m.addAttribute("util", new Utilisateur());
		m.addAttribute("lienSeconnecter", "connexion");
		return "connexion";	
	} 
	
	@RequestMapping(value="/connexion", method = RequestMethod.POST)
	public String connexionPOST (Model m){
		
		
		return "redirect:/espace_personnel";
	}
	
	//*********************************************************************
	@RequestMapping(value="/espace_personnel", method = RequestMethod.GET)
	public String espace_perso(Model m) {
		List<Utilisateur> utilisateurs = m_SqlSession.selectList("login-mapper.listerUtilisateurs");
		m.addAttribute("utilisateurs", utilisateurs);
		return "espace_personnel";
	}
	
	@RequestMapping(value = {"/modifier"}, method = RequestMethod.GET)
	public String recupererUtiliateur(@RequestParam(value="id", required=false) Integer idUtilisateur, Model m) {		
		// Check whether this is an add or edit request 
		Utilisateur donneesUtil;
		if (idUtilisateur != null) {
			donneesUtil = m_SqlSession.selectOne("login-mapper.recupererUtilisateur", idUtilisateur);
			m.addAttribute("lienSenregistrer", "modifier");
		}
		else {
			donneesUtil = new Utilisateur();
			m.addAttribute("lienSenregistrer", "ajouter");
		}

		m.addAttribute("util", donneesUtil);
		return "inscription";
	}
	
	@RequestMapping(value="/modifier", method = RequestMethod.POST)
	public String modifierUtilisateur(@ModelAttribute("ed") Utilisateur util, Model m){
		m_SqlSession.update("login-mapper.modifierUtilisateur", util);
		return "redirect:/espace_personnel";
	}
	@RequestMapping(value = "/supprimer/{idUtilisateur}")
	public String supprimerUtilisateur(@PathVariable("idUtilisateur") Integer idUtilisateur) {
		m_SqlSession.delete("login-mapper.supprimerUtilisateur", idUtilisateur);

		return "redirect:/espace_personnel";
	}
	//Inscription *********************************************************
		@RequestMapping(value="/inscription", method = RequestMethod.GET)
					  //unique à chaque fois, sinon erreur
		public String inscriptionGET (Model m){
			Utilisateur util = new Utilisateur();
			util.setEmail("contact@email.com");
			m.addAttribute("util", util);
			m.addAttribute("lienSenregistrer", "S'enregistrer");
			
			return "inscription";	
		}
		@RequestMapping(value = "/inscription", method=RequestMethod.POST)
					  //unique à chaque fois sinon erreur
		public String inscriptionPOST(@ModelAttribute("util") Utilisateur u) {
			m_SqlSession.insert("login-mapper.inserrerUtilisateur", u);

			return "redirect:/connexion";
		} //******************************************************************
}