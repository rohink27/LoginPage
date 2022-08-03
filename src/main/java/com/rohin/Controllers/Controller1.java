package com.rohin.Controllers;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rohin.Dao.UserRepository;
import com.rohin.HelperClasses.EmailCheck;
import com.rohin.HelperClasses.Mailer;
import com.rohin.HelperClasses.PasswordCheck;
import com.rohin.HelperClasses.TokenGenerator;
import com.rohin.HelperClasses.UsernameCheck;
import com.rohin.Models.User;

@Controller
public class Controller1 {
	@Autowired
	UserRepository reppo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping("/login")
	public String getLoginPage()
	{
		return "login.jsp";
	}
	@PostMapping("/login")
	public void login(@RequestParam("username") String uname, @RequestParam("password") String pass,HttpServletResponse res, HttpSession session, HttpServletRequest req) throws IOException, ServletException
	{
		/*String user= getCurrentUser();
		if(user!=null)
			settoken(user);
		User current= reppo.findByUsername(user);
		String email=current.getEmail();
		System.out.println(email);
		if(EmailCheck.emailCheck(email).length()==0) {
			System.out.println("true");*/
			res.sendRedirect("/");
			return ;
		//}
		
	/*	else
		{
			logoutuser(req);

			res.sendRedirect("/addemail");	
		
		}*/
			
	}
	@RequestMapping("/addemail")
	public String addemail()
	{
		return "getemail.jsp";
	}
	
	@RequestMapping("/email")
	public void email(@RequestParam("email") String email, HttpServletResponse res, HttpServletRequest req, HttpSession session) throws IOException
	{
		if(EmailCheck.emailCheck(email).length()!=0)
		{
			session.setAttribute("emailerror",EmailCheck.emailCheck(email) );
			res.sendRedirect("/addemail");
			return;
		}
	
			String username= getCurrentUser();
		User user= reppo.findByUsername(username);
		String link= "https://localhost/verify?id=" + user.getId() + "&token="+ user.getToken()+ "&email="+ email;
		String email_message= "<a href=\""+ link + "\">Click Here</a>";
		Mailer.send(email, "Verify you email id", email_message);
		res.sendRedirect("inbox");
	}
	@RequestMapping("/emailchange")
	public String emailchange(HttpSession session)
	{
	

		return "getemail.jsp";
	}
	@RequestMapping("/")
	public String welcome(HttpServletRequest req, HttpSession session) throws ServletException
	{
		String user= getCurrentUser();
		if(user!=null)
			settoken(user);
	
		return "welcome.jsp";
	}
	@RequestMapping("/register")
	public String register()
	{
		return "Signup.jsp";
	}
	@PostMapping("/registers")
	public void registers(@RequestParam("username") String uname, @RequestParam("password") String pass, @RequestParam("password2") String pass2, @RequestParam("email") String email, HttpSession session, HttpServletResponse res, HttpServletRequest req) throws IOException
	{
		boolean error=false;
		String message="";
		if(!pass2.equals(pass))
		{
			error=true;
			message= "Passwords Do not match";
			session.setAttribute("error", message);
			res.sendRedirect("register");
			return;
		}
		if(reppo.findByUsername(uname)!=null)
		{
			error=true;
			message= "User already exists";
			session.setAttribute("error", message);
			res.sendRedirect("register");
			return;
		}
		if(UsernameCheck.checkUsername(uname).length()!=0)
		{
			message=UsernameCheck.checkUsername(uname);
			error=true;
			session.setAttribute("error", message);
			res.sendRedirect("register");
			return;
			
		}
		if(PasswordCheck.validPassword(pass).length()!=0)
		{
			message=PasswordCheck.validPassword(pass);
			error=true;
			session.setAttribute("error", message);
			res.sendRedirect("register");
			return;
			
		}
		if(EmailCheck.emailCheck(email).length()!=0)
		{
			message=EmailCheck.emailCheck(email);
			error=true;
			session.setAttribute("error", message);
			res.sendRedirect("register");
			return;
			
		}
		User user= new User();
		user.setPassword(encoder.encode(pass));
		user.setUsername(uname);
		reppo.save(user);
		System.out.println(user);
		settoken(uname);
		user= reppo.findByUsername(uname);
		System.out.println(user);
		
		String link= "https://localhost/verify?id=" + user.getId() + "&token="+ user.getToken()+ "&email="+ email;
		String email_message= "<a href=\""+ link + "\">Click Here</a>";
		Mailer.send(email, "Verify you email id", email_message);
		res.sendRedirect("inbox");
		
		
		
	}
	@RequestMapping("inbox")
	public String check()
	{
		
		return "checkemail.jsp";
	}
	
