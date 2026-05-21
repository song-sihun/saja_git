package org.example.springmvc.service;

import org.example.springmvc.domain.Item;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    public List<Item> findAllItems() {
        List<Item> items = new ArrayList<>();
        Item item = new Item();
        item.setName("tset");
        item.setDescription("tset");
        item.setPrice(1000.0);
        item.setQuantity(10);
        items.add(item);
        return items;
    }
}
