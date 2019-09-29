package net.kleinschmager.dhbw.tfe16.painground.rest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class SimpleController {

   @GetMapping("/greeting")
   public String getString()
   {
      return "{ \"hello\": \"text\"}";
   }
}
