package me.pepe.Twittir.Controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import me.pepe.Twittir.Auth.UserAuthentication;
import me.pepe.Twittir.Auth.UserAuthenticationEntryPoint;
import me.pepe.Twittir.Auth.UserAuthenticationProvider;
import me.pepe.Twittir.Database.Login.LoginRepository;
import me.pepe.Twittir.Database.Tweet.InteractionTweetData;
import me.pepe.Twittir.Database.Tweet.TweetData;
import me.pepe.Twittir.Database.Tweet.TweetRepository;
import me.pepe.Twittir.Database.User.UserAboutData;
import me.pepe.Twittir.Database.User.UserData;
import me.pepe.Twittir.Database.User.UserProfileImageData;
import me.pepe.Twittir.Database.User.UserRepository;
import me.pepe.Twittir.Service.Service;
import me.pepe.Twittir.Utils.Utils;
import me.pepe.Twittir.Utils.Dto.CompleteTweetDto;
import me.pepe.Twittir.Utils.Dto.Dto;
import me.pepe.Twittir.Utils.Dto.FollowCounterDto;
import me.pepe.Twittir.Utils.Dto.Credentials.CredentialsDto;
import me.pepe.Twittir.Utils.Dto.Credentials.CredentialsTokenDto;
import me.pepe.Twittir.Utils.Dto.Credentials.RegisterCredentialsDto;
import me.pepe.Twittir.Utils.Dto.Update.FollowDto;
import me.pepe.Twittir.Utils.Dto.Update.LikesDto;
import me.pepe.Twittir.Utils.Dto.Update.RetweetsDto;
import me.pepe.Twittir.Utils.Dto.Update.UpdateDto;

