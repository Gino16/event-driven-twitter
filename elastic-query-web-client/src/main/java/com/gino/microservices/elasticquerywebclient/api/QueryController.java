package com.gino.microservices.elasticquerywebclient.api;

import com.gino.microservices.elasticquerywebclient.model.ElasticQueryWebClientRequestModel;
import com.gino.microservices.elasticquerywebclient.model.ElasticQueryWebClientResponseModel;
import java.util.ArrayList;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class QueryController {

  @GetMapping("")
  public String index() {
    return "index";
  }

  @GetMapping("/error")
  public String error() {
    return "error";
  }

  @GetMapping("/home")
  public String home(Model model) {
    model.addAttribute("elasticQueryWebClientRequestModel",
        ElasticQueryWebClientRequestModel.builder().build());
    return "home";
  }

  @PostMapping("/query-by-text")
  public String queryByText(@Valid ElasticQueryWebClientRequestModel requestModel, Model model) {
    var responseModels = new ArrayList<>();
    responseModels.add(
        ElasticQueryWebClientResponseModel.builder()
            .id("1")
            .text(requestModel.getText())
            .build()
    );
    model.addAttribute("elasticQueryWebClientResponseModel", responseModels);
    model.addAttribute("searchText", requestModel.getText());
    model.addAttribute("elasticQueryWebClientRequestModel",
        ElasticQueryWebClientRequestModel.builder().build());
    return "home";
  }
}