	@RequestMapping("verify")
	public void verifyuser(@RequestParam("id") int id, @RequestParam("token") String token, @RequestParam("email") String email, HttpServletResponse res, HttpServletRequest req, HttpSession session) throws IOException, ServletException
	{
		User user1= reppo.findByToken(token);
		int expected_id= user1.getId();
		if(id!=expected_id)
		{
			res.sendRedirect("/unexpected");
			return;
		}
		user1.setEmail(email);
		reppo.save(user1);
		settoken(user1.getUsername());
		Authentication authentication = new UsernamePasswordAuthenticationToken(user1.getUsername(), null, Arrays.asList());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		res.sendRedirect("/");
			return;
			
	}
	
	@RequestMapping("unexpected")
	@ResponseBody
	public String error()
	{
		return "An unexpected error has occured, please try again";
	}
	@RequestMapping("passwordchange")
	public String reset_password_email()
	{
		String username= getCurrentUser();
		User user= reppo.findByUsername(username);
		String email= user.getEmail();
		if(email==null||email.equals(""))
			return "getemail.jsp";
		String token= user.getToken();
		int id= user.getId();
		String link= "https://localhost/resetpassword?id=" + user.getId() + "&token="+ user.getToken()+ "&email="+ email;
		String email_message= "<a href=\""+ link + "\">Click Here</a>";
		Mailer.send(email, "Password Reset", email_message);

		return "checkemail.jsp";
	}
	
	@RequestMapping("resetpassword")
	public void resetpasswordpage(@RequestParam("id") int id, @RequestParam("token") String token, @RequestParam("email") String email, HttpServletResponse res, HttpServletRequest req) throws IOException
	{
		User user= reppo.findByToken(token);
		int expected_id= user.getId();
		if(id!=expected_id)
		{
			res.sendRedirect("/unexpected");
			return ;
		}
		Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, Arrays.asList());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		settoken(user.getUsername());
		res.sendRedirect("/newpassword");
		return;
		
	}
	
	@RequestMapping("/newpassword")
	public String change()
	{
		return "password.jsp";
	}
	
	@RequestMapping("password")
	public void changepassword(@RequestParam("password") String pass, @RequestParam("password2") String pass2, HttpSession session, HttpServletResponse res, HttpServletRequest req) throws IOException, ServletException
	{
		if(!pass.equals(pass2))
		{
			session.setAttribute("passerror", "Passwords Do not match");
			res.sendRedirect("newpassword");
			return;
		}
		if(PasswordCheck.validPassword(pass).length()!=0)
		{
			String message=PasswordCheck.validPassword(pass);
			
			session.setAttribute("passerror", message);
			res.sendRedirect("newpassword");
			return;
			
		}
		String username= getCurrentUser();
		User current= reppo.findByUsername(username);
		current.setPassword(encoder.encode(pass));
		reppo.save(current);
		//logoutuser(req);
		authWithHttpServletRequest(req, username, pass);
		res.sendRedirect("/");
		return;
		
	}
	@RequestMapping("logout-success")
	public String logout()
	{
		return "login.jsp";
	}
	
	@RequestMapping("/forgot")
	public String forgot()
	{
		return "getuser.jsp";
	}
	
	@RequestMapping("user")
	public void forgotpassword(@RequestParam("username") String username, HttpSession session, HttpServletResponse res) throws IOException
	{
		User user=reppo.findByUsername(username);
		if(user== null)
		{
			session.setAttribute("usererror", "User does not exist");
			res.sendRedirect("forgot");
			return;
		}
		if(user.getEmail()== null)
		{
			session.setAttribute("usererror", "Email for User does not exist");
			res.sendRedirect("forgot");
			return;
		}
		String link= "https://localhost/resetpassword?id=" + user.getId() + "&token="+ user.getToken()+ "&email="+ user.getEmail();
		String email_message= "<a href=\""+ link + "\">Click Here</a>";
		Mailer.send(user.getEmail(), "Password Reset", email_message);
		res.sendRedirect("/inbox");
		return;
		
	}
	
	public void authWithHttpServletRequest(HttpServletRequest request, String username, String password) {
	    try {
	        request.login(username, password);
	    } catch (ServletException e) {
	        System.out.println(e);
	    }
	}
	public void settoken(String username)
	{
		String token= TokenGenerator.generate();
		User user = reppo.findByToken(token);
		User user2= reppo.findByUsername(username);
		if(user2!=null)
		{
			while(user!=null)
			{
				token= TokenGenerator.generate();
				user = reppo.findByToken(token);
			}
			user2.setToken(token);
			reppo.save(user2);
		}
	}
	public String getCurrentUser()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
		return authentication.getName();
	}
	public String getCurrentPass()
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
		return authentication.getCredentials().toString();
	}
	
	public void logoutuser(HttpServletRequest req) throws ServletException
	{
		req.logout();
	}

}
