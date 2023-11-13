
package com.Tienda.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class IndexController {
    //Se deja la url vacía pues toma el nombre Index
    @RequestMapping("/")
    public String page(Model model) {
        model.addAttribute("attribute", "value");
        //Retorna la vista llamada Index que está en templates.
        return "index";
    }
    
}
