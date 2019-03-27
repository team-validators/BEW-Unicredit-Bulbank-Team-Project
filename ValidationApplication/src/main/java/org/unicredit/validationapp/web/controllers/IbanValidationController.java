package org.unicredit.validationapp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.unicredit.validationapp.domain.Iban;
import org.unicredit.validationapp.service.ValidationService;

@Controller
@RequestMapping("/iban-validation")
public class IbanValidationController {
    private ValidationService validationService;

    @Autowired
    public IbanValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @GetMapping
    public ModelAndView getIbanValidationPage(ModelAndView modelAndView,
                                              @ModelAttribute("validationResult") Iban iban) { //TODO can't transport booleans
        modelAndView.setViewName("iban_validation");
        modelAndView.addObject("validation_result", iban);
        return modelAndView;
    }

    @PostMapping
    public String postIbanValidationPage(@RequestParam(name = "iban_code") String ibanCode,
                                         RedirectAttributes redirectAttributes,
                                         Iban iban) {
        Boolean isValidIban = this.validationService.ibanIsValid(ibanCode);
        iban.setCode(ibanCode);
        iban.setValid(isValidIban);

        redirectAttributes.addFlashAttribute("validationResult", iban);
        return "redirect:/iban-validation";
    }
}