@RestController
@RequestMapping("/api")
public class APIController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TweetRepository tweetRepository;
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private Service service;
	@Autowired
    private UserAuthenticationProvider userAuthenticationProvider;
	public APIController() {
		new Thread() {
			@Override
			public void run() {
				while (userRepository == null || tweetRepository == null) {
					try {
						sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (userRepository.find(7) == null) { // si no está el usuario 3 creado...
					System.out.println("CREANDO DATOS POR DEFECTO");
					service.newUser(new UserData(0, "noticias", "TeleNoticias", true), "test", new UserAboutData(0, "Toda la actualidad del mundo en twittir", "España", 0, 0, "Informar", "info@noticias.com", 0, 0));
					tweetRepository.tweet(1, "Un hombre obtiene una medalla por su labor al salvar un gatito de un arbol", System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000));
					tweetRepository.tweet(1, "Salen ardiendo 3 locales en Málaga", System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000));
					tweetRepository.tweet(1, "Sube el precio de la gasolina en toda españa, algunos conductores de taxi se han puesto en huelga", System.currentTimeMillis() - (1 * 24 * 60 * 60 * 1000));
					tweetRepository.tweet(1, "Josep 'Pitu' Roca, sumiller de El Celler de Can Roca, gana el Premio Especial Sommelier Michelin 2024", System.currentTimeMillis() - (7 * 60 * 60 * 1000));
					service.newUser(new UserData(0, "deportes", "TeleDeportes", true), "test", new UserAboutData(0, "Toda la actualidad de deportes del mundo en twittir", "España", 0, 0, "deportes", "info@noticias.com", 0, 0));
					tweetRepository.tweet(2, "Sandra Quinojo obtiene una medalla olimpica en badminton quedando en primer lugar", System.currentTimeMillis() - (2 * 24 * 60 * 60 * 1000));
					tweetRepository.tweet(2, "Rafa Nadal se lesiona mientras entrenaba para su siguiente partido", System.currentTimeMillis() - (1 * 24 * 60 * 60 * 1000));
					tweetRepository.tweet(2, "Constantes abucheos a Toni Kroos al salir al campo en su anterior partido", System.currentTimeMillis() - (8 * 60 * 60 * 1000));
					tweetRepository.tweet(2, "Queralt Casas lidera el triunfo del Valencia en la cancha de baloncesto del Sepsi SF (61-71)", System.currentTimeMillis() - (6 * 60 * 60 * 1000));
					tweetRepository.tweet(2, "FIFA sanciona y multa a la Selección Colombia por retrasos y actitudes discriminatorias en Eliminatorias", System.currentTimeMillis() - (3 * 60 * 60 * 1000));
					service.newUser(new UserData(0, "tiempo", "TeleTiempo", true), "test", new UserAboutData(0, "Toda la actualidad del tiempo del mundo en twittir", "España", 0, 0, "Informar", "tiempo@noticias.com", 0, 0));
					tweetRepository.tweet(3, "Almeria se pone en alerta roja por fuertes vientos", System.currentTimeMillis());
					tweetRepository.tweet(3, "Se prevee durante toda la semana cielos soleados en todas las islas canarias", System.currentTimeMillis());
					tweetRepository.tweet(3, "Duras lluvias en la localidad de Cordoba, se sigue preveniendo el mismo temporal hasta el fin de semana", System.currentTimeMillis());
					service.newUser(new UserData(0, "juanillogarrio", "Juan Garrido", false), "test");
					tweetRepository.tweet(4, "Hoy he llegado tarde a casa y me han reñido", System.currentTimeMillis() - (24 * 60 * 60 * 1000));
					tweetRepository.tweet(4, "Mi perro tiene hambre pero no come, ¿alguien sabe que le puede pasar?", System.currentTimeMillis());
					tweetRepository.tweet(4, "Mi perro ya esta comiendo, gracias a todos por la ayuda :)", System.currentTimeMillis());
					tweetRepository.tweet(4, "He descubierto porque mi perro no comia, cuando lo saco a la calle se come las plantas y se llena, lo he castigado", System.currentTimeMillis());
					tweetRepository.tweet(4, "Creo que el perro se ha enfadado porque le castigue y me ha hecho caca en la alfonbra, que bien lo que queria para iniciar el lunes :)", System.currentTimeMillis());
					tweetRepository.tweet(4, "He acabado tirando la alfombra porcierto", System.currentTimeMillis());
					service.newUser(new UserData(0, "alberto99", "Alberto Suarez", false), "test", new UserAboutData(0, "Mi twittir", "España", 0, 0, "Estudiante", "albertito99@mail.com", 0, 0));
					tweetRepository.tweet(5, "Me he creado la cuenta pero mi movil se ha roto, no la usaré realmente ;(", System.currentTimeMillis() - (5 * 30 * 60 * 60 * 1000));
					service.newUser(new UserData(0, "rebecagbq94", "Rebequita", false), "test", new UserAboutData(0, "Si me vas a invitar a shushi me puedes seguir sino no", "España", System.currentTimeMillis(), 0, "Emprendedora", "", 34, 654654654));
					tweetRepository.tweet(6, "Hola gente este es mi primer tweet", System.currentTimeMillis() - (2 * 30 * 60 * 60 * 1000));
					service.newUser(new UserData(0, "srv2003", "Sara Rodriguez", false), "test", new UserAboutData(0, "No sigo a gente random", "España", 0, 0, "", "", 34, 654654654));
					tweetRepository.tweet(7, ":)", System.currentTimeMillis());
				}
			}
		}.start();
	}
    @PostMapping("/login")
    public ResponseEntity<Dto> login(@AuthenticationPrincipal CredentialsDto user, HttpServletRequest request, UserAuthentication authentication) {
    	if (user != null) {
        	System.out.println("login with: " + user.getUsername());
        	String realToken = Utils.getRandomToken();
        	String token = userAuthenticationProvider.createToken(realToken);
        	Optional<UserData> userData = userRepository.findByUserName(user.getUsername());
        	if (userData.isPresent()) {
            	System.out.println("token created ON LOGIN: " + token);    	
            	loginRepository.delete(userData.get().getID());
            	loginRepository.newLoginToken(userData.get().getID(), realToken, System.currentTimeMillis(), System.currentTimeMillis() + (60 * 60 * 1000), request.getHeader("user-agent"));
                return ResponseEntity.ok(new CredentialsTokenDto(token));
        	}
    	} else if (authentication != null) {
            return ResponseEntity.ok(new CredentialsTokenDto("already logged"));
    	}
		return new ResponseEntity<Dto>(new CredentialsTokenDto("null"), HttpStatus.UNAUTHORIZED);
    }
    @PostMapping("/register")
    public ResponseEntity<Dto> register(HttpServletRequest request, UserAuthentication authentication) {
    	if (authentication == null) {
            try {
            	RegisterCredentialsDto credentialsDto = UserAuthenticationEntryPoint.OBJECT_MAPPER.readValue(request.getInputStream(), RegisterCredentialsDto.class);
	        	System.out.println("register with: " + credentialsDto.getUsername());
	        	long userID = service.newUser(new UserData(0, credentialsDto.getUsername(), credentialsDto.getName(), false), new String(credentialsDto.getPassword()));
				System.out.println("CREATOR: " + userRepository.getLastUserRegistered());
	        	String realToken = Utils.getRandomToken();
	        	String token = userAuthenticationProvider.createToken(realToken);
            	System.out.println("token created ON REGISTER: " + token);    	
            	loginRepository.delete(userID);
            	loginRepository.newLoginToken(userID, realToken, System.currentTimeMillis(), System.currentTimeMillis() + (60 * 60 * 1000), request.getHeader("user-agent"));
                return ResponseEntity.ok(new CredentialsTokenDto(token));
			} catch (IOException e) {
				e.printStackTrace();
				return new ResponseEntity<Dto>(new CredentialsTokenDto("Server error"), HttpStatus.INTERNAL_SERVER_ERROR);
			}
    	}
		return new ResponseEntity<Dto>(new CredentialsTokenDto("already logged"), HttpStatus.UNAUTHORIZED);
    }
    /*
	//@RequestParam(name="name", required=false, defaultValue="World") String name
	@GetMapping(path = "/getTweets")
	public <T> T getTweets(@RequestParam(required=true) int userID, UserAuthentication authentication) {
		if (authentication != null) {
			System.out.println(authentication == null ? "autentificacion nulla" : " no nulla");
			if (authentication != null) {
				LoginTokenData ltd = (LoginTokenData) authentication.getCredentials();
				System.out.println(ltd != null);
				System.out.println(ltd.getUserID() + " - " + ltd.getLoginKey());
				System.out.println("userID: " + authentication.getUserID());
				System.out.println(authentication.getDetails() != null ? "con detalles" : "sin detalles");
			}
			System.out.println(userID);
			List<Dto> ctd = new ArrayList<Dto>();
			for (TweetData td : tweetRepository.findOfUser(1)) {
				InteractionTweetData itd = tweetRepository.getInteraction(td.getID(), 1);
				ctd.add(new CompleteTweetDto(td, userRepository.find(td.getUserID()), itd != null ? itd.isRetweeted() : false, itd != null ? itd.isLiked() : false, tweetRepository.countRetweets(td.getID()), tweetRepository.countLikes(td.getID())));
			}
			return (T) ResponseEntity.ok(ctd);
		} else {
			return (T) new ResponseEntity<Dto>(Utils.unauthorizedError, HttpStatus.UNAUTHORIZED);
		}
	}
     */
	//@RequestParam(name="name", required=false, defaultValue="World") String name
	@GetMapping(path = "/getTweets")
	public ResponseEntity<List<Dto>> getTweets(HttpServletRequest request, @RequestParam(required=false, defaultValue="0") int userID, UserAuthentication authentication) {
		/*
		Iterator<String> nIterator = request.getHeaderNames().asIterator();
		while (nIterator.hasNext()) {
			String n = nIterator.next();
			System.out.println(n + " - " + request.getHeader(n));
		}
		*/
		System.out.println("authentication " + authentication);
		List<Dto> ctd = new ArrayList<Dto>();
		if (userID == 0) {
			if (authentication != null) {
				for (TweetData td : tweetRepository.findRecomendedsAndMyTweets(authentication.getUserID())) {
					InteractionTweetData itd = tweetRepository.getInteraction(td.getID(), authentication.getUserID());
					ctd.add(new CompleteTweetDto(td, userRepository.find(td.getUserID()), itd != null ? itd.isRetweeted() : false, itd != null ? itd.isLiked() : false, tweetRepository.countRetweets(td.getID()), tweetRepository.countLikes(td.getID())));
				}
			} else {
				for (TweetData td : tweetRepository.findRecomendeds()) {
					ctd.add(new CompleteTweetDto(td, userRepository.find(td.getUserID()), false, false, tweetRepository.countRetweets(td.getID()), tweetRepository.countLikes(td.getID())));
				}
			}
		} else {
			if (authentication != null) {
				for (TweetData td : tweetRepository.findOfUser(userID)) {
					InteractionTweetData itd = tweetRepository.getInteraction(td.getID(), authentication.getUserID());
					ctd.add(new CompleteTweetDto(td, userRepository.find(td.getUserID()), itd != null ? itd.isRetweeted() : false, itd != null ? itd.isLiked() : false, tweetRepository.countRetweets(td.getID()), tweetRepository.countLikes(td.getID())));
				}
			} else {
				for (TweetData td : tweetRepository.findOfUser(userID)) {
					ctd.add(new CompleteTweetDto(td, userRepository.find(td.getUserID()), false, false, tweetRepository.countRetweets(td.getID()), tweetRepository.countLikes(td.getID())));
				}
			}
		}
		return ResponseEntity.ok(ctd);
	}
	@PostMapping(path = "/postTweet")
	public ResponseEntity<Dto> postTweet(@RequestParam(required=true) String content, UserAuthentication authentication) {
		tweetRepository.tweet(authentication.getUserID(), content, System.currentTimeMillis());
		return ResponseEntity.ok(new CompleteTweetDto(new TweetData(0, authentication.getUserID(), content, System.currentTimeMillis()), userRepository.find(authentication.getUserID()), false, false, 0, 0));
	}
	@PostMapping(path = "/likeTweet")
	public ResponseEntity<Dto> likeTweet(@RequestParam(required=true) int tweetID, UserAuthentication authentication) {
		InteractionTweetData itd = tweetRepository.getInteraction(tweetID, authentication.getUserID());
		if (itd == null) {
			tweetRepository.createInteraction(tweetID, authentication.getUserID());
			itd = tweetRepository.getInteraction(tweetID, authentication.getUserID());
		}
		System.out.println((itd == null) + " existe!!!");
		if (!itd.isLiked()) {
			tweetRepository.setLike(tweetID, authentication.getUserID(), true);
		}
		return ResponseEntity.ok(new LikesDto(!itd.isLiked(), tweetRepository.countLikes(tweetID)));
	}
	@PostMapping(path = "/unlikeTweet")
	public ResponseEntity<Dto> unlikeTweet(@RequestParam(required=true) int tweetID, UserAuthentication authentication) {
		InteractionTweetData itd = tweetRepository.getInteraction(tweetID, authentication.getUserID());
		if (itd == null) {
			tweetRepository.createInteraction(tweetID, authentication.getUserID());
			itd = tweetRepository.getInteraction(tweetID, authentication.getUserID());
		}
		if (itd.isLiked()) {
			tweetRepository.setLike(tweetID, authentication.getUserID(), false);
		}
		return ResponseEntity.ok(new LikesDto(itd.isLiked(), tweetRepository.countLikes(tweetID)));
	}
	@PostMapping(path = "/shareTweet")
	public ResponseEntity<Dto> shareTweet(@RequestParam(required=true) int tweetID, UserAuthentication authentication) {
		InteractionTweetData itd = tweetRepository.getInteraction(tweetID, authentication.getUserID());
		if (itd == null) {
			tweetRepository.createInteraction(tweetID, authentication.getUserID());
			itd = tweetRepository.getInteraction(tweetID, authentication.getUserID());
		}
		if (!itd.isRetweeted()) {
			tweetRepository.setRetweeted(tweetID, authentication.getUserID(), true);
		}
		return ResponseEntity.ok(new RetweetsDto(!itd.isRetweeted(), tweetRepository.countRetweets(tweetID)));
	}
	@PostMapping(path = "/unshareTweet")
	public ResponseEntity<Dto> unshareTweet(@RequestParam(required=true) int tweetID, UserAuthentication authentication) {
		InteractionTweetData itd = tweetRepository.getInteraction(tweetID, authentication.getUserID());
		if (itd == null) {
			tweetRepository.createInteraction(tweetID, authentication.getUserID());
			itd = tweetRepository.getInteraction(tweetID, authentication.getUserID());
		}
		if (itd.isRetweeted()) {
			tweetRepository.setRetweeted(tweetID, authentication.getUserID(), false);
		}
		return ResponseEntity.ok(new RetweetsDto(itd.isRetweeted(), tweetRepository.countRetweets(tweetID)));
	}
	@PostMapping(path = "/followUser")
	public ResponseEntity<Dto> followUser(@RequestParam(required=true) long followID, UserAuthentication authentication) {
		UserData userData = userRepository.find(followID);
		if (userData != null) {			
			if (!userRepository.isFollowing(authentication.getUserID(), followID)) {
				userRepository.followUser(authentication.getUserID(), followID);
				return ResponseEntity.ok(new FollowDto(true, new FollowCounterDto(true, userRepository.countFollowing(followID), userRepository.countFollowers(followID))));
			} else {
				return ResponseEntity.ok(new FollowDto(false, new FollowCounterDto(true, userRepository.countFollowing(followID), userRepository.countFollowers(followID))));
			}
		} else {
			return new ResponseEntity<Dto>(new CredentialsTokenDto("User not found"), HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping(path = "/unfollowUser")
	public ResponseEntity<Dto> unfollowUser(@RequestParam(required=true) long followID, UserAuthentication authentication) {
		UserData userData = userRepository.find(followID);
		if (userData != null) {			
			if (userRepository.isFollowing(authentication.getUserID(), followID)) {
				userRepository.unfollowUser(authentication.getUserID(), followID);
				return ResponseEntity.ok(new FollowDto(true, new FollowCounterDto(false, userRepository.countFollowing(followID), userRepository.countFollowers(followID))));
			} else {
				return ResponseEntity.ok(new FollowDto(false, new FollowCounterDto(false, userRepository.countFollowing(followID), userRepository.countFollowers(followID))));
			}
		} else {
			return new ResponseEntity<Dto>(new CredentialsTokenDto("User not found"), HttpStatus.NOT_FOUND);
		}
	}
	@PostMapping(path = "/uploadImageProfile")
	public ResponseEntity<Dto> uploadImageProfile(@RequestParam(required=true) MultipartFile file, UserAuthentication authentication) {
		try {
			System.out.println(file.getBytes().length + " pesa el archivo!");
			userRepository.uploadUserImageProfile(authentication.getUserID(), file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ResponseEntity.ok(new UpdateDto(true) {});
	}
	@PostMapping(path = "/updateUserAboutData")
	public ResponseEntity<Dto> updateUserAboutData(@RequestParam(required=true) String newname, @RequestParam(required=true) String description, @RequestParam(required=true) String email, @RequestParam(required=true) String location, @RequestParam(required=true) String work, @RequestParam(required=true) int numberPrefix, @RequestParam(required=true) int phoneNumber, @RequestParam(required=true) long birthdate, UserAuthentication authentication) {
		userRepository.updateUserAboutData(authentication.getUserID(), birthdate, description, location, numberPrefix, phoneNumber, work, email);
		userRepository.updateName(authentication.getUserID(), newname);
		return ResponseEntity.ok(new UpdateDto(true) {});
	}
	@GetMapping(path = "/getFollowers")
    public ResponseEntity<List<UserData>> getFollowers(@RequestParam(required=true) int user) {
		List<UserData> users = new ArrayList<UserData>();
		for (Integer userID : userRepository.getFollowers(user)) {
			users.add(userRepository.find(userID));
		}
        return ResponseEntity.ok(users);
    }
	@GetMapping(path = "/getFollowing")
    public ResponseEntity<List<UserData>> getFollowing(@RequestParam(required=true) int user) {
		List<UserData> users = new ArrayList<UserData>();
		for (Integer userID : userRepository.getFollowing(user)) {
			users.add(userRepository.find(userID));
		}
        return ResponseEntity.ok(users);
    }
	@GetMapping(path = "/getUser")
    public ResponseEntity<UserData> getUser(@RequestParam(required=true) int user) {
        UserData userData = userRepository.find(user);
        if (userData != null) {
            return ResponseEntity.ok(userData);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
	@GetMapping(path = "/getUserImageProfile")
    public ResponseEntity<byte[]> getUserImageProfile(@RequestParam(required=true) int user) {
    	UserProfileImageData uip = userRepository.getUserImageProfile(user);
    	if (uip != null) {
        	byte[] imageData = userRepository.getUserImageProfile(user).getImageData();
        	return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(imageData);
    	} else {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new byte[0]);
    	}
    }
}