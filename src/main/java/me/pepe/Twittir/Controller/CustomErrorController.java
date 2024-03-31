package me.pepe.Twittir.Controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController  {
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object errorMessage = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        if (Integer.valueOf(status.toString()) == 500) {
        	errorMessage = "Internal Server Error.";
        }
        if (status != null && status instanceof Integer) {
            int statusCode = (Integer) status;
            model.addAttribute("errorCode", statusCode);
            model.addAttribute("errorMessage", errorMessage);
        } else {
            model.addAttribute("errorCode", -1);
            model.addAttribute("errorMessage", "");
        }
        return "error.html";
    }
}