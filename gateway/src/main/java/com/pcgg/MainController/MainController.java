package com.pcgg.MainController;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
public class MainController {

  @Autowired
  MainService mainService;

  @GetMapping
  public List<String> services() {
    System.out.println("gateway call Main");

    return mainService.getServices();

  }
}
