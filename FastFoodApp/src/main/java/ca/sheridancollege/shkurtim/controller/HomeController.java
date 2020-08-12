package ca.sheridancollege.shkurtim.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.shkurtim.beans.Menu;
import ca.sheridancollege.shkurtim.database.DatabaseAccess;

@Controller
public class HomeController {

	@Lazy
	@Autowired
	private DatabaseAccess da;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/register")
	public String getRegister() {
		return "register";
	}

	@PostMapping("/register")
	public String postRegister(@RequestParam String username, @RequestParam String password) {

		da.addUser(username, password);
		Long userId = da.findUserAccount(username).getUserId();
		da.addRole(userId, Long.valueOf(1));
		da.addRole(userId, Long.valueOf(2));
		return "redirect:/";
	}

	@GetMapping("/secure")
	public String secureIndex(Model model, Authentication authentication) {
		
		String username = authentication.getName();
		Long userId = da.findUserAccount(username).getUserId();
		
		List <Menu> menuList= da.getMenuList();
		model.addAttribute("menuList", menuList);
		
		return "/secure/index";
	}
	
	@PostMapping("/secure/submit")
	public String submit(Model model, Authentication authentication,@RequestParam String[] menuList) {
		String username = authentication.getName();
		Long userId = da.findUserAccount(username).getUserId();
		System.out.println(userId);
		
		for(int i=0; i<menuList.length; i++) {
			Menu menu=da.getMenuIDByName(menuList[i]);
			System.out.println(menu);
			System.out.println(menu.getMenuName());
			System.out.println(menu.getPrice());
			System.out.println(menuList[i]);
			System.out.println(menu.getMenuID());
			da.insertCustomerOrder(userId, menu.getMenuID());	
		}
		
		return "/secure/submit";
	}
	
	@GetMapping("/secure/receipt")
	public String receipt(Model model, Authentication authentication) {
		String username = authentication.getName();
		Long userId = da.findUserAccount(username).getUserId();
		
		List<Menu> receipt=da.getCustomerOrderList(userId);
		model.addAttribute("receipt", receipt);
		
		int total = 0;
		
		for (Menu r:receipt) {
			total=total+r.getPrice();
		}
		model.addAttribute("total", total);
		
		double tax= total *0.13;
		model.addAttribute("tax", tax);
		
		double fullPrice = total+tax;
		model.addAttribute("fullPrice", fullPrice);
		
		
		return "/secure/receipt";
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/permission-denied")
	public String permissionDenied() {
		return "/error/permission-denied";
	}
}
