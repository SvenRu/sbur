package com.thehecklers.sburrestdemo.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.thehecklers.sburrestdemo.models.Coffee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestApiDemoController {

    private List<Coffee> coffees = new ArrayList<>();

    public RestApiDemoController() {
        coffees.addAll(List.of(new Coffee("Café Cereza"), new Coffee("Café Ganador"), new Coffee("Café Lareno"),
                new Coffee("Café Tres Pontas")));
    }

    @GetMapping("/coffees")
    public Iterable<Coffee> getCoffees() {
        return coffees;
    }

    @GetMapping("/coffees/{id}")
    public Optional<Coffee> getCoffeeById(@PathVariable String id) {

        for (Coffee c : coffees) {

            if (c.getId().equals(id)) {
                return Optional.of(c);
            }
        }

        return Optional.empty();
    }

    @PostMapping("/coffees")
    public Coffee postCoffee(@RequestBody Coffee coffee) {

        Coffee newCoffee = new Coffee(coffee.getName());
        coffees.add(newCoffee);

        return newCoffee;
    }

    @PutMapping("/coffees/{id}")
    public ResponseEntity<Coffee> putCoffee(@RequestBody Coffee coffee, @PathVariable String id) {

        int index = -1;
        Coffee updatedCoffee = null;

        for (Coffee c : coffees) {
            if (c.getId().equals(id)) {
                index = coffees.indexOf(c);
                updatedCoffee = new Coffee(id, coffee.getName());
                coffees.set(index, updatedCoffee);
            }
        }
        return (index == -1) ? new ResponseEntity(postCoffee(coffee), HttpStatus.CREATED)
                : new ResponseEntity(updatedCoffee, HttpStatus.OK);
    }

    @DeleteMapping("/coffees/{id}")
    public void deleteCoffee(@PathVariable String id) {

        coffees.removeIf(c -> c.getId().equals(id));

    }

}
