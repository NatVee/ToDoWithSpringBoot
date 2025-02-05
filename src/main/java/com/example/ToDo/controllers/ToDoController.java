package com.example.ToDo.controllers;

import com.example.ToDo.model.ToDoItem;
import com.example.ToDo.repositories.ToDoItemRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ToDoController implements CommandLineRunner {

    private final ToDoItemRepository toDoItemRepository;

    public ToDoController(ToDoItemRepository toDoItemRepository) {
        this.toDoItemRepository = toDoItemRepository;
    }

    @GetMapping("/")
    public String index(Model model){

        List<ToDoItem> toDoItems = toDoItemRepository.findAll();
        model.addAttribute("toDoItems", toDoItems);
        model.addAttribute("newToDo", new ToDoItem());
        return "index";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("newToDo") ToDoItem newToDo){
        toDoItemRepository.save(newToDo);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        toDoItemRepository.deleteById(id);
        return "redirect:/";
    }

    @PostMapping("/removeAll")
    public String removeAll(){
        toDoItemRepository.deleteAll();
        return "redirect:/";
    }

    @PostMapping("/search")
    public String search(@RequestParam("searchTerm") String searchTerm, Model model){
        List<ToDoItem> allItems = toDoItemRepository.findAll();
        List<ToDoItem> searchedItems = new ArrayList<>();
        for (ToDoItem toDoItem : allItems) {
            if (toDoItem.getTitle().toLowerCase().contains(searchTerm.toLowerCase())) {
                searchedItems.add(toDoItem);
            }
        }
        model.addAttribute("toDoItems", searchedItems);
        model.addAttribute("newToDo", new ToDoItem());

        return "index";
    }

    @Override
    public void run(String... args) throws Exception {
        toDoItemRepository.save(new ToDoItem("Item 1"));
        toDoItemRepository.save(new ToDoItem("Item 2"));
        System.out.println("Data loaded successfully");
    }
}

