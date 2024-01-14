package com.example.shopmaven.ok;

import com.example.shopmaven.Items;
import com.example.shopmaven.Orders;
import com.example.shopmaven.repo.ItemRepository;
import com.example.shopmaven.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;


import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;



    @GetMapping("/")
    public String home(Model model) {
        List<Items> items = itemRepository.findAll();
        model.addAttribute("itm", items);
        return "home";
    }

    @GetMapping("/sort-newest")
    public String sortnewest(Model model) {
        List<Items> items = itemRepository.findAll(Sort.by(Sort.Order.asc("id")));
        model.addAttribute("itm", items);
        return "home";
    }

    @GetMapping("/sort-highlow")
    public String sorthighlow(Model model) {
        List<Items> items = itemRepository.findAll(Sort.by(Sort.Order.desc("price")));
        model.addAttribute("itm", items);
        return "home";
    }

    @GetMapping("/sort-lowhigh")
    public String sortlowhigh(Model model) {
        List<Items> items = itemRepository.findAll(Sort.by(Sort.Order.asc("price")));
        model.addAttribute("itm", items);
        return "home";
    }


    @GetMapping("/categories")
    public String categories() {
        return "categories";
    }

    @GetMapping("/t-shirts")
    public String tshirts(@RequestParam(name = "sort", defaultValue = "newest") String sort, Model model) {
        Sort sortCriteria = Sort.by(Sort.Order.asc("id")); // Сортировка по умолчанию (newest)

        if ("highlow".equals(sort)) {
            sortCriteria = Sort.by(Sort.Order.desc("price"));
        } else if ("lowhigh".equals(sort)) {
            sortCriteria = Sort.by(Sort.Order.asc("price"));
        }

        List<Items> items = itemRepository.findByCategories(1, sortCriteria);
        model.addAttribute("itm", items);
        return "home";
    }

    @GetMapping("/hoodies")
    public String hoodies(@RequestParam(name = "sort", defaultValue = "newest") String sort, Model model) {
        Sort sortCriteria = Sort.by(Sort.Order.asc("id")); // Сортировка по умолчанию (newest)

        if ("highlow".equals(sort)) {
            sortCriteria = Sort.by(Sort.Order.desc("price"));
        } else if ("lowhigh".equals(sort)) {
            sortCriteria = Sort.by(Sort.Order.asc("price"));
        }

        List<Items> items = itemRepository.findByCategories(2, sortCriteria);
        model.addAttribute("itm", items);
        return "home";
    }

    @GetMapping("/sneakers")
    public String sneakers(@RequestParam(name = "sort", defaultValue = "newest") String sort, Model model) {
        Sort sortCriteria = Sort.by(Sort.Order.asc("id")); // Сортировка по умолчанию (newest)

        if ("highlow".equals(sort)) {
            sortCriteria = Sort.by(Sort.Order.desc("price"));
        } else if ("lowhigh".equals(sort)) {
            sortCriteria = Sort.by(Sort.Order.asc("price"));
        }

        List<Items> items = itemRepository.findByCategories(3, sortCriteria);
        model.addAttribute("itm", items);
        return "home";
    }



    @GetMapping("/buy/{id}")
    public String buy(@PathVariable String id, Model model) {
        // Преобразовать строку в Long (если id является числовым)
        Long itemId = Long.valueOf(id);

        // Загрузить детали продукта на основе ID
        Items items = itemRepository.getReferenceById(itemId);

        // Передать информацию о продукте в представление
        model.addAttribute("itm", items);

        return "buy";
    }

    @PostMapping("/buy/{id}")
    public String buyPost(@PathVariable Long id,
                          @RequestParam("name") String name,
                          @RequestParam("email") String email,
                          @RequestParam("phone") String phone) {

        if (StringUtils.isEmpty(name) || StringUtils.isEmpty(email) || StringUtils.isEmpty(phone)) {
            // Одно или несколько полей не заполнены
            // Вернуть пользователя на страницу с сообщением об ошибке
            return "redirect:/buy/{id}?error=fields";
        }

        Items item = itemRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Item not found"));

        Orders orders = new Orders();
        orders.setName(name);
        orders.setEmail(email);
        orders.setPhone(phone);
        orders.setItem(item);

        orderRepository.save(orders);
        return "redirect:/";
    }






    @GetMapping("/basket")
    public String basket() {
        return "basket";
    }

    @GetMapping("/additem")
    public String additem(){
        return "additem";
    }

    @PostMapping("/additem")
    public String postAddItem(@RequestParam("title") String title,
                              @RequestParam("description") String description,
                              @RequestParam("price") int price,
                              @RequestParam("categories") int categories,
                              @RequestParam("file") MultipartFile file) throws IOException {
        Items items = new Items();
        items.setTitle(title);
        items.setDescription(description);
        items.setPrice(price);
        items.setCategories(categories);

        if (file != null && !file.isEmpty()) {
            byte[] imageBytes = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);
            items.setImage(base64Image);
        }



        itemRepository.save(items);
        return "redirect:/";
    }


}