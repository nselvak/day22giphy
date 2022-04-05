package nus.iss.day22.giphy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import nus.iss.day22.giphy.service.SearchService;

@Controller
@RequestMapping(path="/search")
public class GiphyController {

    @Autowired 
    private SearchService svc;

    @GetMapping
    public String search ( @RequestParam(name="query", required=true) String query,
                            @RequestParam(name="limit", defaultValue = "10") Integer limit, 
                            @RequestParam(name="rating" , defaultValue = "pg") String rating, 
                            Model model) {
        
        List<String> last = svc.searchGiphy(query, limit, rating);

        if (last.isEmpty()) {
            return "error";
        }

        model.addAttribute("list", last);

        return "SearchResult";

    }
    
}
