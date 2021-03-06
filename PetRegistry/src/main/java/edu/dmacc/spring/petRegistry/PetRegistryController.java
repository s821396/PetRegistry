package edu.dmacc.spring.petRegistry;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PetRegistryController {
	
	@Autowired PetDao petDao;
	@Autowired OwnerDao ownerDao;
	
	@RequestMapping(value = "/homepage")
	public ModelAndView owner() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("homepage");
		return modelAndView;
	}
	
	@RequestMapping(value = "/ownerForm")
	public ModelAndView ownerForm() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ownerForm");
		modelAndView.addObject("owner", new Owner());
		return modelAndView;
	}
	
	@RequestMapping(value = "/ownerResult")
	public ModelAndView processOwner(Owner owner) {
		ModelAndView modelAndView = new ModelAndView();
		ownerDao.insertOwner(owner);
		modelAndView.setViewName("ownerResult");
		modelAndView.addObject("o", owner);
		return modelAndView;
	}
	
	@RequestMapping(value = "/viewOwners")
	public ModelAndView viewAllOwners() {
		ModelAndView modelAndView = new ModelAndView();
		List<Owner> allOwners = ownerDao.getAllOwners();
		modelAndView.setViewName("viewOwners");
		modelAndView.addObject("allOwners", allOwners);
		return modelAndView;
	}
	
	@RequestMapping(value = "/petForm")
	public ModelAndView petForm(int ownerId) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("petForm");
		Owner owner = ownerDao.getOwner(ownerId);
		modelAndView.addObject("owner", owner);
		modelAndView.addObject("pet", new Pet(owner));
		return modelAndView;
	}
	
	@RequestMapping(value = "/petResult")
	public ModelAndView processPet(Pet pet) {
		ModelAndView modelAndView = new ModelAndView();
		petDao.insertPet(pet);
		modelAndView.setViewName("petResult");
		modelAndView.addObject("p", pet);
		return modelAndView;
	}
	
	@RequestMapping(value = "/viewAllPets")
	public ModelAndView viewAllPets() {
		ModelAndView modelAndView = new ModelAndView();
		List<Pet> allPets = petDao.getAllPets();
		modelAndView.setViewName("viewAllPets");
		modelAndView.addObject("allPets", allPets);
		return modelAndView;
	}
	
	@RequestMapping(value = "/editPet")
	public ModelAndView editPet(int petId) {
		ModelAndView modelAndView = new ModelAndView();
		Pet pet = petDao.getPet(petId);
		modelAndView.setViewName("editPetForm");
		modelAndView.addObject("p", pet);
		return modelAndView;
	}
	
	@RequestMapping(value = "/editPetResult")
	public ModelAndView editPetResult(Pet pet) {
		ModelAndView modelAndView = new ModelAndView();
		petDao.updatePet(pet);
		List<Owner> allOwners = ownerDao.getAllOwners();
		modelAndView.setViewName("editPetResult");
		modelAndView.addObject("p", pet);
		modelAndView.addObject("allOwners", allOwners);
		return modelAndView;
	}
	
	@RequestMapping(value = "/deletePet")
	public ModelAndView deletePet(int petId) {
		ModelAndView modelAndView = new ModelAndView();
		petDao.removePet(petId);
		List<Pet> allPets = petDao.getAllPets();
		modelAndView.setViewName("viewAllPets");
		modelAndView.addObject("allPets", allPets);
		return modelAndView;
	}
	
	@Bean
	public PetDao petDao() {
		PetDao bean = new PetDao();
		return bean;
	}
	
	@Bean
	public OwnerDao ownerDao() {
		OwnerDao bean = new OwnerDao();
		return bean;
	}
}
