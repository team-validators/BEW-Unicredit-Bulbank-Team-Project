package org.unicredit.validationapp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.unicredit.validationapp.domain.view_models.IbanInformation;
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
                                              @ModelAttribute("iban-object") IbanInformation iban) {
        modelAndView.setViewName("iban_validation");
        modelAndView.addObject("iban_object", iban);
        return modelAndView;
    }

    @PostMapping
    public String postIbanValidationPage(@RequestParam(name = "iban_code") String ibanCode,
                                         RedirectAttributes redirectAttributes) {
        IbanInformation iban = this.validationService.ibanIsValid(ibanCode);

        redirectAttributes.addFlashAttribute("iban-object", iban);
        return "redirect:/iban-validation";
    }
}
