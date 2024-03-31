package me.pepe.Twittir.Controller;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import me.pepe.Twittir.Auth.UserAuthentication;
import me.pepe.Twittir.Auth.UserAuthenticationProvider;
import me.pepe.Twittir.Database.Login.LoginTokenData;
import me.pepe.Twittir.Database.User.UserAboutData;
import me.pepe.Twittir.Database.User.UserData;
import me.pepe.Twittir.Database.User.UserRepository;
import me.pepe.Twittir.Utils.Utils;
import me.pepe.Twittir.Utils.Dto.FollowCounterDto;

@Controller
public class GlobalController {
	// th:text o th:value para remplazar
	// th:each es como hacer un for
	@Autowired
    private UserAuthenticationProvider userAuthenticationProvider;
	@Autowired
	private UserRepository userRepository;
    @RequestMapping("/")
	public String home(Model model, HttpServletRequest httpServletRequest) {
    	UserData loggedUser = getLoggedUser(httpServletRequest);
    	model.addAttribute("userData", loggedUser);
		return "sidebar/home.html";
	}
    @RequestMapping("/search")
	public String search(Model model, HttpServletRequest httpServletRequest) {
    	UserData loggedUser = getLoggedUser(httpServletRequest);
    	model.addAttribute("userData", loggedUser);
		return "sidebar/search.html";
	}
    @RequestMapping("/notifications")
	public String notification(Model model, HttpServletRequest httpServletRequest) {
    	UserData loggedUser = getLoggedUser(httpServletRequest);
    	model.addAttribute("userData", loggedUser);
		return "sidebar/notifications.html";
	}
    @RequestMapping("/messages")
	public String messages(Model model, HttpServletRequest httpServletRequest) {
    	UserData loggedUser = getLoggedUser(httpServletRequest);
    	model.addAttribute("userData", loggedUser);
		return "sidebar/messages.html";
	}
    @RequestMapping("/saveds")
	public String saveds(Model model, HttpServletRequest httpServletRequest) {
    	UserData loggedUser = getLoggedUser(httpServletRequest);
    	model.addAttribute("userData", loggedUser);
		return "sidebar/saveds.html";
	}
    @RequestMapping("/config")
	public String config(Model model, HttpServletRequest httpServletRequest) {
    	UserData loggedUser = getLoggedUser(httpServletRequest);
    	model.addAttribute("userData", loggedUser);
		return "sidebar/config.html";
	}
    @RequestMapping("/profile/{username}")
	public String profile(@PathVariable String username, Model model, HttpServletRequest httpServletRequest) {
    	UserData loggedUser = getLoggedUser(httpServletRequest);
    	model.addAttribute("userData", loggedUser);
    	if (loggedUser != null && username.equals("none")) {
    		username = loggedUser.getUserName();
    	}
    	if (!username.equals("none")) {
        	Optional<UserData> profileData = userRepository.findByUserName(username);
        	if (profileData.isPresent()) {
        		model.addAttribute("followData", new FollowCounterDto(userRepository.isFollowing(loggedUser != null ? loggedUser.getID() : 0, profileData.get().getID()), userRepository.countFollowing(profileData.get().getID()), userRepository.countFollowers(profileData.get().getID())));
            	model.addAttribute("profileData", profileData.get());
            	model.addAttribute("yourProfile", loggedUser != null ? loggedUser.getID() == profileData.get().getID() : false);
            	UserAboutData userAboutData = userRepository.findAboutData(profileData.get().getID());
            	model.addAttribute("profileAboutData", userAboutData);
            	Calendar calInstance = Calendar.getInstance();
            	calInstance.setTimeInMillis(userAboutData.getCreatedTime());
                model.addAttribute("stringSingUp", "Se unió en " + calInstance.getDisplayName(Calendar.MONTH, Calendar.LONG, Utils.spanishLocale) + " de " + calInstance.get(Calendar.YEAR));
                if (userAboutData.getBirthDate() != 0) {
                    calInstance.setTimeInMillis(userAboutData.getBirthDate());
                	model.addAttribute("stringBirthdate", "Cumpleaños: " + calInstance.get(Calendar.DAY_OF_MONTH) + " de " + calInstance.getDisplayName(Calendar.MONTH, Calendar.LONG, Utils.spanishLocale));
                }
        		return "sidebar/profile.html";
        	}
    	}
		return "usernotfound.html";
	}
    @RequestMapping("/ejemplo")
	public String ejemplo(Model model) {
    	model.addAttribute("name", "pepe");
		return "ejemplo.html";
	}	
    @RequestMapping("/greeting")
    public String greeting(Model model) {
    	model.addAttribute("showContent", true);
        return "greeting.html";
    }
    private UserData getLoggedUser(HttpServletRequest httpServletRequest) {
    	LoginTokenData loginTokenData = getLoginTokenData(httpServletRequest);
    	if (loginTokenData != null) {
    		return userRepository.find(loginTokenData.getUserID());
    	}
    	return null;
    }
    private LoginTokenData getLoginTokenData(HttpServletRequest httpServletRequest) {
    	Cookie loginCookie = getCookie("login", httpServletRequest);
    	if (loginCookie != null) {
        	UserAuthentication authentication = userAuthenticationProvider.validateToken(loginCookie.getValue(), httpServletRequest.getHeader("user-agent"));
        	if (authentication != null && authentication.isAuthenticated()) {
        		return (LoginTokenData) authentication.getCredentials();
        	}
    	}
    	return null;
    }
    private Cookie getCookie(String name, HttpServletRequest httpServletRequest) {
    	Cookie[] cookies = httpServletRequest.getCookies();
    	if (cookies != null) {
        	for (Cookie c : httpServletRequest.getCookies()) {
        		if (c.getName().equals(name)) {
        			return c;
        		}
        	}
    	}
    	return null;
    }
}
