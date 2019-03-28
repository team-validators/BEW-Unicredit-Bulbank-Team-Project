package org.unicredit.validationapp.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.unicredit.validationapp.domain.view_models.PersonalIdInformation;
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
                                                    @ModelAttribute("personalIdInformation") PersonalIdInformation personalId) {
        modelAndView.setViewName("personal_id_validation");
        modelAndView.addObject("personal_id_information", personalId);
        return modelAndView;
    }

    @PostMapping
    public String postPersonalIdValidationPage(@RequestParam(name = "personal_id_number") String personalIdCode,
                                               RedirectAttributes redirectAttributes,
                                               PersonalIdInformation personalId) {
        PersonalIdInformation personalIdInformation = this.validationService.personalIdIsValid(personalIdCode);

        redirectAttributes.addFlashAttribute("personalIdInformation", personalIdInformation);
        return "redirect:/personal-id-validation";
    }
}
