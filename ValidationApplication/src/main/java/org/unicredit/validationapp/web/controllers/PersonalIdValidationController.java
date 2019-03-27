package org.unicredit.validationapp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.unicredit.validationapp.domain.Iban;
import org.unicredit.validationapp.domain.PersonalId;
import org.unicredit.validationapp.service.ValidationService;

@Controller
@RequestMapping("/personal-id-validation")
public class PersonalIdValidationController {
    private ValidationService validationService;

    @Autowired
    public PersonalIdValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @GetMapping
    public ModelAndView getPersonalIdValidationPage(ModelAndView modelAndView,
                                              @ModelAttribute("validationResult") PersonalId personalId) { //TODO can't transport booleans
        modelAndView.setViewName("personal_id_validation");
        modelAndView.addObject("validation_result", personalId);
        return modelAndView;
    }

    @PostMapping
    public String postPersonalIdValidationPage(@RequestParam(name = "personal_id_number") String personalIdCode,
                                         RedirectAttributes redirectAttributes,
                                         PersonalId personalId) {
        Boolean isValidPersonalId = this.validationService.personalIdIsValid(personalIdCode); //TODO
        personalId.setCode(personalIdCode);
        personalId.setValid(isValidPersonalId);

        redirectAttributes.addFlashAttribute("validationResult", personalId);
        return "redirect:/personal-id-validation";
    }
}
