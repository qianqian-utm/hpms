package com.hpms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.hpms.model.Transaction;
import com.hpms.service.TransactionService;

@Controller
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/transaction_record")
    public ModelAndView transactionRecord(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (isUserLoggedIn(session)) {
            ModelAndView mv = new ModelAndView("layout");
            mv.addObject("currentPage", "transaction_record");
            request.setAttribute("title", "Transaction Records");
            request.setAttribute("content", "transaction_table"); 
            mv.addObject("transactions", transactionService.getAllTransactions());
            return mv;
        }
        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/change_status/{id}")
    public ModelAndView changeTransactionStatus(@PathVariable Long id, @ModelAttribute("status") String status, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isUserLoggedIn(session)) {
            return new ModelAndView("redirect:/login");
        }

        try {
            transactionService.changeTransactionStatus(id, status);
            redirectAttributes.addFlashAttribute("successMessage", "Transaction status updated successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return new ModelAndView("redirect:/transaction_record");
    }

    private boolean isUserLoggedIn(HttpSession session) {
        return session.getAttribute("loggedUser") != null;
    }
}
